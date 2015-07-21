/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.ActorDSL._
import akka.testkit.{ EventFilter, TestProbe }
import scala.concurrent.duration.{ Duration, DurationInt, MILLISECONDS => Millis }

object RestaurantSpec {

  trait ChefNoRouter {
    this: Restaurant =>

    private val chefPrepareFoodDuration =
      Duration(context.system.settings.config.getDuration("restaurant.chef.prepare-food-duration", Millis), Millis)

    private val chefAccuracy = context.system.settings.config getInt "restaurant.chef.accuracy"

    override def createChef() =
      context.actorOf(Chef.props(chefPrepareFoodDuration, chefAccuracy), "chef")
  }
}

class RestaurantSpec extends BaseAkkaSpec {

  import RestaurantSpec._

  "Creating Restaurant" should {
    "result in logging a status message at debug" in {
      EventFilter.debug(pattern = ".*[Oo]pen.*", occurrences = 1) intercept {
        actor(new Restaurant(Int.MaxValue) with ChefNoRouter)
      }
    }
    "result in creating a child actor with the name 'chef'" in {
      actor("create-chef")(new Restaurant(Int.MaxValue) with ChefNoRouter)
      TestProbe().expectActor("/user/create-chef/chef")
    }
    "result in creating a child actor with the name 'waiter'" in {
      actor("create-waiter")(new Restaurant(Int.MaxValue) with ChefNoRouter)
      TestProbe().expectActor("/user/create-waiter/waiter")
    }
  }

  "Sending CreateGuest to Restaurant" should {
    "result in creating a Guest" in {
      val restaurant = actor("create-guest")(new Restaurant(Int.MaxValue) with ChefNoRouter)
      restaurant ! Restaurant.CreateGuest(Food.Akkacore, Int.MaxValue)
      TestProbe().expectActor("/user/create-guest/$*")
    }
  }

  "Sending ApproveFood to Restaurant" should {
    "result in forwarding PrepareFood to Chef if foodLimit not yet reached" in {
      val chef = TestProbe()
      val restaurant =
        actor(new Restaurant(Int.MaxValue) {
          override def createChef() = chef.ref
        })
      restaurant ! Restaurant.ApproveFood(Food.Akkacore, system.deadLetters)
      chef.expectMsg(Chef.PrepareFood(Food.Akkacore, system.deadLetters))
    }
    "result in logging a status message at info if foodLimit reached" in {
      EventFilter.info(pattern = ".*[Ss]orry.*", occurrences = 1) intercept {
        val guest = actor(new Act {})
        val restaurant = actor(new Restaurant(0) with ChefNoRouter)
        restaurant ! Restaurant.ApproveFood(Food.Akkacore, guest)
      }
    }
    "result in stopping the Guest if foodLimit reached" in {
      val probe = TestProbe()
      val guest = actor(new Act {})
      probe.watch(guest)
      val restaurant = actor(new Restaurant(0) with ChefNoRouter)
      restaurant ! Restaurant.ApproveFood(Food.Akkacore, guest)
      probe.expectTerminated(guest)
    }
  }

  "On termination of Guest, Restaurant" should {
    "remove the guest from the foodLimit bookkeping" in {
      val chef = TestProbe()
      val restaurant =
        actor(new Restaurant(Int.MaxValue) {
          override def createChef() = chef.ref
        })
      restaurant ! Restaurant.CreateGuest(Food.Akkacore, Int.MaxValue)
      val guest = chef.expectMsgPF() {
        case Chef.PrepareFood(Food.Akkacore, guest) => guest
      }
      chef.watch(guest)
      system.stop(guest)
      chef.expectTerminated(guest)
      chef.within(2 seconds) {
        chef.awaitAssert {
          restaurant ! Restaurant.ApproveFood(Food.Akkacore, guest)
          chef.expectMsgPF(100 milliseconds) {
            case Chef.PrepareFood(Food.Akkacore, `guest`) => ()
          }
        }
      }
    }
  }

  "On failure of Guest Restaurant" should {
    "stop it" in {
      val chef = TestProbe()
      val restaurant =
        actor(new Restaurant(Int.MaxValue) {
          override def createChef() = chef.ref
        })
      restaurant ! Restaurant.CreateGuest(Food.Akkacore, 0)
      val guest = chef.expectMsgPF() {
        case Chef.PrepareFood(Food.Akkacore, guest) => guest
      }
      chef.watch(guest)
      guest ! Waiter.FoodServed(Food.Akkacore)
      chef.expectTerminated(guest)
    }
  }

  "On failure of Waiter Restaurant" should {
    "restart it and resend PrepareFood to Chef" in {
      val chef = TestProbe()
      actor("resend-prepare-food")(new Restaurant(Int.MaxValue) {
        override def createChef() = chef.ref
        override def createWaiter() =
          actor(context, "waiter")(new Act {
            become { case _ => throw Waiter.FrustratedException(Food.Akkacore, system.deadLetters) }
          })
      })
      val waiter = TestProbe().expectActor("/user/resend-prepare-food/waiter")
      waiter ! "blow-up"
      chef.expectMsg(Chef.PrepareFood(Food.Akkacore, system.deadLetters))
    }
  }
}

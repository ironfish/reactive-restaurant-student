/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.ActorDSL._
import akka.testkit.{ EventFilter, TestProbe }
import scala.concurrent.duration.DurationInt

class RestaurantSpec extends BaseAkkaSpec {

  "Creating Restaurant" should {
    "result in logging a status message at debug" in {
      EventFilter.debug(pattern = ".*[Oo]pen.*", occurrences = 1) intercept {
        system.actorOf(Restaurant.props(Int.MaxValue))
      }
    }
    "result in creating a child actor with the name 'chef'" in {
      system.actorOf(Restaurant.props(Int.MaxValue), "create-chef")
      TestProbe().expectActor("/user/create-chef/chef")
    }
    "result in creating a child actor with the name 'waiter'" in {
      system.actorOf(Restaurant.props(Int.MaxValue), "create-waiter")
      TestProbe().expectActor("/user/create-waiter/waiter")
    }
  }

  "Sending CreateGuest to Restaurant" should {
    "result in creating a Guest" in {
      val restaurant = system.actorOf(Restaurant.props(Int.MaxValue), "create-guest")
      restaurant ! Restaurant.CreateGuest(Food.Akkacore)
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
        val restaurant = system.actorOf(Restaurant.props(0))
        restaurant ! Restaurant.ApproveFood(Food.Akkacore, guest)
      }
    }
    "result in stopping the Guest if foodLimit reached" in {
      val probe = TestProbe()
      val guest = actor(new Act {})
      probe.watch(guest)
      val restaurant = system.actorOf(Restaurant.props(0))
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
      restaurant ! Restaurant.CreateGuest(Food.Akkacore)
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
}

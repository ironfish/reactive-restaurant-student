/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.testkit.{ EventFilter, TestProbe }

class WaiterSpec extends BaseAkkaSpec {

  "Sending ServeFood to Waiter" should {
    "result in sending ApproveFood to Restaurant" in {
      val restaurant = TestProbe()
      val guest = TestProbe()
      implicit val _ = guest.ref
      val waiter = system.actorOf(Waiter.props(restaurant.ref, system.deadLetters, Int.MaxValue))
      waiter ! Waiter.ServeFood(Food.Akkacore)
      restaurant.expectMsg(Restaurant.ApproveFood(Food.Akkacore, guest.ref))
    }
  }

  "Sending Complaint to Waiter" should {
    "result in sending PrepareFood to Chef" in {
      val chef = TestProbe()
      val guest = TestProbe()
      implicit val _ = guest.ref
      val waiter = system.actorOf(Waiter.props(system.deadLetters, chef.ref, 1))
      waiter ! Waiter.Complaint(Food.Akkacore)
      chef.expectMsg(Chef.PrepareFood(Food.Akkacore, guest.ref))
    }
    "result in a FrustratedException if maxComplaintCount exceeded" in {
      val waiter = system.actorOf(Waiter.props(system.deadLetters, system.deadLetters, 0))
      EventFilter[Waiter.FrustratedException.type](occurrences = 1) intercept {
        waiter ! Waiter.Complaint(Food.Akkacore)
      }
    }
  }
}

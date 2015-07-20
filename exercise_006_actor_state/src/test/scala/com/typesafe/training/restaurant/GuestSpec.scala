/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.testkit.{ EventFilter, TestProbe }

class GuestSpec extends BaseAkkaSpec {

  "Sending FoodServed to Guest" should {
    "result in increasing the foodCount and log a status message at info" in {
      val guest = system.actorOf(Guest.props(system.deadLetters, Food.Akkacore))
      EventFilter.info(pattern = """.*[Ee]njoy.*1\.*""", occurrences = 1) intercept {
        guest ! Waiter.FoodServed(Food.Akkacore)
      }
    }
  }

  "Sending FoodFinished to Guest" should {
    "result in sending ServeFood to Waiter" in {
      val waiter = TestProbe()
      val guest = system.actorOf(Guest.props(waiter.ref, Food.Akkacore))
      guest ! Guest.FoodFinished
      waiter.expectMsg(Waiter.ServeFood(Food.Akkacore))
    }
  }
}

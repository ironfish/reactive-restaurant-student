/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.testkit.{ EventFilter, TestProbe }
import scala.concurrent.duration.DurationInt

class GuestSpec extends BaseAkkaSpec {

  "Sending FoodServed to Guest" should {
    "result in increasing the foodCount and log a status message at info" in {
      val guest = system.actorOf(Guest.props(system.deadLetters, Food.Akkacore, 100 milliseconds))
      EventFilter.info(pattern = """.*[Ee]njoy.*1\.*""", occurrences = 1) intercept {
        guest ! Waiter.FoodServed(Food.Akkacore)
      }
    }
    "result in sending ServeFood to Waiter after finishFoodDuration" in {
      val waiter = TestProbe()
      val guest = createGuest(waiter)
      waiter.within(50 milliseconds, 200 milliseconds) { // The timer is not extremely accurate, relax the timing constraints.
        guest ! Waiter.FoodServed(Food.Akkacore)
        waiter.expectMsg(Waiter.ServeFood(Food.Akkacore))
      }
    }
  }

  "Sending FoodFinished to Guest" should {
    "result in sending ServeFood to Waiter" in {
      val waiter = TestProbe()
      val guest = createGuest(waiter)
      guest ! Guest.FoodFinished
      waiter.expectMsg(Waiter.ServeFood(Food.Akkacore))
    }
  }

  def createGuest(waiter: TestProbe) = {
    val guest = system.actorOf(Guest.props(waiter.ref, Food.Akkacore, 100 milliseconds))
    waiter.expectMsg(Waiter.ServeFood(Food.Akkacore)) // Creating Guest immediately sends Waiter.ServeFood
    guest
  }
}

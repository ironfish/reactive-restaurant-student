/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.testkit.TestProbe
import scala.concurrent.duration.DurationInt

class ChefSpec extends BaseAkkaSpec {

  "Sending PrepareFood to Chef" should {
    "result in sending a FoodPrepared response after prepareFoodDuration" in {
      val sender = TestProbe()
      implicit val _ = sender.ref
      val chef = system.actorOf(Chef.props(100 milliseconds, 100))
      sender.within(50 milliseconds, 200 milliseconds) { // The time is not extremely accurate, so we relax the timing constraints.
        chef ! Chef.PrepareFood(Food.Akkacore, system.deadLetters)
        sender.expectMsg(Chef.FoodPrepared(Food.Akkacore, system.deadLetters))
      }
    }
    "result in sending a FoodPrepared response with a random Food for an inaccurate one" in {
      val waiter = TestProbe()
      val accuracy = 50
      val runs = 1000
      val chef = system.actorOf(Chef.props(0 milliseconds, accuracy))
      val guest = system.deadLetters
      var foods = List.empty[Food]
      for (_ <- 1 to runs) {
        implicit val _ = waiter.ref
        chef ! Chef.PrepareFood(Food.Akkacore, guest)
        foods +:= waiter.expectMsgPF() {
          case Chef.FoodPrepared(food, `guest`) => food
        }
      }
      val expectedCount = runs * accuracy / 100
      val variation = expectedCount / 10
      foods count (_ == Food.Akkacore) shouldEqual expectedCount +- variation
    }
  }
}

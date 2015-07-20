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
      val chef = system.actorOf(Chef.props(100 milliseconds))
      sender.within(50 milliseconds, 1000 milliseconds) { // busy is innccurate, so we relax the timing constraints.
        chef ! Chef.PrepareFood(Food.Akkacore, system.deadLetters)
        sender.expectMsg(Chef.FoodPrepared(Food.Akkacore, system.deadLetters))
      }
    }
  }
}

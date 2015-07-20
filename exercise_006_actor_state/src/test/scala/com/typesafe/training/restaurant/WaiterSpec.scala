/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.testkit.TestProbe

class WaiterSpec extends BaseAkkaSpec {

  "Sending ServeFood to Waiter" should {
    "result in sending a FoodServed response to sender" in {
      val sender = TestProbe()
      implicit val _ = sender.ref
      val waiter = system.actorOf(Waiter.props)
      waiter ! Waiter.ServeFood(Food.Akkacore)
      sender.expectMsg(Waiter.FoodServed(Food.Akkacore))
    }
  }
}

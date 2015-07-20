/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.testkit.TestProbe

class WaiterSpec extends BaseAkkaSpec {

  "Sending ServeFood to Waiter" should {
    "result in sending ApproveFood to Restaurant" in {
      val restaurant = TestProbe()
      val guest = TestProbe()
      implicit val _ = guest.ref
      val waiter = system.actorOf(Waiter.props(restaurant.ref))
      waiter ! Waiter.ServeFood(Food.Akkacore)
      restaurant.expectMsg(Restaurant.ApproveFood(Food.Akkacore, guest.ref))
    }
  }
}

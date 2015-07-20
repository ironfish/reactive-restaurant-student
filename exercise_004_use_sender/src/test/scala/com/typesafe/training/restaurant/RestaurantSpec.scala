/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.testkit.{ EventFilter, TestProbe }

class RestaurantSpec extends BaseAkkaSpec {

  "Creating Restaurant" should {
    "result in logging a status message at debug" in {
      EventFilter.debug(pattern = ".*[Oo]pen.*", occurrences = 1) intercept {
        system.actorOf(Restaurant.props)
      }
    }
  }

  "Sending a message to Restaurant" should {
    "result in sending a 'Food Prepared' message as response" in {
      val sender = TestProbe()
      implicit val _ = sender.ref
      val restaurant = system.actorOf(Restaurant.props)
      restaurant ! "Prepare Food"
      sender.expectMsgPF() { case message if message.toString matches ".*[Ff]ood.*" =>() }
    }
  }
}

/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.Props
import akka.testkit.EventFilter

class RestaurantSpec extends BaseAkkaSpec {

  "Sending a message to Restaurant" should {
    "result in logging a 'Cooking Food' message at info" in {
      val restaurant = system.actorOf(Props(new Restaurant))
      EventFilter.info(pattern = ".*[Ff]ood.*", occurrences = 1) intercept {
        restaurant ! "Prepare Food!"
      }
    }
  }
}

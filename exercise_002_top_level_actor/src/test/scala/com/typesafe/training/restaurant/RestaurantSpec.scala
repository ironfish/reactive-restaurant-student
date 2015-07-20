/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.testkit.EventFilter

class restaurantSpec extends BaseAkkaSpec {

  "Creating Restaurant" should {
    "result in logging a status message at debug" in {
      EventFilter.debug(pattern = ".*[Oo]pen.*", occurrences = 1) intercept {
        system.actorOf(Restaurant.props)
      }
    }
  }

  "Sending a message to Restaurant" should {
    "result in logging a 'Food Prepared' message at info" in {
      val restaurant = system.actorOf(Restaurant.props)
      EventFilter.info(pattern = ".*[Ff]ood.*", occurrences = 1) intercept {
        restaurant ! "Prepare Food!"
      }
    }
  }
}

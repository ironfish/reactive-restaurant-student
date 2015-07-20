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
    "result in creating a child actor with the name 'chef'" in {
      system.actorOf(Restaurant.props, "create-chef")
      TestProbe().expectActor("/user/create-chef/chef")
    }
    "result in creating a child actor with the name 'waiter'" in {
      system.actorOf(Restaurant.props, "create-waiter")
      TestProbe().expectActor("/user/create-waiter/waiter")
    }
  }

  "Sending CreateGuest to Restaurant" should {
    "result in creating a Guest" in {
      val restaurant = system.actorOf(Restaurant.props, "create-guest")
      restaurant ! Restaurant.CreateGuest(Food.Akkacore)
      TestProbe().expectActor("/user/create-guest/$*")
    }
  }
}

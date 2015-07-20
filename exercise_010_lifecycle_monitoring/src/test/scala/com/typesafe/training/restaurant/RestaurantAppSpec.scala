/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.testkit.TestProbe

class RestaurantAppSpec extends BaseAkkaSpec {

  import RestaurantApp._

  "Calling argsToOpts" should {
    "return the correct opts for the given args" in {
      argsToOpts(List("a=1", "b", "-Dc=2")) should ===(Map("a" -> "1", "-Dc" -> "2"))
    }
  }

  "Calling applySystemProperties" should {
    "apply the system properties for the given opts" in {
      System.setProperty("c", "")
      applySystemProperties(Map("a" -> "1", "-Dc" -> "2"))
      System.getProperty("c") should ===("2")
    }
  }

  "Creating RestaurantApp" should {
    "result in creating a top-level actor named 'restaurant'" in {
      new RestaurantApp(system)
      TestProbe().expectActor("/user/restaurant")
    }
  }

  "Calling createGuest" should {
    "result in sending CreateGuest to Restaurant count number of times" in {
      val probe = TestProbe()
      new RestaurantApp(system) {
        createGuest(2, Food.Akkacore, Int.MaxValue)
        override def createRestaurant() = probe.ref
      }
      probe.receiveN(2) shouldEqual List.fill(2)(Restaurant.CreateGuest(Food.Akkacore))
    }
  }
}

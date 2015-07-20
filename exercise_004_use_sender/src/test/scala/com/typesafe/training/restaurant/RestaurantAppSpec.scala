/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.ActorDSL._
import akka.event.Logging.Info
import akka.testkit.{ EventFilter, TestProbe }

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
    "result in sending a message to Restaurant" in {
      val restaurant = TestProbe()
      new RestaurantApp(system) {
        override def createRestaurant() = restaurant.ref
      }
      restaurant.expectMsgType[Any]
    }
    "result in logging Restaurant's response at info" in {
      EventFilter.custom({ case Info(source, _, "response") => source contains "$" }, 1) intercept {
        new RestaurantApp(system) {
          override def createRestaurant() = actor(new Act { become { case _ => sender() ! "response" } })
        }
      }
    }
  }
}
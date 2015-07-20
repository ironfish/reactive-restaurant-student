/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, Props }

object Waiter {

  case class ServeFood(food: Food)
  case class FoodServed(food: Food)

  def props: Props =
    Props(new Waiter)
}

class Waiter extends Actor {

  import Waiter._

  override def receive: Receive = {
    case ServeFood(food) => sender() ! FoodServed(food)
  }
}

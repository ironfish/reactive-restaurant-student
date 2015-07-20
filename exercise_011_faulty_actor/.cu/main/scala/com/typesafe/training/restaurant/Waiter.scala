/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorRef, Props }

object Waiter {

  case class ServeFood(food: Food)
  case class FoodServed(food: Food)

  def props(restaurant: ActorRef): Props =
    Props(new Waiter(restaurant))
}

class Waiter(restaurant: ActorRef) extends Actor {

  import Waiter._

  override def receive: Receive = {
    case ServeFood(food)                   => restaurant ! Restaurant.ApproveFood(food, sender())
    case Chef.FoodPrepared(food, guest) => guest ! FoodServed(food)
  }
}


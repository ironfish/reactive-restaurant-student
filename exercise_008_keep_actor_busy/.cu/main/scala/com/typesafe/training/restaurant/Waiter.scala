/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorRef, Props }

object Waiter {

  case class ServeFood(food: Food)
  case class FoodServed(food: Food)

  def props(chef: ActorRef): Props =
    Props(new Waiter(chef))
}

class Waiter(chef: ActorRef) extends Actor {

  import Waiter._

  override def receive: Receive = {
    case ServeFood(food)                   => chef ! Chef.PrepareFood(food, sender())
    case Chef.FoodPrepared(food, guest) => guest ! FoodServed(food)
  }
}


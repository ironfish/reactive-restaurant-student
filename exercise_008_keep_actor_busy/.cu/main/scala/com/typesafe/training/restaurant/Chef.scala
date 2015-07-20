/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorRef, Props }
import scala.concurrent.duration.FiniteDuration

object Chef {

  case class PrepareFood(food: Food, guest: ActorRef)
  case class FoodPrepared(food: Food, guest: ActorRef)

  def props(prepareFoodDuration: FiniteDuration): Props =
    Props(new Chef(prepareFoodDuration))
}

class Chef(prepareFoodDuration: FiniteDuration) extends Actor {

  import Chef._

  override def receive: Receive = {
    case PrepareFood(food, guest) =>
      busy(prepareFoodDuration)
      sender() ! FoodPrepared(food, guest)
  }
}

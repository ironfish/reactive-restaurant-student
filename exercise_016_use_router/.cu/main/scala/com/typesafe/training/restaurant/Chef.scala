/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorRef, Props }
import scala.concurrent.duration.FiniteDuration
import scala.util.Random

object Chef {

  case class PrepareFood(food: Food, guest: ActorRef)
  case class FoodPrepared(food: Food, guest: ActorRef)

  def props(prepareFoodDuration: FiniteDuration, accuracy: Int): Props =
    Props(new Chef(prepareFoodDuration, accuracy))
}

class Chef(prepareFoodDuration: FiniteDuration, accuracy: Int) extends Actor {

  import Chef._

  override def receive: Receive = {
    case PrepareFood(food, guest) =>
      busy(prepareFoodDuration)
      sender() ! FoodPrepared(pickFood(food), guest)
  }

  private def pickFood(food: Food): Food =
    if (Random.nextInt(100) < accuracy)
      food
    else
      Food.anyOther(food)
}

/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorLogging, ActorRef, Props }
import scala.concurrent.duration.FiniteDuration

object Guest {

  case object FoodException extends IllegalStateException("Too much food!")
  case object FoodFinished

  def props(waiter: ActorRef, favoriteFood: Food, finishFoodDuration: FiniteDuration, foodLimit: Int): Props =
    Props(new Guest(waiter, favoriteFood, finishFoodDuration, foodLimit))
}

class Guest(waiter: ActorRef, favoriteFood: Food, finishFoodDuration: FiniteDuration, foodLimit: Int)
    extends Actor with ActorLogging {

  import Guest._
  import context.dispatcher

  private var foodCount = 0

  orderFavoriteFood()

  override def receive: Receive = {
    case Waiter.FoodServed(food) =>
      foodCount += 1
      log.info("Enjoying my {} yummy {}!", foodCount, food)
      context.system.scheduler.scheduleOnce(finishFoodDuration, self, FoodFinished)
    case FoodFinished if foodCount > foodLimit =>
      throw FoodException
    case FoodFinished =>
      orderFavoriteFood()
  }

  override def postStop(): Unit =
    log.info("Goodbye!")

  private def orderFavoriteFood(): Unit =
    waiter ! Waiter.ServeFood(favoriteFood)
}

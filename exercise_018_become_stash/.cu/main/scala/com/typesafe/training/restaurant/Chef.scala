/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorRef, Props, Stash }
import scala.concurrent.duration.FiniteDuration
import scala.util.Random

object Chef {

  case class PrepareFood(food: Food, guest: ActorRef)
  case class FoodPrepared(food: Food, guest: ActorRef)

  def props(prepareFoodDuration: FiniteDuration, accuracy: Int): Props =
    Props(new Chef(prepareFoodDuration, accuracy))
}

class Chef(prepareFoodDuration: FiniteDuration, accuracy: Int) extends Actor with Stash {

  import Chef._
  import context.dispatcher

  override def receive: Receive = 
    ready

  private def ready: Receive = {
    case PrepareFood(food, guest) =>
    context.system.scheduler.scheduleOnce(prepareFoodDuration, self, FoodPrepared(pickFood(food), guest))
    context.become(busy(sender()))
  }

  private def busy(waiter: ActorRef): Receive = {
    case foodPrepared: FoodPrepared =>
      waiter ! foodPrepared
      unstashAll()
      context.become(ready)
    case _ =>
      stash()
  }

  private def pickFood(food: Food): Food =
    if (Random.nextInt(100) < accuracy)
      food
    else
      Food.anyOther(food)
}


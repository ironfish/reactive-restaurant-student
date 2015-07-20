/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorLogging, ActorRef, Props }

object Guest {

  case object FoodFinished

  def props(waiter: ActorRef, favoriteFood: Food): Props =
    Props(new Guest(waiter, favoriteFood))
}

class Guest(waiter: ActorRef, favoriteFood: Food) extends Actor with ActorLogging {

  import Guest._

  private var foodCount = 0

  override def receive: Receive = {
    case Waiter.FoodServed(food) =>
      foodCount += 1
      log.info(s"Enjoying my $foodCount yummy $food!")
    case FoodFinished =>
      waiter ! Waiter.ServeFood(favoriteFood)
  }
}

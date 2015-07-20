/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorLogging, ActorRef, Props }
import scala.concurrent.duration.{ Duration, MILLISECONDS => Millis }

object Restaurant {

  case class CreateGuest(favoriteFood: Food)

  def props: Props =
    Props(new Restaurant)
}

class Restaurant extends Actor with ActorLogging {

  import Restaurant._

  private val chefPrepareFoodDuration =
    Duration(context.system.settings.config.getDuration("restaurant.chef.prepare-food-duration", Millis), Millis)
  private val guestFinishFoodDuration =
    Duration(context.system.settings.config.getDuration("restaurant.guest.finish-food-duration", Millis), Millis)

  private val chef = createChef()
  private val waiter = createWaiter()

  log.debug("Restaurant Open")

  override def receive: Receive = {
    case CreateGuest(favoriteFood) => createGuest(favoriteFood)
  }

  protected def createChef(): ActorRef =
    context.actorOf(Chef.props(chefPrepareFoodDuration), "chef")

  protected def createWaiter(): ActorRef =
    context.actorOf(Waiter.props(chef), "waiter")

  protected def createGuest(favoriteFood: Food): ActorRef =
    context.actorOf(Guest.props(waiter, favoriteFood, guestFinishFoodDuration))
}

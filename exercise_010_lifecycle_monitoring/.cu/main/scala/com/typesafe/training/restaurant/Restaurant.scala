/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorLogging, ActorRef, Props, Terminated }
import scala.concurrent.duration.{ Duration, MILLISECONDS => Millis }

object Restaurant {

  case class CreateGuest(favoriteFood: Food)
  case class ApproveFood(food: Food, guest: ActorRef)

  def props(foodLimit: Int): Props =
    Props(new Restaurant(foodLimit))
}

class Restaurant(foodLimit: Int) extends Actor with ActorLogging {

  import Restaurant._

  private val chefPrepareFoodDuration =
    Duration(context.system.settings.config.getDuration("restaurant.chef.prepare-food-duration", Millis), Millis)
  private val guestFinishFoodDuration =
    Duration(context.system.settings.config.getDuration("restaurant.guest.finish-food-duration", Millis), Millis)

  private val chef = createChef()
  private val waiter = createWaiter()

  private var guestFoodLimit = Map.empty[ActorRef, Int] withDefaultValue 0

  log.debug("Restaurant Open")

  override def receive: Receive = {
    case CreateGuest(favoriteFood) =>
      val guest = createGuest(favoriteFood)
      context.watch(guest)
    case ApproveFood(food, guest) if guestFoodLimit(guest) < foodLimit =>
      guestFoodLimit += guest -> (guestFoodLimit(guest) + 1)
      chef forward Chef.PrepareFood(food, guest)
    case ApproveFood(food, guest) =>
      log.info("Sorry, {}, but you have reached your limit.", guest)
      context.stop(guest)
    case Terminated(guest) =>
      log.info("Thanks, {}, for being our guest!", guest)
      guestFoodLimit -= guest
  }

  protected def createChef(): ActorRef =
    context.actorOf(Chef.props(chefPrepareFoodDuration), "chef")

  protected def createWaiter(): ActorRef =
    context.actorOf(Waiter.props(self), "waiter")

  protected def createGuest(favoriteFood: Food): ActorRef =
    context.actorOf(Guest.props(waiter, favoriteFood, guestFinishFoodDuration))
}


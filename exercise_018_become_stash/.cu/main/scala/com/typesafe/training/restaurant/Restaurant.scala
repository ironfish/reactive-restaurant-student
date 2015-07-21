/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy, Terminated }
import akka.routing.FromConfig
import scala.concurrent.duration.{ Duration, MILLISECONDS => Millis }

object Restaurant {

  case class CreateGuest(favoriteFood: Food, foodLimit: Int)
  case class ApproveFood(food: Food, guest: ActorRef)

  def props(foodLimit: Int): Props =
    Props(new Restaurant(foodLimit))
}

class Restaurant(foodLimit: Int) extends Actor with ActorLogging {

  import Restaurant._

  override val supervisorStrategy: SupervisorStrategy = {
    val decider: SupervisorStrategy.Decider = {
      case Guest.FoodException =>
        SupervisorStrategy.Stop
      case Waiter.FrustratedException(food, guest) =>
        chef.tell(Chef.PrepareFood(food, guest), sender())
        SupervisorStrategy.Restart
    }
    OneForOneStrategy()(decider orElse super.supervisorStrategy.decider)
  }

  private val chefPrepareFoodDuration =
    Duration(context.system.settings.config.getDuration("restaurant.chef.prepare-food-duration", Millis), Millis)
  private val chefAccuracy = context.system.settings.config getInt "restaurant.chef.accuracy"
  private val waiterMaxComplaintCount = context.system.settings.config getInt "restaurant.waiter.max-complaint-count"
  private val guestFinishFoodDuration =
    Duration(context.system.settings.config.getDuration("restaurant.guest.finish-food-duration", Millis), Millis)

  private val chef = createChef()
  private val waiter = createWaiter()

  private var guestFoodLimit = Map.empty[ActorRef, Int] withDefaultValue 0

  log.debug("Restaurant Open")

  override def receive: Receive = {
    case CreateGuest(favoriteFood, foodLimit) =>
      val guest = createGuest(favoriteFood, foodLimit)
      context.watch(guest)
    case ApproveFood(food, guest) if guestFoodLimit(guest) < foodLimit =>
      guestFoodLimit += guest -> (guestFoodLimit(guest) + 1)
      chef forward Chef.PrepareFood(food, guest)
    case ApproveFood(food, guest) =>
      log.info("Sorry, {}, but you have reached your limit.", guest.path.name)
      context.stop(guest)
    case Terminated(guest) =>
      log.info("Thanks, {}, for being our guest!", guest.path.name)
      guestFoodLimit -= guest
  }

  protected def createChef(): ActorRef =
    context.actorOf(FromConfig.props(Chef.props(chefPrepareFoodDuration, chefAccuracy)), "chef")

  protected def createWaiter(): ActorRef =
    context.actorOf(Waiter.props(self, chef, waiterMaxComplaintCount), "waiter")

  protected def createGuest(favoriteFood: Food, foodLimit: Int): ActorRef =
    context.actorOf(Guest.props(waiter, favoriteFood, guestFinishFoodDuration, foodLimit))
}


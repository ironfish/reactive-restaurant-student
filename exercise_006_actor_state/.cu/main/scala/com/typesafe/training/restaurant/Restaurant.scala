/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorLogging, ActorRef, Props }

object Restaurant {

  case class CreateGuest(favoriteFood: Food)

  def props: Props =
    Props(new Restaurant)
}

class Restaurant extends Actor with ActorLogging {

  import Restaurant._

  private val waiter = createWaiter()

  log.debug("Restaurant Open")

  override def receive: Receive = {
    case CreateGuest(favoriteFood) => createGuest(favoriteFood)
  }

  protected def createWaiter(): ActorRef =
    context.actorOf(Waiter.props, "waiter")

  protected def createGuest(favoriteFood: Food): ActorRef =
    context.actorOf(Guest.props(waiter, favoriteFood))
}

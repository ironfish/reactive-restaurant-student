/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorLogging, ActorRef, Props }

object Restaurant {

  case object CreateGuest

  def props: Props =
    Props(new Restaurant)
}

class Restaurant extends Actor with ActorLogging {

  import Restaurant._

  log.debug("Restaurant Open")

  override def receive: Receive = {
    case CreateGuest => createGuest()
  }

  protected def createGuest(): ActorRef =
    context.actorOf(Guest.props)
}

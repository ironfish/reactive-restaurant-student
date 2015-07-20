/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorLogging, Props }

object Restaurant {

  def props: Props =
    Props(new Restaurant)
}

class Restaurant extends Actor with ActorLogging {

  log.debug("Restaurant Open")

  override def receive: Receive = {
    case _ => sender() ! "Food Prepared"
  }
}

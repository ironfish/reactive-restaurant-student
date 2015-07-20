/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, Props }

object Guest {

  def props: Props =
    Props(new Guest)
}

class Guest extends Actor {

  override def receive: Receive =
    Actor.emptyBehavior
}

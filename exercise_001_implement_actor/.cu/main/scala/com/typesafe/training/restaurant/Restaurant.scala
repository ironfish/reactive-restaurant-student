/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorLogging }

class Restaurant extends Actor with ActorLogging {

  override def receive: Receive = {
    case _ => log.info("Cooking Food")
  }
}

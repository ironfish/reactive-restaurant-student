/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ Actor, ActorRef, Props }

object Waiter {

  case class ServeFood(food: Food)
  case class FoodServed(food: Food)
  case class Complaint(food: Food)
  case object FrustratedException extends IllegalStateException("Too many complaints!")

  def props(restaurant: ActorRef, chef: ActorRef, maxComplaintCount: Int): Props =
    Props(new Waiter(restaurant, chef, maxComplaintCount))
}

class Waiter(restaurant: ActorRef, chef: ActorRef, maxComplaintCount: Int) extends Actor {

  import Waiter._

  private var complaintCount = 0

  override def receive: Receive = {
    case ServeFood(food) =>
      restaurant ! Restaurant.ApproveFood(food, sender())
    case Chef.FoodPrepared(food, guest) =>
      guest ! FoodServed(food)
    case Complaint(food) if complaintCount == maxComplaintCount =>
      throw FrustratedException
    case Complaint(food) =>
      complaintCount += 1
      chef ! Chef.PrepareFood(food, sender())
  }
}


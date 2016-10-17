/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging
import scala.annotation.tailrec
import scala.collection.breakOut
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn

object RestaurantApp {

  private val opt = """(\S+)=(\S+)""".r

  def main(args: Array[String]): Unit = {
    val opts = argsToOpts(args.toList)
    applySystemProperties(opts)
    val name = opts.getOrElse("name", "restaurant")

    val system = ActorSystem(s"$name-system")
    val restaurantApp = new RestaurantApp(system)
    restaurantApp.run()
  }

  private[restaurant] def argsToOpts(args: Seq[String]): Map[String, String] =
    args.collect { case opt(key, value) => key -> value }(breakOut)

  private[restaurant] def applySystemProperties(opts: Map[String, String]): Unit =
    for ((key, value) <- opts if key startsWith "-D")
      System.setProperty(key substring 2, value)
}

class RestaurantApp(system: ActorSystem) extends Terminal {

  private val log = Logging(system, getClass.getName)

  private val restaurant = createRestaurant()

  restaurant ! "Prepare Food"

  def run(): Unit = {
    log.warning(f"{} running%nEnter commands into the terminal, e.g. `q` or `quit`", getClass.getSimpleName)
    commandLoop()
    Await.ready(system.whenTerminated, Duration.Inf)
  }

  protected def createRestaurant(): ActorRef =
    system.actorOf(Restaurant.props, "restaurant")

  @tailrec
  private def commandLoop(): Unit =
    Command(StdIn.readLine()) match {
      case Command.Guest(count, food, foodLimit) =>
        createGuest(count, food, foodLimit)
        commandLoop()
      case Command.Status =>
        status()
        commandLoop()
      case Command.Quit =>
        system.terminate()
      case Command.Unknown(command) =>
        log.warning("Unknown command {}!", command)
        commandLoop()
    }

  protected def createGuest(count: Int, food: Food, foodLimit: Int): Unit =
    ()

  protected def status(): Unit =
    ()
}

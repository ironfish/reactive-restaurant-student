/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import scala.util.parsing.combinator.RegexParsers

trait Terminal {

  protected sealed trait Command

  protected object Command {

    case class Guest(count: Int, food: Food, foodLimit: Int) extends Command

    case object Status extends Command

    case object Quit extends Command

    case class Unknown(command: String) extends Command

    def apply(command: String): Command =
      CommandParser.parseAsCommand(command)
  }

  private object CommandParser extends RegexParsers {

    def parseAsCommand(s: String): Command =
      parseAll(parser, s) match {
        case Success(command, _) => command
        case _                   => Command.Unknown(s)
      }

    def createGuest: Parser[Command.Guest] =
      opt(int) ~ ("guest|g".r ~> opt(food) ~ opt(int)) ^^ {
        case count ~ (food ~ foodLimit) =>
          Command.Guest(
            count getOrElse 1,
            food getOrElse Food.Akkacore,
            foodLimit getOrElse Int.MaxValue
          )
      }

    def getStatus: Parser[Command.Status.type] =
      "status|s".r ^^ (_ => Command.Status)

    def quit: Parser[Command.Quit.type] =
      "quit|q".r ^^ (_ => Command.Quit)

    def food: Parser[Food] =
      "A|a|M|m|K|k".r ^^ Food.apply

    def int: Parser[Int] =
      """\d+""".r ^^ (_.toInt)
  }

  private val parser: CommandParser.Parser[Command] =
    CommandParser.createGuest | CommandParser.getStatus | CommandParser.quit
}

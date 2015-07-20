/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import scala.util.Random

sealed trait Food

object Food {

  case object Akkacore extends Food
  case object MahiPlay extends Food
  case object KingScala extends Food

  val foods: Set[Food] =
    Set(Akkacore, MahiPlay, KingScala)

  def apply(code: String): Food =
    code.toLowerCase match {
      case "a" => Akkacore
      case "m" => MahiPlay
      case "k" => KingScala
      case _   => throw new IllegalArgumentException("""Unknown food code "$code"!""")
    }

  def anyOther(food: Food): Food = {
    val others = foods - food
    others.toVector(Random.nextInt(others.size))
  }
}

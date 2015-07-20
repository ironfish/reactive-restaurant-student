/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

class FoodSpec extends BaseSpec {

  import Food._

  "foods" should {
    "contain Akkacore, MahiPlay and KingScala" in {
      foods should ===(Set[Food](Akkacore, MahiPlay, KingScala))
    }
  }

  "Calling apply" should {
    "create the correct Food for a known code" in {
      apply("A") should ===(Akkacore)
      apply("a") should ===(Akkacore)
      apply("M") should ===(MahiPlay)
      apply("m") should ===(MahiPlay)
      apply("K") should ===(KingScala)
      apply("k") should ===(KingScala)
    }
    "throw an IllegalArgumentException for an unknown code" in {
      an[IllegalArgumentException] should be thrownBy apply("1")
    }
  }

  "Calling anyOther" should {
    "return an other Food than the given one" in {
      forAll(foods) { food => anyOther(food) should !==(food) }
    }
  }
}

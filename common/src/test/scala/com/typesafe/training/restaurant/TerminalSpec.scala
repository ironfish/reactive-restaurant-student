/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

class TerminalSpec extends BaseSpec with Terminal {

  "Calling Command.apply" should {
    "create the correct CreateGuest command for the given input" in {
      Command("guest") should ===(Command.Guest(1, Food.Akkacore, Int.MaxValue))
      Command("2 g") should ===(Command.Guest(2, Food.Akkacore, Int.MaxValue))
      Command("g m") should ===(Command.Guest(1, Food.MahiPlay, Int.MaxValue))
      Command("g 1") should ===(Command.Guest(1, Food.Akkacore, 1))
      Command("2 g m 1") should ===(Command.Guest(2, Food.MahiPlay, 1))
    }
    "create the GetStatus command for the given input" in {
      Command("status") should ===(Command.Status)
      Command("s") should ===(Command.Status)
    }
    "create the Quit command for the given input" in {
      Command("quit") should ===(Command.Quit)
      Command("q") should ===(Command.Quit)
    }
    "create the Unknown command for illegal input" in {
      Command("foo") should ===(Command.Unknown("foo"))
    }
  }
}

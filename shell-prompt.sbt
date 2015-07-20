/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

import scala.Console
import scala.util.matching._

shellPrompt in ThisBuild := { state =>
  val manRmnd = Console.RED + "[run the man command]" + Console.RESET
  val prjNbrNme = Project.extract(state).get(name)
  prjNbrNme match {
    case ("base" | "common" | "entry_state") =>
      s"$manRmnd $prjNbrNme > "
    case _ =>
      val prjNmeRx: Regex = """(?<=[a-zA-z]\d\d\d_).*""".r
      val prjNbrRx: Regex = """(\d\d\d)""".r
      val prjNme: String = prjNmeRx findAllIn prjNbrNme mkString
      val prjNbr: Option[String] = prjNbrRx findFirstIn prjNbrNme
      s"$manRmnd [${prjNbr.get}] $prjNme > "
  }
}

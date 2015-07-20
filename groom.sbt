/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */
def groom: Command = Command.command("groom") { state =>
  val prjNme = Project.extract(state).get(name)
  val prjBase = Project.extract(state).get(baseDirectory)
  val prjSrcMain = Project.extract(state).get(sourceDirectory) + "/main"
  prjNme match {
    case ("base" | "common" | "entry_state") =>
      state.log.info(s"Command groom not supported for project ${prjNme}")
      state
    case _ if (new sbt.File(prjSrcMain)).exists =>
      state.log.info(s"Project ${prjNme} has already been groomed")
      state
    case _ =>
      val mapPrev = AttributeKey[Map[String, String]]("map-prev")
      val pm = state get mapPrev
      val prvPrjNme = pm.get(prjNme)
      val prvPrjBase = prjBase.toString.replaceAll(prjNme, prvPrjNme)
      val prvPrjBaseMain = prjSrcMain.replaceAll(prjNme, prvPrjNme)
      IO.copyFile(new sbt.File(prvPrjBase + "/build.sbt"), new sbt.File(prjBase + "/build.sbt"), false)
      IO.copyDirectory(new sbt.File(prvPrjBaseMain), new sbt.File(prjSrcMain))
      val newState = Command.process(";reload;eclipse", state)
      newState.log.info(s"Project ${prjNme} has been groomed from ${prvPrjNme}")
      newState
  }
}

commands in Global ++= Seq(groom)

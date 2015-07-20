/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */
def front: Command = Command.command("front") { state =>
  val prjNme = Project.extract(state).get(name)
  val prjBase = Project.extract(state).get(baseDirectory)
  val prjSrc = Project.extract(state).get(sourceDirectory)
  val prjSrcMain = prjSrc + "/main"
  prjNme match {
    case ("base" | "common" | "entry_state") =>
      state.log.info(s"Command front not supported for project ${prjNme}")
      state
    case _ =>
      IO.copyDirectory(new sbt.File(prjBase + "/.cu/main"), new sbt.File(prjSrcMain), true)
      state.log.info(s"Project ${prjNme} has been fronted")
      state
  }
}

commands in Global ++= Seq(front)

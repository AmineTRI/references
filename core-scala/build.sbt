name := "core-scala"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"   // this is recommended with ScalaTest but not required
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scalamock" %% "scalamock" % "4.1.0" % "test"

addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.3")      // this is recommended to flag errors in ScalaTest
                                                                  // at compile time



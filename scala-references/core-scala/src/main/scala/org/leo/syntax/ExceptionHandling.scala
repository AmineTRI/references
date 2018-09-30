package org.leo.syntax

import java.io.{FileNotFoundException, FileReader, IOException}

import scala.util.Try

/**
  * Exceptions works the same as java but in scala there is no checked exceptions
  * one consequence of that is that methods will not have to say that they throw some exception (it's optional)
  */
object ExceptionHandling extends App {
  // try works the same as java in scala
  try {
    val f = new FileReader("input.txt")
  } catch {
    // catch block uses pattern matching in scala
    case ex: FileNotFoundException => {
      println("Missing file exception")
      // throwing exception also works like java
      throw new IOException
    }
    case ex: IOException => {
      println("IO Exception")
    }
  } finally {
    // we can have finally block also in scala
    println("Exiting finally...")
  }

  // we can also handle exception with the Option/Some/None Monad
  def toInt(s: String): Option[Int] = {
    try {
      Some(Integer.parseInt(s.trim))
    } catch {
      case e: Exception => None
    }
  }

  toInt("x") match {
    case Some(i) => println(i)
    case None => println("That didn't work.")
  }

  // another way is using the Try/Success/Failure Monad
  // It is simpler to use since it will return Success(value) if ok or Failure(Throwable) if not
  def trytToInt(s: String): Try[Int] = Try {
    Integer.parseInt(s.trim)
  }

  /**
    * This is how we say a mthod throws some Exception in scala
    */
  @throws(classOf[Throwable]) // this is optional and even useless
  def flameThrower() = throw new Exception("flames...")
}

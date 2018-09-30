package org.leo.syntax

/**
  * Scala offers two mechanisms to let the compiler make implicit choices
  * these are implicit parameters and implicit conversions
  * Implicits are used to make the compiler implicitly(invisibly) resolve some type related errors
  * using some extra information supplied in scope
  * These errors are unsupplied arguments for method calls in the case of implicit parameters
  * and a supplied type not matching the expected type for implicits conversions
  */
object Implicits extends App {

  /**
    * Implicit parameters allow a function to have a list of arguments marked as implicit
    * only arg lists can be implicit, we cannot have an argument who is implicit while others in the same list
    * who are not (we can use the ()()... to do that)
    * once an argument is implicit, if it is not explicitly passed on a call, scala will try to find an implicit value
    * to pass, the search is done by type
    * Scala will first try to find implicit parameters accessible directly (without a reference) where the method is
    * called
    * then it will search for it in all companion objects of the implicit parameter type
    */
  def multiply(value: Int)(implicit by: Int) = value * by

  implicit val multiplier = 2
  //implicit var anotherMultiplier = 4    // implicit values can also be vars
  //implicit def f: Int = 6               // implicit values can also be functions (it will be called as f()

  // we cannot have more than one implicit candidate value in the call scope
  println(multiply(3))

  /**
    * Implicit conversion is, in the other hand, a way to resolve type mismatch when we want to call a method
    * on another type than the argument's type
    * All we have to do is supply an implicit method that does the type conversion
    */
  def alert(msg: String) = println(msg)

  implicit def convertIntToString(int: Int) = int toString

  alert(7)          // this will be equivalent to alert(convertIntToString(7))

  /**
    * Another aspect of implicit conversion is when you try to access a member on a type that does not have it
    * all you have to do is to define a class that have that member and that can be built using the original type
    * along with an implicit method that does the conversion to the new type
    */
  class HelloInt(x: Int) {
    val word = "hello"
    def sayHello() = for (i <- 1 to x) println("hello")
  }

  implicit def convertIntToHelloInt(int: Int) = new HelloInt(int)

  3.sayHello()
  println(3.word)

  /**
    * What we just did is commonly called type enrichement and it's common enough to have its own shorthand called
    * implicit class
    * The rule is if we have a class with a single argument, we can mark it as implicit and omit the implicit method
    * definition
    * this turns out to be very useful to enrich libraries and classes you can't change
    */
  implicit class HeyDouble(x: Double) {
    val magicWord = "hey"
    def hey() = for (i <- 1 to x.round.toInt) println("hey")
  }

  2.4.hey()
  println(2.4.magicWord)

  /**
    * The combination of implicit parameters and implicit conversion when used with type classes (generic traits)
    * can allow you to give classes new functionality without code modification
    */

  //suppose we have the following cases classes that we can not modify (suppose they come from another library)
  final case class Person(firstName: String, lastName: String)
  final case class Dog(name: String)

  // we would like to add a chat function to both of them so we need an implicit class that can add it right ?
  // but before we will need something that allows any class to actually chat
  trait CanChat[A] {
    def chat(x: A): String
  }

  /**
    * Since object define a single instance of the type with the same name we can use them as implicit values to
    * implicit parameters
    */
  implicit object PersonCanChat extends CanChat[Person] {
    def chat(x: Person) = s"Hi, I'm ${x.firstName}"
  }

  implicit object DogCanChat extends CanChat[Dog] {
    def chat(x: Dog) = s"Woof, I'm ${x.name}"
  }

  implicit class ChatPlugin[A](x: A) {
    def chat(implicit canChat: CanChat[A]) = canChat.chat(x)
  }

  // Now we can call chat on any instance of Person or Dog, it will use ChatPlugin to make implicit conversion
  // the later will use implicit parameter to call chat on the appropriate value between PersonCanChat and DogCanChat
  Person("Leo", "Essid").chat
  Dog("doggy").chat
}

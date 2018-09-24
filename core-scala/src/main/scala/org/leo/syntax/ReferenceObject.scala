package org
package leo
package syntax

import java.util

import scala.util.Random
import scala.collection.mutable

/**
  * This object is an introduction to Scala programming so it will contain a
  * huge amount of information, the first one is about the name scala
  * that stands for scalable language and surprisingly it has nothing to do
  * with the programs written in scala being more scalable thant those written
  * in another language but it means scala grows with the demand of its community
  * and can be used to program any type of application.
  *
  * Scala is Pure Object meaning that every value is an object (no primitive types)
  * and every operation is a method call (even operators such as + or *)
  * The last sentence mean also that scala does not have operators but characters like
  * * or - or + can be used as method names
  *
  * Scala is functional :
  * 1/ methods are first class values (as much as data members), they can be passed
  * as arguments, assigned, returned as results of operations or other methods , being
  * literals, assigned to variables, modified
  * 2/ Functions do not cause side effects on arguments, besides, scala programs can be
  * written in an imperative style (handling mutable objects) but scala
  * offers the choice of having a large amount of immutable objects (more than java Strings)
  *
  * Scala being hybrid imperative/functional means that vars, side effects and mutable objects
  * are not evil but programmers who want to use functional style should try to use them
  * as less as possible and that's exactly what we are trying to do, so we should use those
  * little three devils only when needed and with a justification
  *
  * Scala makes heavy use of java in the back scenes, it compiles to JVM bytecode, and is
  * totally compatible with java code in both ways
  *
  * In scala the entry point or the executable code is either defined in a standalone object extending App
  * or in a standalone object that have a main method
  * In the first case args are actually available in the whole object
  *
  * This Object is called Singleton object and is defined with object and not class
  * It is also called a standalone object (in opposition to a companion object)
  * All singleton objects (even companion objects) does'nt define a type, i.e we cannot
  * have instances of them but they extend classes or traits and be used to refer to these types
  *
  * Like companion object, a singleton object's methods and fields can be accessed in a java static
  * like way
  *
  * We can see an object as a the unique instance of its own type, and we can access this instance by its name
  * All objects are lazily created when referenced, like lazy vals
  */
object ReferenceObject extends App {

  // in scala, most the time the ; is optional

  // there are two types of variables in scala val and var
  // val are like java final variables, they cannot be reassigned
  // but they can be altered (look at arrays for instance) thus they are not immutable
  // scala encourage to use a functional style, and one aspect of a functional code
  // is the use of vals, if you want a functional style of code, try to not use vars
  // at least as much as possible
  val value = "some value"

  // var are like java non final variables
  var anotherValue = 3

  // if scala can infer the variable type then we can omit the type
  val someVar = 7
  val list = new util.ArrayList[String]()

  //val wrongVar          // this will not compile beacuase scala cannot infer the variable type

  // we can also specify the type
  val s : String = "a value"  // java.lang "based" types are visible to scala without import

  // array creation
  // parameters in generics are written inside []
  // and arrays are generic classes in scala
  val intArray: Array[Int] = new Array[Int](4)
  val stringArray = Array("one", "two")

  println(intArray(3))      // array members are accessed with () not with [] like java

  /*
  This is a cool feature of scala :
  if a given method takes only one parameter we can call it without the dot after the instance name
  for example 1 + 2 is actually (1).+(2) and also 0 to 5 is (0).to(5)
   */
  for (s <- 0 to 3) {         // the <- can be read as "in"
    println(intArray(s))
  }

  // another use of "in" operator in a foor loop
  for(i <- intArray) {
    println(i)
  }

  /**
    * This is how we declare a method in scala
    */
  def max(a: Int, b: Int): Int = {
    if (a > b)
      a   // no return statement needed
    else
      b   // no return statement needed
  }

  /**
    * If the type can be inferred (and if the method is not recursive) then we can omit the return type
    * Also if the method is one statement, we can omit the curly braces
    */
  def max2(a: Int, b: Int) = if (a > b) a else b

  /**
    * void becomes Unit in scala
    * in this example the Unit can be omitted
    * Unit means that no value is returned by this method, i.e this functions is only used for
    * its side effects, the later being a blasphemy in functional programming, they should also
    * be avoided as much as possible to have a functional style code
    */
  def sideEffectsFunction(): Unit = println("hey")

  // lambda org.leo.syntax in scala : function literals
  var array = new Array[String](3)
  array.foreach(s => println(s))    // what is inside the () is called a functional literal

  // if a functional literal takes one argument and have only on statement we can omit the arguments
  array.foreach(println)

  // lists
  // here is the big news : Lists in scala are immutable !!! (unlike arrays)
  val intList = List(1, 2, 3)
  val stringList = List("one", "two", "three")

  // since list are immutable, operations on lists results in new lists
  val mixedList = intList ::: stringList    // the ::: method in a list concatenates it with another list
                                            // the result here is a list of Any

  val newIntList = 0 :: intList             // this is the the cons operator or method ::
                                            // it adds an element in the beginning of a list

  /*
  A not about the last two methods (operators :: and :::), you may think about the rule where if a method f
  is defined for a type A and have only one argument of type B then if we have a and B instances of A and B
  then we can call f like : a f b as if it were a.f(b) (the call is from the left operand)
  Then you might think that :: is a method of the Int type , well it is not !!!
  Actually there is one more rule about that shit : if the method f name ends with : like f: for example
  then the call will be b f: a (meaning the call is from the right operand) so
  0 :: intList is actually intList.::(0)
   */

  // the cons method is also used to create new lists from an empty one Nil
  val myList = 1 :: 2 :: 3 :: Nil   // this is the same as intList
                                    // Nil is a List with no values (empty list) is is the same as List()

  val appendedList = 1 +: myList    // we can also append to list using +: but it's execution time will grow
                                    // linearly with the list's size, whilst :: time is constant

  val number = myList(2)            // list members are accessed like arrays using ()

  // Tuples
  /*
  Tuples are avery useful container that like List is immutable but unlike list can hold different types
  of data, where a list can be List[String] or List[Int] for example a Tuple can be a Tuple2[Int, String]
  or even a Tuple6[Int, String, Char, Byte, Int, String]
   */
  val tuple2 = (1, "one")
  val tuple3 = (2, "two", 'g')

  val num = tuple2._1         // tuple elements are accessed using _n
  val char = tuple3._3

  // Maps and Sets
  /*
  While arrays are always mutable and list and tuples always immutable
  scala offers two packages for both Maps and Sets scala.collection.immutable
  and scala.collection.mutable with version of the same classes with the same names
   */

  var immutableSet = Set("Boeing", "Airbus")            // by default an immutable set is created
  immutableSet += "Lear"                                // this creates a new set and it is shorthand for immutableSet + Lear
                                                        // because immutable sets does not have a += method unlike mutable sets

  var mutableSet = mutable.Set("one", "two")
  mutableSet += "three"                                 // this is an actual +=

  // creating a map
  val treasureMap = mutable.Map[Int, String]()
  treasureMap += (1 -> "Go to island.")                 // the -> method on an Int taking another object returns a tuple of both
  treasureMap += (2 -> "Find big X on ground.")
  treasureMap += (3 -> "Dig.")

  // again, no imports are needed for immutable maps since they are the default like immutable sets
  val immutableMap = Map(1 -> "one", 2 -> "two")        // RQ = Map, List, Array, Set methods are called factory methods
                                                        // it is actually a method named apply defined in the companion object of each
                                                        // of these classes

  // scala data types : except String(java.lang) they are all in the scala package
  // so they are loaded in every scala file source
  // numeric types : integral and float / double
  // integral types (same ranges as java counter parts)
  val byte: Byte = 1
  val int: Int = 1234
  val chara: Char = '4'
  val anotherChar: Char = 3
  val long: Long = 342344L
  val short: Short = 234

  val float: Float = 3.4F
  val double: Double = 4.5e3d

  val string: String = "stringy"

  val boolean: Boolean = true

  // literals : the same literals as java actually
  // Int literals
  val dec = 3         // decimal int literal
  val hex = 0x00F     // hexadecimal int literal , starts with 0X or 0x

  // Long literals : int literals ending with L (otherwise they are Int)
  val decLong = 123414L
  val hexLong = 0XFFABL

  // if a literal is assigned to short or byte types and it is in their range
  // then it will be treated as that
  val shorty: Short = 31
  val byty: Byte = 1

  // double and floats are the same as Java's, a combination of decimal digits, a potential
  // decimal dot and a potential E for an exponent which is btw a factor of multiplication by a power of 10
  val floaty = 1.45e2f        // a float for 1.45 * 10^2
  val doubly = 3.14d

  // character literals are unicode characters between single quotes
  val chary = '1'
  val unicodeChary = '\u0041'   // like java we can have unicode code literals
  val escapeChar = '\n'         // we can also have escape sequences as character literals

  // String literals are put between double quotes
  val myString = "leopard"
  // to avoid having multiple escapes in strings we can use the three quotation mark
  // to insert whatever the fuck we want into a string, from specials chars, new lines ...
  val rawString =
    """ wholy shit ///\\\\!!!!
      |this is a serious raw Stringy string." ' '' !
    """.stripMargin     // here strip margins corrects leading spaces in the output of a raw string

  // Symbol literals : scala has a special king of literals named symbols (instances of Symbol class)
  // they are not so much different from Strings, but they are interned meaning two symbols with the same
  // value are referencing the same object
  // they need to be prefixed with '
  // i really don't know where these are useful (and i don't give a fuck about that)
  // they say they can be used as identifier for a closed set of String constants (quick comparison)
  // like database column names for instance
  val columnName = 'col

  // boolean literals : nothing more to say over java's ones
  val bool = true

  // String interpolation : a magnificent way to avoid building strings with concatenations
  // by preceding a string with the s interpolator, any expression inside the double quotes
  // will be evaluated and will be having toString called upon its value if a $ precedes it
  // curly braces are necessary to evaluate method calls and operations or any expression
  // with non-identifiers
  val interpolatedString = s"hello $myString ! ${max2(2, int)}"

  // another string interpolator is raw, which does not recognize character escapes
  // otherwise it behaves the same as the s interpolator
  val rawInterpolatedString = raw"hello \\\\ no escape $myString"  // prints hello \\\\\ no escape leopard

  // another default interfpolator is f, which formats an expression if we put a formatter after it
  // starting with %, if none is inserted, the f interpolator will use %s which is the same as using
  // the s interpolator
  val fInterpolatedString = f"${math.Pi}%.5f"   // will print 3.14159

  // besides these 3 default interpolators, users can also define their custom String interpolator

  // scala provides multiple operators on its basic types, these operators are actually methods defined
  // in these classes, since in scala there is no operator, only methods
  val sum = 1 + 2   // this is actually 1.+(2)

  // operator notation (operand1 op operand2) is not only for operator like methods but it works for any method
  val indexOfa = myString indexOf 'a'               // same as myString.indexOf('a')
  val indexOfaFrom = myString indexOf ('a', 1)      // even for methods that takes multiple args (but () are necessary

  // that was what we call infix operator notation, there are another two operator notations :
  // prefix and postfix operators which are essentially unary methods

  // prefix operator methods must and have their method name defined as unary_identifier
  // where identifier is on of these four characters  + - ! ~
  val pref = -7 // prefix operator example
  val anotherPref = !boolean
  val anotherWay = boolean.unary_!    // same as the previous

  // postfix operators are just methods with no arguments
  val loweredString = myString toLowerCase      // the rule is to keep the () when side effects exist like println()

  // arithmetic operators : scala have the same operators as java + - * / % (defined as synthetic functions)
  val result = 1 + 2 / 3 * 4 % 5

  // relational operators : same as java (but defined as methods)
  val b = 1 > 2
  val c = 3 >= 0

  // logical operators (also methods)
  var boolres = b && c      // if b is false then c will not be evaluated
  var boolresu = b || c     // if b is true then c will not be evaluated

  boolres = b & c           // c is evaluated even if b is false
  boolresu = b | c          // c is evaluated even if b is true

  // bitwise operator : operations on each individual bit of integer types
  val bitwiseAnd = 1 & 2        // bitwise And result is 0
  val bitwiseOr = 1 | 2         // bitwise Or result is 3
  val bitwiseXor = 1 ^ 3        // bitwise Xor result is 2
  val bitwiseComplement = ~1    // bitwise complement, inverts each bit result is -2

  // scala also defines the same bit shift operators on integer types
  // left shit << right shift >> and unsigned right shift >>>, these operators shift
  // bits of an integer the same way of java's ones (see java ReferenceClass)

  // Objects equality : we can use == to compare all types of objects, we can also compare objects
  // not being the same type or even against null without exceptions being thrown and the result is always boolean
  // note that scala and java == is the same for primitive types (compares actual values) but scala does the same
  // with all types while java == compares reference equality for types objects
  // we can still use java == as eq in scala and its opposite ne to compare reference equality but this only
  // work for objects that map directly to java objects
  val test1 = result == 1
  //val test2 = result == null
  val test3 = myString == shorty

  // scala unified types
  // all values in scala have a type
  // the base class in scala is Any which is abstract, every class in scala extends directly or indirectly from Any
  // Any have two direct subclasses AnyVal and AnyRef
  // AnyVal is the parent of all 9 value types : Int Long Short Char Boolean Float Double Byte Unit
  // AnyVal is non nullable, and is represented by primitive types in the underlying java system
  // Unit is a special object and there is only one instance of Unit, it used to return no meaningful information
  // like we use void in java, it can be declared both using Unit or just ()
  val unit = Unit
  val theSameUnit = ()

  // AnyRef is in the other hand the parent of any reference type, any user defined class that does not inherit from
  // AnyVal, and is represented by objects in the underlying java system, it is equivalent to java's Object class
  val anyRef = new AnyRef

  // Nothing is a type that is a subtype to every scala class, there is no instances of Nothing
  // is it used as a base value for classes that takes parameters (remember List[Nothing] which can be any list)
  // Nothing is also used as return types for methods that never returns normally (always throws exception for example)
  // Nothing is created because everything needs to have a type in scala
  def nothingElseMatters : Nothing = throw new RuntimeException

  // Null is a trait in scala (everything needs a type, even null) and its only instance is null
  // Null is a subtype of all reference types AnyRef
  // this null instance is only defined in scala so it can access java libraries and should never be used otherwise
  var aNull: String = _       // the _ placeholder initialize a var (never a val) to its default value (0, false, () for
                              // value types and null for reference types (which is bad))

  // To avoid using null (especially in initializing vars) we can uses Nil (which Lis[Nothing]) for lists
  // and Option for other references
  // Option[T] is a container for an element of the type T, it can only be None or Some[T]

  var anOption: Option[String] = None
  anOption = Some("ss")

  // common functions on Option
  anOption.getOrElse("empty option")
  var stringValueFromOption = anOption.get
  anOption.isDefined    // true if Some
  anOption.isEmpty      // true if None

  // Nil is equal to List() and it represents an empty list
  val emptyList = Nil

  // expression within {} is called a block, its last expression's result is the blocks result
  val block = {                   // block value is 8
    val someString = "in block"
    someString.length
  }

  // functions are like methods but without the def
  (a: Int) => a + 1                                   // anonymous function
  val addOne = (a: Int) => a + 1                      // we can call it simply like addOne(3)
  val intToString : Int => String = _ toString        // another style

  val multipleLineAndParams = (a: Int, b: Int) => {
    val i = a + 1
    b + i
  } : Int                                             // we can specify return type also for function

  def function4 : String => Int = _ length            // we can assign functions to methods

  // methods may have multiple parameter list
  def multiParam(a: Int, b: Int)(c: Int): Int = a * b * c                         // this is also called currying
  def curryingExecutor(a: Int, b: Int)(op: (Int, Int) => Int) : Int = op(a, b)

  // we can fix on parameter list of a currying function and pass the partial function around
  val halfFunction = curryingExecutor(3, 3)_
  halfFunction((x, y) => x + y)                   // same as curyingExecutor(3, 3)(_+_)
  halfFunction.apply(_-_)                         // another style
  halfFunction(_*_)                               // yet another style

  // case classes are special scala class that are immutable and compared by value
  // they are instantiated without new, and must have a parameter list that can be empty
  case class Point(x: Int, y: Int)

  val point = Point(1, 2)
  print(point.x)                              // the parameters x and y are public vals

  val samPoint = Point(1, 2)
  val anotherPoint = Point(2, 1)

  point == samPoint       // true
  point == anotherPoint   // false

  // pattern matching is a powerful substitute for java's switch statement
  val x: Int = Random.nextInt(10)

  x match {
    case 0 => "zero"
    case 1 => "one"
    case 2 => "two"
    case _ => "many"            // _ is a catch all operator here
  }

  // match expression have a value that is return when a match occurs
  def matchTest(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "many"
  }

  matchTest(3)  // many
  matchTest(1)  // one

  // case classes are useful when used with match
  // suppose we have the following structure
  // the sealed keyword before a class or a trait is used to extending this class or trait only possible inside the
  // same file, this assures that all subtypes are known, really usefull for case classes and matching => we no more
  // need to worry about _ catch all
  sealed abstract class Notification

  case class Email(sender: String, title: String, body: String) extends Notification {
    def write(s: String) = "writing email"
  }

  case class SMS(caller: String, message: String) extends Notification {
    def write(s: String) = "writing sms"
  }

  case class VoiceRecording(contactName: String, link: String) extends Notification {
    def record(s: String) = "recording voice"
  }

  // we can do something like this
  def showNotification(notification: Notification, importantPeople: Seq[String]): String = {
    notification match {                                          // this is a match on object type not value
      case Email(email, title, _) =>                              // notice how we ignored the body field here with _
                                                                  // because we will not use it in the return String
        s"You got an email from $email with title: $title"
      case SMS(phoneNum, message) =>
        s"You got an SMS from $phoneNum! Message: $message"
      case VoiceRecording(name, link) if importantPeople.contains(name) =>      // this if is called pattern guards
                                                                                // they are used to make cases more specific
                                                                                // by adding more controls
        s"you received a Voice Recording from $name! Click the link to hear it: $link"
    }
  }

  val someSms = SMS("12345", "Are you there?")
  val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")

  println(showNotification(someSms, Seq("Tom")))  // prints You got an SMS from 12345! Message: Are you there?
  println(showNotification(someVoiceRecording, Seq("Tom")))  // you received a Voice Recording from Tom! Click the link to hear it: voicerecording.org/id/123

  // we can do pattern matching on type only, useful when we want to call some method of the type
  def action(notification: Notification) = {
    notification match {
      case s: SMS => s.write("writing")
      case v: VoiceRecording => v.record("recording")
      case e: Email => e.write("writing")
        // we dont need to have _ case, the class Notification is sealed, there will not be any other subtypes somewhere else
    }
  }


  // Higher order functions (or also methods) are function that takes other functions as arguments
  // or return a function as a result
  // map for example is a higher order method of collections
  val collection = List(1, 2, 3)
  val doubleInt = (a: Int) => a * 2

  collection.map(doubleInt)       // passing an existing function
  collection.map(x => x * 2)      // passing a new function (we can omit the Int using inference
  collection.map(_ * 2)           // another style (that i don't like)

  // we can also pass methods to higher order function, compiler will coerce the method into a function
  def addInt(a: Int) = a + 1
  collection.map(addInt)

  // example of a higher order methods
  // method that accepts a function
  def promotion(salaries: List[Double], raiseFunction: Double => Double): List[Double] =
    salaries.map(raiseFunction)

  def smallPromotion(salaries: List[Double]): List[Double] = promotion(salaries, x => x * 1.1)

  // method that return a function
  def urlBuilder(ssl: Boolean, domainName: String): (String, String) => String = {
    val schema = if (ssl) "https://" else "http://"
    (endpoint: String, query: String) => s"$schema$domainName/$endpoint?$query"
  }

  val domainName = "www.example.com"
  def getURL = urlBuilder(ssl=true, domainName)
  val endpoint = "users"
  val query = "id=1"
  val url = getURL(endpoint, query) // "https://www.example.com/users?id=1": String

  // in scala, methods can be nested
  def factorial(x: Int): Int = {
    def fact(x: Int, accumulator: Int): Int = {       // this method is only available in the factorial method scope
      if (x <= 1) accumulator
      else fact(x - 1, x * accumulator)
    }

    fact(x, 1)
  }

  // Apply methods : this is a syntactic sugar that is used in classes like List or Set
  // it can be defined in an object or in a class
  // in an object we can call it directly (once the object imported) by passing the params to the objects name
  // in a class we can use it on an instance
  def apply(argument: String) = "hey--" + argument

  // then wen call it like
  ReferenceObject("applying")

  // it is more used from companion objects to create instances of their companion classes
  val ll = List("K")            // this is a call for apply in List companion object

  // on a class, we call it from an instance
  class TestApply {
    def apply(a: Int, b: String) =  a + b
  }

  val test = new TestApply()
  val testApplyed = test(2, "yo")

  // extractor object are used to extract args from an object, it is called with the unapply method to do the inverse
  // of what the apply method did

  // the return type of unapply should be a boolean if it is a test,
  // an Option[T] if return is type T
  // an Option([T1,...,Tn]) if more than a value is returned
  def unapply(arg: String): Option[String] = {
    val array: Array[String] = arg.split("--")
    if (array.tail.nonEmpty) Some(array.last) else None
  }

  // assigning the extracted argument to a value
  val applyed = ReferenceObject("leo")
  val ReferenceObject(passedValue) = applyed
  println(passedValue)                         // prints leo

  // extractors can also be used with a match like this
  applyed match {
    case ReferenceObject(someArg) => println(someArg)  // prints leo
    case _ => println("Could not extract an Argument")
  }

  // for comprehensions is an expression going like for (enumerators) yield e
  // where enumerators is a list of comma separated list of generators (introducing new values) or filters
  val gen = List(1, 2, 3, 45)
  val comprehension = for (num <- gen if (num < 10))
    yield num


  // just a word about thi fucking language, it is so messed, so fucked up that you can actually define a val using a
  // block, once executed, the block's return value will be the val's value
  val fuckedUpValue = {println("some fucked up computation, side effects, whatever the fuck else"); 5}
  val anotherFuckedUpVal = {
    println("what ?")
    fuckedUpValue + 4
  }                                 // congratulation Mr Odersky, you have created the most complicated, never ending
                                    // syntax a programming language could ever have !!!

}

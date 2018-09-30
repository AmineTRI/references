/*
 * packages in scala are not quite like java ones, they are more like namespaces
 * used to modularize programs
 * While scala does not give a fuck about the files structure, it is still recommended
 * that the package have the same name that the hierarchy of directories the file is declared in
 * Another advantage to stick with this rule is to help IDEs resolve classes definitions files
 * The package itself is not defined using the directories hierarchy, it is rather defined when
 * declared (one or many packages) at the top of a scala class, after a class definition (with braces), or with
 * curly braces syntax
 * We can have multiple levels of nested packages using multiple package keywords and curly braces (it is
 * really intuitive)
 *
 * we can declare nested packages using the . like java or we can also use multiple package keywords like
 * in this class : we could also have wrote org.leo.core.syntax here
 *
 * We can also omit package declaration when defining a class, this injects the class of some empty unqualified
 * package making them un-importable but all classes defined as such, are visible to each other
 *
 * A word about file system for scala projects :
 * - java structure should be preferred even if scala allows greater flexibility : this means that a compilation (trait,
 * object or class named Class in a package com.company.stuff should be placed in a directory com/company/stuff
 * - each file should contain only one compilation Unit except for companion objects and their companion classes
 * - One case where multi compilation units are allowed is for sealed traits and their multiple implementations, there
 * is also on important rule for this case, it is that the file should be named in a camelCase after the trait name
 * with a lower case first letter
 */
package org
package leo.core

/*
 * we can use curly braces to define nested packages, in this case, class Reference class's full identifier
 * is org.leo.syntax.ReferenceClass
 */
package syntax {

  /*
   * import can be used to import classes and traits, but also methods and fields
   * in companion objects or standalone singletons since they are static like
   *
   * Besides scala implicitly import the java.lang and scala packages along with a singleton
   * object's members called Predef in the scala package, Predef contains useful methods such as
   * println or assert
   *
   * Unlike java, imports can be invoked anywhere in the code
   */
  import scala.collection.mutable           // importing the whole mutable package :
                                            // we can then call classes using mutable.ClassName
  import References.ReferencePackageClass   // importing a class from a package
  import References.InnerPackage._          // importing all classes from package
  import scala.{BigDecimal, BigInt}         // importing selected classes from package
  import scala.{Stream => Streamy}          // import class from package as a given name
  import _root_.org.leo.syntax._            // import using the root of the project

  /**
    * A class may contain members (fields defined with var or val and methods
    * defined by def), when an instance of the class is created with the new
    * keyword, a place in memory is created on runtime to hold its state (i.e its
    * fields, not methods)
    *
    * A scala class file does not necessarily have the name of the class defined in it
    * but it is a good practice
    */
  class ReferenceClass(myInt: Int = 3, private var myString: String = "Reference", l: List[String]) {

    // we can use imports anywhere !!!
    import scala.Seq

    /**
      * Constructors : in scala constructors are different than java, in fact the primary constructor
      * of a class is the class body !! that's why we can have arguments with the class name
      * these arguments will automatically declare private val data members with the same names
      * unless you put var or val so they become public ( you can also put private var if you want them to
      * be private vars or even put val to make them public vals) the rule is default is private val
      * otherwise you can write any combination of private(optional) and var or val
      * That's also explain why we can write statements in a scala class body outside method definition
      * Notice also that scala standalone object and companion objects cannot have constructors
      * We can also enforce the primary constructor to be private and the class should be then defined as
      * class ReferenceClass private {}
      * Constructors arguments can also have default values, this mean that we can call the constructor
      * without providing the argument with the default value and it will have the latter
      * If the argument with default value is not the last one, then when omitting it in a call, the following
      * arguments should be named for example new ReferenceClass(l = List("e"))
      */
    println("Hello from scala ReferenceClass : " + this.myInt + " " + this.myString)

    /**
      * we can also define other auxiliary constructors using the this keyword as method name
      * but the new constructor must call the primary constructor as its first statement
      * or call another auxiliary constructor
      * Auxliary constructors may be private
      */
    def this(myString: String) = this(3, myString, List("value"))

    // data members
    /*
      This field is public, there is no explicit keyword for public access in scala
      since it is the default access modifier
   */
    var myField = 1
    // private fields are only accessed inside the defining class
    private var _myPrivateField = "i am private"                    // syntax : _ before private fields
    private var _sum = 0

    // special syntax for getters and setters of private fields
    def myPrivateField = _myPrivateField
    def myPrivateField_(s: String): Unit = _myPrivateField = s

    def sum = _sum
    def sum_(a: Int) = _sum = a

    /**
      * All methods arguments are vals
      * argument types are necessary
      */
    def doThings(param: Int): Int = {
      // param = 6      // error, param is val not var
      // return is optional in scala and should be avoided, the last statement, if it has the same type
      // of the method's return type is considered the value to return
      param
    }

    /**
      * a minimalist syntax for a scala method
      * No return, no curly braces (only one expression) and no return type
      * As for the return type it is recommended to explicitly express them, especially
      * for public methods, (this example does not, wich is opposed to this recommendation),
      * but the rest is good
      */
    def getSum(number: Int) = _sum += number

    def add(b: Byte): Unit = {
      _sum += b
    }

    def checksum(): Int = ~(sum & 0xFF) + 1

    /**
      * This is a special singleton object since it has the same name as its enclosing class
      * it is called a companion object and it have to be defined in the same source file as its companion
      * class
      * A class and its companion object can access each other's private members
      * This is how scala avoid using statics, companion object are holders of static methods
      */
    object ReferenceClass {

      private val cache = mutable.Map.empty[String, Int]

      // this method can be called from outside the class using ReferenceClass.calculate
      // like static methods in java
      def calculate(s: String): Int =
        if (cache.contains(s))
          cache(s)
        else {
          val ref = new ReferenceClass(3, "yo", List("a"))
          for (c <- s)
            ref.add(c.toByte)
          val cs = ref.checksum()
          cache += (s -> cs)
          cs
        }
    }

    /**
      * Case classes are special scala classes that are immutable, compared by values and with a default constructor that
      * must have parameters
      * We create case classe without the new keyword (see reference Object)
      */
    case class ReferenceCaseClass(name: String, value: Int)

    /**
      * Inner classes : scala inner classes differ from those in java, in java an inner class is a member of the class
      * where in scala it is a member of the instance (object)
      * this mean if class Graph has an inner class Node and graph1 and graph2 are two instances of Graph,
      * then graph1.Node is not the same type as graph2.Node
      */
    class Graph {
      class Node {
        var connectedNodes: List[Node] = Nil
        def connectTo(node: Node) {
          if (connectedNodes.find(node.equals).isEmpty) {
            connectedNodes = node :: connectedNodes
          }
        }
      }

      var nodes: List[Node] = Nil
      def newNode: Node = {
        val res = new Node
        nodes = res :: nodes
        res
      }
    }

    // all Node instances must be instantiated with newNode method, there is no object.new in scala
    val graph1: Graph = new Graph
    val node1: graph1.Node = graph1.newNode
    val node2: graph1.Node = graph1.newNode
    node1.connectTo(node2)                              // legal
    val graph2: Graph = new Graph
    val node3: graph2.Node = graph2.newNode
    // node1.connectTo(node3)                           // illegal!! graph2.Node is not the same as graph1.Node

    // actually there is a type that enables us to work as if it were java inner classes it is Graph#Node
    // lets rewrite the same example using this
    class FreeGraph {
      class Node {
        var connectedNodes: List[FreeGraph#Node] = Nil
        def connectTo(node: FreeGraph#Node) {
          if (connectedNodes.find(node.equals).isEmpty) {
            connectedNodes = node :: connectedNodes
          }
        }
      }

      var nodes: List[Node] = Nil
      def newNode: Node = {
        val res = new Node
        nodes = res :: nodes
        res
      }
    }

    val freeGraph1: FreeGraph = new FreeGraph
    val freeNode1: freeGraph1.Node = freeGraph1.newNode
    val freeNode2: freeGraph1.Node = freeGraph1.newNode
    freeNode1.connectTo(freeNode2)                              // legal
    val freeGraph2: FreeGraph = new FreeGraph
    val freeNode3: freeGraph2.Node = freeGraph2.newNode
    freeNode1.connectTo(freeNode3)                           // now this becomes legal !!!

  }
}

/**
  * Classes in this package will have their full identifiers start with org.leo.core.References
  * We cannot refer directly to classes in a different namespace, but we can refer directly to classes
  * in two cases :
  * - they are in the same package (namespace)
  * - they are in a namespace or package that is higher thant the current one
  *
  * for the other cases we use imports or full identifiers
  */
package References {

  class ReferencePackageClass {

    // val refCls: ReferenceClass = new ReferenceClass(3, "f", List("d"))    // ERROR (without an import or full identifier)
  }

  package InnerPackage {

    /**
      * We can make a class package private using the keyword private before class : this makes the class
      * only visible for other members of the same package
      */
    private class InnerPackageClass {

      val refPkg: ReferencePackageClass = new ReferencePackageClass()             // we can access this class here : higher up package
    }
  }
}

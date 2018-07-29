package org.leo.core.syntax;

/**
 * static import is a way to import constants and static methods (members)
 * from other classes (even personal classes) so we can use these constants, methods
 * without prefixing them with the class name
 * It can also be used to import enumeration values the same way
 * RQ : if this is used too much the code might become unreadable, so easy on them
 */
import static java.lang.Math.PI;
import static java.lang.Math.cos;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Java keywords, literals, data types,operators and syntax testing class
 * a top level class cannot be static (only inner classes can)
 * 
 * a java class can extend only one class (abstract or concrete) and multiple interfaces
 */
@SuppressWarnings("unused")
public final class ReferenceClass extends ReferenceAbstractClass {
  
  // in a java program an expression is anything that evaluates to a single value, whilst a statement is an execution
  // unit

  /**  
   * Access modifiers Public, Protected, Private, package
   * they define the same access for variables, methods, classes and constructors
   * they can only be used outside methods
   * a class can only be public or package private
   */
  /*package*/ String    defaultString = "visible in the current package";
  public String         publicString = "visible everywhere";
  protected String      protectedString = "visible in the current package and all sub classes regardless of package";
  private String        privateString = "visible only in this class";

  /**
   * final members have to be initialized only once, either in the declaration of in the constructor
   * only the reference is final/constant in java, i.e if the final object have methods that can change its content
   * then we can use them after the variable is initialized
   * this mean that java does not have a mechanism to create immutable objects !
   * 
   * To create mutable object in java you need to :
   * - make the class final
   * - make all class fields final
   * - no methods to change fields states
   * - make all fields private (to avoid calling their methods that can change their states)
   * 
   * This class being final, it cannot have sub classes (that extend it)
   */
  private final String  finalString;

  /**
   * Static variables also called Class Variables (as opposed to object variables) are variables shared by all
   * instances of the class
   */
  public static int               staticInteger = 0;
  
  /**
   * The transient keyword is used only for attributes to make them not serializable
   * Serialization is the process by which java converts an object into series of bytes to be
   * sent through network to another JVM, or to be persisted into disk or storage, the same bytes stream can be
   * deserialized to reconstruct the original object
   * members that are marked as transient do not have their values stored and the default value of their
   * data type is stored instead
   * 
   * There are two cases where transient is useless, but will cause no errors though
   * static transient : useless because static is not part of the object state so it will never be serialized
   * static final     : useless because final members will be directly serialized by their final values
   */
  private transient String        transientValue = "transient";
  
  /**
   * When working in multithreading, the volatile keywords guarantees that any read/write to this variable
   * is done in the main memory (the one shared between all threads/CPUs, and not the CPU local memory)
   * this mean that at one point of execution, all threads share the same updated value of the volatile attribute
   * it also guarantees a happens before to the given variable
   * 
   * Unlike synchronization on an object, volatile works on primitives and objects the same, allow null value, do not block other
   * threads or acquire any lock, 
   */
  private volatile String         volatileString = "volatile";

  /**
   * This static block will be called only once, either the first time an object of type Syntax is created
   * or at the first call of a static member(variable or method, even main).
   * Usually it is used to initialize static variables
   * If there are more than one static block, they will be executed in their definition order 
   */
  static {
    System.out.println("Static Block Called");
  }

  /**
   * This initializer block will be called each time an object is created from this class, just before the constructor
   * Usually it is used to initialize instance variables together with the constructor
   */
  {
    System.out.println("Instance initializer block called");
    
    // a very known usage is the map initializer (combined with the use of anonymous inner classes)
    HashMap<String, String> map = new HashMap<String, String>() {
      {
        put("key", "value");
      }
      
      private static final long serialVersionUID = 6798017890409183145L;
    };
    
    System.out.println(map.size());
  }

  /**
   * Private constructor prevents sub classes to call default constructor
   * It forces subclasses to call another public constructor (using the super keyword), provided the latter with another signature
   */
  private ReferenceClass() {
    finalString = "this is a constant";
  }

  /**
   * The main method is the entry point to a java program, it have to be public to be accessed and executed by the JVM
   * the main needs to be static so the JVM does not need an instance to run it , it will load the class in memory and call the man method
   * main method cannot be overridden, the same goes to any static method (they are class members)
   * @param args
   * 
   * I choose here to right ... instead of [], this is called varargs and the function using it becomes a variadic function
   * varargs should be the last parameter of the function and is always an array that can have one, multiple or no entries at all
   * the three dots (...) in a varargs are also called ellipsis
   * the call to a variadic function should end with an array or multiple values separated by "," as if the function accept a lot of parameters
   */

  /**
   * Final method, it cannot be overridden by a sub class
   */
  public final void finalMethod() {
    System.out.println("This method can not be overridden");
  }

  /**
   * Static method, can be invoked without instance using the class name
   * Can only access static members of the class
   * It also cannot access non static methods
   * this and super are not allowed in static methods
   * they also cannot be overridden, in fact, method overriding is done in a dynamic context (runtime),
   * while static methods (like their name says) are fetched statically (compile time)
   */
  public static void staticMethod() {
    staticInteger = 2;                // this is OK
    anotherStaticMethod();            // this is OK

    // Compilation Errors :
    //privateString = "new value";    // we cannot access a non static variable from here !        
    //finalMethod();                  // we cannot call non static methods here !
  }

  public static void anotherStaticMethod() {
    System.out.println("Another static method");
  }

  //--------------------------------------------------------------
  // Testing Nested Types
  //--------------------------------------------------------------

  /**
   * Nested classes can have any access modifier (public, private, protected or package private)
   * Non static nested classes are called inner classes and have access to the enclosing classe's members, even private ones,
   * this is not the case for static nested classes
   */

  /**
   * Nested static classes can be considered top level classes for their owns
   * They usually are used for packaging convenience
   * They are created using the outer class name : Syntax.NestedStaticClass instance = new Syntax.NestedStaticClass();
   */
  public static class NestedStaticClass {
    // Nested Static class

    public void doStuff() {
      System.out.println("doing stuff");
    }
  }

  /**
   * Nested static classes can also be abstract
   * Abstract classes cannot be final
   * Abstract classes cannot be instantiated
   * Only abstract classes can have abstract methods
   */
  public abstract static class NestedStaticAbstractClass {

    /**
     * An abstract method is not implemented and cannot be static, final or native
     */
    public abstract void doThings();
  }

  /**
   * Inner classes can be considered instance members like methods and variables (only one value as a member of the enclosing object)
   * Inner classes can only be exist within an instance of the enclosing class, thus, it has direct access to the enclosing classe's
   * variables and methods, and since it is linked to an instance, it cannot declare static fields itself
   * Inner classes are instantiated only from an existing instance of the outer class :
   * Syntax outerObject = new Syntax();
   * Syntax.InnerClass innerObject = outerObject.new InnerClass();
   */
  public class InnerClass {

    //public static String      staticInnerString = "a static String declared in the inner class"; // can not create static fields
    public static final String  finalInnerString = "this is ok"; //but we can declare static final fields (consts)
    public String               publicInnerString = "a public String declared in the inner class";
    private String              privateInnerString = "a private String declared in the inner class";

    public InnerClass() {
      privateString = "Modified from Inner class";
    }

  }

  /**
   * We can also have nested interfaces (inside classes or interfaces)
   * inner interfaces are implicitly static
   * Their only use is for namespace issues resolution for interfaces with a common name or readability/encapsulation
   * 
   */
  public /*static*/ interface InnerInterface {

    public void makeStuff();
  }
  
  /**
   * We also can have nested enumerations
   * that are also static implicitly
   * @author fahdessid
   *
   */
  public /*static*/ enum InnereEnumeration {
    VAL1,
    VAL2,
    VAL3
  }
  
  /**
   * And why not an inner annotation !!!
   * its the same as an inner interface
   */
   @interface innerAnnotation {
     String parameter() default "default parameter value";
  }

  /**
   * Method-local inner classes can only be instantiated in their enclosing scope
   */
  public void methodLocalInnerClass() {
    int counter = 0;

    // Method local inner classes do not have access modifier (but they can be abstract or final)
    class MethodLocalInnerClass {
      public void test() {
        //counter is accessed in a final/effectively final mode
        System.out.println("Method local inner class " + Integer.toString(counter));
      }
    }

    MethodLocalInnerClass inner = new MethodLocalInnerClass();
    inner.test();
  }

  /**
   * Anonymous inner classes are useful when extending classes (abstract or concrete)
   * or when implementing interfaces
   * Anonymous Class can either extend a class or implement a single interface
   * they also cannot have a constructor (they don't even have a name !!)
   * Anonymous class can access enclosing class members and only effectively final or final local variables in their scope
   * they cannot have static members or initializers but can have static constants
   */
  public void anonymousInnerClassTest() {
    String someStringVariable = "local string";

    // an anonymous inner class can be created from an interface
    InnerInterface anonymous1 = new InnerInterface() {

      @Override
      public void makeStuff() {
        System.out.println("i am anonymously making stuff");

        // all anonymous classes have access to the enclosing members, even private ones
        publicString = "modified from anonymous class";
        privateString = "private modfied from anonymmous class";

        // local variables are only accessible if they are final or effectively final
        System.out.println(someStringVariable);
      }
    };

    // an anonymous inner class can be created from an abstract class
    NestedStaticAbstractClass anonymous2 = new NestedStaticAbstractClass() {

      @Override
      public void doThings() {
        System.out.println("i am anonymously doing thnigs");
      }
    };

    // an anonymous inner class can be created from a concrete class
    NestedStaticClass anonymous3 = new NestedStaticClass() {

      @Override
      public void doStuff() {
        System.out.println("overriding doing stuff");
      }
    };
    
    // a very known usage is the creation of an initializer block for a new created anonymous inner map
    HashMap<String, String> map = new HashMap<String, String>() {
      {
        put("key", "value");
      }
      private static final long serialVersionUID = -1825443817833966965L;
    };
    
    System.out.println(map.size());

    anonymous1.makeStuff();
    anonymous2.doThings();
    anonymous3.doStuff();
  }

  //--------------------------------------------------------------
  // Testing keywords, data types and syntax
  //--------------------------------------------------------------

  /**
   * this is an implementation of an abstract method
   */
  @Override
  public void doAbstractStuff() {
    System.out.println("abstractly doing stuff"); 
  }
  
  /**
   * A method to test pass by value concept
   */
  public static void passTest(Point p, int v) {
    p.x = v;                // this will affect the passed argument
    p = new Point(1, 3);    // this will have no effect on the passed argument (changing reference)
    v = 0;                  // same here, this will not affect the passed integer
  }
  
  /**
   * This is just to show where we use the native keyword
   * it is only used in methods and only incompatible with strictfp
   * This is used to call a method from outside the JVM (usually from some C++/C code) using JNI
   * I'll make an example of this in a separate class
   */
    public native void callNative();
    
    /**
     * This is just to demonstrate the use of the strictfp keyword
     * strictfp can only be used for interfaces, classes and non abstract methods
     * it cannot be used on attributes, abstract methods and constructors
     * it was introduced back in java 1.2 and its sole existence reason is to make sure
     * floating points calculations have the same result for all platforms (16/32/63 bits processors)
     * and just for the record, it's an implementation of the IEEE 754 standards for floating points
     * processing (the fuck am i supposed to do with that ?? absolutely nothing)
     */
    private strictfp double floatPointOperation() {
      double d = 3.4d;
      return d * d;
    }
    
    /**
     * This method is synchronized, meaning any thread executing it acquires the lock on this class
     * so other threads executing the method block on it (the object) until the first thread releases it
     * this also establishes a happens-before relation between the first execution and any subsequent lock acquisition after the release
     * this means that states changes will be visible to all other threads executing the method after the first release
     * The lock release occurs in the return statement or upon an uncaught exception throw
     * 
     * Constructors cannot be synchronized
     * Static synchronized method are special because they acquire a lock on the entire Class, not the object
     */
    public synchronized void synchronizelyDoStuff() {
      // thread safe manipulation
    }
    
    /**
     * Another way to do synchronization is using statements synchronization
     * But here we can specify the object to acquire the lock on
     */
    public void statementSynchronization() {
      synchronized(this) {
        // thread safe manipulation and lock on this
      }
    }
    
  @SuppressWarnings("unused")
  public static void main(String... args) throws Exception {
    // if assertions are enabled (-ea or -enableassertions),  the next assertion is checked at runtime
    // and if it is false then an AssertionError will be thrown by the java runtime
    // the expression after : is optional and should have a value (not void), it will be used to detail the error
    // Assertions are used for debugging and should never be used to replace conditional tests (if, else ...), because
    // assertions can be disabled
    // Assertions exists since java 1.4
    assert(args.length > 0) : "no args found";

    // branching statements (break and continue, along with return)
    // the break keyword can be used in the unlabeled form to terminate an innermost loop (do-while, while, switch or for)
    for (String s : args) {
      if (s.equals("found")) {
        System.out.println("gotcha !!");
        break;
      }
    }

    // THE NEXT IS TO AVOID !!!! (LABELS ARE BAD => SPAGHETTI CODE)
    // there is also a labeled form of break, it terminates and outer loop given the later is labeled
    String[] testArray = {"val1", "val2", "val3"};

    loopToBreak :
      for (String s : args) {
        for (String t : testArray) {
          if (s.equals(t)) {
            System.out.println("gotcha !");
            break loopToBreak;
          }
        }
      }

    // the continue keyword can be used to skip the current iteration in a loop (do-while, while, switch or for)
    // it will ignore all statements after the continue keyword for the current iteration and move on to the next iteration
    for (String s : args) {
      System.out.println("start");
      if (s.equals("skip")) {
        System.out.println("ignored");
        continue;
      }

      System.out.println("not ignored");
    }

    // THE NEXT IS TO AVOID ALSO
    // the labeled form of the continue keywords allows you to skip the current iteration of an outer loop
    loopToContinue : 
      for (String s : args) {
        for (String t : testArray) {
          if (t.equals(s)) {
            System.out.println(s + " found");
            continue loopToContinue;
          }
        }
      }
    
    // we also know the return keywords that ends a method
    // if the methods returns any other type a part from void then we call return followed by the value
    // but if the method returns void (no return) we can call return alone to end the method somewhere before the
    // last statement of that method
    
    // reserved keywords are forbidden in a java program
    // const is reserved but were replaced by final
    // goto is reserved but is against structural programming

    // java literals
    // a literal is any constant value that can be assigned to a variable

    // true and false are the only boolean literals
    boolean condition1 = true;
    boolean condition2 = false;

    // integer literals are 4 types
    int decimal = 145;              // decimal literal with digits in 0-9
    int octal = 0120;               // octal literal with digits in 0-7, should always start with 0
    int hexaDecimal = 0x123Face;    // hexadecimal literal with digits in 0-9 and characters in a-f or A-F, should start with 0X or 0x
    int binary = 0b1101;            // binary literal with digits in 0-1, should start with 0B or 0b, only in java 7 and later

    // by default all integer literals are of type int, if you want to use a long you need to add an L or l to the end of the literal
    // int variables are stored in 4 bytes (32 bits) while long ones are stored in 8 bytes (64 bits)
    long bigInteger = 136;              // this will be stored as an int
    long explicitBigInt = 136L;         // this will be stored as a long
    long implicitBigInt = 9999999999L;  // the L or l is needed to declare longs, even if the literal does not fit into an int

    // starting from java 7 we can add underscores to all numeric literals to group digits, they are ignored and only used for readability
    int organizedInt = 123_456_789;

    // floating point are decimal fractions or exponential
    // integer based literals (D is not optional here !!!)
    double doublePrecision = 34d;             // the suffix d or D is needed to distinguish from integer literals
    double simplePrecision = 56f;             // while the D makes the precision stored in 8 bytes (double precision) the f or F makes it simple (4 bytes)
    // here some variants of floating point literals (the D becomes optional)
    double d2 = 1.2;
    double d3 = .3;
    double d4 = 4.;
    // exponential floating point literal (the D is optional)
    double exp = 4.5e4d;
    // by default every floating point literal is double, to use float we need to add the f or F suffix
    float floaty = 4.5F;

    // character literals are unsigned integers represented by UNICODE characters of 2 bytes (16 bits) ranging from 0 to 65535
    char a = 'a';                           // single quotes char
    char integerChar = 062;                 // integer literal char,it is a UNICODE char and can be decimal, hexadecimal or octal
    char unicodeChar = '\u0064';            // UNICODE literal char of the character a, the form is \ uxxxx where xxxx are 4 hexadecimal numbers
    // in the last comment i separated the \ and the uxxxx because java compiler transforms all the code into UNICODE first so it will complain 
    // that \ uxxx (without the space) is and invalid UNICODE character even if it is in the comment !!! 
    // by the way, a java program is all UNICODE so :
    String s \u003d "123"\u003b       // this will compile as String s  = "123"; and this will compile

    char escapeChar = '\n';                 // escaped literal char
    int b = a;                              // this is OK because all chars are integers

    // String literals are any sequence of char literals between double quotes
    String ss = "this is a string !! \u0061";
    String spanningString = "this is a two line "
        + "String yeah !!";

    // null literal, null is the representation of lack of object, it is neither an object nor a type
    String nullString = null;                       // null can only be assigned to objects, and it can be assigned to all objects in java
    //int a = null;                                 // this will not compile, primitive types can't be null
    Integer aa = null;
    @SuppressWarnings("null")
    int aaa = aa;                                   // this will compile but will throw NullPointerException at runtime
    ReferenceClass nullSyntax;                      // like 0 is the default for integers, false for booleans, null is the default value for any object
    System.out.println(null instanceof Integer);    // this is false
    Integer i = null;
    System.out.println(i instanceof Integer);       // this is false for any type

    // java primitive data types
    // there are 8 primitive types in java : byte short char int long double float boolean
    // DEF : two's complement is a way to represent signed N bits number, all you have to do is to convert the number to N-binary
    // then invert it (0 to 1 and 1 to 0) then adding 1 to get the complement or the same number with the opposite sign
    // in a two's complement notation, a leading 1 if for negative numbers while a leading 0 is for positive numbers

    // the story is the following, N bits can hold numbers up to 2^N in the binary a system (a computer for instance :p) but only positive ones,
    // so we sacrifice one bit to hold the sign and we are left with 2^N-1 numbers in both positive and negative
    // that's why a byte does not hold 0 to 255 which is 2^8 but -128 to -1 and 0 to 127, cool huh !!

    // byte is a numeric 8 bits signed two's complement integer data type
    // its range is from -128 to 127 and its default value is 0
    byte aByte = 100;
    byte anotherByte = 'c';

    // short is a numeric 16 bits signed two's complement integer data type
    // its range is from -32768 to 32767 and its default value is 0
    short aShort = 12450;

    // int is a numeric 32 bits signed two's complement integer data type
    // its range is from -2^31 to 2^31 - 1 or from +2,147,483,647 to -2,147,483,648 and default value is 0
    // int values overflow silently meaning Integer.MAX_VALUE + 1 == Integer.MIN_VALUE
    int anInt = 1241514141;

    // long is a numeric 64 bits signed two's complement integer data type
    // its range is from -2^63 to 2^63 - 1 or +9,223,372,036,854,775,807 to -9,223,372,036,854,775,808 and default value is 0
    long aLong = 1L;

    // float are 32 bits floating points with 0.0 as default value
    float aFloat = 1.4f;

    //double are 64 bits floating points with 0.0 as default value
    double aDouble = 5.6d;

    // boolean can only be true or false and false is the default value
    boolean aBoolean = true;

    // char is a 16 bit UNICODE character holder type
    // its range is from \u0000 or 0 to \uffff or 65,535
    char aChar = '\uffff';
    char anotherChar = 4;
    char yetAnotherChar = 'a';

    // except for boolean, any primitive type data can be casted to another
    // if the target can hold that much then it's widening and we can do we implicitly
    int intFromShort = aShort;

    // if data loss occurs then it's narrowing and a cast needs to be explicitly done
    int intFromFloat = (int)aFloat;

    // java operators
    // unary operators require only one operand
    // we can use - or + to set the sign if a numeric variable
    int x = 0;
    boolean y = true;

    System.out.println(x++);    // increment and return old value => prints 0 but x is now 1
    System.out.println(++x);    // increment and return new value => print 2 and x is 2
    System.out.println(x--);    // decrement and return old value => print 2 but x is now 1
    System.out.println(--x);    // decrement and return new value => prints 0 and x is now 0

    System.out.println(!y);     // return negated value of boolean => prints false

    System.out.println(~10);    // -11 : minus the count of positive values from 0 (just a bitwise flip for all bits)
    System.out.println(~-10);   // 9   : plus the count of negative values ending at -1 (just a bitwise flip for all bits)

    // arithmetic operators perform addition, subtraction, multiplication and division and modulo operations
    System.out.println(10 * 10 / 5 + 3 - 1 * 4 / 2);    // 21
    System.out.println(10 / 3);               // 3 (division)
    System.out.println(10 % 3);               // 1 (modulo)

    // shift operators

    System.out.println(10 << 2);    // moves all bits to left (2 times) equivalent to 10 * 2^2 and result is 40
    System.out.println(10 >> 5);    // moves all bits to right (5 times) equivalent to 10 / 2^5
    System.out.println(-10 >>> 5);  // same as >> for positive numbers  but changes parity bit to 0 for a negative numbers

    // logical operators 
    // && AND ,  || OR (you know this ...)
    System.out.println(10 > 4 && 3 < 4);    // true
    System.out.println(2 > 1 || 1 > 2);     // true

    // bitwise operators &, |
    // for boolean operators bitwise will have the same result as logical ones with
    // the plus of executing second check if first OR operand is true or first AND operator is false (increments for example ...)
    // on non boolean it will not return a boolean and will perform operation on bits
    System.out.println(10 < 13 & (++x) > 2);   //true and x is incremented
    System.out.println(9 & 7);    // 1
    System.out.println(5 ^ 6);    // 3 , XOR operation bitwise : OR and not both

    // ternary operator
    int c = a < b ? a : b;        // condition ? result if true : result if false;

    //relational operators : > < >= <=
    //equality operators : != ==
    // assignment = += -= *= /= %= >>= <<= &= |=
    a += 4;     // a = a + 4;
    short w = 3;
    short m = 4;

    m += 10;            // this is ok m = 14
    //m = m + w;        // compile error because result is int
    m = (short)(m+w);   // this is ok

    // using static imports : calling Math's cos method and PI constants
    double piCosinus = cos(PI);

    // for loops
    // simple for loop
    for (int j = 0; i < 11; i ++) {
      System.out.println("this is the " + j + " iteration of the simple for loop");
    };

    // all three arguments are optional, and this is an infinite loop
    /*for ( ; ; ) {
      System.out.println("infinite loop");
    };*/

    // enhanced for loop
    String[] strings = {"a","b","c"};

    // this can be used on arrays and all Collections
    for (String entry : strings) {
      System.out.println("hey it's " + entry);
    }

    // while loop
    int counter = 0;

    while (counter < 10) {
      System.out.println(counter++);
    }

    // do while loop
    do {
      System.out.println(counter--);
    } while (counter > 0);

    // switch statement
    // it can replace if else blocks sometimes
    // switch statement can work, starting from java 7, with int, char, short, byte, String
    // but also with enumerations and some Wrapper classes such as Integer, Character, Byte, Short
    int switcher = 0;

    switch (switcher) {
    case 1: 
      // do some stuff here, really, whatever
      staticMethod();
      System.out.println("doing some stuff");
      break;
    case 2:
      System.out.println("and some more stuff here");
      break;
    case 3:
      // this is an interesting ... case... HAHA.. OK ... just kidding
      // actually the break statement is recommended because if not present the switch will fall through
      // meaning it will continue all the cases after until it finds a break
      // so here if the switcher variable is 3 then the code in case 3 and 4 will be executed
      System.out.println("case 3 here, no break though ...");
    case 4:
      System.out.println("case 4 here");
      break;
    case 5: case 6: case 7:
      // we can also have multiple case labels for the same switcher value
      System.out.println("this is case 5 ... and 6 ... wow wait ... and 7 too !!!");
      break;
    default :
      System.out.println("default case if none is executed");
      break;
    }

    // well why not the if else statement now ...basic right
    int score = 70;

    // RQ : once a condition is satisfied, the following ones are ignored even if they are satisfied
    // for instance 70 passes both C and D but the result is still going to be C
    if (score >= 90) {
      System.out.println("A");
    } else if (score >= 80) {
      System.out.println("B");
    } else if (score >= 60) {
      System.out.println("C");
    } else if (score >= 50) {
      System.out.println("D");
    }

    // Arrays
    // an array holds a fixed number of objects, its length is fixed upon creation, and will never be modified again, (try adding a member to an array ...)
    // the brackets in the array declaration can be put after the array type or after the array name
    int[] integerArray;
    String stringArray[];
    
    //an array can be created, initialized using different syntaxes
    // using the new keyword
    integerArray = new int[3];
    
    //directly assigning values using array constants, but have to be used at declaration and cannot be used on already declared array such as stringArray
    String[] anotherStringArray = {"a", "aaa", "bbbb"};
    
    // we can also have multidimensional arrays, or arrays of arrays
    String[][] multidimensionalStringArray = {{"one", "two"}, {"apple", "orange"}};
    int[] multidimensionalIntArray[] = new int[2][3];
    
    // another way to create an array, combining new with initializer, here we cannot put the length into the brackets
    multidimensionalIntArray[0] = new int[]{1, 2, 3};
    
    // array constants also cannot be used outside of initialization, i.e directly at declaration
    // multidimensionalIntArray[1] = {4, 5};          // does not compile
    // integerArray = {1, 2, 3};                      // does not compile

    multidimensionalIntArray[1] = new int[2];
    multidimensionalIntArray[1][0] = 4;
    multidimensionalIntArray[1][1] = 5;
    
    // block statements
    // block statements regroup one or more statement and can have a name
    // block statements are used every where (in if, loops, lambda, static initializer, a method is a block ...)
    // but there is no clear meaning in using them alone inside a method for example...
    int num1 = 1;
    
    {
      boolean variable = false;
    }
    
    myBlock : {
      System.out.println("Statement 1");
      System.out.println("Statement 2");
      
      // variables names cannot overlap with the outside scope
      //int num1 = 3;         // does not compile
      int variable = 7;       // but we can shadow variables in other blocks
      int num2 = 2;           // the scope of num2 is only inside the block, it vanishes outside
    }
    
    // java is PASS-BU VALUE, that's said and will never change but...
    // here where it gets tricky, all object in java are references so when we call a method on an object
    // we actually calling it on the value of the said reference and java is somehow PASS-BY-REFERENCE'S VALUE!!!
    // well that means if inside the method, a reassignment for another reference (object) will not affect the passed argument
    // but a change of the passed argument's state (attributes for example) will affect the passed argument
    // as for primitives, since they do not have any attribute, you cannot test altering them, all you can do is reassignment
    // and it will not affect the passed argument
    int v = 12;
    Point p = new Point(10, 90);
    
    passTest(p, v);   // check comments in method passTest
    
    // Autoboxing an Unboxing
    // converting a primitive variable to it's wrapper class type is called autoboxing, it occurs when a primitive variable
    // is assigned to an object of the wrapper class
    Integer n = 12;
    int pr = 10;
    Integer wr = pr;
    
    //or if a primitive variable is passed to a method that expects a wrapper class object, or if the primitive variable
    List<Integer> list = new ArrayList<>();
    int num = 156;
    
    list.add(14);
    list.add(num);
    
    // converting a wrapper class object to it's primitive type is called unboxing, it occurs when a wrapper class object
    // is assigned to a primitive type variable
    Double d = new Double(3d);
    double dd = d;
    
    // or if an object of the wrapper class is passed to a method that expects a primitive type variable
    int unboxed = list.get(0);
    
    // Exception handling : exceptional events
    FileInputStream fis = null;
    
    try {
      fis = new FileInputStream("some path");
      throw new ReferenceException();
    } catch (IOException e) {
      // to throw an exception, the method have to have a throws statement
      throw new Exception("testing checked exceptions");
      // we can have multiple catch blocks
    } catch(ArrayIndexOutOfBoundsException e1) {
      // the throws statement is not needed when the exception to throw is unchecked
      // all unchecked exceptions extend RuntimeException
      throw new RuntimeException("testing unchecked exceptions");
      // a single catch block can catch more than one type of exceptions
    } catch (RuntimeException | ReferenceException e2) {
      System.out.println("testing multiple exception catching");
      // we can also throw errors and yet we do not need a throws statement
      throw new StackOverflowError("testing throwing errors");
      // we can even catch errors !!!
    } catch (StackOverflowError e3) {
      System.out.println("testing catching errors");
    } finally {
      // finally block executes no matter what happens (except if the program ends before with a system.exit() for example
      // the finally block is used to free resources and close connections
      fis.close();
    }
    
    // starting from java 7, if a resource implements the AutoClosable interface and overrides the close method
    // we can omit the finally block and make a try with resources syntax
    // we can have more than one resource declaration in the try with resources, all of them need to be AutoClosable
    try (FileReader fr = new FileReader("some path"); FileInputStream fs = new FileInputStream("some other path")) {
      // do some stuff with fr or fs (which are final by the way)
      fr.read();
      fs.read();
    } catch (IOException e) {
      // log or whatever
      // we do not need the finally block, the resources fr and fs will be closed automatically
    }   
  }
  
}

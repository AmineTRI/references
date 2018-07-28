package org.leo.core.syntax;

/**
 * An interface is some sort of contract that defines a set of behaviors to be implemented
 * It is not a part of a class hierarchy unlike an abstract class
 * it does not regroup classes but makes them agree on a contract to implement some behaviors
 * without knowledge how other classes implements them
 * 
 * An interface can only have constants, method signatures, default and static methods with implementations, nested types
 * An interface or more can be implemented by a class
 * An interface can extend multiple interfaces
 * 
 * Constructors are not allowed in interfaces, and an interface cannot be instantiated
 */
public interface ReferenceInterface {

  // only public constants are allowed as fields in an interface
  // private or protected is not allowed (compile error)
  // and public or attributes with no modifier will be implicitly considered static final
  public static final String           someField = "some field";
  
  // any method (except static or default starting java 8) are abstract and public
  // no private methods allowed (but starting from java 9 we can have concrete private methods)
  public abstract void toImplement(String value);
  
  // all abstract methods in an interface are public abstract implicitly
  int sumInts(int a, int b);
}

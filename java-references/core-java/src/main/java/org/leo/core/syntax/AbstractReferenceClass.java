package org.leo.core.syntax;

/**
 * This is an abstract class, it cannot be instantiated and MAY have abstract methods
 * only abstract classes can have abstract methods
 * While an interface is used to make a class have a complete set of methods (contract), an abstract
 * class is used to group classes in a family or group
 * Abstract class and any child are parts of a class hierarchy, while an interface is not because it can have relationships
 * with other interfaces
 * also a class can only extend one abstract class but can implements multiple interfaces
 *
 */
public abstract class AbstractReferenceClass {
  
  /**
   * An abstract class cannot be implemented
   * the following combination are ILLEGAL :
   * abstract static
   * abstract final
   * abstract native
   * private abstract
   * synchronized abstract
   * abstract strictfp
   */
  public abstract void doAbstractStuff();
}

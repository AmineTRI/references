package org.leo.core.syntax;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * Enumerations are classes that have a predefined list of constants
 * These constants are actually static instances of the enumeration type
 * 
 * All enumerations extend Enum so they cannot extend any other class
 * but they still can implement any interface
 */
public enum ReferenceEnumeration {

  // Enumeration Instances
  SAM(175, 86) {
    @Override
    public void sayName() {
      System.out.println("hey it's SAM !!");
    }
  },
  ALEX(160, 58) {
    @Override
    public void sayName() {
      System.out.println("hey it's ALEX !!");
    }
  };

  // Data Members
  public int height;
  public int weight;

  /**
   * Enumerations can have only private constructors or package private ones
   * Either ways you cannot invoke an Enumeration constructor yourself
   * Only the Enumeration can call the constructors to build its constant values
   */
  private ReferenceEnumeration(int height, int weight) {
    this.height = height;
    this.weight = weight;
  }

  /**
   * Since Enumerations are classes, they can have methods
   */
  public int computeSum() {
    return height + weight;
  }

  /**
   * Enumerations can have abstract methods but they have to be Implemented by
   * all the Enumerations values
   */
  public abstract void sayName();

  @SuppressWarnings("unused")
  public static void main(String[] args) {
    ReferenceEnumeration person = ReferenceEnumeration.ALEX;
    System.out.println(person.computeSum());

    // enumerations comes with a useful set of predefined static methods
    ReferenceEnumeration A = ReferenceEnumeration.valueOf("SAM");     // A is SAM
    ReferenceEnumeration[] values = ReferenceEnumeration.values();    // all the enumeration values in an array

    // java have a special Set that handles enumerations more efficiently
    EnumSet<ReferenceEnumeration> enumSet = EnumSet.of(ReferenceEnumeration.SAM, ReferenceEnumeration.ALEX);

    // Java have a special Map that can have enumeration values as key
    EnumMap<ReferenceEnumeration, String> enumMap = new EnumMap<ReferenceEnumeration, String>(ReferenceEnumeration.class);
  }
}

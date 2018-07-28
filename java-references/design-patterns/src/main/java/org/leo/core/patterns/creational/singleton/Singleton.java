package org.leo.core.patterns.creational.singleton;

/**
 * Singleton Design Pattern implementation
 * 
 * The aim is to create only one instance of the Singleton class
 * @author fahd
 */
public class Singleton /*implements Serializable*/ {

  // private constructor
  private Singleton(){
    // do nothing or initialize parameters for example
  };

  // returning unique instance
  public static Singleton getInstance() {
    if (instance == null) {

      /* it is better to use local synchronization like this than using global synchronization
        on the method level avoiding slowing the application down if the singleton is called often */
      synchronized (Singleton.class) {
        instance = new Singleton();
      }
    }

    return instance;
  }

  /* Avoiding deserialization when the Singleton implements the Serializable interface by returning the unique instance
  private Object readResolve() {
    return instance;
  } */

  // DATA MEMBERS
  private static Singleton    instance = null;
  
  /* We could instantiate the singleton instance right away like this
   private static Singleton instance = new Singleton();
   thus avoiding the need to test if the instance is null in the getInstance() method
   but it is better to do the test and have a null instance here in order to get a lazy-loaded Singleton
   meaning that the singleton is created only the first time it's needed */
}

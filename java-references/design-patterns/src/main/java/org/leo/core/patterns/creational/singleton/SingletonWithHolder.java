package org.leo.core.patterns.creational.singleton;

/**
 * A variant of Singleton design pattern implementation based on the Singleton Holder
 * Since inner classes are loaded only when called first time, there is no need to do synchronisation
 * @author fahd
 */
public class SingletonWithHolder {

  // private constructor
  private SingletonWithHolder() {
    if (Holder.instance != null) {
      throw new IllegalStateException("Already instantiated");
    }
  }

  private static class Holder {
    private static SingletonWithHolder instance = new SingletonWithHolder();
  }

  public static SingletonWithHolder getInstance() {
    return Holder.instance;
  }
}

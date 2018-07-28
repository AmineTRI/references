package org.leo.core.patterns.creational.examlpes;

import org.leo.core.patterns.creational.singleton.SingletonEnum;

/**
 * Testing Singleton implementations
 * @author fahd
 */
public class SingletonExample {
  
  public static void main(String[] args) {
    SingletonEnum enumsingleton1 = SingletonEnum.INSTANCE;
    SingletonEnum enumsingleton2 = SingletonEnum.INSTANCE;
    enumsingleton1.value = "first";
    
    System.out.println(enumsingleton2.value);
    
  }
  
}

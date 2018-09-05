package org.leo.core.syntax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generic types and methods (added in java 5) allows a class, a method or an interface to have a type (class or interface) parameter
 * in order to achieve strong type checking at compile time, avoid casts and implementing generic algorithms
 * We define a generic type (class or interface) using the syntax ClassName<T1,T2,T3...>
 * 
 * The Ts are called type variables and can be any non-primitive type you specify:
 * any class type, any interface type, any array type, or even another type variable
 * 
 * We can restrict the type variable to a family of classes using the extends keyword (for classes AND interfaces)
 * so that we can only have type variables extending or implementing the given type
 * this is called bounded type parameter and it makes us call methods of the extended class from a T object
 * We can even have multiple bounded types using the & operator, even mixing interfaces and classes, but classes should be first
 * or we get a nasty error, of course we can use only one class and multiple interface and also the type used concretely should satisfy
 * the equation of extends and implements
 */
public class ReferenceGenericClass<T extends Number & Comparable<T>> {
  
  // we can have T anywhere (constructor, field, method ...)  where a type is needed in this class
  private T member;
  
  // we cannot have static fields of the type parameter in generic types
  //public static T field;
  
  // a normal constructor to avoid shadowing the default one by the next constructor
  public ReferenceGenericClass() {
  }
  
  /**
   *  a generic constructor introduces its own type variable to be used in the 
   *  the scope of the constructor
   *  this can be used in a non generic class also
   */
  public <U> ReferenceGenericClass(U a) {
    System.out.println(a == null);
    //good luck doing something with a...
    // checkout the call in the main
  }
  
  /**
   * A generic method that uses bounded types
   */
  public <U extends Comparable<U>> int compareU(U a, U b) {
    return a.compareTo(b);
  }
  
  /**
   * A generic method introduces its own type variable to be used in the 
   * scope of the method
   * it can be used in a non generic type too
   * it can be static also
   */
  public <K,V> void genericMethod(K val, V var, T param) {
    // do things
    // checkout the call in the main
  }
  
  /**
   * Just a test method to demonstrate type inference
   * @param l
   */
  private static void takeList(List<String> l) {
    System.out.println("Just Testing");
  }
  
  /**
   * Extending generic types
   */
  private static class CustomList<E,P> extends ArrayList<E> {
    // do stuff in this class
  }
  
  /**
   *  The wildcard  ? can be used on generic classes in a variety of situations: as the type of a parameter, field, or local variable
   *  it can be used in method definition, variables declarations but not in the definition of generic classes
   */
  
  /**
   * Since Gen<A> does not extend Gen<B> if a extends B
   * We can relax the restriction by using the wildcard ? like this
   * This allow us to write a method that works for Gen<X> and all Gen class
   * using sub types of X
   * this is called upper bounded wildcard
   * this class accept any list of Number or other object extending Number such as Integer or Double
   * this means that if A extends B then Gen<A> extends Gen<? extends B>
   */
  public void useWildcardUpperBound(List<? extends Number> numbers) {
    // do things
  }
  
  /**
   * We can also relax the restriction in the other way
   * meaning accepting all generic class of a type X and its superclasses
   * this is called lower bounded wildcard
   * This class accept any list of integers or other object that are super classes of Integer such as Numbers
   * this means that if A extends B then Gen<B> extends Gen<? super A>
   */
  public void useWildcardLowerBound(List<? super Integer> ints) {
    // do other things
  }
  
  /**
   * Another case is the Unbounded wildcard
   * this is used to accept a generic type of an unknown variable type
   * this is used if we need to call operation on the Object class as variable type
   * or if the operations needed do not depend on the variable type of the generic type
   * This means that for any class A, Gen<A> extends Gen<?>
   */
    public void useWildcardUnbound(List<?> objects) {
      // calling object operation
      for (Object o : objects) {
        System.out.println(o.toString());
      }      
    }
    
    /**
     * In the absence of the helper method, 
     */
    public void testWildcardCapture(List<?> i) {
      // the following statement will not compile
      // because i is seen as a list of Object and the compiler cannot tell if i.get(0) return an object of type Object
      // and hence, it cannot infer the type Object
      
      // i.set(0, i.get(0));                       
      testWildcardCaptureHelper(i);
    }
    
    /**
     *  Helper method created so that the wildcard can be captured through type inference.
     */
    private <T> void testWildcardCaptureHelper(List<T> l) {
        l.set(0, l.get(0));
    }
    
    /**
     * We cannot instantiate the type parameter
     * workaround in the next method
     */
    public <E> void instanceTypeParameter(List<E> list) {
      //E e = new E();      // this does not work of course
      //list.add(e);        
    }
    
    /**
     * This is a workaround for the restriction on creating instances from the type parameter
     * using java reflection
     */
    public <E> void instanceTypeParameterReflected(List<E> list, Class<E> c) throws Exception {
      E e = c.newInstance();
      list.add(e);
    }
    
    /**
     * We cannot use instanceof on generic types
     */
    public <E> void getInstanceof(List<E> list) {
      //boolean res = list instanceof List<String>;     // does not compile
    }
    
    // we cannot create generic type exception
    //public class GenericException<T> extends Exception {
    //}
    
    /**
     * We can use throws on a type variable class if it is of Throwable type 
     * We cannot catch a type variable class
     * We cannot throw a type variable class
     */
    public <T extends Exception> void genericException() throws T {   // throws is OK
      //try {
        // something
      //} // catch (T t) {   // error
        //throw new T();     // error
      //}
    }
  
  public static void main(String[] args) {
    // to use a generic type we need to pass a type parameter to replace the T
    ReferenceGenericClass<Integer> instance = new ReferenceGenericClass<Integer>(); 
    
    // starting from java 7 we can use the diamond operator as long as the type parameter can be
    // determined from the context
    ReferenceGenericClass<Integer> anotherInstance = new ReferenceGenericClass<>();
    
    // this is called the raw type of a generic type, non generic types cannot have raw types
    // this cannot be done if the constructor takes an argument of the type of the type variable
    ReferenceGenericClass raw = new ReferenceGenericClass();
    
    // raw types are used for backward compatibility since we can assign a parameterized types
    // to their raw types, the inverse raises an unchecked conversion warning
    raw = instance;     // no problem
    instance = raw;     // warning : unchecked conversion => unsafe in runtime
    
    // calling generic method
    anotherInstance.<Integer, String>genericMethod(4, "hey", 1);
    
    // we can use the type inference to omit the argument types
    anotherInstance.genericMethod("stringy K", "stringy V", 2);
    
    // using generic constructor, we cannot explicitly specify argument type, inference works fine
    ReferenceGenericClass<Long> a = new ReferenceGenericClass<>("string");
    
    // sub typing generic types is no trivial, if A extends or implements B then Gen<A> does not extends Gen<B>
    ArrayList<Number> numbers = new ArrayList<>();
    ArrayList<Integer> ints = new ArrayList<>();
    
    //numbers = ints;       // this does not compile , even knowing that Integer extends Number
    numbers.add(13);        // but this is ok because an Integer is (or extends) Number
    
    // we naturally can sub type generic types for example if Gen<E> extends BigGen<E> then Gen<String>
    // extends BigGen<String> like we have ArrayList<String> extends List<String>
    // we can even have another type parameter in the child class going like Gen<E,P> extends BigGen<E>
    // this imply that Gen<String,Integer> and Gen<String,Double> and so on, extends BigGen<String>
    List<String> strings;
    ArrayList<String> texts = new ArrayList<>();
    strings = texts;                                                  // this is ok
    CustomList<String, Double> customs = new CustomList<>();
    strings = customs;                                                // this is also ok
    
    // we have seen type inference in generic assignment using the diamond <> or in generic methods
    // by omitting the angled brackets when calling the method, or in generic constructor calls
    // java uses the target type to do the inference, example :
    List<String> emptyList = Collections.emptyList();
    
    // we could use if we want to not use inference 
    List<String> emptyList2 = Collections.<String>emptyList();
    
    // now the following would not work in java 7 and prior
    // we have a method that take a List<String> called takeList
    takeList(Collections.emptyList());
    // in Java 7 e should use the following
    takeList(Collections.<String>emptyList());
    // the fact is, it is only starting from java 8 that method argument expanded target type based type inference
    
    /*  generics where introduced to java in order to ensure type safety and tighter type checks
     * the compiler applies then what we call type erasure when compiling generic code in order to 
     * - replace all type parameters by their bounds or the Object class so we only have normal(without generics) code after compilation
     * - insert type casts to ensure type safety if necessary
     * - Generate bridge methods to preserve polymorphism in extended generic types
     *
     * Type erasure ensures that no new classes are created for parameterized types; consequently, generics incur no runtime overhead
     */
    
    // here are some restrictions when using generic classes :
    // 1)  we cannot use primitive types when creating instances of generic types
    //ReferenceGenericClass<int> primitiveInstance = new ReferenceGenericClass<>();       // this does not compile
    ReferenceGenericClass<Integer> primitiveInstance = new ReferenceGenericClass<>();     // we can use Wrapper classes instead
    // 2) we cannot create instances of the type parameter : see method instanceTypeParameter && instanceTypeParameterReflected
    // 3) we cannot have static fields of type parameter type
    // 4) we cannot use instance of on generic classes objects: see method getInstanceof
    // 5) unless using unbounded wildcard, we cannot cast to parameterized types
    List<Integer> li = new ArrayList<>();
    //List<Number>  ln = (List<Number>) li;  // compile-time error
    // 6) we cannot create arrays from generic types
    //List<String>[] arrayOfLists = new List<String>[10];       // error
    // we cannot create, catch or throw generic exceptions, but we can use them in throws clause : see class GenericException
    // and method genericException
    // 7) we cannot Overload a method Where the Formal Parameter Types of Each Overload Erase to the Same Raw Type
    // this mean we cannot have two methods like :
    //public void print(Set<String> strSet) { }
    //public void print(Set<Integer> intSet) { }
  }
}

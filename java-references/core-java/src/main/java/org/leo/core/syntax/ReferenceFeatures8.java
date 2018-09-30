package org.leo.core.syntax;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;
import lombok.NonNull;

/**
 * In this class we try to demonstrate new features introduced in java 8
 * 
 * Besides from the features demonstrated here (programming language features), java 8 comes
 * with more embedded features that we will not mention here (probably because i don't give a fuck about them... for now)
 * here are some honorable mentions :
 * - the new Nashorn javascript engine that replaces good old Rhino (enables us to call js with jjs command, embed js in java code and inversely)
 * - Base64 Encoding and Decoding
 * - Enhancement of a lot of APIs (security ,networking, JDBC, IO, JAXP, Internationalization, Java FX...), Tools, JVM
 * - method parameter reflection
 * - improved type inference
 */
public class ReferenceFeatures8 {

  private String field = "default";
  public static final String STATIC_FIELD = "STATIC";

  /**
   * A functional interface is an interface with only one abstract method
   * it is also called Single Abstract Method Interface or SAM Interface
   * A functional interface can extend another interface if the later does not
   * have any abstract methods
   * 
   * Java 8 comes with a set of predefined functional interfaces in the 
   * java.util.function package such as Consumer<T> BiConsumer<T,U> or Function<T,R>
   */
  @FunctionalInterface
  private interface NoParameterFunctionable {

    /**
     * A functional interface must have only one abstract method
     * but it can have any number of default or static methods
     */
    void noParameterFunction();

    /**
     *  Functional interfaces may also have Object class methods
     */
    int hashCode();

    String toString();

    boolean equals(Object obj);
  }

  @FunctionalInterface
  private interface SingleParameterFunctionable {
    void singleParameterFunction(String param);
  }

  @FunctionalInterface
  private interface MultipleParameterFunctionable {
    int multipleParamtersFunction(int a, int b);
  }

  public static void staticReferenceMethod(String param) {
    System.out.println("Static Method Reference");
  }

  private int instanceReferenceMethod(int a, int b) {
    return a + b;
  }

  private static class SomeClass {

    public int sum(int a, int b) {
      return a * b;
    }

    public boolean sameOrNot(SomeClass instance) {
      return this == instance;
    }
  }

  @FunctionalInterface
  private interface AnotherFunctionable {
    SomeClass create();
  }

  @FunctionalInterface
  private interface YetAnotherFunctionable {
    boolean compare(SomeClass a, SomeClass b);
  }

  /**
   * Demonstrating new interface features
   */
  private interface SomeInterface {

    /**
     * We can now have implemented methods inside of an interface
     * we may ask then what's the difference between a java 8 interface and an abstract class
     * Well an interface is still not part of the class hierarchy and still does not allow constructors
     */
    default void defaultMethod() {
      System.out.println("Call from default method of SomeInterface");
    }

    public static void statiMethod() {
      System.out.println("Call from static method of SomeInterface");
    }
  }

  private interface AnotherInterface {
    default void defaultMethod() {
      System.out.println("Call from default method of SomeInterface");
    }
  }

  /**
   * A class to demonstrate interface new features
   */
  private static class MyClass implements SomeInterface, AnotherInterface {

    /**
     * Default methods can be overridden
     */
    @Override
    public void defaultMethod() {
      SomeInterface.super.defaultMethod();
      AnotherInterface.super.defaultMethod();
      System.out.println("Call from default overriden method");
    }

    private void test() {
      /*
       *  call default method, since the same default method exists in the two implemented
       *  interfaces, if we have'nt overridden the default method this class would not compile
       *  this is also the same if we had a default method and an abstract method with the same signature
       */
      this.defaultMethod();
    }

    public static void main(String[] args) {
      MyClass instance = new MyClass();
      instance.test();
    }
  }

  @Data
  public static class Product {  
    public int id;  
    public String name;  
    public float price;

    public Product(int id, String name, float price) {  
      this.id = id;  
      this.name = name;  
      this.price = price;  
    }
  }  

  public void test() {
    // Lambda expressions are a way to implement a functional interface
    NoParameterFunctionable f1 = () -> {
      // here we are overriding the noParameterFunction method
      System.out.println("My Awesome Function without parameters");
    };

    // we can also remove the () in the (x), but only in one parameter methods
    SingleParameterFunctionable f2 = (x) -> {
      System.out.println("another awesome function with a single parameter");
    };

    // here we specify the lambda parameters as in but we could omit it
    // we only need to put the parameters types when the compiler cannot infer it (it will make you know it,
    // don't worry about it)
    MultipleParameterFunctionable f3 = (int x,int y) ->  {
      System.out.println("Adding x and y");
      return x + y;
    };

    // we can omit the return keyword and use () instead of {} if w-the body is only one statement (not expressions though)
    MultipleParameterFunctionable f4 = (x,y) -> (x + y);

    String someVariable = "Value";

    NoParameterFunctionable f5 = () -> {
      // lambda expression can access a local variable only if it is final or effectively final
      System.out.println(someVariable);

      // we can also access the object enclosing the lambda with "this"
      // we can even modify the instance after the lambda
      System.out.println(this.field);

      // we can also access static fields the same way
      System.out.println(ReferenceFeatures8.STATIC_FIELD);
    };

    /* method reference is a way to simplify the implementation of lambda expressions
     * if all we do in a lambda body is calling another function without parameters the we can use method reference
     * the function we refer to should take the same number/types of argument and return the same type of result as
     * the method in the functional interface we are implementing in the lambda
     * Actually this is not exactly the best definition, since a lambda expression takes a functional Interface, it was
     * a specific use case, the more general use case is another way to implement a functional interface
     */

    // static method reference ClassName::statiMethodName
    SingleParameterFunctionable f6 = ReferenceFeatures8::staticReferenceMethod;

    // instance method reference instance::methodName
    MultipleParameterFunctionable f7 = this::instanceReferenceMethod;

    SomeClass instance = new SomeClass();
    MultipleParameterFunctionable f8 = instance::sum;

    // constructor method reference ClassName::new
    AnotherFunctionable f9 = SomeClass::new;

    /* Method reference to an arbitrary or unknown instance's of some type
     * here the sameOrNot returns the boolean needed by the SAM's abstract method but does not take two instances
     * of SomeClass like expected in the SAM's abstract method
     * instead the call to the someOrNot is like instance1.sameOrNot(instance2)
     * then instance1 and instance2 will play the role of the expected 2 arguments of the SAM's abstract method (tricky !!)
     */
    YetAnotherFunctionable f10 = SomeClass::sameOrNot;

    // java 8 introduces the Optional class that helps dealing with NullPointerException
    Optional<String> empty = Optional.<String>empty();            // an empty Optional object with no value
    Optional<String> value = Optional.of("value");                // an Optional with the given non null value, if null is passed then NPE
    Optional<String> nullable = Optional.ofNullable("value");     // an Optional with the given value if non null otherwise an empty Optional

    empty.isPresent();                                             // check is value is not empty
    String v = value.get();                                        // if value present returns it else NoSuchElementException
    nullable.ifPresent(System.out::printf);                        // if value is present execute the Consumer on it otherwise do nothing

    /* there are other interesting methods on Optional such as filter, map, flatMap, orElse, orElseGet
     * orElseThrow and Object methods such as equals hashCode or toString
     */

    /*  Java 8 introduces the forEach in the Iterable and Stream interfaces
     * forEach take a Consumer so we can use lambda expression, method reference or an anonymous Consumer implementation
     */
    List<String> strings = new ArrayList<>();

    strings.forEach(s -> System.out.println("s"));
    strings.forEach(System.out::println);

    /*
     * Java 8 comes with a new Date/Time API, the aim is to resolve the issues with the existing util.Date and util.Calender
     * APIs, these have issues such as being not thread safe, poor design and the absence of direct handling of time zones
     * The new API package is java.time
     */
    // LocalDate create a new date in the user's context local date system
    LocalDate nowDate = LocalDate.now();               
    // we can see how easy is day to day or period to period operations are
    // We can use Period class, Duration class or the ChronoUnit class
    LocalDate aDate = LocalDate.of(2018, 07, 29).plusDays(1);
    LocalDate previousMonthSameDay = LocalDate.now().minus(1, ChronoUnit.MONTHS);

    // LocalTime manage time without date the same way as LocalDate
    LocalTime nowTime = LocalTime.now();
    LocalTime sixThirty = LocalTime.of(6, 30);
    LocalTime sevenThirty = LocalTime.parse("06:30").plus(1, ChronoUnit.HOURS);

    // we can see LocalDateTime as the combination of LocalDate and LocalTime
    LocalDateTime nowDateTime = LocalDateTime.now();
    LocalDateTime dayAtTime = LocalDateTime.of(2018, 7, 29, 06, 30);
    LocalDateTime futureDateTime = dayAtTime.plusDays(2).plusHours(3).plusMonths(4).plusYears(5);

    // ZonedDateTime works the same as LocalDateTime by specifies a time zone with ZoneId
    ZoneId zone = ZoneId.of("Europe/Paris");
    ZonedDateTime zonedDateTime = ZonedDateTime.of(nowDateTime, zone);

    // The StringJoiner class is added to Java 8 to build strings based on a delimiter and optionally a suffix and a prefix
    StringJoiner commaSperated = new StringJoiner(",");

    commaSperated.add("one");
    commaSperated.add("two");
    commaSperated.add("three");

    System.out.println(commaSperated);                // one,two,three

    StringJoiner joinNames = new StringJoiner(":", "[", "]");   

    joinNames.add("leo");  
    joinNames.add("lex");  
    joinNames.add("max");  

    System.out.println(joinNames);                    // [leo:lex:max]

    // we can merge two StringJoiner
    StringJoiner merged = joinNames.merge(commaSperated);

    System.out.println(merged);                       // [leo:lex:max:one,two,three]

    /*
     * Stream API : one major feature of Java 8, a new package java.util.stream is composed of classes and interfaces and enumerations
     * that allows to program in a functional style
     * A stream does not store elements; it conveys (transfers somehow) elements from a given source (a data structure, IO channels, arrays)
     * through a pipeline of operations
     * A stream is functional in nature, it does not modify the elements of its source, it creates another stream based on the operations done on the 
     * source's elements
     * A stream is lazy evaluated when needed
     * The elements of a stream are only visited once during the life of a stream. Like an Iterator, 
     * a new stream must be generated to revisit the same elements of the source
     * You can use stream to filter, collect, print, and convert from one data structure to other etc ...
     * 
     * Stream are always serial (sequential) but we can be make them parallel using parallelStream method instead of stream, for collections, this should be used on synchronized collections or
     * non blocking ones
     */

    // there are a lot (really too many) methods that can be invoked on a stream, here we will try to demonstrate some of them

    List<Product> productsList = new ArrayList<Product>();  

    productsList.add(new Product(1,"HP Laptop",25000f));  
    productsList.add(new Product(2,"Dell Laptop",30000f));  
    productsList.add(new Product(3,"Lenevo Laptop",28000f));  
    productsList.add(new Product(4,"Sony Laptop",28000f));  
    productsList.add(new Product(5,"Apple Laptop",90000f));  

    List<Float> productPriceList2 = productsList.stream()
        .filter(p -> p.price > 30000)                         // filtering data using a Predicate
        .map(p -> p.price)                                    // fetching price using a Function
        .collect(Collectors.toList());                        // collecting as list using a Collector

    System.out.println(productPriceList2);

    // Streams allows us to iterate n times an operation starting a given point
    Stream.iterate(1, element -> element + 1)       // use 1 as base and iterate by adding 1  
    .filter(element -> element % 5 == 0)            // filter elements divisible by 5
    .limit(5)                                       // stop iteration if having 5 items
    .forEach(System.out::println);                  // print each element

    // map / reduce example
    Float totalPrice = productsList.stream()  
        .map(product -> product.price)
        .reduce(0.0f, (sum, price) -> sum + price);  // accumulating price  

    // More precise code   
    float totalPrice2 = productsList.stream()  
        .map(product -> product.price)  
        .reduce(0.0f, Float::sum);                   // accumulating price, by referring method of Float class  

    // find max
    Product productA = productsList.stream()  
        .max((product1, product2) -> {   
          return product1.price > product2.price ? 1: -1;
        }).get();

    // find min
    Product productB = productsList.stream()  
        .max((product1, product2) -> product1.price < product2.price ? 1: -1)
        .get();

    // count elements
    long count = productsList.stream()  
        .filter(product -> product.price < 30000)  
        .count();  

    // create a set from a list
    Set<Float> productPriceList = productsList.stream()  
        .map(product -> product.price)  
        .collect(Collectors.toSet());                 // collect it as Set(removes duplicate elements)

    // create a map from a list
    Map<Integer, Product> productMapById = productsList.stream()
        .collect(Collectors.toMap(p -> p.id, p -> p));  // collect as a map

    // we have seen in the previous examples the use of Collectors class, this class helps creating Collector
    // objects that are used to accumulate elements into collections, summarize elements according to some criteria,
    // grouping or partitioning
    // the collectors class have a lot (really too many) of functions

    // Collector grouping example
    Map<Integer, List<Product>> mapById = productsList.stream().
        collect(Collectors.groupingBy(p -> p.id));
    Map<String, List<Product>> mapByName = productsList.stream()
        .collect(Collectors.groupingBy(Product::getName));

    // Collector partitioning example
    // partitioning is a special case of grouping where we group on the two values of a boolean function result
    Map<Boolean, List<Product>> partitionByPrice = productsList.stream()
        .collect(Collectors.partitioningBy(p -> p.getPrice() >= 3000));       // take a predicate

    /*
     * The operation we apply on streams are called aggregation operations and a sequence of such operations are called pipelines
     * There are intermediate operations (we can call zero or more of them in a pipeline), they return a stream (like filter or map for instance)
     * and there are terminal operations that return non stream result such as a primitive type, a collection or nothing like forEach
     * 
     * The intermediate operations are lazy (not eager), they do not start until a terminal operation is called.
     */

    // pipeline example on a collection
    List<Person> persons = new ArrayList<>();

    double average = persons
        .stream()
        .filter(p -> p.getGender() == Person.Sex.MALE)
        .mapToInt(Person::getAge)
        .average()
        .getAsDouble();

    /*
     * Terminal operations are also called reduction operations or reducers, they can :
     * return a single value like sum, min, max, average, count ...
     * return a collection : collect
     * general purpose reducer : reduce
     * return nothing, just side effects like forEach ...
     */

    /*
     * General purpose reducers : the reduce method takes two arguments :
     * - identity : the initial value and also the default value to return if the input stream is empty, in the following example it is a 0
     * - accumulator : a function that takes two arguments : a partial result of the process and the next element in the stream, it always returns a new
     *                 value each time it process an element of the stream, so suppose we want to create a collection as final result, this mean that
     *                 the reduce accumulator will create a new collection in each iteration of the stream's elements, which sucks like shit, that's why
     *                 we use collect to do that
     */
    Integer sum = persons
        .stream()
        .filter(p -> p.getGender() == Person.Sex.MALE)
        .mapToInt(Person::getAge)
        .reduce(0, (a, b) -> a +b);

    /*
     * The collect terminal aggregation operations, unlike the reduce operation, does not create a new value when it processes an element of the stream
     * but rather mutates or modifies an existing value
     * there are two ways to call the collect method, either by passing 3 arguments, or by calling a Collector (they are both tested just bellow this comment)
     */

    /*
     * Suppose we want to calculate the average of age in male population, the 3 arguments collect takes :
     * 1) a supplier : a factory function that can provide instances of the wanted final result container
     * 2) an accumulator : modifies the result container using the actual stream element
     * 3) a combiner : merges two instances of the result container
     * 
     * Since for average calculation needs to store two values (count and sum) we cannot use Integer for example as result container
     * We will use a custom class called Averager and then use method reference to implement the 3 arguments
     */
    Averager av = persons
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .collect(Averager::new, Averager::accept, Averager::combine);

    // result is here
    System.out.println(av.average());
    
    /*
     * collect other way is by passing a Collector, this is well suited for collections
     * We use the Collectors class with its factory methods to return different collectors
     * Besides from returning collections, the Collectors class have other interesting methods like grouppingBy, reducingBy
     * joining, counting, averaging, mapping ....
     */
    Map<Person.Sex, Double> averageAgeByGender = persons
        .stream()
        .collect(
            Collectors.groupingBy(
                Person::getGender,                      
                Collectors.averagingInt(Person::getAge)));

    // Arrays parallel sorting is a new features in java 8, and of course there are a lot of methods 
    int[] integers = {5,8,1,0,6,9};

    System.out.println(Arrays.stream(integers).mapToObj(i -> String.valueOf(i)).reduce((s,a) -> s.concat(a)).get());

    // Sorting array elements in parallel  
    Arrays.parallelSort(integers);  
    System.out.println(Arrays.stream(integers).mapToObj(i -> String.valueOf(i)).reduce((s,a) -> s.concat(a)).get());

    int[] ints = {5,8,1,0,6,9, 30, -4};
    System.out.println(Arrays.stream(ints).mapToObj(i -> String.valueOf(i)).reduce((s,a) -> s.concat(a)).get());

    // sorting array until 4th element
    Arrays.parallelSort(ints, 0, 4);  
    System.out.println(Arrays.stream(ints).mapToObj(i -> String.valueOf(i)).reduce((s,a) -> s.concat(a)).get());

    // Type Annotations and Pluggable Type Systems : annotations can now be used other than on declarations
    // we can use them with new implements throws and cats for example
    @NonNull String nonNullString = "value";
    // we can also use annotation repeatedly , the annotation should have the @Repeatable annotation when declared
  }

  public static void main(String[] args) {
    System.out.println("RUNNING");
    ReferenceFeatures8 features = new ReferenceFeatures8();
    features.test();
  }

  /**
   * A Person class for tests
   */
  public static class Person {

    private String name;
    private int age;
    private Sex gender;

    public enum Sex {
      MALE,
      FEMALE
    }

    public String getName() {
      return this.name;
    }

    public int getAge() {
      return this.getAge();
    }

    public Sex getGender() {
      return this.gender;
    }
  }

  /**
   * Custom Result container for collect
   */
  class Averager implements IntConsumer {

    private int total = 0;
    private int count = 0;

    public double average() {
      return count > 0 ? ((double) total)/count : 0;
    }

    public void accept(int i) { 
      total += i; count++; 
    }
    public void combine(Averager other) {
      total += other.total;
      count += other.count;
    }
  }
}

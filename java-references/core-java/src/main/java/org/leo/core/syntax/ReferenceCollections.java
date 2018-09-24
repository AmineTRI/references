package org.leo.core.syntax;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * This class is for testing :
 * 1/ The Collections Framework
 * 2/ Data Structures
 * 3/ Comparator / Comparable
 * 4/ Search and Sort algorithms
 * 
 * Unlike Arrays, Collections does not need to have assigned capacity upon creation, they also can change size
 * Also Unlike Arrays, Collections cannot hold primitive types
 * A collection is a container, a data structure that holds multiple objects and can manipulate them
 * 
 * The collections Framework is a set of interfaces, abstract classes and concrete classes
 * The core Interfaces of the collections framework are :
 * In the first level we find Collection and Map, Collection extends Iterable interface so all collections (under Collection and not Map) are Iterable
 * Under the Collection Interface we find the second layer of collection interfaces which are: Set, List, Queue and Deque(inherits queue)
 * 
 * The core abstract classes are those who implement the interfaces :
 * AbstractCollection as a first layer (implements Collections)
 * Second Layer (inherits AbstractCollection) is : AbstractList, AbstractSet and AbstractQueue (they all implement the appropriate interface)
 * 
 * The concrete classes inherit the abstract ones and implement the interfaces
 * I like this diagram and the table in this link here : https://infinitescript.com/2014/10/java-collections-framework/
 * 
 * A question may occur when looking at the concrete implementations of Collection (and Map also), why does all these classes implement
 * their parent interface when they are already extending an abstract class that already implement the interface, for example, why does ArrayList
 * implement List when it extends AbstractList that already implement List, is in't redundant ?
 * The answer is : yes that's a full blown redundancy !! and we could omit the core interface implementations in the concrete classes, 
 * the java creators (Josh Bloch for the collection framework) did this on purpose only for documentation matters.
 * 
 * The util package offers two classes that have static methods for collection and arrays which are Collections and Arrays
 * 
 * All Interfaces in the collection framework (even Map, which belongs to the framework also) are generic interfaces
 */
public class ReferenceCollections {

  public static void main(String[] args) {

    /*
     * The Collection Interface : parent to all collections
     * Collection provides :
     * - basic operations : size(), contains(Object), isEmpty(), add(E), remove(Object), iterator()
     * - bulk operations : containsAll(Collection<?>), addAll(Collection<? extends E>), removeAll(Collection<?>), retainAll(Collection<?>), clear()
     * - array operations : toArray(), toArray(T[])
     * - stream operations : stream(), parallelStream()
     */
    Collection<String> collection = new ArrayList<String>();    // i assigned an ArrayList just to make the code compile later

    // A Collection can be traversed in 3 ways : for construct, iterator, aggregate operations

    // Traversing using aggregate operations is added since Java 8 and it means using stream operations or forEach
    collection.forEach(System.out::println);
    collection.stream().forEach(System.out::println);
    collection.parallelStream().forEach(System.out::println);   // if the collection is large we can use parallel stream

    // Traversing using a for construct
    for (String s : collection) {
      System.out.println(s);
    }

    // Traversing using an iterator
    Iterator<String> it = collection.iterator();

    while(it.hasNext()) {
      System.out.println(it.next());
    }

    // or also
    for(Iterator<String >iterator = collection.iterator(); iterator.hasNext();) {
      System.out.println(iterator.next());
    }

    /*
     * The Set interface is a collection that cannot contains duplicate elements, it does not offer any new method other than what it inherit from the Collection
     * Interface
     * 
     * The Set interface implements a strong contract on the behavior of equals and hashCode operations, this means that two Set instances are equals if they contains
     * the same elements but this need a bit more information about the equals and hashCode methods :
     * First of all the difference between == and equals is that == compares object references while equals works like this :
     * - if equals method is not overridden then the equals method of the closest parent will be used
     * - if no parent overrides equals then Object equals will be used and this one is the same as ==
     * - if equals is overridden, we also need to override hashCode because the two method have a contract that says :
     *   1) if two objects are equal then they must have the same hashCode
     *   2) if two objects have the same hashCode they may or may not be equal
     */

    /*
     * Java provides 3 implementations of the Set interface (also extends AbstractSet)
     */

    // HashSet is the best performing Set implementation, it is backed by a HashTable but does not guarantee the order of its elements
    Set<String> hashSet = new HashSet<>();

    // TreeSet backed by a tree map, implements SortedSet that adds order to a Set based on the Comparator interface, it is slower than a HashSet
    // TreeSet also extends NavigeableSet interface, providing operations to give closest matches target for a given element (like lower, ceiling , floor, higher)
    Set<String> treeSet = new TreeSet<>();

    // LinkedHashSet extends the HashSet, keeps insertion order and is slightly slower than a HashSet
    Set<String> linkedHashSet = new LinkedHashSet<>();

    // we can transform a collection to a Set to eliminate duplicates
    collection = new HashSet(collection);   // if we used new LinkedHashSet we could preserve the order

    // we can also transform it using a stream and a collector
    collection.stream().collect(Collectors.toSet());

    /*
     * The List interface also called sequence is a collection that retains insertion order, allows duplicates, provides methods to access
     * elements (add or remove) in a positional access mode using the element's index or position
     * Much like the Set interface, the List interface strengthen the equal/hashCode contract by making two list equal if they contains the same
     * elements at the same positions
     * 
     * other than the methods inherited from Collection, List adds more methods :
     * Positional methods : set, get, add, remove (they take position as argument)
     * Search methods : indexOf, lastIndexOf
     * Iterator :  aside from the iterator method inherited from Collection, List has the listIterator method that returns a ListIterator which enrich
     *             the Iterator(next, hasNext, remove) with previous and hasPrevious, so it can traverse a list in both ways
     *             the listIterator is overloaded with another that can take an int so the iteration can start from another index other than the beginning.
     *             Here we need to take a break to explain the index in the iterator, actually the index always point in the gaps between two elements, next returns
     *             the following element and places the index after it, previous returns the previous element and places the index just before it
     *             so a call for next and then previous returns the same element, also a call for previous then next will return the same element
     * Range view : the sublist method returns a view on a portion of the list, it is a view means that changes on it will be reflected on the original list
     *              so operation like this list.sublist(start, end).clear() will remove that portion of list 
     *                
     *            
     * 
     * List have 3 main implementations in java
     */

    // ArrayList, the best performing implementation, backed by a resizable array, its default constructor start with a low initial capacity
    // that resize dynamically as we add elements, it is a better practice to construct it with a high capacity to avoid resizing cost
    List<String> arrayList = new ArrayList<>();

    // LinkedList, backed by a double linked list, have a better performance than ArrayList on add and remove, but worse on get and set
    // besides it implements the Deque interface allowing it to have its methods such as poll peek or offer
    List<String> linkedList = new LinkedList<>();

    /*
     *  Vector is roughly an ArrayList that is synchronized, ArrayList still is a better choice because :
     *  - in a thread safe ArrayList does not need synchronization, otherwise we can manually synchronize it
     *  - Both Vector and ArrayList increase in size as more elements are added, but Vector doubles size while ArrayList increase by 50%
     *  - Vectore, since it is synchronized, have more overhead than ArrayList
     */    
    List<String> vector = new Vector<>();

    /*
     *  Stack is another implementation of the List, it extends Vector and represents a LIFO stack where only push on the top
     *  it has methods such as pop peek search and push and a constructor for an empty Stack  
     */
    List<String> stack = new Stack<>();

    /*
     * The Queue interface is a holder for data being about to be processed
     * I frankly doubt that queues are useful, all use cases i know can be done with a simple list
     * Elements are generally stored in FIFO but some implementation differ from that such as priority queues that store elements
     * regarding their order if they implement a Comparable or using a Comparator passed in a constructor
     * The main implementations of Queue are PriorityQueue and LinkedList(through Deque)
     * Other implementations are used in specific cases : PriorityBlockingQueue is a blocking Queue used when thread safe is needed, some other queues restrict
     * the number of elements it can contain, we call them bounded queues
     * in general java.util queues are unbounded and not thread safe, unlike those from java.util.concurrent
     * 
     * Another thing about queues is that their methods exist in two forms : one throws an exception on failure the other returns a value (false or null)
     * add -> offer (insertion)
     * remove -> poll (head reading with removal)
     * element -> peek (head reading)
     */
    Queue<String> priorityQueue = new PriorityQueue<>();

    /*
     * The Deque interface defines a double ended queue (we can add at the top or at the bottom), it is a combination of a Stack(LIFO) and a Queue(FIFO)
     * Deque extends Queue and the most common implementations are ArrayDeque and LinkedList
     * same thing for the 6 methods we talked about in queues but we now have 12 of them with First and Last appended to each
     * addFirst -> offerFirst / addLast -> offerLast
     * removeFirst -> pollFirst / removeLast -> pollLast
     * getFirst -> peekFirst / getLast -> peekLAst
     */
    Deque<String> arrayDeque = new ArrayDeque<>();

    /*
     * The Map interface does not belong to the collection framework since it does not extend the Collection interface
     * A Map maps keys to values, the keys cannot be duplicates
     * 
     * The main implementations of Map are HashMap, TreeMap and LinkedHashMap and they are analog in comparison to 
     * HashSet, TreeSet, LinkedHashSet (really comparable even in the implemented/ extended classes and interface names)
     * 
     * Map have basic operations (such as put, get, containsKey, containsValue, size, and isEmpty) bulk operations (such as putAll and clear)
     * and collection views operations (such as keySet, entrySet, and values)
     * 
     * Like the Set and List interfaces, Map strengthens the requirements on the equals and hashCode methods so two Map instances are equal if they represent the same key-value mappings.
     */
    Map<Integer, String> hashMap = new HashMap<>();

    // The collection view operations are the only way to iterate through a map
    for (Integer i : hashMap.keySet()) {    // it is a set because keys cannot be duplicates
      System.out.println(hashMap.get(i));
    }

    for (Entry<Integer, String> entry : hashMap.entrySet()) {   // again a set because keys are unique, Entry is class that represents a Map tuple value
      System.out.println(entry.getValue());
    }

    for (String s : hashMap.values()) {   // values return a Collection that cannot be a Set because we can have duplicate values for different keys
      System.out.println(s);
    }

    /*
     * The Dictionary Abstract class is neither a map neither a collection, it is a base for the HashTable Class and works pretty much the same like
     * the Map (so why the fuck it is there ??)
     * This class is obsolete, and java makers encourage you to forget about it and use Map instead
     */
    Dictionary<Integer, String> dictionary = new Hashtable<>();

    /*
     * HashTable was the concrete implementation of Dictionary, but since java 2 it has be rebuilt to implement Map and be included in the Collection Framework
     * It works exactly like HashMap, the difference is that HashTable is synchronized
     * HashMap is more encouraged to replace HashTable, and in case we want thread safety we can use ConcurrentHashMap
     * Also another difference, HashMap allows null as values or keys but HashTable does not
     * 
     * The keys in both HashMap and HashTable are looked up using the hashCode method
     */
    Hashtable<Integer, String> hashTable = new Hashtable();

    /*
     * Object Ordering : Comparator / Comparable
     * we can sort a list's element by one of the two ways : 
     */
    Collections.sort(arrayList);      // the list elements Class need to implement the Comparable interface otherwise we get a ClassCastException

    Comparator<String >stringComparatorByLength = new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        if (o1.length() == o2.length())
          return 0;
        else
          return o1.length() > o2.length() ? 1 : -1;
      }
    };

    Collections.sort(arrayList, stringComparatorByLength);

    /*
     * What are Comparable and Comparator ?
     * These classes define the natural ordering of objects
     * If we want some Class instances to be compared on by another, we make the class implement the Comparable Interface
     * and override its unique abstract method compareTo (look at the ComparablePerson class bellow)
     * 
     * Some times we want to compare instances of a Class we don't own (from a third party library for example), or
     * we want to compare instances of a Comparable class but on another criteria for example
     */
    List<ComparablePerson> persons = new ArrayList<ComparablePerson>();
    
    Collections.sort(persons);                            // sorting using natural ordering
    Collections.sort(persons, new PersonComparator());    // sorting using another comparator


    // algorithms, complexity(definition and algorithms) and thread safety for all of the above, also retrieval type (random ...)

    // search and sort algorithms
    // Data Structures
  }

  /**
   * Example of a Comparable Class
   */
  public static class ComparablePerson implements Comparable<ComparablePerson> {

    private final String firstName;
    private final String lastName;
    private final ZonedDateTime hireDate;

    public ComparablePerson(String firstName, String lastName) {
      if (firstName == null || lastName == null)
        throw new NullPointerException();

      this.firstName = firstName;
      this.lastName = lastName;
      this.hireDate = ZonedDateTime.now();
    }
    
    public ZonedDateTime hireDate() {
      return hireDate;
    }

    public boolean equals(Object o) {
      if (!(o instanceof ComparablePerson))
        return false;

      ComparablePerson n = (ComparablePerson) o;
      return n.firstName.equals(firstName) && n.lastName.equals(lastName);
    }

    public int hashCode() {
      return 42*firstName.hashCode() + lastName.hashCode();
    }

    public String toString() {
      return firstName + " " + lastName;
    }

    @Override
    public int compareTo(ComparablePerson o) {
      int lastCmp = lastName.compareTo(o.lastName);
      return (lastCmp != 0 ? lastCmp : firstName.compareTo(o.firstName));
    }
  }

  /**
   * Example of a Comparator Class
   * The Type parameter class does not necessarily need to implement Comparable
   */
  public static class PersonComparator implements Comparator<ComparablePerson> {

    @Override
    public int compare(ComparablePerson o1, ComparablePerson o2) {
      return o2.hireDate().compareTo(o1.hireDate());
    }
  }

}

package org.leo.core.syntax;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import org.leo.core.syntax.ReferenceFeatures8.Person;

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
 * In the first level we find Collection and Map(not a true collection therefore), Collection extends Iterable interface so all collections (under Collection and not Map) are Iterable
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
 * 
 * Collections implementations that are in the java.util package are not synchronized, the ones that are synchronized are those who are in java.util.concurrent and 
 * some implementations that are not general purpose ones
 * 
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

    // Traversing using aggregate operations is added since Java 8 and it means using stream operations or forEach
    collection.forEach(System.out::println);
    collection.stream().forEach(System.out::println);
    collection.parallelStream().forEach(System.out::println);   // if the collection is large we can use parallel stream

    /*
     *  we can apply pipelines to collections using stream and aggregate operations, pipelines is the succession of zero or more intermediate operations (those who return a stream,
     *  like filter for instance) and on terminal operation that return non stream result such as a primitive type, a collection or nothing like forEach
     *  you can see more about aggregate operations reduction and parallelism in the ReferenceFeatures8 class
     */
    collection.stream().map(x -> x.substring(0, 3)).filter(x -> (x.length() > 2)).forEach(System.out::println);

    /*
     * We work with implementations of the collection framework interfaces, they can be classified as the following: 
     * 1) General-purpose implementations : most commonly used implementations, designed for everyday use.
     * 2) Special-purpose implementations : designed for use in special situations and display nonstandard performance characteristics, usage restrictions, or behavior.
     * 3) Concurrent implementations : designed to support high concurrency, they are part of the java.util.concurrent package.
     * 4) Wrapper implementations : are used in combination with other types of implementations, often the general-purpose ones, to provide added or restricted functionality.
     * 5) Convenience implementations : are mini-implementations, typically made available via static factory methods, that provide convenient
     *                                  efficient alternatives to general-purpose implementations for special collections (for example, singleton sets).
     * 6) Abstract implementations : are skeletal implementations that facilitate the construction of custom implementations.
     */

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

    //Java provides 3 general purpose implementations of the Set interface (also extends AbstractSet) :

    // HashSet is the best performing Set implementation, it is backed by a HashTable but does not guarantee the order of its elements
    Set<String> hashSet = new HashSet<>();

    /* TreeSet backed by a tree map, implements SortedSet that adds order to a Set based on the Comparator interface, it is slower than a HashSet
     * Aside from Set's operations, SortedSet add the following methods to its implementations like TreeSet :
     * range view : subSet, headSet, tailSet
     * Endpoints : first and last
     * Comparator access : comparator
     * 
     * TreeSet also extends NavigeableSet interface, providing operations to give closest matches target for a given element (like lower, ceiling , floor, higher)
     */ 
    Set<String> treeSet = new TreeSet<>();

    // LinkedHashSet extends the HashSet, keeps insertion order and is slightly slower than a HashSet
    Set<String> linkedHashSet = new LinkedHashSet<>();

    // we can transform a collection to a Set to eliminate duplicates
    collection = new HashSet(collection);   // if we used new LinkedHashSet we could preserve the order

    // we can also transform it using a stream and a collector
    collection.stream().collect(Collectors.toSet());

    // java also provides 2 special purpose set implementations :

    /*
     * The EnumSet is a high performance set implementation for enumeration types, there are two cool ways to create
     * an EnumSet
     */
    Set<DayOfWeek> enumSet = EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.SUNDAY);                             // through a range of values
    Set<DayOfWeek> anotherEnumSet = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY);    // by specifying values

    /*
     * CopyOnWriteArraySet is a Set implementation backed up by a copy-on-write array. All mutative operations, such as add, set, and remove,
     * are implemented by making a new copy of the array; no locking is ever required. Even iteration may safely proceed concurrently with element insertion and deletion.
     * Unlike most Set implementations, the add, remove, and contains methods require time proportional to the size of the set.
     * This implementation is only appropriate for sets that are rarely modified but frequently iterated.
     * It is well suited to maintaining event-handler lists that must prevent duplicates.
     * 
     * This was copied from oracle doc as it is, i have nothing to add or explain because i don't give a fuck about this type of set (for now at least)
     */
    Set<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

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
     * List have 2 main general purpose implementations in java
     */

    // ArrayList, the best performing implementation, backed by a resizable array, its default constructor start with a low initial capacity
    // that resize dynamically as we add elements, it is a better practice to construct it with a high capacity to avoid resizing cost
    List<String> arrayList = new ArrayList<>();

    // LinkedList, backed by a double linked list, have a better performance than ArrayList on add and remove, but worse on get and set
    // besides it implements the Deque interface allowing it to have its methods such as poll peek or offer
    List<String> linkedList = new LinkedList<>();

    /*
     * Copied from oracle doc :
     * 
     * If you frequently add elements to the beginning of the List or iterate over the List to delete elements from its interior, you should consider using LinkedList.
     * These operations require constant-time in a LinkedList and linear-time in an ArrayList. But you pay a big price in performance.
     * Positional access requires linear-time in a LinkedList and constant-time in an ArrayList. Furthermore, the constant factor for LinkedList is much worse.
     * If you think you want to use a LinkedList, measure the performance of your application with both LinkedList and ArrayList before making your choice; ArrayList is usually faster.
     */

    /*
     *  Vector is roughly an ArrayList (that's why i don't count 3 general purpose implementation) that is synchronized, ArrayList still is a better choice because :
     *  - in a thread safe ArrayList does not need synchronization, otherwise we can manually synchronize it
     *  - Both Vector and ArrayList increase in size as more elements are added, but Vector doubles size while ArrayList increase by 50%
     *  - Vectore, since it is synchronized, have more overhead than ArrayList
     */    
    List<String> vector = new Vector<>();

    /*
     * List have a special purpose implementation that is exactly analog to the CopyOnWriteArraySet 
     */
    List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    /*
     *  Stack is another implementation of the List, it extends Vector and represents a LIFO stack where only push on the top
     *  it has methods such as pop peek search and push and a constructor for an empty Stack  
     */
    List<String> stack = new Stack<>();

    /*
     * The Queue interface is a holder for data being about to be processed
     * 
     * I frankly doubt that queues are useful, all use cases i know can be done with a simple list
     * 
     * Elements are generally stored in FIFO but some implementation differ from that such as priority queues that store elements
     * regarding their order if they implement a Comparable or using a Comparator passed in a constructor
     * 
     * The main general Purpose implementations of Queue are PriorityQueue and LinkedList(through Deque)
     * 
     * LinkedList is the most used implementation
     * 
     * Concurrent Queue implementations are provided by the java.util.concurrent package and are :
     * 
     * LinkedBlockingQueue — an optionally bounded FIFO blocking queue backed by linked nodes
     * ArrayBlockingQueue — a bounded FIFO blocking queue backed by an array
     * PriorityBlockingQueue — an unbounded blocking priority queue backed by a heap
     * DelayQueue — a time-based scheduling queue backed by a heap
     * SynchronousQueue — a simple rendezvous mechanism that uses the BlockingQueue interface 
     * PriorityBlockingQueue is a blocking Queue used when thread safe is needed
     * 
     *  Bounded queues are the ones that restrict the number of elements they can contains, blocking means thread safe
     * 
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
     * The LinkedList implementation is more flexible than the ArrayDeque implementation.
     * LinkedList implements all optional list operations. null elements are allowed in the LinkedList implementation but not in the ArrayDeque implementation.
     * LinkedList is based on list while the ArrayDeque is based on a resizeable array
     * 
     * Generally ArrayDeque is better than LinkedList as a deque(add and remove operation at both ends)
     * 
     * same thing for the 6 methods we talked about in queues but we now have 12 of them with First and Last appended to each
     * addFirst -> offerFirst / addLast -> offerLast
     * removeFirst -> pollFirst / removeLast -> pollLast
     * getFirst -> peekFirst / getLast -> peekLAst
     * 
     * there is a concurrent implementation of deque which is LinkingBlockingDeque but we don't give a shit about it
     */
    Deque<String> arrayDeque = new ArrayDeque<>();

    /*
     * The Map interface does not belong to the collection framework since it does not extend the Collection interface
     * A Map maps keys to values, the keys cannot be duplicates
     * 
     * The 3 general purpose implementations of Map are HashMap, TreeMap and LinkedHashMap and they are analog in comparison to 
     * HashSet, TreeSet, LinkedHashSet (really comparable even in the implemented/ extended classes and interface names)
     * 
     * Since the TreeMap implements the SortedMap in an analog way with TreeSet implements SortedSet, the SortedMap add its methods to it
     * which are : 
     * range view : subMap, headMap, tailMap
     * Endpoints : firstKey and lastKey
     * Comparator access : comparator
     * 
     * Map have basic operations (such as put, get, containsKey, containsValue, size, and isEmpty) bulk operations (such as putAll and clear)
     * and collection views operations (such as keySet, entrySet, and values)
     * 
     * Like the Set and List interfaces, Map strengthens the requirements on the equals and hashCode methods so two Map instances are equal if they represent the same key-value mappings.
     * 
     * There is a rule on classes that can be made map keys, actually you can make any class a key for a map but if it does not follow these rules it will cause problems
     * 1) the class should override equal and hashCode methods of Object, otherwise we can put two map entries with the same key object since we cannot compare them
     * 2) the class should be immutable, otherwise any change on an existing key (its hashCode is changed as a consequence) will make the entry to be lost forever
     * 
     * All of Map's implementations use equal method to compare keys, except the IdentityHashMap that uses the == operator and violates this contract
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
     * Even if Map 3 general purposes are comparable to the Set's ones, LinkedHashMap provides two capabilities that are not available with LinkedHashSet :
     * 1) You can order it based on key access rather than insertion, meaning when you look up the value associated with a key, that key will be moved to the end of the map
     * 2) LinkedHashMap provides the removeEldestEntry method that we can override to impose a way for removing old entries automatically when new ones are added to the map.
     *    This makes it very easy to implement a custom cache like the following example that keeps only the 100 recent entries
     *    
     *    protected boolean removeEldestEntry(Entry<Integer, String> eldest) {
     *      return size() > 100;
     *    }
     */
    
    /*
     * There are 3 special purpose Map implementations which are EnumMap, WeakHashMap and IdentityHashMap :
     */
     
    /*
     *  EnumMap is a high-performance Map implementation for use with enum keys
     */
    Map<DayOfWeek, String> enumMap = new EnumMap<>(DayOfWeek.class);
    
    /*
     * WeakHashMap is an implementation of the Map interface that stores only weak references to its keys
     * Storing only weak references allows a key-value pair to be garbage-collected when its key is no longer referenced outside of the WeakHashMap
     * This class provides the easiest way to harness the power of weak references
     * It is useful for implementing "registry-like" data structures, where the utility of an entry vanishes when its key is no longer reachable by any thread.
     */
    Map<Person, String> weakHashMap = new WeakHashMap<>();
    
    /*
     * IdentityHashMap is an identity-based Map implementation based on a hash table
     * This class is useful for topology-preserving object graph transformations, such as serialization or deep-copying
     * To perform such transformations, you need to maintain an identity-based "node table" that keeps track of which objects have already been seen
     * Identity-based maps are also used to maintain object-to-meta-information mappings in dynamic debuggers and similar systems
     * Identity-based maps are useful in thwarting "spoof attacks" that are a result of intentionally perverse equals methods because
     * IdentityHashMap never invokes the equals method on its keys. An added benefit of this implementation is that it is fast.
     * 
     * In practice IdentityHashMap uses == instead of equals to compare keys, that's why it is faster and does not need keys to be immutable
     */
    Map<Person, String> identityHashMap = new IdentityHashMap<>();

    /*
     * There is also a concurrent Implementation of the Map interface, it is the ConcurrentMap interface provided by the java.util.concurrent package
     * which extends Map with atomic putIfAbsent, remove, and replace methods
     * ConcurrentHashMap implementation of that interface is a highly concurrent, high-performance implementation backed up by a hash table
     * This implementation never blocks when performing retrievals and allows the client to select the concurrency level for updates.
     * It is intended as a drop-in replacement for Hashtable: in addition to implementing ConcurrentMap, it supports all the legacy methods peculiar to Hashtable.
     */
    ConcurrentMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
    
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
    
    /*
     * Wrapper implementations : 
     * 
     * Other that the sort method, the Collections class is a class that provides only static methods
     * Among these static methods we can find synchronization wrappers that provides a synchronization version for each one of the
     * 8 core interfaces (Collection, List, Set, Map, SortedMap, SortedSet, NavigableMap, NavigableSet)
     * 
     * here are some examples :
     */
    Collection<String> synchronizedCollection = Collections.synchronizedCollection(collection);
    List<String> synchronizedList = Collections.synchronizedList(arrayList);
    Set<String> synchronizedSet = Collections.synchronizedSet(hashSet);
    Map<Integer, String> synchroinizedMap = Collections.synchronizedMap(hashMap);
    
    /*
     * There returned implementations are as much thread safe as a Vector for example, but for concurrent access you need to synchronize the code block on it
     * because the concurrent calls will be on the original collections
     */
    synchronized(synchronizedCollection) {
      for (String s: synchronizedCollection) {
        System.out.println(s);
      }
    }
    
    /*
     * The only down side of these wrappers it's that does not allow to call interface implementation methods, they return one of the core interfaces and never
     * a given implementation like ArrayList or HashMap for example
     */
    
    /*
     * There is another interesting set of wrappers called unmodified wrappers that returns an immutable version of the 8 core interfaces
     */
    Collection<String> immutableCollection = Collections.unmodifiableCollection(collection);
    List<String> immutableList = Collections.unmodifiableList(arrayList);
    Set<String> immutableSet = Collections.unmodifiableSet(hashSet);
    Map<Integer, String> immutableMap = Collections.unmodifiableMap(hashMap);
    
    /*
     * Let's see another interesting set of wrappers called checked wrappers, it tackles a problem with generic collections:   
     * The generic system provided by java is a static one, the checking is done in compile time, such a system can be beaten simply by
     * unchecked casts for example (we could add an object that does not fit in the collection for example)
     * The checked wrappers provide a dynamically type safe view of the collection that eliminates such risks
     * Again, this wrapper is provided for the 8 core interfaces and Queue interface
     */
    Collection<String> typeSafeCollection = Collections.checkedCollection(collection, String.class);
    List<String> typeSafeList = Collections.checkedList(arrayList, String.class);
    Set<String> typeSafeSet = Collections.checkedSet(hashSet, String.class);
    Queue<String> typeSafeQueue = Collections.checkedQueue(priorityQueue, String.class);
    Map<Integer, String> typeSafeMap = Collections.checkedMap(hashMap, Integer.class, String.class);
    
    /*
     * Convenience implementations : 
     * 
     * The Arrays.asList method returns a list view over an array, permitting passing the array as an argument for when you need a Collection
     * or a List, but the resulting list cannot be modified in size and if you try you get an UnsupportedOperationException
     * this is also a way to create a fixed size list
     */
    List<Integer> arrayAsList = Arrays.asList(new Integer[3]);
    
    /*
     * Creating and initializing an immutable collection with n times the same value 
     */
    List<String> nTimesList = new ArrayList<>(Collections.nCopies(100, "value"));
    
    /*
     * Creating an immutable collection(set , list or map) with a single value, can be used for methods that need a collections as argument
     */
    Set<Integer> singleValSet = Collections.singleton(1);
    List<Integer> singleValList = Collections.singletonList(2);
    Map<Integer, String> singleValMap = Collections.singletonMap(3, "three");
    
    /*
     * Empty lists , sets or map that are immutable
     */
    Set<String> emptySet = Collections.EMPTY_SET;
    List<String> emptyList = Collections.EMPTY_LIST;
    Map<Integer, String> emptyMap = Collections.EMPTY_MAP;
    
    /*
     * The Collections class also provides some useful algorithms that the great majority work only for lists
     * Here are some of the most important ones :
     */
    
    /*
     * The sort method sorts a list using the merge sort algorithm that is fast (n log(n)) and stable
     */
    Collections.sort(arrayList);                              // form that sorts with the natural order
    
    List<ComparablePerson> personList = new ArrayList<>();
    Collections.sort(personList, new PersonComparator());     // form that takes a comparator
    
    /*
     * The shuffle method does the inverse of sort, destroys any trace of order using a source of randomness
     */
    Collections.shuffle(arrayList);                   // form using default random
    Collections.shuffle(arrayList, new Random());     // form that can take a source of randomness
    
    /*
     * Collections also provide 5 routine data manipulation operations on lists 
     */
    Collections.reverse(arrayList);                           // reverse the order of all elements
    Collections.swap(arrayList, 5, 14);                       // swaps two elements given their indexes
    Collections.fill(arrayList, "default");                   // replace all elements by the passed value
    Collections.copy(new ArrayList<String>(), arrayList);     // copies the elements of the second argument into the first overriding its elements (at least the same size, the rest is unaffected)
    Collections.addAll(arrayList, "newVal");                  // adds all element in the second to last argument (or the array) into the list given as first argument
    
    /*
     * The search method locates the position of a given value in a list using the binary search algorithm
     * it returns the index of the searched value if the list contains it, if not it returns -(insertion point) -1 where insertion point is where the element should be placed
     * this method require the list to be sorted and has two forms, one that takes the list and the search value where the list should be ordered in the natural order
     * the other form takes also a comparator 
     */
    Collections.binarySearch(arrayList, "someValue");       // natural order form
    Collections.binarySearch(personList, new ComparablePerson("john", "doe"), new PersonComparator());
    
    /*
     * Composition method provided by Collections are frequency and disjoint methods 
     */
    Collections.frequency(arrayList, "val");          // return the number of time where a value is present in the list
    Collections.disjoint(arrayList, personList);      // tests if two lists are disjoint meaning they have no element in common
    
    /*
     * Extreme values methods return the minimum and the maximum of a list by the natural order or the order using a given Comparator
     */
    Collections.min(arrayList);                             // minimum by natural order
    Collections.max(personList, new PersonComparator());    // maximum given a comparator
    
    /*
     * A words about custom collections, you can extend one of the provided abstract classes, implement its abstract method to get a custom collection
     * that provides your needed custom functionalities 
     * abstract classes are :
     * - AbstractCollection : Collection that is neither a Set nor a List. you must provide the iterator and the size methods.
     * - AbstractSet : Set, use is identical to AbstractCollection.
     * - AbstractList : List backed up by a random-access data store (array). you must provide the positional access methods and the size method.
     *                  The abstract class takes care of listIterator (and iterator).
     * - AbstractSequentialList : List backed up by a sequential-access data store (linked list). you must provide the listIterator and size methods.
     *                            The abstract class takes care of the positional access methods. (This is the opposite of AbstractList.)
     * - AbstractQueue : you must provide the offer, peek, poll, and size methods and an iterator supporting remove.
     * - AbstractMap : Map. you must provide the entrySet view. This is typically implemented with the AbstractSet class.
     *                 If the Map is modifiable, you must also provide the put method. 
     */

    // algorithms, complexity(definition and algorithms) and thread safety for all of the above, also retrieval type (random ...)
    // search sort, shuffle,  algorithms with complexity and types
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

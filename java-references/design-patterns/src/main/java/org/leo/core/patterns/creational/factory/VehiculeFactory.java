package org.leo.core.patterns.creational.factory;

import java.util.HashMap;
import java.util.Map;

import org.leo.core.objects.vehicules.Bike;
import org.leo.core.objects.vehicules.Car;
import org.leo.core.objects.vehicules.Truck;
import org.leo.core.objects.vehicules.Vehicule;
import org.leo.core.objects.vehicules.VehiculeType;

/**
 * Factory Design Pattern Implementation with registration (avoiding modifying factory when adding new implementations)
 * 
 * This patterns is used to create objects of the same family but with different behaviors
 * This class contains the logic of creating objects from the Shape family given a String criteria
 * 
 * This implementation of factory relies in switch logic and an Abstract class as a family
 * Also vehicules criteria are tested against an enumeration values
 * @author fahd
 */
@SuppressWarnings("rawtypes")
public class VehiculeFactory {

  // implementation without registration
  public static Vehicule buildCar(VehiculeType type) {
    Vehicule vehicule = null;
    
    switch (type) {
      case CAR:
        vehicule = new Car();
        break;

      case TRUCK:
        vehicule = new Truck();
        break;

      case BIKE:
        vehicule = new Bike();
        break;

      default:
        // throw some exception
        break;
    }
    
    return vehicule;
  }
  
  // Implementation with registration without reflection (the best)
  public static Vehicule buildRegistredCar(VehiculeType type) {
    return (Vehicule)VehiculeFactory.getInstance().registredVehicules.get(type).createVehicule();
  }
  
  // Implementation with registration and reflection (reflection mechanism makes execution slower)
  public static Vehicule buildReflectionRegistredCar(VehiculeType type) {
    Vehicule vehicule = null;
    Class clazz = getInstance().reflectionRegistredVehicules.get(type);
    
    //now here is the reflection part
    try {
      // we are calling the default constructor with no args
      vehicule = (Vehicule)clazz.newInstance();
      
      /* if we want to call a declared constructor that takes a string for example we can do this :
       * 
      Constructor c = clazz.getDeclaredConstructor(new Class[] {String.class}); // as much values of Class as the desired constructors args number
      vehicule = (Vehicule)c.newInstance(new Object[] {"value for the constructor's string arg"}); // as much values of Object as the desired constructor args number
      */
    } catch (ReflectiveOperationException e) {
      e.printStackTrace();
    }
    
    return vehicule;
  }
  
  /**
   * Factory instance needed to register vehicles from outside this class
   * Note that this cannot be used in abstract factories since no instances
   * are possible for abstract classes
   */
  public static VehiculeFactory getInstance() {
    return instance;
  }
  
  /**
   * Register vehicle without reflection from concrete classes
   */
  public static void registerVehicule(VehiculeType id, Vehicule v) {
    getInstance().registredVehicules.put(id, v);
  }
  
  /**
   * Register vehicle with reflection from concrete classes
   */
  public static void registerVehiculeWithReflection(VehiculeType id, Class c) {
    getInstance().reflectionRegistredVehicules.put(id, c);
  }
  
  // DATA MEMBERS
  private Map<VehiculeType, Vehicule>   registredVehicules = new HashMap<VehiculeType, Vehicule>();
  private Map<VehiculeType, Class>      reflectionRegistredVehicules = new HashMap<VehiculeType, Class>();
  private static VehiculeFactory        instance = new VehiculeFactory();
}

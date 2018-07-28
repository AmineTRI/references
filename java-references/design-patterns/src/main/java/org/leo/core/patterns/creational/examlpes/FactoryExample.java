package org.leo.core.patterns.creational.examlpes;

import org.leo.core.objects.vehicules.Vehicule;
import org.leo.core.objects.vehicules.VehiculeType;
import org.leo.core.patterns.creational.factory.VehiculeFactory;

/**
 * Testing Factory Design Pattern
 * @author fahd
 */
public class FactoryExample {

  public static void main(String[] args) {

    //Testing regular factory without registration
    //get an object of Car
    Vehicule vehicule1 = VehiculeFactory.buildCar(VehiculeType.CAR);
    vehicule1.construct();
    
    //get an object of Truck
    Vehicule vehicule2 = VehiculeFactory.buildCar(VehiculeType.TRUCK);
    vehicule2.construct();
    
    //get an object of Bike
    Vehicule vehicule3 = VehiculeFactory.buildCar(VehiculeType.BIKE);
    vehicule3.construct();
    
    //Testing factory with registration
    Vehicule vehicule4 = VehiculeFactory.buildRegistredCar(VehiculeType.TRUCK);
    vehicule4.construct();
    
    //Testing factory with registration using reflection
    Vehicule vehicule5 = VehiculeFactory.buildReflectionRegistredCar(VehiculeType.CAR);
    vehicule5.construct();
  }
}

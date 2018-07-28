package org.leo.core.objects.vehicules;

import org.leo.core.patterns.creational.factory.VehiculeFactory;

@SuppressWarnings("static-access")
public class Car extends Vehicule {

  static {
    // registration without reflection
    VehiculeFactory.getInstance().registerVehicule(VehiculeType.CAR, new Car());

    // registration with reflection
    VehiculeFactory.getInstance().registerVehiculeWithReflection(VehiculeType.CAR, Car.class);
  }

  public Car() {
    super(VehiculeType.CAR);
  }

  @Override
  public void construct() {
    System.out.println("Building Car");
    // add accessories
  }
  
  @Override
  public Vehicule createVehicule() {
    return new Car();
  }
}

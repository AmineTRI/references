package org.leo.core.objects.vehicules;

import org.leo.core.patterns.creational.factory.VehiculeFactory;

@SuppressWarnings("static-access")
public class Truck extends Vehicule {

  static {
    // registration without reflection
    VehiculeFactory.getInstance().registerVehicule(VehiculeType.TRUCK, new Truck());

    // registration with reflection
    VehiculeFactory.getInstance().registerVehiculeWithReflection(VehiculeType.TRUCK, Truck.class);
  }

  public Truck() {
    super(VehiculeType.TRUCK);
  }

  @Override
  public void construct() {
    System.out.println("Building Truck");
    // add accessories
  }
  
  @Override
  public Vehicule createVehicule() {
    return new Truck();
  }
}

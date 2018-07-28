package org.leo.core.objects.vehicules;

import org.leo.core.patterns.creational.factory.VehiculeFactory;

@SuppressWarnings("static-access")
public class Bike extends Vehicule {

  static {
    // registration without reflection
    VehiculeFactory.getInstance().registerVehicule(VehiculeType.BIKE, new Bike());
    
    // registration with reflection
    VehiculeFactory.getInstance().registerVehiculeWithReflection(VehiculeType.BIKE, Bike.class);
  }
  
  public Bike() {
    super(VehiculeType.BIKE);
  }

  @Override
  public void construct() {
    System.out.println("Building Bike");
    // add accessories
  }
  
  @Override
  public Vehicule createVehicule() {
    return new Bike();
  }
}

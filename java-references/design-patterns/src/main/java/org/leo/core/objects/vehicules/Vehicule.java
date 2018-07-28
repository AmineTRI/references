package org.leo.core.objects.vehicules;

public abstract class Vehicule {

  public Vehicule(VehiculeType type) {
    this.type = type;
    arrangeParts();
  }

  private void arrangeParts() {
    // Do one time processing here
  }

  // Do subclass level processing in this method
  public abstract void construct();
  
  // registration without reflection instanciation method
  public abstract Vehicule createVehicule();

  public VehiculeType getModel() {
    return type;
  }

  public void setModel(VehiculeType type) {
    this.type = type;
  }
  
  // DATA MEMBERS
  private VehiculeType type = null;
}

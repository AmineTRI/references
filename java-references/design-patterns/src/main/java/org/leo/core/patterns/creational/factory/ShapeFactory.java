package org.leo.core.patterns.creational.factory;

import org.leo.core.objects.Drawable;
import org.leo.core.objects.shapes.Circle;
import org.leo.core.objects.shapes.Rectangle;
import org.leo.core.objects.shapes.Shape;
import org.leo.core.objects.shapes.Square;
import org.leo.core.objects.shapes.Triangle;

/**
 * Factory Design Pattern Implementation
 * 
 * This patterns is used to create objects of the same family but with different behaviors
 * This class contains the logic of creating objects from the Shape family given a String criteria
 * 
 * This implementation of factory relies in conditional logic and an Interface as a family
 * Also shapes criteria are tested against constants in the Shape Interface
 * @author fahd
 */
public class ShapeFactory extends AbstractDrawableFactory {
  
  public Shape getShape(String shape) {
    if (shape == null) {
      return null;
    }
    
    if (shape.equals(Shape.CIRCLE)) {
      return new Circle();
    } else if (shape.equals(Shape.RECTANGLE)) {
      return new Rectangle();
    } else if (shape.equals(Shape.SQUARE)) {
      return new Square();
    } else if (shape.equals(Shape.TRIANGLE)) {
      return new Triangle();
    } 
    
    return null;
  }

  @Override
  public Drawable getDrawable(String criteria) {
    return getShape(criteria);
  }
}

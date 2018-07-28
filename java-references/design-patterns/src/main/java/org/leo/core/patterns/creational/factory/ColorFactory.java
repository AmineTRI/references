package org.leo.core.patterns.creational.factory;

import org.leo.core.objects.Drawable;
import org.leo.core.objects.colors.Blue;
import org.leo.core.objects.colors.Color;
import org.leo.core.objects.colors.Green;
import org.leo.core.objects.colors.Red;
import org.leo.core.objects.colors.Yellow;

/**
 * Factory Design Pattern Implementation
 * 
 * This patterns is used to create objects of the same family but with different behaviors
 * This class contains the logic of creating objects from the Color family given a String criteria
 * 
 * This implementation of factory relies in conditional logic and an Interface as a family
 * Also shapes criteria are tested against constants in the Shape Interface
 * @author fahd
 */
public class ColorFactory extends AbstractDrawableFactory {
  
  public Color getColor(String color) {
    if (color == null) {
      return null;
    }
    
    if (color.equals(Color.RED)) {
      return new Red();
    } else if (color.equals(Color.BLUE)) {
      return new Blue();
    } else if (color.equals(Color.GREEN)) {
      return new Green();
    } else if (color.equals(Color.YELLOW)) {
      return new Yellow();
    } 
    
    return null;
  }

  @Override
  public Drawable getDrawable(String criteria) {
    return getColor(criteria);
  }
}

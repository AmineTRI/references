package org.leo.core.patterns.creational.factory;

import org.leo.core.objects.Drawable;

/**
 * Abstract Factory Implementation
 * 
 * This class is extended by the all the Factories
 * @author fahd
 */
public abstract class AbstractDrawableFactory {
  
  
  public static AbstractDrawableFactory getFactory(String factory) {
    AbstractDrawableFactory f = null;
    
    switch(factory) {
      case "Color" :
        f =  new ColorFactory();
        break;
      case "Shape" :
        f =  new ShapeFactory();
        break;
      default:
        break;
    }
    
    return f;
  }
  
  public abstract Drawable getDrawable(String criteria);
  
}

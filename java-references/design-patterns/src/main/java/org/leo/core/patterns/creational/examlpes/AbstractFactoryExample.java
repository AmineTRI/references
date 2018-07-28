package org.leo.core.patterns.creational.examlpes;

import org.leo.core.objects.Drawable;
import org.leo.core.objects.colors.Color;
import org.leo.core.objects.shapes.Shape;
import org.leo.core.patterns.creational.factory.AbstractDrawableFactory;

/**
 * Testing abstract factory pattern
 * @author fahd
 */
public class AbstractFactoryExample {

  public static void main(String[] args) {
    // drawing a circle
    Drawable d1 = AbstractDrawableFactory.getFactory("Shape").getDrawable(Shape.CIRCLE);
    d1.draw();
    
    // drawing a square
    Drawable d2 = AbstractDrawableFactory.getFactory("Shape").getDrawable(Shape.SQUARE);
    d2.draw();
    
    // filling with blue
    Drawable d3 = AbstractDrawableFactory.getFactory("Color").getDrawable(Color.BLUE);
    d3.draw();
    
    // filling with red
    Drawable d4 = AbstractDrawableFactory.getFactory("Color").getDrawable(Color.RED);
    d4.draw();
  }

}

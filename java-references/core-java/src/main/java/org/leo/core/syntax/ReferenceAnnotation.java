package org.leo.core.syntax;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations are used to add meta data about the program
 * they can be used examined at compile time or at runtime
 * they can also be used by some software tools that generates code, XML and any other
 * stuff (think about LOMBOK for example)
 * 
 * Annotation do not do any action, their meta data can be accessed using java reflection
 * The retention policy annotation (to use on annotations) defines when the meta data should be discarded
 * There are three levels of retention policy (SOURCE(discarded before compile time), CLASS which is the default and discards before runtime, RUNTIME)
 * 
 * By default an annotation can have any target, but we can specify a restriction using the target annotation
 * which is an array
 * 
 * Java also comes with some predefined annotations like override or deprecated
 * All annotations extend lang.Annotation and cannot extend anything
 * 
 * Only primitive types, String, class, arrays (1 dimension), annotations, enumerations can be
 * used as annotations elements
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ReferenceAnnotation {

  // annotation can have constants (any field is implicitly constant: public static final)
  public static final int count = 0;

  // annotations elements are implicitly public abstract
  public abstract String parameterOne();

  int parameterTwo();

  // we can also have array elements, that can be used with {} or one single value
  String[] paramList();

  // elements with default can be skipped when using the annotation
  String someParameter() default "default param value ";
  
  // this inner annotation is only used to show the use of single parameter annotations
  public @interface OneParamAnnotation {
    // if the annotation have a single element, which name should be value
    // we can omit writing value when using the annotation (look down here)
    String value();
  }
  
  // this inner class (that can only be public by the way) is only here to show how we use the annotation
  @ReferenceAnnotation(
    parameterOne="one",
    parameterTwo=3,
    paramList= {"val1", "val2"}
    // we are skipping someParameter but we could have wrote a value here
  )
  @OneParamAnnotation("theValue")
  public class uselessInnerClass {
  }
}

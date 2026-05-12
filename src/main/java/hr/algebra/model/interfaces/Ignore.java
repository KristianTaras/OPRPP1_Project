package hr.algebra.model.interfaces;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//setting of the annotation
@Retention(RetentionPolicy.RUNTIME)
//Runtime - sticker stays stuck even when the program is running, enables reflection
//Other states - source (sticker exists only while writing .java file, when .class file is compiled it disappears)
//and class ( sticker stays in .class file, but JVM ignores it when the program runs)
@Target(ElementType.FIELD)
public @interface Ignore {
    //@interface for defining annotations (metadata/sticker), presence checked through reflection
}

package main.common.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface RenderInfo {

    public String label() default "";

    public String description() default "";
    
    public String prompt() default "";

    public String defaultValue() default "";

    public boolean includeInInfo() default true;
}
   
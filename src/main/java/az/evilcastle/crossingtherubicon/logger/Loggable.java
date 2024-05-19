package az.evilcastle.crossingtherubicon.logger;

import az.evilcastle.crossingtherubicon.model.constant.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Loggable {

    LogLevel level() default LogLevel.DEBUG;

    int[] args() default {};
}

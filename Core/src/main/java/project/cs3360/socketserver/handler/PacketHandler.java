package project.cs3360.socketserver.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandler {
    Priority priority() default Priority.NORMAL;
}

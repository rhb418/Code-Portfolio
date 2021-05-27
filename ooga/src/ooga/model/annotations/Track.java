package ooga.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ooga.model.components.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Track {

  Class<? extends Component>[] value() default {};
}

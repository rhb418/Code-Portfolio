package ooga.model.util;

import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import java.lang.reflect.Modifier;
import org.reflections.Reflections;

public class AdapterFactory {
  public static PolymorphicJsonAdapterFactory createAdapterOfType(Class superClass, String label) {
    PolymorphicJsonAdapterFactory adapter = PolymorphicJsonAdapterFactory.of(superClass, label);
    Reflections reflections = new Reflections(superClass.getPackageName());
    for (Object subclass : reflections.getSubTypesOf(superClass)) {
      Class subClazz = (Class) subclass;
      if (!Modifier.isAbstract(subClazz.getModifiers()))
        adapter = adapter.withSubtype(subClazz, subClazz.getSimpleName());
    }
    return adapter;
  }
}

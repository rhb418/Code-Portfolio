package ooga.model;


import static org.junit.jupiter.api.Assertions.assertTrue;

import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import ooga.model.components.Component;
import ooga.model.util.AdapterFactory;
import org.junit.jupiter.api.Test;

public class AdapterFactoryTest {

  @Test
  void createAdapter() {
    PolymorphicJsonAdapterFactory adapter = AdapterFactory.createAdapterOfType(Component.class, "type");
    assertTrue(adapter != null);
  }
}

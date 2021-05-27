package ooga.model;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ooga.model.components.Component;
import ooga.model.exceptions.InvalidDataFileException;
import ooga.model.exceptions.NotADirectoryException;
import ooga.model.objects.EntityManagerAdapter;
import ooga.model.objects.GameObject;
import ooga.model.objects.ObjectFactory;
import ooga.model.observables.ObservableObject;
import ooga.model.util.FileReader;
import org.reflections.Reflections;

public class LevelFactory {

  private JsonAdapter<GameLevel> levelAdapter;
  private ObjectFactory objectFactory;

  public LevelFactory(File objectsDir) throws FileNotFoundException {
    if (!objectsDir.isDirectory()) {
      throw new NotADirectoryException(objectsDir.getName());
    }

    Map<String, GameObject> presetMap = new HashMap<>();

    PolymorphicJsonAdapterFactory<Component> componentAdapter = createComponentAdapter();
    Moshi moshi = new Moshi.Builder().add(componentAdapter).build();

    Type type = Types.newParameterizedType(List.class, GameObject.class);
    JsonAdapter<List<GameObject>> adapter = moshi.adapter(type);

    File[] objectFiles = objectsDir.listFiles((dir, name) -> name.contains(".json"));

    for (File objectFile : objectFiles) {
      addObjects(objectFile, adapter, presetMap);
    }

    objectFactory = new ObjectFactory(presetMap);
  }

  public static PolymorphicJsonAdapterFactory<Component> createComponentAdapter() {
    PolymorphicJsonAdapterFactory<Component> adapter = PolymorphicJsonAdapterFactory
        .of(Component.class, "type");
    Reflections reflections = new Reflections(Component.class.getPackageName());
    for (Class subclass : reflections.getSubTypesOf(Component.class)) {
      adapter = adapter.withSubtype(subclass, subclass.getSimpleName());
    }
    return adapter;
  }

  private void addObjects(File objectsFile, JsonAdapter<List<GameObject>> adapter,
      Map<String, GameObject> presetMap) throws FileNotFoundException, InvalidDataFileException {
    String objectsText = FileReader.readFile(objectsFile);

    List<GameObject> objectPresets;
    try {
      objectPresets = adapter.fromJson(objectsText);
    } catch (IOException e) {
      throw new InvalidDataFileException(objectsFile.getName());
    }

    for (GameObject object : objectPresets) {
      presetMap.put(object.getName(), object);
    }
  }

  Level buildLevel(File levelFile) throws FileNotFoundException, InvalidDataFileException {
    EntityManagerAdapter entityManagerAdapter = new EntityManagerAdapter(objectFactory);
    Moshi objectMoshi = new Moshi.Builder().add(entityManagerAdapter).build();
    JsonAdapter<GameLevel> levelAdapter = objectMoshi.adapter(GameLevel.class);

    String levelText;
    try {
      levelText = FileReader.readFile(levelFile);
    } catch (IOException e) {
      throw new FileNotFoundException(levelFile.getName());
    }

    GameLevel newLevel;
    try {
      newLevel = levelAdapter.fromJson(levelText);
    } catch (IOException e) {
      throw new InvalidDataFileException(levelFile.getName());
    }

    newLevel.init();
    return newLevel;
  }
}

package ooga.model;

import com.squareup.moshi.JsonDataException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import ooga.model.exceptions.NotADirectoryException;
import ooga.model.objects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LevelFactoryTest {

  LevelFactory factory;
  String exampleDir = "example/";
  String objectDirectory = exampleDir + "objects";
  Map<String, Integer> objectsCount;

  String badLevelFile = exampleDir + "levels/level2.json";
  String basicLevelFile = exampleDir + "levels/level1.json";
  int basicLevelObjectsCount = 42;
  int newObjectsCount;
  Level basicLevel;

  @BeforeEach
  void setup() throws IOException, URISyntaxException {
    objectsCount = new HashMap<>();
    newObjectsCount = 0;
    factory = new LevelFactory(getFile(objectDirectory));
  }

  private void incrementObjectsCount(GameObject object) {
      objectsCount.putIfAbsent(object.getName(), 0);
      objectsCount.put(object.getName(), objectsCount.get(object.getName()) + 1);
  }


  @Test
  void buildBasicLevelTest() throws URISyntaxException, FileNotFoundException {
    basicLevel = factory.buildLevel(getFile(basicLevelFile));
    assertEquals(basicLevelObjectsCount, basicLevel.generateObjects().size());
  }

  @Test
  void assertCorrectObjects() throws URISyntaxException, FileNotFoundException {
    basicLevel = factory.buildLevel(getFile(basicLevelFile));
    basicLevel.generateObjects().forEach(this::incrementObjectsCount);

    int goombaCount = 1;
    String goombaName = "goomba";
    int marioCount = 1;
    String marioName = "mario";
    int qBlockCount = 3;
    String qBlockName = "qblock";
    int brickCount = 3;
    String brickName = "brick";
    int blockCount = 34;
    String blockName = "block";

    assertEquals(goombaCount, objectsCount.get(goombaName));
    assertEquals(marioCount, objectsCount.get(marioName));
    assertEquals(qBlockCount, objectsCount.get(qBlockName));
    assertEquals(brickCount, objectsCount.get(brickName));
    assertEquals(blockCount, objectsCount.get(blockName));
  }

  @Test
  void assertIncorrectObjects() {
    assertThrows(JsonDataException.class, () -> factory.buildLevel(getFile(badLevelFile)));
  }

  @Test
  void assertNotADirectory() {
    assertThrows(NotADirectoryException.class, () -> new LevelFactory(getFile(badLevelFile)));
  }

  File getFile(String fileName) throws URISyntaxException {
    return new File(getClass().getClassLoader().getResource(fileName).toURI());
  }
}

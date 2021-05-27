package ooga.model;

import com.google.common.base.Preconditions;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Consumer;
import ooga.model.exceptions.DirectoryNotFoundException;
import ooga.model.exceptions.InvalidDataFileException;
import ooga.model.exceptions.NotADirectoryException;
import ooga.model.exceptions.RequiredFileNotFoundException;
import ooga.model.observables.ObservableLevel;
import ooga.model.observables.ObservableModel;
import ooga.model.observables.ObservableObject;
import ooga.model.util.FileReader;

public class Model implements ObservableModel {

  private Level currentLevel;
  private File[] levels;
  private LevelFactory levelFactory;
  private String FILE_EXTENSION = ".json";
  private File levelsDir;
  private GameConfiguration config;
  private String OBJECTS_DIRECTORY_NAME = "objects";
  private String LEVELS_DIRECTORY_NAME = "levels";
  private String CONFIG_FILE_NAME = "config" + FILE_EXTENSION;
  private Consumer<ObservableLevel> levelChangeCallback;
  private Consumer<Boolean> endGameCallback;

  public Model() {
    String name = Preconditions.checkNotNull("osjfa");
  }

  public void setGame(File directory) throws FileNotFoundException, InvalidDataFileException {
    if (!directory.isDirectory()) {
      throw new NotADirectoryException(directory.getName());
    }

    File objectsDir;
    try {
      objectsDir = FileReader.getFile(directory, OBJECTS_DIRECTORY_NAME);
    } catch (FileNotFoundException e) {
      throw new DirectoryNotFoundException(OBJECTS_DIRECTORY_NAME);
    }

    levelFactory = new LevelFactory(objectsDir);

    try {
      levelsDir = FileReader.getFile(directory, LEVELS_DIRECTORY_NAME);
    } catch (FileNotFoundException e) {
      throw new DirectoryNotFoundException(LEVELS_DIRECTORY_NAME);
    }

    File configFile;
    try {
      configFile = FileReader.getFile(directory, CONFIG_FILE_NAME);
    } catch (FileNotFoundException e) {
      throw new RequiredFileNotFoundException(CONFIG_FILE_NAME);
    }

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<GameConfiguration> adapter = moshi.adapter(GameConfiguration.class);
    try {
      config = adapter.fromJson(FileReader.readFile(configFile));
    } catch (IOException e) {
      throw new InvalidDataFileException(configFile.getName());
    }

    if (config == null) {
      throw new InvalidDataFileException(configFile.getName());
    }

    setCurrentLevel(config.getStartLevel());
  }

  public void setCurrentLevel(String levelName)
      throws FileNotFoundException, InvalidDataFileException {
    File levelFile = FileReader.getFile(levelsDir, levelName + FILE_EXTENSION);
    currentLevel = levelFactory.buildLevel(levelFile);

    currentLevel.setOnLevelEnd(this::changeLevel);

    notifyLevelChange();
  }

  private void changeLevel(boolean change){
      if(!change){
        try {
          setCurrentLevel("level" + currentLevel.getLevelNumber());
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
      else{
        try {
          int nextLevel = currentLevel.getLevelNumber()+ 1;
          setCurrentLevel("level" + nextLevel);
        } catch (FileNotFoundException e) {

          endGameCallback.accept(true);
        }
      }
  }

  private void notifyLevelChange() {
    if (levelChangeCallback != null) {
      levelChangeCallback.accept(currentLevel.asObservable());
    }
  }

  public void handleCode(String k, boolean on) {
    currentLevel.handleCode(k, on);
  }


  public void step(double deltaTime) {
    currentLevel.update(deltaTime);
  }


  @Override
  public void setOnLevelChange(Consumer<ObservableLevel> callback) {
    levelChangeCallback = callback;
  }

  @Override
  public void setOnTextUpdate(Consumer<String> text) {

  }


  public void setOnGameEnd(Consumer<Boolean> callback) {
    endGameCallback = callback;
  }
}

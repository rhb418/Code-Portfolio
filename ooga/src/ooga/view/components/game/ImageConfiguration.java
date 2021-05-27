package ooga.view.components.game;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageConfiguration {

  private static final Logger logger = LogManager.getLogger(ImageConfiguration.class);
  private Map<String, String> codeToPath;
  private Map<String, Image> cachedImages;
  private String directory;

  public ImageConfiguration(String directory) {
    this.directory = directory;
    this.cachedImages = new HashMap<>();
    Moshi moshi = new Moshi.Builder().build();
    var type = Types.newParameterizedType(Map.class, String.class, String.class);
    JsonAdapter<Map<String, String>> mapAdapter = moshi.adapter(type);
    try {
      String json = Files.readString(Paths.get(directory, "images.json"));
      this.codeToPath = mapAdapter.fromJson(json);
      logger.info("Created image config {}", codeToPath);
    } catch (IOException e) {
      logger.warn("Couldn't create image config for path {}", directory);
    }
  }

  public Image getImage(String mode, double width, double height) {
    Image image;
    String fileName = codeToPath.get(mode);
    image = getImageFile("images/" + fileName, width, height);
    return image;
  }

  private Image getImageFile(String mode, double width, double height) {
    String fileName = codeToPath.get(mode);
    System.out.println(width);
    try {
      System.out.println(new File(directory, mode).toURI().toURL().toExternalForm());
      Image image =
          new Image(
              new File(directory, mode).toURI().toURL().toExternalForm(),
              width,
              height,
              false,
              true);
      cachedImages.put(mode, image);
      return image;
    } catch (MalformedURLException e) {

    }
    return null;
  }
}

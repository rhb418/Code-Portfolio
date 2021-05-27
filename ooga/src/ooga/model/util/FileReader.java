package ooga.model.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import ooga.model.exceptions.NotADirectoryException;

public class FileReader {

  public static String readFile(File toConvert) throws FileNotFoundException {
    Path filePath = toConvert.toPath();

    try {
      return Files.readString(filePath);
    } catch (IOException e) {
      throw new FileNotFoundException(toConvert.getName());
    }
  }

  public static File getFile(File directory, String fileName) throws FileNotFoundException {
    if (!directory.isDirectory()) {
      throw new NotADirectoryException(directory.getName());
    }
    File[] matches = directory.listFiles((file, s) -> s.equals(fileName));

    if (matches == null || matches.length == 0) {
      throw new FileNotFoundException();
    }

    return matches[0];
  }
}

package cellsociety.configuration;

import java.net.URISyntaxException;

public class StyleFileHandler extends FileHandler {

  /**
   * Creates a new SAXParser with ruleset defined in the StyleReader class and parses the input XML
   * file
   *
   * @param filename a String representing the name of the file input
   * @throws URISyntaxException an Exception if file cannot be converted to URI format for the File
   *                            class
   */
  public StyleFileHandler(String filename) throws URISyntaxException {
    super(filename, "Style");
  }
}

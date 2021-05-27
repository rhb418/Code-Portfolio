package cellsociety.configuration;

import java.net.URISyntaxException;

public class ConfigFileHandler extends FileHandler {

  private XMLReaderWriter myReader = new XMLReaderWriter();

  /**
   * Creates a new SAXParser with ruleset defined in the XMLReaderWriter class and parses the input
   * XML file
   *
   * @param filename a String representing the name of the file input
   * @throws URISyntaxException an Exception if file cannot be converted to URI format for the File
   *                            class
   */
  public ConfigFileHandler(String filename) throws URISyntaxException {
    super(filename, "Config");
  }
}

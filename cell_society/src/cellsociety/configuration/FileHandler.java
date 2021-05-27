package cellsociety.configuration;

import java.io.File;
import java.net.URISyntaxException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

public abstract class FileHandler {

  private DefaultHandler myReader;

  /**
   * Defines a new FileHandler type object that passes a file to be read by a specific XML Parser
   *
   * @param filename   a String for the name of a file
   * @param readerType a String for what type of parser being created
   * @throws URISyntaxException an Exception thrown when file is invalid
   */
  public FileHandler(String filename, String readerType) throws URISyntaxException {
    try {
      initializeReader(readerType);
      File file = new File(getClass().getClassLoader().getResource(filename).toURI());
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      saxParser.parse(file, myReader);
    } catch (Exception e) {
      throw new URISyntaxException("", "Invalid input file");
    }
  }

  private void initializeReader(String readerType) {
    switch (readerType) {
      case "Config" -> myReader = new XMLReaderWriter();
      case "Style" -> myReader = new StyleReader();
    }
  }

  /**
   * Returns the specific parser being used in this FileHandler
   *
   * @return a FileHandler object, the specific Parser being used
   */
  public DefaultHandler getReader() {
    return myReader;
  }
}

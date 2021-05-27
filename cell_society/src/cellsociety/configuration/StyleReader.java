package cellsociety.configuration;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StyleReader extends DefaultHandler {

  private String language;
  private int gridType;
  private boolean enableGrid;
  private String cellColor;
  private String cellShape;

  private boolean bLanguage = false;
  private boolean bGridType = false;
  private boolean bEnableGrid = false;
  private boolean bCellColor = false;
  private boolean bCellShape = false;

  /**
   * Reads the tag name of an XML element and sets where to save the data
   *
   * @param uri        a String representing the namespace URI of the tag
   * @param localName  a String representing the local name of the tag
   * @param qName      a String representing the name of the tag
   * @param attributes an Attribute representing the attribute of the tag
   */  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    if (qName.equalsIgnoreCase("language")) {
      bLanguage = true;
    } else if (qName.equalsIgnoreCase("gridType")) {
      bGridType = true;
    } else if (qName.equalsIgnoreCase("enableGrid")) {
      bEnableGrid = true;
    } else if (qName.equalsIgnoreCase("cellColor")) {
      bCellColor = true;
    } else if (qName.equalsIgnoreCase("cellShape")) {
      bCellShape = true;
    }
  }

  /**
   * Reads the element data within an XML tag
   *
   * @param ch     a char array containing the letters in the XML file
   * @param start  an integer representing the absolute start index of the element data within a
   *               tag
   * @param length an integer representing the length of the element data within tag
   * @throws SAXException an Error if a file cannot be parsed
   */  @Override
  public void characters(char ch[], int start, int length) {
    try {
      if (bLanguage) {
        language = new String(ch, start, length);
        bLanguage = false;
      } else if (bGridType) {
        gridType = Integer.parseInt(new String(ch, start, length));
        bGridType = false;
      } else if (bEnableGrid) {
        enableGrid = Boolean.parseBoolean(new String(ch, start, length));
        bEnableGrid = false;
      } else if (bCellColor) {
        cellColor = new String(ch, start, length);
        bCellColor = false;
      } else if (bCellShape) {
        cellShape = new String(ch, start, length);
        bCellShape = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the language parsed from a Style XML file
   *
   * @return a String representing the Language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Returns the grid type parsed from a Style XML file
   *
   * @return an integer representing the grid type
   */
  public int getGridType() {
    return gridType;
  }

  /**
   * Returns whether the grid should be enabled or not
   *
   * @return a boolean representing if the grid is enabled or not
   */
  public boolean getEnableGrid() {
    return enableGrid;
  }

  /**
   * Returns the color that cells should be
   *
   * @return a String representing the hex code of a color that cells should be
   */
  public String getCellColor() {
    return cellColor;
  }

  /**
   * Returns the shape that cells should be
   *
   * @return a String representing the shape that cells should be
   */
  public String getCellShape() {
    return cellShape;
  }
}
package cellsociety.configuration;

import cellsociety.evolution.Evolution;
import cellsociety.evolution.EvolutionFire;
import cellsociety.evolution.EvolutionGOL;
import cellsociety.evolution.EvolutionPerc;
import cellsociety.evolution.EvolutionRPSLS;
import cellsociety.evolution.EvolutionSeg;
import cellsociety.evolution.EvolutionSugar;
import cellsociety.evolution.EvolutionWator;
import cellsociety.Grid;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static cellsociety.Constants.*;

public class XMLReaderWriter extends DefaultHandler {

  private String id = "";
  private String gameType = GOL;
  private String title = "";
  private String author = "";
  private String description = "";

  private int gridHeight = DUMMY_HEIGHT;
  private int gridWidth = DUMMY_WIDTH;
  private double percent;
  private double probCatch;

  private Evolution myEvolution;

  private ArrayList<Integer> watorParameter = new ArrayList<>();
  private ArrayList<Integer> gridElements = new ArrayList<>();

  boolean bID = false;
  boolean bGameType = false;
  boolean bTitle = false;
  boolean bAuthor = false;
  boolean bDescription = false;

  boolean bGridHeight = false;
  boolean bGridWidth = false;
  boolean bPercent = false;
  boolean bProbCatch = false;
  boolean bWatorParameter = false;
  boolean bGridElements = false;

  private HashMap<String, ArrayList<Integer>> evolutionValues = new HashMap<>();
  private ArrayList<Integer> golValues = new ArrayList<>();
  private ArrayList<Integer> percValues = new ArrayList<>();
  private ArrayList<Integer> segValues = new ArrayList<>();
  private ArrayList<Integer> fireValues = new ArrayList<>();
  private ArrayList<Integer> watorValues = new ArrayList<>();
  private ArrayList<Integer> rpslsValues = new ArrayList<>();
  private ArrayList<Integer> sugarValues = new ArrayList<>();

  /**
   * Creates an XMLReaderWriter class which defines how a SAX Parser reads XML data to create Grid
   * and Evolutions objects and write current simulation configurations to a file.
   */
  public XMLReaderWriter() {
    golValues.addAll(Arrays.asList(0, 1));
    evolutionValues.put(GOL, golValues);
    percValues.addAll(Arrays.asList(0, 1, 2));
    evolutionValues.put(PERCOLATION, percValues);
    segValues.addAll(Arrays.asList(1, 2, 3));
    evolutionValues.put(SEGREGATION, segValues);
    fireValues.addAll(Arrays.asList(3, 4, 5));
    evolutionValues.put(FIRE, fireValues);
    watorValues.addAll(Arrays.asList(2, 5, 6));
    evolutionValues.put(WATOR, watorValues);
    rpslsValues.addAll(Arrays.asList(1, 3, 4, 5, 6));
    evolutionValues.put(RPSLS, rpslsValues);
    sugarValues.addAll(Arrays.asList(1, 2, 7, 8, 9));
    evolutionValues.put(SUGAR, sugarValues);
  }

  /**
   * Reads the tag name of an XML element and sets where to save the data
   *
   * @param uri        a String representing the namespace URI of the tag
   * @param localName  a String representing the local name of the tag
   * @param qName      a String representing the name of the tag
   * @param attributes an Attribute representing the attribute of the tag
   */
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    if (qName.equalsIgnoreCase("id")) {
      bID = true;
    } else if (qName.equalsIgnoreCase("game")) {
      bGameType = true;
    } else if (qName.equalsIgnoreCase("title")) {
      bTitle = true;
    } else if (qName.equalsIgnoreCase("author")) {
      bAuthor = true;
    } else if (qName.equalsIgnoreCase("description")) {
      bDescription = true;
    } else if (qName.equalsIgnoreCase("height")) {
      bGridHeight = true;
    } else if (qName.equalsIgnoreCase("width")) {
      bGridWidth = true;
    } else if (qName.equalsIgnoreCase("entry")) {
      bGridElements = true;
    } else if (qName.equalsIgnoreCase("percent")) {
      bPercent = true;
    } else if (qName.equalsIgnoreCase("probCatch")) {
      bProbCatch = true;
    } else if (qName.equalsIgnoreCase("watorParameter")) {
      bWatorParameter = true;
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
   */
  @Override
  public void characters(char ch[], int start, int length) throws SAXException {
    try {
      if (bID) {
        id = new String(ch, start, length);
        bID = false;
      } else if (bGameType) {
        gameType = new String(ch, start, length);
        bGameType = false;
      } else if (bTitle) {
        title = new String(ch, start, length);
        bTitle = false;
      } else if (bAuthor) {
        author = new String(ch, start, length);
        bAuthor = false;
      } else if (bDescription) {
        description = new String(ch, start, length);
        bDescription = false;
      } else if (bGridHeight) {
        gridHeight = Integer.parseInt(new String(ch, start, length));
        bGridHeight = false;
      } else if (bGridWidth) {
        this.gridWidth = Integer.parseInt(new String(ch, start, length));
        bGridWidth = false;
      } else if (bPercent) {
        percent = Double.parseDouble(new String(ch, start, length));
        bPercent = false;
      } else if (bProbCatch) {
        probCatch = Double.parseDouble(new String(ch, start, length));
        bProbCatch = false;
      } else if (bWatorParameter) {
        watorParameter.add(Integer.parseInt(new String(ch, start, length)));
        bWatorParameter = false;
      } else if (bGridElements) {
        for (int i = start; i < start + length; i++) {
          gridElements.add(changeInvalidValue(Character.getNumericValue(ch[i])));
        }
        bGridElements = false;
      }
    } catch (Exception e) {
      throw new SAXException("Invalid input file");
    }
  }

  /**
   * Returns a Grid created from the data parsed from the XML file input. If there are not enough
   * values to fill the grid, random cell values based on the selected game will be added to the end
   * of the value list to fit the grid size.
   *
   * @return a Grid of gridHeight rows x gridWidth columns filled with a list of gridElements
   */
  public Grid getGrid() {
    int totalElements = gridWidth * gridHeight;
    if (gridElements.size() < totalElements) {
      gridElements.addAll(
          createRandomElements(totalElements - gridElements.size(), 1));
    }
    return new Grid(gridWidth, gridHeight, gridElements);
  }

  /**
   * Returns an Evolution of a certain type given in the XML file input. Uses passes parameters to
   * constructors for certain gameTypes.
   *
   * @return an Evolution of the same type as gameType
   */
  public Evolution getEvolution() {
    if (watorParameter.size() < 3) {
      watorParameter = new ArrayList<>(Arrays
          .asList(1, 1, 1)); // Default values for Wator simulation if not reading incorrect file
    }

    switch (gameType) {
      case GOL -> myEvolution = new EvolutionGOL(getGrid());
      case PERCOLATION -> myEvolution = new EvolutionPerc(getGrid());
      case SEGREGATION -> myEvolution = new EvolutionSeg(getGrid(), percent);
      case FIRE -> myEvolution = new EvolutionFire(getGrid(), probCatch);
      case WATOR -> myEvolution = new EvolutionWator(getGrid(),
          watorParameter.get(0),
          watorParameter.get(1), watorParameter.get(2));
      case RPSLS -> myEvolution = new EvolutionRPSLS(getGrid());
      case SUGAR -> myEvolution = new EvolutionSugar(getGrid());
      default -> myEvolution = new EvolutionGOL(getGrid());
    }
    myEvolution.setNames(title, description);
    return myEvolution;
  }

  /**
   * CURRENTLY UNUSED: Creates a new XML File containing the data for the current running
   * simulation, but with random cell values
   *
   * @param width    an integer representing the width of the simulation grid, in number of cells
   * @param height   an integer representing the height of the simulation grid, in number of cells
   * @param filepath a String representing the file path of the output file
   */
  public void writeRandomConfiguration(int width, int height, String filepath) {
    ArrayList<Integer> elements = createRandomElements(width, height);
    try {
      writeCurrentConfiguration(filepath, elements);
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  private ArrayList<Integer> createRandomElements(int width, int height) {
    ArrayList<Integer> elements = new ArrayList<>();
    ArrayList<Integer> cellValues = new ArrayList<>();
    Random rand = new Random();
    int randomValue;

    switch (gameType) {
      case GOL -> cellValues = golValues;
      case PERCOLATION -> cellValues = percValues;
      case SEGREGATION -> cellValues = segValues;
      case FIRE -> cellValues = fireValues;
      case WATOR -> cellValues = watorValues;
      case RPSLS -> cellValues = rpslsValues;
      case SUGAR -> cellValues = sugarValues;
    }

    for (int k = 0; k < width; k++) {
      for (int j = 0; j < height; j++) {
        randomValue = cellValues.get(rand.nextInt(cellValues.size()));
        elements.add(randomValue);
      }
    }
    return elements;
  }

  /**
   * Writes a new configuration file using parameters from the currently playing game and a list of
   * input values
   *
   * @param filepath a String representing he file path for the output configuration file
   * @param elements a List of integers that store the value of cells, in order of first row, second
   *                 row, etc.
   * @throws XMLStreamException XML Error when file cannot be written or accessed
   */
  public void writeCurrentConfiguration(String filepath, List<Integer> elements)
      throws XMLStreamException {
    try {
      StringWriter stringWriter = new StringWriter();

      XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
      XMLStreamWriter xmlWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

      xmlWriter.writeStartDocument();
      xmlWriter.writeStartElement("grid");

      writeElement(xmlWriter, "id", id);
      writeElement(xmlWriter, "game", gameType);
      writeElement(xmlWriter, "title", title);
      writeElement(xmlWriter, "author", author);
      writeElement(xmlWriter, "description", description);

      switch (gameType) {
        case SEGREGATION:
          writeElement(xmlWriter, "percent", percent);
          break;
        case FIRE:
          writeElement(xmlWriter, "probCatch", probCatch);
          break;
        case WATOR:
          for (int i = 0; i < 3; i++) {
            writeElement(xmlWriter, "watorParameter", watorParameter.get(i));
          }
          break;
      }

      writeElement(xmlWriter, "height", gridHeight);
      writeElement(xmlWriter, "width", gridWidth);
      writeEntries(xmlWriter, elements);

      xmlWriter.writeEndElement();
      xmlWriter.writeEndDocument();
      xmlWriter.flush();
      xmlWriter.close();

      String xmlString = stringWriter.getBuffer().toString();
      Files.writeString(Paths.get(filepath), xmlString);

      stringWriter.close();

    } catch (XMLStreamException e) {
      throw new XMLStreamException("Invalid input file");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeElement(XMLStreamWriter x, String element, String value)
      throws XMLStreamException {
    x.writeStartElement(element);
    x.writeCharacters(value);
    x.writeEndElement();
  }

  private void writeElement(XMLStreamWriter x, String element, double value)
      throws XMLStreamException {
    x.writeStartElement(element);
    x.writeCharacters("" + value);
    x.writeEndElement();
  }

  private void writeElement(XMLStreamWriter x, String element, int value)
      throws XMLStreamException {
    x.writeStartElement(element);
    x.writeCharacters("" + value);
    x.writeEndElement();
  }

  private void writeEntries(XMLStreamWriter x, List<Integer> elements) {
    for (int i = 0; i < gridHeight; i++) {
      String row = "";
      for (int j = 0; j < gridWidth; j++) {
        row += "" + elements.get(i * gridWidth + j);
      }
      try {
        writeElement(x, "entry", row);
      } catch (XMLStreamException e) {
        e.printStackTrace();
      }
    }
  }

  private int changeInvalidValue(int cellValue) {
    boolean validEntry = evolutionValues.get(gameType).contains(cellValue);
    if (!validEntry) {
      switch (gameType) {
        case GOL, PERCOLATION:
          return 0;
        case SEGREGATION, RPSLS, SUGAR:
          return 1;
        case FIRE:
          return 4;
        case WATOR:
          return 5;
      }
    }
    return cellValue;
  }
}
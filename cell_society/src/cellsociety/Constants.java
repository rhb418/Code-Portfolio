package cellsociety;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class Constants {

  // XML Constants
  public static final String BASIC_STARTER_FILE = "PercolationCheckerEdge.xml";
  public static final String GOL = "GOL";
  public static final String PERCOLATION = "Percolation";
  public static final String SEGREGATION = "Segregation";
  public static final String FIRE = "Fire";
  public static final String WATOR = "Wator";
  public static final String RPSLS = "RPSLS";
  public static final String SUGAR = "Sugar";
  public static final int DUMMY_HEIGHT = 10; // default height and width for bad files
  public static final int DUMMY_WIDTH = 10;

  // DISPLAY Constants
  public static final int FRAMES_PER_SECOND = 1;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final int SIZE = 625;
  public static final int CONTROL_HEIGHT = 190;
  public static final double CELL_STROKE_PROPORTION = 50.0;
  public static final String STYLESHEET = "/cellsociety/display/resources/";
  public static final String DEFAULT_STYLE = "default.css";
  public static final String DARK_STYLE = "dark.css";
  public static final String LIGHT_STYLE = "light.css";
  public static final String DEFAULT_COMMAND = "DefaultStyle";
  public static final String DARK_COMMAND = "DarkStyle";
  public static final String LIGHT_COMMAND = "LightStyle";
  public static final String[] STYLES = {DEFAULT_STYLE, DARK_STYLE, LIGHT_STYLE};
  public static final int DEFAULT = 0; // default style
  public static final String PROPERTIES = "cellsociety.display.resources.";
  public static final Insets ITEM_SPACING = new Insets(5, 10, 5, 10);
  // javafx CSS does not have an option for margins like this, so having a constant here is the next best thing
  public static final String PAUSE = "PauseCommand";
  public static final String RESUME = "ResumeCommand";
  public static final String RESTART = "RestartCommand";
  public static final String STEP = "StepCommand";
  public static final String CHANGE_SIM_PROMPT = "ChangeSimCommand";
  public static final String ADD_SIM_PROMPT = "AddSimCommand";
  public static final String GRAPH_PROMPT = "GraphCommand";
  public static final String GRIDLINES_PROMPT = "GridlinesCommand";
  public static final String SAVE_PROMPT = "SaveCommand";
  public static final String RECT_DROP = "RectDrop";
  public static final String RECT_CLOSE_DROP = "RectCloseDrop";
  public static final String TRI_DROP = "TriDrop";
  public static final String HEX_DROP = "HexDrop";
  public static final String FINITE_PROMPT = "FiniteCommand";
  public static final String TOROIDAL_PROMPT = "ToroidalCommand";
  public static final int SAME_WINDOW_NEW_FILE = 0;
  public static final int NEW_WINDOW_NEW_FILE = 1;
  public static final Color[] COLOR_MAP = {Color.BLACK, Color.WHITE, Color.BLUE, Color.RED,
      Color.YELLOW, Color.GREEN, Color.GRAY, Color.YELLOW, Color.ORANGE, Color.RED};
  public static final String CONFIG_FILE_PATH = "data/";
  // using the classLoader().getResource() directs us to files in target, but I want them to be in
  // the data folder, so I had to use this string
  public static final double HEXAGONS_PER_WIDTH = 3/2.0;
  public static final double HEXAGON_HALF = 0.5;
  public static final int HEXAGON_WIDTH_MUL = 3;
  public static final int HEXAGON_HEIGHT_MUL = 2;
  public static final String[] PIE_CHART_COLOR_MAP = {"black", "white", "blue", "red", "yellow", "green", "gray", "yellow", "orange", "red"};
  public static final String CSS_PIE_COLOR_FX = "-fx-pie-color: ";
  public static final int FIELD_WIDTH = 200;
  public static final int FIELD_HEIGHT = 30;
  public static final double MIN_FPS = 1/4.0;
  public static final double MAX_FPS = 5;
  public static final String[] LANGUAGES = {"English", "Mandarin", "Gibberish"};
  public static final String SPLASH = "Welcome to Cell Society!";

  // SIMULATION Constants
  public static final int DEAD = 0;
  public static final int ALIVE = 1;
  public static final int OPEN = 1;
  public static final int BLOCKED = 0;
  public static final int PERCOLATED = 2;
  public static final int EMPTY_SPOT = 1;
  public static final int BURNING = 3;
  public static final int BURNED = 4;
  public static final int TREE = 5;
  public static final int SHARK = 6;
  public static final int FISH = 2;
  public static final int KELP = 5;
  public static final int EVEN = 0;
  public static final int ODD = 1;
  public static final int RECTANGULAR_FINITE = 0;
  public static final int RECTANGULAR_TOROIDAL = 10;
  public static final int RECTANGULAR_CLOSE_FINITE = 1;
  public static final int RECTANGULAR_CLOSE_TOROIDAL = 11;
  public static final int TRIANGULAR_FINITE = 2;
  public static final int TRIANGULAR_TOROIDAL = 12;
  public static final int HEXAGONAL_FINITE = 3;
  public static final int HEXAGONAL_TOROIDAL = 13;
  public static final int RECTANGULAR = 0;
  public static final int RECTANGULAR_CLOSE = 1;
  public static final int TRIANGULAR = 2;
  public static final int HEXAGONAL = 3;
  public static final int FINITE = 0;
  public static final int TOROIDAL = 1;
  public static final int ROCK = 6;
  public static final int PAPER = 1;
  public static final int SCISSORS = 4;
  public static final int LIZARD = 5;
  public static final int SPOCK = 3;
  public static final int DEFAULT_NUM_STEPS = 1;
  public static final int AGENT = 2;

  public static final int NO_SUGAR = 1;
  public static final int SMALL_SUGAR = 7;
  public static final int MEDIUM_SUGAR = 8;
  public static final int LARGE_SUGAR = 9;
  public static final String PERCENT_SATISFIED = "PercentSatisfied";
  public static final String PROBCATCH = "ProbCatch";
  public static final String SUGAR_GROW_BACK_INTERVAL = "SugarGrowth";
  public static final String ENERGY_PER_PREY = "EnergyPerPrey";
  public static final String TURNS_TO_REPRODUCE = "TurnsToReproduce";
  public static final String THRESHOLD = "Threshold";
}

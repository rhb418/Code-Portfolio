package slogo.view;

import java.util.ResourceBundle;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * The SceneBuilder class handles the creation of Workspaces.
 *
 * SceneBuilder makes use of the TabPane class in order to have multiple Workspaces running at
 * once. New Workspaces can be made by using the newTabButton and closed at will. If the last
 * Workspace is closed, a new one will be opened automatically.
 *
 * @author Bill Guo
 */
public class SceneBuilder {

  private static final String DEFAULT_LANGUAGE = "English";
  private static final String DEFAULT_LABEL_FILE = "slogo/resources/labels/Labels_English";
  private static final String DEFAULT_SHAPE_FILE = "slogo/resources/toolbar/menuButton/shapes/"
      + "English";
  private static final String DEFAULT_COLOR_FILE = "slogo/resources/toolbar/Color";

  private TabPane masterLayout;
  private ResourceBundle labelBundle;
  private ResourceBundle shapeBundle;
  private ResourceBundle colorBundle;
  private int tabNumber;

  /**
   * Constructor of the SceneBuilder.
   */
  public SceneBuilder() {
    labelBundle = ResourceBundle.getBundle(DEFAULT_LABEL_FILE);
    shapeBundle = ResourceBundle.getBundle(DEFAULT_SHAPE_FILE);
    colorBundle = ResourceBundle.getBundle(DEFAULT_COLOR_FILE);
    tabNumber = 1;
  }

  /**
   * Initializes the formation of the TabPane that represents the SceneBuilder.
   * @return TabPane representing SceneBuilder
   */
  public TabPane initialize() {
    masterLayout = makeMasterLayout();
    Tab initialTab = makeNewTab();
    Tab newTabButton = makeNewTabButton();
    masterLayout.getTabs().addAll(initialTab, newTabButton);
    return masterLayout;
  }

  private TabPane makeMasterLayout() {
    TabPane returnTabPane = new TabPane();
    returnTabPane.getStyleClass().add("scenebuilder-tabpane");
    returnTabPane.setId("SceneBuilder");
    return returnTabPane;
  }

  private Tab makeNewTab() {
    Tab newTab = new Tab(labelBundle.getString("Workspace") + tabNumber,
        new Workspace(labelBundle, shapeBundle, colorBundle, DEFAULT_LANGUAGE).initialize());
    newTab.setId("Tab" + tabNumber);
    tabNumber++;
    return newTab;
  }

  private Tab makeNewTabButton() {
    Tab newTabButton = new Tab("+");
    newTabButton.setId("NewTabButton");
    newTabButton.setOnSelectionChanged(e -> handleNewTabButton());
    return newTabButton;
  }

  private void handleNewTabButton() {
    Tab newTab = makeNewTab();
    masterLayout.getTabs().add(masterLayout.getTabs().size() - 1, newTab);
    masterLayout.getSelectionModel().select(masterLayout.getTabs().size() - 2);
  }

}

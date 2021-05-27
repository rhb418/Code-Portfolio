package ooga.view.components.gameselection;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.MalformedURLException;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ooga.view.util.LabelPair;
import ooga.view.util.MetaGame;
import ooga.view.util.ObservableResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

class GSelectionView extends VBox {

  private static final Logger logger = LogManager.getLogger(GSelectionView.class);
  private static double IMAGE_DIM = 200;
  private final LabelPair gameDateLabel;
  private final LabelPair gameAuthorLabel;
  private final LabelPair gameLevelsLabel;
  private final LabelPair gameTagsLabel;
  private String directory = "/home/joshu/Pictures/";
  private ImageView thumbnail;
  private Label gameTitleLabel;
  private ObservableResource resources;
  private Button playGame;
  private MetaGame metadata;
  private Consumer<String> playRequestedCallback;

  public GSelectionView(ObservableResource resources) {
    setId("selection-view");
    this.thumbnail = new ImageView();
    this.gameTitleLabel = new Label();
    this.resources = resources;
    this.gameAuthorLabel = new LabelPair(resources.getStringBinding("GameAuthor"));
    this.gameDateLabel = new LabelPair(resources.getStringBinding("DateCreated"));
    this.gameTagsLabel = new LabelPair(resources.getStringBinding("GameTags"));
    this.gameLevelsLabel = new LabelPair(resources.getStringBinding("GameLevels"));
    thumbnail.getStyleClass().addAll("game-selection-thumbnail");
    getStyleClass().addAll("game-selection-view");

    this.playGame = new JFXButton();
    playGame.getStyleClass().addAll("game-item-button", "secondary", "special");
    playGame.setGraphic(new FontIcon());
    playGame.setOnAction(e -> notifySelection());

    VBox.setVgrow(playGame, Priority.ALWAYS);
    gameTitleLabel.getStyleClass().add("game-selection-title");
    getChildren()
        .addAll(gameTitleLabel, thumbnail, playGame, gameAuthorLabel, gameDateLabel, gameTagsLabel,
            gameLevelsLabel);
  }

  private void notifySelection() {
    if (this.playRequestedCallback == null) {
      logger.warn("Play Request Callback is null");
      return;
    }
    if (this.directory == null) {
      logger.warn("No directory set for game reqeusts");
      return;
    }
    playRequestedCallback.accept(directory);
    logger.debug("Play Request for directory {} sent", directory);
  }

  private void readMetaData(File directory) {
    // TODO: Set Meta data of game on view
    this.metadata = MetaGame.createMetaDataFromDirectory(directory);
    gameAuthorLabel.setValue(metadata.getAuthor());
    gameDateLabel.setValue(metadata.getDateCreated());
    gameLevelsLabel.setValue(metadata.getLevels().toString());
    gameTagsLabel.setValue(metadata.getTags().toString());
  }

  public void setOnPlayRequested(Consumer<String> callback) {
    this.playRequestedCallback = callback;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
    File gameDirectory = new File(directory);
    File fileImage = new File(directory + "thumbnail.jpg");
    try {
      Image image =
          new Image(fileImage.toURI().toURL().toExternalForm(), IMAGE_DIM, IMAGE_DIM, false, true);
      this.thumbnail.setImage(image);
      String gameName = fileImage.getParentFile().getName();
      gameTitleLabel.setText(gameName);
      logger.debug("GSelection View set to: {}", directory);
      readMetaData(gameDirectory);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }
}

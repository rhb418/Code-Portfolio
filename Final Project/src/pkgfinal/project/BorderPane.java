package pkgfinal.project;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontPosture;

public class BorderPane extends Pane {

    private BorderPane borderPane;
    private LinePane linePane;
    private Label title;
    private Button startButton;
    private RadioButton easyButton;
    private RadioButton mediumButton;
    private RadioButton hardButton;
    private Label easyLabel;
    private Label mediumLabel;
    private Label hardLabel;
    private Label rulesLabel;
    private Label writtenRules;
    private Stage lStage;
    private Stage hStage;
    private Stage eStage;
    private ArrayList<Circle> circleList;

    public BorderPane() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double halfScreenWidth = screenSize.getWidth() / 2;
        double thirdScreenWidth = screenSize.getWidth() / 3;
        double fourthScreenWidth = screenSize.getWidth() / 4;
        double halfScreenHeight = screenSize.getHeight() / 2;
        double thirdScreenHeight = screenSize.getHeight() / 3;
        double fourthScreenHeight = screenSize.getHeight() / 4;

        ToggleGroup difficultyToggle = new ToggleGroup();
        Font titleFont = Font.font("Broadway", FontWeight.BOLD, 200);
        Font mainFont = Font.font("Times New Roman", 30);
        Font rulesTitle = Font.font("Times New Roman", FontPosture.ITALIC, 40);
        Font rulesFont = Font.font("Times New Roman", 25);

        title = new Label("LineSweeper");
        title.setTextFill(Color.WHITE);
        title.setFont(titleFont);
        title.setLayoutX(thirdScreenWidth - 325);
        title.setLayoutY(thirdScreenHeight - 200);

        easyLabel = new Label("Easy");
        easyLabel.setTextFill(Color.WHITE);
        easyLabel.setFont(mainFont);
        easyLabel.setLayoutX(thirdScreenWidth - 10);
        easyLabel.setLayoutY(thirdScreenHeight + 150);
        easyButton = new RadioButton();
        easyButton.setLayoutX(screenSize.getWidth() / 3);
        easyButton.setLayoutY(halfScreenHeight);

        mediumLabel = new Label("Medium");
        mediumLabel.setTextFill(Color.WHITE);
        mediumLabel.setFont(mainFont);
        mediumLabel.setLayoutX(halfScreenWidth - 40);
        mediumLabel.setLayoutY(thirdScreenHeight + 150);
        mediumButton = new RadioButton();
        mediumButton.setLayoutX(halfScreenWidth);
        mediumButton.setLayoutY(halfScreenHeight);

        hardLabel = new Label("Hard");
        hardLabel.setTextFill(Color.WHITE);
        hardLabel.setFont(mainFont);
        hardLabel.setLayoutX((thirdScreenWidth - 15) * 2);
        hardLabel.setLayoutY(thirdScreenHeight + 150);
        hardButton = new RadioButton();
        hardButton.setLayoutX((thirdScreenWidth - 5) * 2);
        hardButton.setLayoutY(halfScreenHeight);

        startButton = new Button("Start Game");
        startButton.setFont(mainFont);
        startButton.setLayoutX(halfScreenWidth - 80);
        startButton.setLayoutY(halfScreenHeight + 100);

        rulesLabel = new Label("Rules");
        rulesLabel.setTextFill(Color.WHITE);
        rulesLabel.setFont(rulesTitle);
        rulesLabel.setLayoutX(fourthScreenWidth);
        rulesLabel.setLayoutY(thirdScreenHeight * 2 - 20);
        writtenRules = new Label("Players can only move from one dot to the next adjacent dot\nPlayers can make only one move at a time\n"
                + "The Red player (bottom right) always starts the game\nIf mines are captured, then 6 points are deducted from value of area\n"
                + "You must enclose half of the perimeter of a shape to obtain points");
        writtenRules.setTextFill(Color.WHITE);
        writtenRules.setFont(rulesFont);
        writtenRules.setLayoutX(fourthScreenWidth);
        writtenRules.setLayoutY((fourthScreenHeight * 3) - 65);

        easyButton.setToggleGroup(difficultyToggle);
        mediumButton.setToggleGroup(difficultyToggle);
        hardButton.setToggleGroup(difficultyToggle);

        startButton.setOnAction(this::processButtonPress);

        circleList = new ArrayList<Circle>();

        double xStart = fourthScreenWidth - 10;
        double yStart = (thirdScreenHeight * 2) + 40;
        int i = 0;

        while (i < 5) {
            Circle circle = new Circle(xStart, yStart, 5);
            circle.setFill(Color.WHITE);
            xStart = xStart;
            yStart = yStart + 30;
            circleList.add(circle);

            i++;
        }
        for (Circle thiscircle : circleList) {
            getChildren().add(thiscircle);
        }

        getChildren().add(startButton);
        getChildren().add(title);
        getChildren().add(easyButton);
        getChildren().add(mediumButton);
        getChildren().add(hardButton);
        getChildren().add(easyLabel);
        getChildren().add(mediumLabel);
        getChildren().add(hardLabel);
        getChildren().add(rulesLabel);
        getChildren().add(writtenRules);
    }

    public void setlStage(Stage lStage) {
        this.lStage = lStage;
    }

    public void sethStage(Stage hStage) {
        this.hStage = hStage;
    }

    public void seteStage(Stage eStage) {
        this.eStage = eStage;
    }

    public void processButtonPress(ActionEvent e) {
        if (e.getSource() == startButton) {
            if (easyButton.isSelected()) {
                eStage.show();
            }
            if (mediumButton.isSelected()) {
                lStage.show();
            }
            if (hardButton.isSelected()) {
                hStage.show();
            }
        }
    }

    public void processRadioButton(ActionEvent e) {

    }

}

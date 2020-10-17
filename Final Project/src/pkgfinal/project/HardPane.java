package pkgfinal.project;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class HardPane extends Pane {

    private ArrayList<RadioButton> buttonList;
    private ArrayList<Line> lineList;
    private double xstart = 558;
    private double ystart = 558;
    private int sent = 5;
    private int eventhindex = 1;
    private double xblueStart = 8;
    private double yblueStart = 8;
    private Label cornerlabel1, cornerlabel2, centerlabel1, centerlabel2, m1label, m2label, m3label, m4label, middleTop, middleBottom, middleLeft, middleRight;
    private Circle mine1, mine2, mine3, mine4;
    private Button end;
    private Stage sstage;

    public void setSstage(Stage sstage) {
        this.sstage = sstage;
    }

    public HardPane() {
        double xindex = 8; //starts the line at the center of one of the radioButtons
        int yindex = 0;

        lineList = new ArrayList<Line>();
        buttonList = new ArrayList<RadioButton>();

        while (xindex < 600) {  //makes a 12 by 12 playing board

            while (yindex < 600) {
                Line line = new Line();
                line.setStartX(xindex);
                line.setStartY(yindex + 8);
                line.setEndX(Math.min(xindex, 558));
                line.setEndY(Math.min(yindex + 50, 550));
                line.setStrokeWidth(2);
                line.setFill(Color.BLACK);
                lineList.add(line);

                Line line2 = new Line();
                line2.setStartX(xindex);
                line2.setStartY(yindex + 8);
                line2.setEndX(Math.min(xindex + 50, 550));
                line2.setEndY(Math.min(yindex + 8, 558));
                line2.setStrokeWidth(2);
                line2.setFill(Color.BLACK);
                lineList.add(line2);

                yindex = yindex + 50;

            }

            xindex = xindex + 50;
            yindex = 0;
        }
        for (Line thisline : lineList) {
            getChildren().add(thisline);
        }

        xindex = 0;
        yindex = 0;
        while (xindex < 600) {

            while (yindex < 600) {
                RadioButton button = new RadioButton();
                button.setTranslateX(xindex);

                button.setTranslateY(yindex);
                buttonList.add(button);
                yindex = yindex + 50;

            }
            xindex = xindex + 50;
            yindex = 0;
        }

        for (RadioButton thisbutton : buttonList) {
            getChildren().add(thisbutton);
        }
        Label corner1Label = new Label("-6");
        corner1Label.setLayoutX(25);
        corner1Label.setLayoutY(525);

        Label corner2Label = new Label("-6");
        corner2Label.setLayoutX(525);
        corner2Label.setLayoutY(25);

        Label middleTop = new Label("5");
        middleTop.setLayoutX(130);
        middleTop.setLayoutY(425);

        Label middleBottom = new Label("5");
        middleBottom.setLayoutX(130);
        middleBottom.setLayoutY(125);

        Label middleLeft = new Label("5");
        middleLeft.setLayoutX(430);
        middleLeft.setLayoutY(425);

        Label middleRight = new Label("5");
        middleRight.setLayoutX(430);
        middleRight.setLayoutY(125);

        Label centerlabel2 = new Label("20");
        centerlabel2.setLayoutX(275);
        centerlabel2.setLayoutY(275);

        Label m1Label = new Label("-6");
        m1Label.setLayoutX(275);
        m1Label.setLayoutY(125);

        Label m2Label = new Label("-6");
        m2Label.setLayoutX(275);
        m2Label.setLayoutY(425);

        Label m3Label = new Label("-6");
        m3Label.setLayoutX(125);
        m3Label.setLayoutY(275);

        Label m4Label = new Label("-6");
        m4Label.setLayoutX(425);
        m4Label.setLayoutY(275);

        Circle mine1 = new Circle(283, 133, 20, Color.RED);
        Circle mine2 = new Circle(283, 433, 20, Color.RED);
        Circle mine3 = new Circle(133, 283, 20, Color.RED);
        Circle mine4 = new Circle(433, 283, 20, Color.RED);
        Circle mine5 = new Circle(533, 33, 20, Color.RED);
        Circle mine6 = new Circle(33, 533, 20, Color.RED);

        Button end = new Button("End the Game and Calculate Score");
        end.setLayoutX(175);
        end.setLayoutY(600);
        end.setOnAction(this::processButtonPress);

        getChildren().add(mine1);
        getChildren().add(mine2);
        getChildren().add(mine3);
        getChildren().add(mine4);
        getChildren().add(mine5);
        getChildren().add(mine6);
        getChildren().add(corner1Label);
        getChildren().add(corner2Label);
        getChildren().add(centerlabel2);
        getChildren().add(middleTop);
        getChildren().add(middleBottom);
        getChildren().add(middleLeft);
        getChildren().add(middleRight);
        getChildren().add(m1Label);
        getChildren().add(m2Label);
        getChildren().add(m3Label);
        getChildren().add(m4Label);
        getChildren().add(end);

        int buttonIndex = 0;
        while (buttonIndex < 144) {
            buttonList.get(buttonIndex).setOnAction(this::processRadioButtonAction);
            buttonIndex = buttonIndex + 1;

        }
    }

    public void processButtonPress(ActionEvent event) {
        sstage.show();
    }

    public void processRadioButtonAction(ActionEvent event) {
        int button = 0;
        sent = sent - 2;
        while (sent == 3) {
            if (event.getSource() == buttonList.get(button)) {
                double xend = buttonList.get(button).getTranslateX() + 8;
                double yend = buttonList.get(button).getTranslateY() + 8;

                Line newLine = new Line(xstart, ystart, xend, yend);
                if (Math.sqrt((newLine.getEndX() - newLine.getStartX()) * (newLine.getEndX() - newLine.getStartX()) + (newLine.getEndY() - newLine.getStartY()) * (newLine.getEndY() - newLine.getStartY())) > 51) {
                    newLine.setStroke(null);
                }
                if (Math.sqrt((newLine.getEndX() - newLine.getStartX()) * (newLine.getEndX() - newLine.getStartX()) + (newLine.getEndY() - newLine.getStartY()) * (newLine.getEndY() - newLine.getStartY())) < 51) {
                    newLine.setStroke(Color.RED);
                    newLine.setStrokeWidth(4);
                    getChildren().add(newLine);
                }

                ystart = yend;
                xstart = xend;
                sent = sent + 1;

            } else {
                button = button + 1;
            }

        }
        while (sent == 2) {
            if (event.getSource() == buttonList.get(button)) {
                double xblueend = buttonList.get(button).getTranslateX() + 8;
                double yblueend = buttonList.get(button).getTranslateY() + 8;

                Line newLine = new Line(xblueStart, yblueStart, xblueend, yblueend);
                if (Math.sqrt((newLine.getEndX() - newLine.getStartX()) * (newLine.getEndX() - newLine.getStartX()) + (newLine.getEndY() - newLine.getStartY()) * (newLine.getEndY() - newLine.getStartY())) > 51) {
                    newLine.setStroke(null);
                }
                if (Math.sqrt((newLine.getEndX() - newLine.getStartX()) * (newLine.getEndX() - newLine.getStartX()) + (newLine.getEndY() - newLine.getStartY()) * (newLine.getEndY() - newLine.getStartY())) < 51) {
                    newLine.setStroke(Color.BLUE);
                    newLine.setStrokeWidth(4);
                    getChildren().add(newLine);
                }

                yblueStart = yblueend;
                xblueStart = xblueend;
                sent = sent + 3;

            } else {
                button = button + 1;
            }

        }

    }

}

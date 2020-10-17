package pkgfinal.project;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FinalProject extends Application {

    @Override
    public void start(Stage primaryStage) {

        Score root3 = new Score();
        LinePane root = new LinePane();
        EasyLinePane root4 = new EasyLinePane();
        HardPane root5 = new HardPane();
        BorderPane bRoot = new BorderPane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Image image = new Image("file:");
        ImageView imageView = new ImageView(image);
        Group group = new Group(imageView, bRoot);

        Scene scene = new Scene(root, 600, 600);
        Scene easyscene = new Scene(root4, 600, 600);
        Scene hardscene = new Scene(root5, 650, 650);
        Scene bscene = new Scene(group, screenSize.getWidth(), screenSize.getHeight() - 85, Color.DODGERBLUE);

        Stage linestage = new Stage();
        linestage.setScene(scene);
        linestage.setX(300);
        linestage.setY(150);
        linestage.setTitle("Medium Difficulty: LineSweeper");
        bRoot.setlStage(linestage);

        Stage easylinestage = new Stage();
        easylinestage.setScene(easyscene);
        easylinestage.setX(300);
        easylinestage.setY(150);
        easylinestage.setTitle("Easy Difficulty: LineSweeper");
        bRoot.seteStage(easylinestage);

        Stage hardlinestage = new Stage();
        hardlinestage.setScene(hardscene);
        hardlinestage.setX(300);
        hardlinestage.setY(150);
        hardlinestage.setTitle("Hard Difficulty: LineSweeper");
        bRoot.sethStage(hardlinestage);

        Scene scorescene = new Scene(root3, 600, 600);
        Stage scorestage = new Stage();
        scorestage.setX(1000);
        scorestage.setY(150);
        scorestage.setTitle("Scoring");
        scorestage.setScene(scorescene);
        root.setSstage(scorestage);
        root4.setSstage(scorestage);
        root5.setSstage(scorestage);

        primaryStage.setTitle("LineSweeper");
        primaryStage.setScene(bscene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}

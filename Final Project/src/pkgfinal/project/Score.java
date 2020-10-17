
package pkgfinal.project;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Score extends Pane{
    private TextField red1, red2, red3, blue1, blue2, blue3; 
    private Label redlabel1, redlabel2, redlabel3, bluelabel1, bluelabel2, bluelabel3, score, redtitle, bluetitle, redscore, bluescore; 
    private Font redfont, titlefont, endfont;
    private Button scorebutton; 
    int redv1, redv2, redv3, bluev1, bluev2, bluev3; 
    
    public Score() {
        
    Button scorebutton = new Button("Calculate Score"); 
    scorebutton.setLayoutX(195);
    scorebutton.setLayoutY(250);
    scorebutton.setOnAction(this::processButtonPress);
    
    
    
    red1 = new TextField("0");
    red1.setLayoutX(40);
    red1.setLayoutY(70);
    red1.setOnAction(this::processred1);
            
    red2 = new TextField("0");
    red2.setLayoutX(40);
    red2.setLayoutY(135);  
    red2.setOnAction(this::processred2);
    
    red3 = new TextField("0");
    red3.setLayoutX(40);
    red3.setLayoutY(200);
    red3.setOnAction(this::processred3);
    
    blue1 = new TextField("0");
    blue1.setLayoutX(300);
    blue1.setLayoutY(70);
    blue1.setOnAction(this::processblue1);
    
    blue2 = new TextField("0");
    blue2.setLayoutX(300);
    blue2.setLayoutY(135);
    blue2.setOnAction(this::processblue2);
    
    blue3 = new TextField("0");
    blue3.setLayoutX(300);
    blue3.setLayoutY(200);
    blue3.setOnAction(this::processblue3);
    
    Font redfont = new Font("Arial", 12);
    Font titlefont = Font.font("Arial", FontWeight.BOLD, 32);
    
    
    Label redlabel1 = new Label("Enter the Number of Squares Captured:"); 
    redlabel1.setLayoutX(20);
    redlabel1.setLayoutY(45);
    redlabel1.setFont(redfont);
    redlabel1.setTextFill(Color.BLACK);
    
    Label redlabel2 = new Label("Enter the Number of Mines Captured:"); 
    redlabel2.setLayoutX(20);
    redlabel2.setLayoutY(110);
    redlabel2.setFont(redfont);
    redlabel2.setTextFill(Color.BLACK);
    
    Label redlabel3 = new Label("Enter the Number of Bonus Points:"); 
    redlabel3.setLayoutX(20);
    redlabel3.setLayoutY(175);
    redlabel3.setFont(redfont);
    redlabel3.setTextFill(Color.BLACK);    
    
    Label redtitlelabel = new Label("Red:"); 
    redtitlelabel.setLayoutX(20);
    redtitlelabel.setLayoutY(10);
    redtitlelabel.setFont(titlefont);
    redtitlelabel.setTextFill(Color.RED); 

    Label bluelabel1 = new Label("Enter the Number of Squares Captured:"); 
    bluelabel1.setLayoutX(300);
    bluelabel1.setLayoutY(45);
    bluelabel1.setFont(redfont);
    bluelabel1.setTextFill(Color.BLACK);
    
    Label bluelabel2 = new Label("Enter the Number of Mines Captured:"); 
    bluelabel2.setLayoutX(300);
    bluelabel2.setLayoutY(110);
    bluelabel2.setFont(redfont);
    bluelabel2.setTextFill(Color.BLACK);
    
    Label bluelabel3 = new Label("Enter the Number of Bonus Points:"); 
    bluelabel3.setLayoutX(300);
    bluelabel3.setLayoutY(175);
    bluelabel3.setFont(redfont);
    bluelabel3.setTextFill(Color.BLACK);    
    
    Label bluetitlelabel = new Label("Blue:"); 
    bluetitlelabel.setLayoutX(300);
    bluetitlelabel.setLayoutY(10);
    bluetitlelabel.setFont(titlefont);
    bluetitlelabel.setTextFill(Color.BLUE);
    
   
  
    

            
    
    
    getChildren().add(red1);
    getChildren().add(red2);
    getChildren().add(red3);
    getChildren().add(blue1);
    getChildren().add(blue2);
    getChildren().add(blue3);
    getChildren().add(redlabel1);
    getChildren().add(redlabel2);
    getChildren().add(redlabel3);
    getChildren().add(redtitlelabel);
    getChildren().add(bluelabel1);
    getChildren().add(bluelabel2);
    getChildren().add(bluelabel3);
    getChildren().add(bluetitlelabel);
    getChildren().add(scorebutton);
    
    
    

    
    
    
    }
 public void processred1(ActionEvent event){
    redv1 = Integer.parseInt(red1.getText()); 
 }
  public void processred2(ActionEvent event){
    redv2 = Integer.parseInt(red2.getText()); 
 }
   public void processred3(ActionEvent event){
    redv3 = Integer.parseInt(red3.getText()); 
 }
  public void processblue1(ActionEvent event){
    bluev1 = Integer.parseInt(blue1.getText()); 
 }
  public void processblue2(ActionEvent event){
    bluev2 = Integer.parseInt(blue2.getText()); 
 }
  public void processblue3(ActionEvent event){
    bluev3 = Integer.parseInt(blue3.getText()); 
 }
    
    public void processButtonPress(ActionEvent e){
     int blueScore = bluev1 - 6*bluev2 + bluev3; 
     int redScore = redv1 - 6*redv2 + redv3; 
     Font titlefont = Font.font("Arial", FontWeight.BOLD, 32);
     
    Label bluescore = new Label(""); 
    bluescore.setText("Blue Score: " +blueScore);
    bluescore.setLayoutX(330);
    bluescore.setLayoutY(250);
    bluescore.setTextFill(Color.BLUE);
    getChildren().add(bluescore);
    
    Label redscore = new Label(""); 
    redscore.setText("Red Score: " + redScore);
    redscore.setLayoutX(30);
    redscore.setLayoutY(250);
    redscore.setTextFill(Color.RED); 
    getChildren().add(redscore);
    
    if (redScore > blueScore){
    Label redwin = new Label(""); 
    redwin.setText("Red Wins!");
    redwin.setFont(titlefont); 
    redwin.setLayoutX(180);
    redwin.setLayoutY(300);
    redwin.setTextFill(Color.RED);
    getChildren().add(redwin); 
        
    }
    
    if (redScore < blueScore){
    Label bluewin = new Label(""); 
    bluewin.setText("Blue Wins!");
    bluewin.setFont(titlefont); 
    bluewin.setLayoutX(180);
    bluewin.setLayoutY(300);
    bluewin.setTextFill(Color.BLUE);
    getChildren().add(bluewin); 
        
    }
    if (redScore == blueScore){
   Label tie = new Label(""); 
    tie.setText("It is a Tie");
    tie.setFont(titlefont); 
    tie.setLayoutX(180);
    tie.setLayoutY(300);
    tie.setTextFill(Color.PURPLE);
    getChildren().add(tie);  
    }
     
     
      
        
    }
}

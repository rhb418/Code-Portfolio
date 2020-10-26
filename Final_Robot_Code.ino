//Pins for QTI connections on board: We used 3 QTI sensors in total
#define lineSensor1 47 //the first QTI sensor: the right one
#define lineSensor2 51 //the second QTI sensor: the middle one
#define lineSensor3 52 //the third QTI sensor: the left one

#include <LiquidCrystal.h> //the library needed for the LCD display
#include <Servo.h> //the library needed to control the servo motors

Servo servoLeft;                             // Declare left servo signal
Servo servoRight;                           // Declare right servo signal 

float threshold = 501.0;                   //magnet sensing - If higher than this value +20 - ''magnet detected", if lower than this value +20 - "magnet not detected"
static int nhash = 0;                      // for the counter function
static int value = 0;                      // for the position of the magnet
int bellPin = 4;                           // song
int thresh = 150;                          //creates a threshold value - line following - If higher than this value - "black", if lower - "white"; 

void setup() {
  Serial.begin(9600);                      //own serial monitor
  Serial3.begin(9600);                     //LCD display
  Serial2.begin(9600);                     //team monitor - start the serial monitor so we can view the output
  servoLeft.attach(12);                      // Attach left signal to pin 12
  servoRight.attach(11);                    //Attaches right servo to pin 11
  pinMode(5,OUTPUT);                        //Set pin5 to output - yellow LED
  pinMode(2,OUTPUT);                        //Set pin2 to output - red LED
  pinMode(bellPin, OUTPUT);                 //Set pin4 to output
  digitalWrite(bellPin, HIGH);              //piezospeaker connection
  digitalWrite(2,LOW);                      //Initially set pin2 to low - red LED off
  digitalWrite(5,LOW);                      //Initially set pin5 to low - yellow LED off
  Serial3.write(12);
}


//Defines funtion 'RCTime' to read value from QTI sensor
  long RCtime(int sensorIn) { //takes in one of the three QTI sensor pin 47, 51, 52
  long duration = 0;
  pinMode(sensorIn, OUTPUT); // Sets pin as OUTPUT
  digitalWrite(sensorIn, HIGH); // Pin HIGH  - to discharge capacitor
  delay(1); // Waits for 1 millisecond to make sure the capacitor is fully discharged
  pinMode(sensorIn, INPUT); // Sets pin as INPUT and time til pin goes low
  digitalWrite(sensorIn, LOW); // Pin LOW

  while(digitalRead(sensorIn)) { // Waits for the pin to go LOW
    duration++;

  }
return duration; // Returns the duration of the pulse
}

//QTI sensor is first activated by recieving 5V. This will cause the current to flow
//through the LED side. LED emits IR light. IR light reflecting off the surface below
//will cause a change in the abilitiy of current to flow through the phototransistor
//side. The resistance changes depending on the reflectivity over the surface. 

//dark surface - high resistance - higher RC time
//light surface - low resistance - lower RC time

void loop() {
  
//LINE FOLLOWING CODE
  int qti1 = RCtime(lineSensor1);     //Calls funtion 'RCTime' Request reading from QTI sensor at pin 'linesensor1' saves value in variable 'qti1'
  int qti2 = RCtime(lineSensor2);     //Calls funtion 'RCTime' Request reading from QTI sensor at pin 'linesensor2' saves value in variable 'qti2'
  int qti3 = RCtime(lineSensor3);     //Calls funtion 'RCTime' Request reading from QTI sensor at pin 'linesensor3' saves value in variable 'qti3' 

//4 scenarios 
  if (qti2 >= thresh && qti1 < thresh && qti3 < thresh){  //Drive forward when the central qti sensor is over black and the other two are over white
  servoLeft.writeMicroseconds(1400);         
  servoRight.writeMicroseconds(1597.5);
  }

  else if (qti2< thresh && qti1 < thresh && qti3 > thresh){   //Turn left when the left most qti sensor is over black and the other two are over white
  servoLeft.writeMicroseconds(1550);        
  servoRight.writeMicroseconds(1550);
  }
  
  else if (qti2< thresh && qti1 > thresh && qti3 < thresh){ //Turn right when the right most qti sensor is over black and the other two are over white
  servoLeft.writeMicroseconds(1450);         
  servoRight.writeMicroseconds(1450);
  }
  
  else if (qti2> thresh && qti1 > thresh && qti3 > thresh) { //Pauses when all three sensors are over black - makes a decision to go forward or to fully stop depending on the counter number
  servoLeft.writeMicroseconds(1500);          
  servoRight.writeMicroseconds(1500);
  delay(1000); 

 //Counter for line following 
  nhash = nhash+1; //everytime it stops nhash variable counts how many stops the bot made
  Serial.println(nhash);
  float reading = (analogRead(4)); //hall effect sensor reading
  Serial.println(reading); //for hall effect sensor output views
  
//MAGNET SENSING CODE
  if (reading > threshold+20){  //Turns the yellow LED on when the value read by the hall sensor is greater than 521
  digitalWrite(5,HIGH);
  value = nhash; //stores the magnet position in a variable called value
  Serial3.write(12);
  Serial3.print('y'); //prints out 'y' for 'yes magnet detected'

  delay(1000);  
  digitalWrite(5,LOW); //Yellow LED off after 1 sec on delay
  }
  else if(reading <= threshold+20){ //Turns the red LED on when the value read by the hall sensor is less than or equal to 521
    digitalWrite(2, HIGH);
    Serial3.write(12);
    Serial3.print('n');  //prints out 'n' for 'no magnet not detected'

    delay(1000);
    digitalWrite(2, LOW); //Red LED off after 1 sec on delay
  }
//COUNTER CONTINUED  
  if(nhash>=5){ //if the bot stops for the fifth time (or more) it stops
  servoLeft.writeMicroseconds(1500);         
  servoRight.writeMicroseconds(1500);

//COMMUNICATION WITH OTHER BOTS - only after the bot makes a full stop
  char dino = value +60; //our detection in ASCII
  Serial2.print(dino); //sends out our detection to the team
  Serial3.write(12);
  while(true){
    int val = Serial2.read();
//RESPONSE TO DIFFERENT SITUATIONS
    if(val > 75 && val <= 85){ //if returned value from the sentry bot in between 75 to 85
      Serial3.print(1); //print 1 on LCD display
      song(); //sing the song - we escaped! or we detected 5 and everyone is happy
     
    }
    else if(val>=70 && val<= 75){ //if returned value from the sentry bot in between 70 to 75
      Serial3.print(0); //print 0 on LCD display - we failed to escape - don't do anything
        
  }
  }
  }
   
  else{ //keep moving after encountering a hashmark until the fifth hashmark 
  servoLeft.writeMicroseconds(1450);         
  servoRight.writeMicroseconds(1547.5);
  delay(500);
  }
  }
}

//CODE FOR OUR SONG
void song(){
  digitalWrite(bellPin, HIGH);
 
  tone(bellPin, 988, 250);
  delay(1000);
  tone(bellPin, 988, 250);
  delay(400);
  tone(bellPin, 880, 250);
  delay(400);
  tone(bellPin, 988, 250);
  delay(1000);
 
  tone(bellPin, 988, 250);
  delay(400);
  tone(bellPin, 880, 250);
  delay(400);
  tone(bellPin, 988, 250);
  delay(1000);
 
  tone(bellPin, 988, 250);
  delay(400);
  tone(bellPin, 880, 250);
  delay(400);
  tone(bellPin, 784, 1000);
  delay(1000);
  tone(bellPin, 880, 1000);
  delay(1000);
}

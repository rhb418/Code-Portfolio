package slogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.commands.Command;
import slogo.commands.Make;
import slogo.commands.MathOps;
import slogo.commands.super_commands.DoTimes;
import slogo.commands.super_commands.For;
import slogo.commands.super_commands.If;
import slogo.commands.super_commands.IfElse;
import slogo.commands.super_commands.Repeat;
import slogo.commands.super_commands.To;
import slogo.commands.turtle_commands.Back;
import slogo.commands.turtle_commands.Forward;
import slogo.commands.turtle_commands.Left;
import slogo.model.CommandExecutor;
import slogo.model.parser.Parser2;
import slogo.turtle.Turtle;

import static org.junit.jupiter.api.Assertions.*;
import static slogo.commands.CommandConstants.POW;
import static slogo.commands.CommandConstants.PRODUCT;
import static slogo.commands.CommandConstants.QUOTIENT;
import static slogo.commands.CommandConstants.SUM;

public class CommandExecutionTester {

  private CommandExecutor myCE;
  private Turtle myTurtle;
  private List<Turtle> myTurtleList;
  private List<Integer> myActiveTurtles;
  private HashMap<String, Double> myVars;

  @BeforeEach
  void setUp() {
    myTurtle = new Turtle(0);
    myTurtleList = new ArrayList<>();
    myTurtleList.add(myTurtle);
    myActiveTurtles = new ArrayList<>();
    myActiveTurtles.add(0);
    myVars = new HashMap<>();
    myVars.put(":dist", 100.0);
    myVars.put(":angle", 220.0);

  }

  @Test
  void testBasicCE() {
    String input = "sum fd :dist fd :angle";
    Parser2 p = new Parser2(input, new HashMap<>(),new HashMap<>(), new HashMap<>(), "English");

    List<Command> commandList = p.newParser();

    myCE = new CommandExecutor(commandList, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(320, result);
    assertEquals(320, myTurtle.getXPosition());
  }

  @Test
  void testComplexForward() {
    Command fd1 = new Forward(new String[]{null});
    Command sum1 = new MathOps(new String[]{"10", null}, SUM);
    Command sum2 = new MathOps(new String[]{"10", null}, SUM);
    Command sum3 = new MathOps(new String[]{"10", null}, SUM);
    Command sum4 = new MathOps(new String[]{"5", "5"}, SUM);

    List<Command> commandList = new ArrayList<>();
    commandList.add(fd1);
    commandList.add(sum1);
    commandList.add(sum2);
    commandList.add(sum3);
    commandList.add(sum4);

    myCE = new CommandExecutor(commandList, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(40, result);
    assertEquals(40, myTurtle.getXPosition());
  }

  @Test
  void testComplexForward2() {
    Command fd1 = new Forward(new String[]{null});
    Command sum1 = new MathOps(new String[]{null, "5"}, SUM);
    Command sum2 = new MathOps(new String[]{null, "5"}, SUM);
    Command sum3 = new MathOps(new String[]{null, "30"}, SUM);
    Command sum4 = new MathOps(new String[]{"10", "20"}, SUM);

    List<Command> commandList = new ArrayList<>();
    commandList.add(fd1);
    commandList.add(sum1);
    commandList.add(sum2);
    commandList.add(sum3);
    commandList.add(sum4);

    myCE = new CommandExecutor(commandList,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(70, result);
    assertEquals(70, myTurtle.getXPosition());

  }

  @Test
  void testVariableCreation() {
    Command var1 = new Make(new String[]{":dist", null});
    Command sum1 = new MathOps(new String[]{"5", "4"}, SUM);
    Command var2 = new Make(new String[]{":size", "23"});
    Command prod1 = new MathOps(new String[]{":dist", ":size"}, PRODUCT);

    List<Command> commandList = new ArrayList<>();
    commandList.add(var1);
    commandList.add(sum1);
    commandList.add(var2);
    commandList.add(prod1);

    myCE = new CommandExecutor(commandList, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();
    assertEquals(207, result);
  }

  @Test
  void testRepeat() {
    String input = "repeat 3 [ fd sum 10 sum 10 sum 10 sum 5 5 ] fd 50";
    Parser2 p = new Parser2(input, new HashMap<>(),new HashMap<>(), new HashMap<>(), "English");

    List<Command> commandList = p.newParser();


    myCE = new CommandExecutor(commandList, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();
    assertEquals(50, result);
    assertEquals(170, myTurtle.getXPosition());

  }

  @Test
  void testDoTimes() {
    String input = "dotimes [ :k 210 ] [ sum fd :k product 0 :k ] ";
    Parser2 p = new Parser2(input, new HashMap<>(),new HashMap<>(), new HashMap<>(), "English");

    List<Command> commandList = p.newParser();

    myCE = new CommandExecutor(commandList, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(210, result);
    assertEquals(22155, myTurtle.getXPosition());
  }

  @Test
  void testFor() {
    Command fd1 = new Forward(new String[]{null});
    Command sum1 = new MathOps(new String[]{":k",null}, SUM);
    Command sum4 = new MathOps(new String[]{"0", ":k"}, PRODUCT);

    List<Command> commandList1 = new ArrayList<>();
    commandList1.add(fd1);
    commandList1.add(sum1);
    commandList1.add(sum4);

    Command for1 = new For(new String[]{":k", "1", "10", "1"}, commandList1);
    List<Command> commands = new ArrayList<>();
    commands.add(for1);

    myCE = new CommandExecutor(commandList1, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(0, result);
  }

  @Test
  void testIf() {
    String input = "tell [ 0 1 2 ] if greater? id 1 [ fd 50 ]";
    Parser2 p = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> commands = p.newParser();

    myCE = new CommandExecutor(commands,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(0, myTurtleList.get(0).getXPosition());
    assertEquals(0, myTurtleList.get(1).getXPosition());
    assertEquals(50, myTurtleList.get(2).getXPosition());

  }

  @Test
  void testIfElse() {
    Command fd1 = new Forward(new String[]{"20"});
    List<Command> commandList1 = new ArrayList<>();
    commandList1.add(fd1);
    Command r1 = new Repeat(new String[]{"420"}, commandList1);

    Command fd2 = new Forward(new String[]{"2"});

    List<Command> trueCommands = new ArrayList<>();
    List<Command> falseCommands = new ArrayList<>();

    trueCommands.add(r1);
    falseCommands.add(fd2);

    Command ifelse = new IfElse(new String[]{"1"}, trueCommands, falseCommands);
    List<Command> commands = new ArrayList<>();
    commands.add(ifelse);

    myCE = new CommandExecutor(commands, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();
    assertEquals(8400, myTurtle.getXPosition());


  }

  @Test
  void testTo() {
    List<String> parameters = new ArrayList<>();
    parameters.add(":cheese");
    parameters.add(":onions");
    parameters.add(":sausage");

    Command fd1 = new Forward(new String[]{":cheese"});
    Command rot = new Left(new String[]{":onions"});
    Command back = new Back(new String[]{":sausage"});

    List<Command> args = new ArrayList<>();
    args.add(fd1);
    args.add(rot);
    args.add(back);

    Command to = new To(new String[]{"10", "60", "30"}, args, parameters, "Test");
    List<Command> run = new ArrayList<>();
    run.add(to);

    myCE = new CommandExecutor(run, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    //assertEquals(-5,myTurtle.getXPosition());
    assertEquals(60, myTurtle.getDirection());


  }

  @Test
  void testBasicErrorCheck() {
    Command fd1 = new Forward(new String[]{"20"});
    Command quot = new MathOps(new String[]{"-1.23", "-76.456"}, POW);

    List<Command> commandList = new ArrayList<>();
    commandList.add(fd1);
    commandList.add(quot);

    myCE = new CommandExecutor(commandList, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();


    assertEquals(0, result);
    assertEquals(0, myTurtle.getXPosition());
  }

  @Test
  void testNestedErrorCheck() {
    Command fd1 = new Forward(new String[]{"50"});
    Command sum1 = new MathOps(new String[]{"5", ":sdfgsd0"}, QUOTIENT);
    Command sum4 = new Back(new String[]{});

    List<Command> commandList1 = new ArrayList<>();
    commandList1.add(fd1);
    commandList1.add(sum1);
    commandList1.add(sum4);

    Command if1 = new If(new String[]{"1"}, commandList1);
    List<Command> commands = new ArrayList<>();
    commands.add(if1);

    myCE = new CommandExecutor(commands, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(0, result);

  }

  @Test
  void tripleRepeatTest() {
    Command fd1 = new Forward(new String[]{"50"});
    Command math1 = new MathOps(new String[]{"1", "2"}, SUM);
    List<Command> list1 = new ArrayList<>();
    list1.add(fd1);
    list1.add(math1);

    Command repeat1 = new Repeat(new String[]{"2"}, list1);
    List<Command> list2 = new ArrayList<>();
    list2.add(repeat1);

    Command repeat2 = new Repeat(new String[]{"3"}, list2);
    List<Command> list3 = new ArrayList<>();
    list3.add(repeat2);

    Command repeat3 = new Repeat(new String[]{"4"}, list3);
    List<Command> list4 = new ArrayList<>();
    list4.add(repeat3);

    myCE = new CommandExecutor(list4, myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();


    assertEquals(3, result);

  }

  @Test
  void testParserSimple() {
    String input = "fd sum sum sum sum 10 20 30 5 5";
    Parser2 p = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");

    List<Command> commands = p.newParser();



    myCE = new CommandExecutor(commands,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(70,myTurtle.getXPosition());

  }

  @Test
  void testTell(){
    String input = "tell [ 0 1 2 ] fd product id 50";
    Parser2 p = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> commands = p.newParser();

    myCE = new CommandExecutor(commands,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(0, myTurtleList.get(0).getXPosition());
    assertEquals(50, myTurtleList.get(1).getXPosition());
    assertEquals(100, myTurtleList.get(2).getXPosition());

  }

  @Test
  void testBoolOps(){
    String input = "fd product greater? 10 5 100";
    Parser2 p = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> commands = p.newParser();

    myCE = new CommandExecutor(commands,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(100, myTurtleList.get(0).getXPosition());
  }

  @Test
  void testAsk(){
    String input = "tell [ 0 1 2 ] ask [ 0 1 ] [ fd id ]";
    Parser2 p = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> commands = p.newParser();

    myCE = new CommandExecutor(commands,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(0, myTurtleList.get(0).getXPosition());
    assertEquals(1, myTurtleList.get(1).getXPosition());

  }

  @Test
  void testAskWith(){
    String input = "tell [ 0 1 2 ] fd product id 50 askwith [ greater? xcor 50 ] [ fd 20 ]";
    Parser2 p = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> commands = p.newParser();

    myCE = new CommandExecutor(commands,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(0, myTurtleList.get(0).getXPosition());
    assertEquals(50, myTurtleList.get(1).getXPosition());
    assertEquals(120, myTurtleList.get(2).getXPosition());

  }

  @Test
  void testTurtleCommands(){
    String input = "setxy 20 40 back 10 fd 20 cs lt 90 rt 90 pd pu st ht fd 50";
    Parser2 p = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> commands = p.newParser();

    myCE = new CommandExecutor(commands,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(50, myTurtleList.get(0).getXPosition());

  }

  @Test
  void testMoreTurtleCommands(){
    String input = "seth 90 fd 50 home towards 20 0 fd 50";
    Parser2 p = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> commands = p.newParser();

    myCE = new CommandExecutor(commands,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(50, myTurtleList.get(0).getXPosition());

  }

  @Test
  void testSomeBasicSuperCommands(){
    String input = "setbg 1 setsh 1 setpalette 1 45 97 125 setpensize 2 setpc 1 fd sum sh turtles fd pc";
    Parser2 p = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> commands = p.newParser();

    myCE = new CommandExecutor(commands,myTurtleList, myActiveTurtles, myVars);
    double result = myCE.executeCommands();

    assertEquals(3, myTurtleList.get(0).getXPosition());

  }


}
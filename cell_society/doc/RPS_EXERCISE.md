# Simulation Lab Discussion

## Rock Paper Scissors

## Names and NetIDs
Robert Barnette rhb19
Kevin Yang kyy2
Felix Jiang fj32

### High Level Design Idea
**Determine the winner**: use a cost method that calculates how many players can destroy you. Lowest cost wins. If there is a tie for lowest cost, tied players continue playing 

**Player Class**: Has some method that picks their choice

**Move Class**: Can compare moves against each other without attachment to a player

**ChooseWin Class**: Given a bunch of players and their choices, return all the costs of the players

**Comparison Class**:  A simple comparison between 2 player objects, has an inner method that compares two player's moves

### CRC Card Classes

This class's purpose or value is to manage the player's actions and data:
```java
 public class Player {
     public makeMove ();
     public Score getScore ();
     public Move getMove();
     public int getID(); 
     public void setScore(int score);
     public void updateScore(); // adds 1 to score
 }
```

This class's purpose or value is to create a new move for a player:
```java
public class Move{
  public generateNewMove(); 

  public String getMyState(); 
  
}
```

This class's purpose or value is to be calculate the winner of a round, update will call itself recursively if there is a tie to compute the winner:
```java
 public class ChooseWin {
     public int update (Collections<Player>)  
 }
```

This class compares two player's moves and returns true if m1 beats m2, false otherwise. Update would call this function for each player onto every other player, and update the cost function accordingly. 
```java
 public class Comparison {
     public boolean compare (Move m1, Move m2) 
 }
```

This class's purpose or value is to start the game:
```java
 public class Main {
     public void start () 
 }
```

### Use Cases

 * A new game is started with five players, their scores are reset to 0.
 ```java
List<Player> list = new ArrayList<>();
for(int i = 0; i < 5; i++){
  list.add(new Player());
  list.get(i).setScore(0);
}
start();
 ```

 * A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
 player.makeMove(); 
 ```

 * Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 ChooseWin myChooser = new  ChooseWin(); 
 
 int winner = myChooser.update(list); 
 list.get(winner).updateScore();
 ```

 * A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 Move newMove 
 // change internal data structure to update relationships

 ```

 * A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 // inside the player class, change the move object
 Move newGame = new ChildMove();
 ```


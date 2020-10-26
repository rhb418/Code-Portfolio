# Code-Portfolio
Programming Portfolio for Robert Barnette
## Final Project
This Java project represents the implementation of a custom game called linesweeper. This implementation
uses a variety of JavaFX objects to create an interactive two player game with a start page describing the 
rules and allowing the players to select one of three difficulties. The players can then launch the game and play 
until completion. Following the end of the game the players can then load a final page to calculate the score and
determine the winner.
## Final_Robot_Code.ino
This Arduino code controlled our robot as a part of the ECE 110 Integrated design challenge.
It uses readings from QTI sensors as well as a hall effect sensor to navigate a circular path
and detect magnetic fields when the robot is stopped. 
## PizzaCalc.c and PizzaCalc.s
This program reads data from an input file containing a number of different pizza types each with a size and price. 
It then calculates the amount of unit area of pizza per dollar for a given pizza and stores this information along 
with the name of the pizza in a linked list node. This linked list of pizzas is then sorted by highest to lowest
area per dollar and is then printed out. The .c version of this file is the solution to this problem in C and the
.s file is the solution written in MIPS Assembly language.
## cpu.circ
This is a logisim circuit file of a cpu that I created to implement a simple RISC 16-bit cpu architecture.
## Cachesim.c
This program simulates the hit/miss behavior of a write through write no allocate cpu cache.
It does this by taking command line arguments of a file of load and store requests, a cache size,
an associativity, and a block size then simulating a cache with these parameters. It then reads 
the file of load and store requests, checking if the data requested is in the simulated cache and 
then updates the cache after each request. It also then prints whether the data access was a 
cache hit or miss.

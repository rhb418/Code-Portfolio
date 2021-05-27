## Refactoring Lab Discussion
### Team 02
### Names

Bill Guo(wg78)
billy luqiu (wyl6)
Robert Barnette (rhb19)
Tomas Esber (tee9)


### Issues in Current Code
Longest method is 33 lines, some methods are difficult to read with a number of for loops and if else blocks. Some classes violate the single responsibility principle in that they attempt to fulfill multiple responsibilities. Additionally the command execution tests need to be refactored to match the new command executor.


#### Class Parser2
* Code smells of long methods and long chains of for loops/if statements

* Design issue Hard coded array lists can be put in a resource file

* Resource bundle with command constants and a separate constants class currently exist in the code

#### Class Toolbar
* Violates single responsibility principle, handles the methods of all buttons in the Toolbar


### Refactoring Plan

* What are the code's biggest issues?
  Long methods, CheckStyle errors, Types should be as general as possible, turning instance variables into local variables when they are only used in one method.

* Which issues are easy to fix and which are hard?

Long methods are harder to fix than the other issues, which are all relatively easy to fix as the fix involves deleting unnecessary lines of code that do not affect implementation

* What are good ways to implement the changes "in place"?
    * Test code after writing it
    * Look for the code's dependencies

### Refactoring Work

* Issue chosen: Fix and Alternatives
    - Chose to fix the issue of having Command Constants information held in both a class and a properties file. This is clearly duplicate code that we thought we needed to address.
    - We decided to keep the class of command constants and remove the properties file. The class allows for easier/cleaner code in the Command subclasses that rely on obtaining the associated value of a ```MathOps``` or  ```BoolOps``` command type.
    - The fix is in the ```SimpleCommandGenerator``` which relies on the properties file
    - We decided to abort this refactoring since to get our parser functioning we had to make assumptions about how we wanted to hold the name of user inputted commands in Strings.
    - We tried to refactor it using reflection to call the command constants class in order to get the proper field, but the class names were subtly different due to different requirements and assumptions made by the commands package and the parser package, and thus we decided that the first design was better as we standardized commands name would've required significant refactoring and since the parser and commands package are slightly different, it was fine to have them read data from two separate files

* Issue chosen: Fix and Alternatives
  Fixed all checkstyle errors and making sure parameters/return values were of the abstract maps/lists not specific types
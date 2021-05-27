## SLogo API Changes
### Team 02
### Names
Billy Luqiu (wyl6)
Tomas Esber (tee9)
Bill Guo (wg78)
Robert Barnette (rhb19)


### Changes to the Initial APIs

#### Backend External

* Method changed:

  ```getPreviousFunctionsDefined()```

    * Why was the change made?
        - Change really came from the BackEnd Internal. As a result of a different implementation in our CommandExecutor, we decided not to use an ```ImmutableMap<>``` but rather a ```List<String>``` where the ```String``` represents the user-defined function.
    * Major or Minor (how much they affected your team mate's code)
        - More of a minor change. The major change that affected this implementation came from BackEnd Internal. User defined functions make up only one function out of many dozens.
    * Better or Worse (and why)
        - Somewhat better in our view since it simplifies code needed for parsing ```To``` commands, for example. The user also has the option of potentially overriding their original command.

* Method changed:
  ```getPreviousCommands()```
    * Why was the change made?
        - We delegated the "remembering commands" action to the ```Command``` abstract superclass.
    * Major or Minor (how much they affected your team mate's code)
        - Again more of a minor change. Since our BackendExternal is really only composed of one class, we did not see any reason to change many of our methods.

    * Better or Worse (and why)
        - Better implementation in our view since it makes more sense for the actual individual SuperCommands to _know_ what commands they _have_.


#### Backend Internal

* Method changed:Turtle class had new methods
  setPenState get PenState and etc

    * Why was the change made?
        * Complete implementations required it
    * Major or Minor (how much they affected your team mate's code)
        * Minor, affected the relevant command classes in the commands package, but nothing else
    * Better or Worse (and why)
        * better, these were needed features for complete implementation and did not affect our other features badly

* Method changed:

    * Why was the change made? Parsers parseCommand method returns a list ofo commands instead of a command, since we decided that to parse a command we needed to return the list as a string of commands can be made of many different commands

    * Major or Minor (how much they affected your team mate's code) major, we needed to let the command execute execute a list of commands, but this actually wasn't too bad as we just had a for loop to iterate over all commands that were parsed

    * Better or Worse (and why)
    * Better as we we needed this functionality in order to do nested commands properly, something we weren't considereding beforehand


* Method changed: buildCommand feature

    * Why was the change made?
    * refactored so that design was better in that thea ctual instances of the commands were being created in a separate class and not in the main parser class
    * Major or Minor (how much they affected your team mate's code)
    * Very minor, nothing changed as far as overall API goes
    * Better or Worse (and why)
    * Better, code easier to read and follows single responsibility principles more closely by delegating tasks appropraitely

* Method changed:

    * Why was the change made?

    * Major or Minor (how much they affected your team mate's code)

    * Better or Worse (and why)

* Method changed:

    * Why was the change made?

    * Major or Minor (how much they affected your team mate's code)

    * Better or Worse (and why)



#### Frontend External

* Method changed: ```getLanguage()```

    * Why was the change made?
      Method became obsolete with how the backend is created
    * Major or Minor (how much they affected your team mate's code)
      Minor, backend does not need to use this method to accomplish language changes
    * Better or Worse (and why)
      Better, removed unnecessary method

#### Frontend Internal

* Method changed: ```getConsoleText()```

    * Why was the change made?
      Became  obsolete as the Backend does not need information from the console display

    * Major or Minor (how much they affected your team mate's code)
      Minor, team mate's code still expects commands in String form, just from a different view object

    * Better or Worse (and why)
      Better as it follows single class resposibility principle better


* Method changed: ```initialize()```

    * Why was the change made?
      Frontend uses initialize on classes to grab their JavaFX display object

    * Major or Minor (how much they affected your team mate's code)
      Major, team mate's code now calls this method to grab the object rather than using a getter

    * Better or Worse (and why)
      Better as it is a way to give the scenebuilder the display object without having other classes access the entire view object.

* Method changed: ```updateAllViews()```

* Why was the change made?
  SceneBuilder no longer needs to send information between views

* Major or Minor (how much they affected your team mate's code)
  Minor, view copmenetes are stilled updated in the same way, only where the method is called changes

* Better or Worse (and why)
  Better as it follows single class responsibility principle better.

* Method changed: ```updateAllViews()```

    * Why was the change made?
      SceneBuilder no longer needs to send information between views

    * Major or Minor (how much they affected your team mate's code)
      Minor, view copmenetes are stilled updated in the same way, only where the method is called changes

    * Better or Worse (and why)
      Better as it follows single class responsibility principle better.
     

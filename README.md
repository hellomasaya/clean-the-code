# Software Engineering Assignment #1 - Clean code
## Harshita Sharma(20171099)
-------------------------------
## Instructions to run the code:

### Install/Update Java
      * sudo add-apt-repository ppa:openjdk-r/ppa
      * sudo apt-get update -q 
      * sudo apt install -y openjdk-11-jdk 

### For The Main File
      * Clone this repo 
      * install ant by running 'sudo apt-get install ant'
      * then go to the folder where you have cloned this repo
      * Run 'ant compile'
      * Run 'ant jar'
      * Run 'java -cp build/jar/args.jar com.cleancoder.args.Arguments'

### For the tests
      * Run the command given below from the root folder of this repo
      * Run 'java -cp "lib/junit-4.13.jar:lib/hamcrest-core-1.3.jar:build/jar/args.jar" ./test/com/cleancoder/args/ArgsTest.java testCreateWithNoSchemaOrArguments'
---------------------------------
## Changes made:
[Ref. Clean code by Robert C. Martin and class slides]

1. [Arguments.java](./src/com/cleancoder/args/Arguments.java):

      a. Naming: Class name `ArgsMain` has been changed to `Arguments` to avoid mental mapping and because *classes should have noun or noun phrase names* and to *avoid mental mapping*.
      
      b. Visual Design of Code: Unifying list of arguments i.e. arguments in separate lines
---------------------------------

2. [Args.java](./src/com/cleancoder/args/Arguments.java):  

      a. Naming: 
      - Variable `schema` changed to `argSchema` for reflecting the 
      - Method `parseArgumentStrings` changed to `parseListofArguments` for better readability and to provide better and descriptive names. *"A long descriptive name is better than a short enigmatic name. A long descriptive name is better than a long descriptive comment."*
      - Changed `elementTail` to `elementSymbol`.
      
      b. Reduce number of function arguments for:
      - `parseSchema()` and `parseListofArguments()` by declaring all variables in the entire scope of the class `Args()`.
      - Passed `element` to method `putIntoMarshaler` instead of passing `elementId` and `elementSymbol` separately.
      
      c. Functions must do exactly one thing:  
      - Args.java: Broke down `parseSchemaElement` function into `parseSchemaElement` and `putIntoMarshaler` corresponding to the two fuctions/actions which were being preformed.

      d. Order of functions changed according to: *"put the private utilities called by a public function right after the public function itself. This follows the stepdown rule and helps the program read like a newspaper article."*

      e. Using inbuilt function `isBlank()` to check for blank or empty strings instead of `element.length() > 0`.

      f. In method `parseSchemaElement`, to avoid using hardcoded indices for e.g. - 
      ```
      char elementId = element.charAt(0);
      ```
      used variables instead -
      ```
      int index = 0;
      char elementId = element.charAt(index);
      ```


---------------------------------
3. Add necessary comments

---------------------------------
4. Visual Design of Code:  
      - Unifying list of arguments i.e. arguments in separate lines
      - Lesser lines of alignment hence, attention

---------------------------------
    

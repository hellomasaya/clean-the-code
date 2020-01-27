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
      
      b. Visual Design of Code: 
      - Unifying list of arguments i.e. arguments in separate lines
      - Removed extra whitespaces and newlines
      - Removed extra curly braces that reduce the readability of the code

      c. 
---------------------------------

2. [Args.java](./src/com/cleancoder/args/Arguments.java):  

      a. Naming: *"A long descriptive name is better than a short enigmatic name. A long descriptive name is better than a long descriptive comment."*  
      - Variable `schema` changed to `argSchema` for reflecting the 
      - Method `parseArgumentStrings` changed to `parseListofArguments` for better readability and to provide better and descriptive names.
      - Changed `elementTail` to `elementSymbol`.
      - Changed `argsList` to `listOfArgs` for better readability.
      - Renamed method `parseArgumentCharacter` to `setMarshaler`.
      - Renamed meaningless variables `m` and `e` to more meaningful name like `matchedMarshaler`, `err` respectively.
      - Renamed method `has` to `hasFoundArgument`
      
      b. Reduce number of function arguments for:
      - `parseSchema()` and `parseListofArguments()` by declaring all variables in the entire scope of the class `Args()`.
      - Passed `element` to method `putIntoMarshaler` instead of passing `elementId` and `elementSymbol` separately.
      
      c. Functions must do exactly one thing:  
      - Broke down `parseSchemaElement` method into `parseSchemaElement` and `putIntoMarshaler` corresponding to the two fuctions/actions which were being preformed.
      - Broke `parseArgumentCharacters` method into `matchMarshaler` and `setMarshaler` corresponding to the two fuctions/actions which were being preformed.

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
      f. To improve the scalability of the code, a HashMap `marshalersMappedToSymbols` (that maps each schema symbol to its corresponding marshaler) has been added. Now, in the case of adding a new Marshaler type, one just needs to add an entry of the form `put("Symbol", marshalerType());` in the hashtable, and a corresponding get function at the end of the class.

      g. Added new **utility functions** to improve the readability of if-else statements. For example: 
      Changed `if(!Character.isLetter(elementId))` to  `if(isNotLetter(elementId))`.

      h. Visual Design of Code: 
      - Unifying list of arguments i.e. arguments in separate lines
      - Removed extra whitespaces and newlines
      - Removed extra curly braces that reduce the readability of the code

---------------------------------
3. [MapArgumentMarshaler.java](./src/com/cleancoder/args/MapArgumentMarshaler.java)






---------------------------------
4. [BooleanArgumentMarshaler.java](./src/com/cleancoder/args/BooleanArgumentMarshaler.java), [StringArgumentMarshaler.java](./src/com/cleancoder/args/StringArgumentMarshaler.java), [StringArrayArgumentMarshaler.java](./src/com/cleancoder/args/StringArrayArgumentMarshaler.java):  

      a. Naming: 
      - Changed `am` to `argsMarshaler`
      - Changed `e` to `err`

      b. Visual Design of Code: 
      - Unifying list of arguments i.e. arguments in separate lines
      - Removed extra whitespaces and newlines
      - Removed extra curly braces that reduce the readability of the code

---------------------------------
5. [IntegerArgumentMarshaler.java](./src/com/cleancoder/args/IntegerArgumentMarshaler.java), [DoubleArgumentMarshaler.java](./src/com/cleancoder/args/DoubleArgumentMarshaler.java)

      a. Naming:
      - Changed `am` to `argsMarshaler`
      - Changed `e` to `err`
      - Changed `parameter` to a more meningful and descriptive name `argValue` 

      b. Visual Design of Code: 
      - Unifying list of arguments i.e. arguments in separate lines
      - Removed extra whitespaces and newlines
      - Removed extra curly braces that reduce the readability of the code

---------------------------------
    

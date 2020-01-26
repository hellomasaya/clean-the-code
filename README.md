# Software Engineering Assignment #1 - Clean code
## Harshita Sharma(20171099)
-------------------------------

## Changes made:
[Ref. Clean code by Robert C. Martin and class slides]

1. Classes:

      a. Naming:  
      - Arguments.java: Class name `ArgsMain` has been changed to `Arguments` to avoid mental mapping and because *classes should have noun or noun phrase names*.
      
      b. 
---------------------------------
2. Functions:  

      a. Must do exactly one thing:  
      - Args.java: Broke down `parseSchemaElement` function into `parseSchemaElement` and `matchMarshaler` corresponding to the two fuctions/actions which were being preformed.

---------------------------------
3. Add necessary comments

---------------------------------
4. Visual Design of Code:  
      - Unifying list of arguments i.e. arguments in separate lines
      - Lesser lines of alignment hence, attention

---------------------------------

# Instructions For The Tutorial.
    Please read the instructions carefully and try to complete them during the tutorial.

### Install/Update Java
      * sudo add-apt-repository ppa:openjdk-r/ppa
      * sudo apt-get update -q 
      * sudo apt install -y openjdk-11-jdk 

### For The Main File

      * Clone this repo 
      * install ant by running 'sudo apt-get install ant'
      * then go to the folder where you have cloned this repo
      * run 'ant compile'
      * run 'ant jar'
      * run 'java -cp build/jar/args.jar com.cleancoder.args.ArgsMain'
### For the tests
        * Run the command given below from the root folder of this repo
        * 'java -cp "lib/junit-4.13.jar:lib/hamcrest-core-1.3.jar:build/jar/args.jar" ./test/com/cleancoder/args/ArgsTest.java testCreateWithNoSchemaOrArguments'
    

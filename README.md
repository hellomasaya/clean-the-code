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

      b. Order of functions changes according to: *"put the private utilities called by a public function right after the public function itself. This follows the stepdown rule and helps the program read like a newspaper article."*

---------------------------------
3. Add necessary comments

---------------------------------
4. Visual Design of Code:  
      - Unifying list of arguments i.e. arguments in separate lines
      - Lesser lines of alignment hence, attention

---------------------------------
    

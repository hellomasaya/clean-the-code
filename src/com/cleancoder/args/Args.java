package com.cleancoder.args;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

public class Args {
  private final String argSchema;
  private final List<String> listOfArgs;
  private final Map<Character, ArgumentMarshaler> marshalers;
  private final Set<Character> argsFound;
  private ListIterator<String> currentArgument;

  public Args(String schema, String[] args) throws ArgsException {
    argSchema = schema;
    listOfArgs = Arrays.asList(args);
    marshalers = new HashMap<Character, ArgumentMarshaler>();
    argsFound = new HashSet<Character>();

    parseSchema();
    parseArgumentList();
  }

  private void parseSchema() throws ArgsException {
    for (String element : argSchema.split(","))
      if (!isEmpty(element)){
        parseSchemaElement(element.trim());
    }
  }

  private boolean isEmpty(String element){
    return element.length() == 0;
  }

  private void parseSchemaElement(String element) throws ArgsException {
    char elementId = element.charAt(0);
    validateSchemaElementId(elementId);
    putIntoMarshaler(element);
  }

  private void validateSchemaElementId(char elementId) throws ArgsException {
    if (!Character.isLetter(elementId))
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
  }

  private void putIntoMarshaler(String element) throws ArgsException {
    char elementId = element.charAt(0);
    String elementTail = element.substring(1);

    if (elementTail.length() == 0)
      marshalers.put(elementId, new BooleanArgumentMarshaler());
    else if (elementTail.equals("*"))
      marshalers.put(elementId, new StringArgumentMarshaler());
    else if (elementTail.equals("#"))
      marshalers.put(elementId, new IntegerArgumentMarshaler());
    else if (elementTail.equals("##"))
      marshalers.put(elementId, new DoubleArgumentMarshaler());
    else if (elementTail.equals("[*]"))
      marshalers.put(elementId, new StringArrayArgumentMarshaler());
    else if (elementTail.equals("&"))
      marshalers.put(elementId, new MapArgumentMarshaler());
    else
      throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
  }

  private void parseArgumentList() throws ArgsException {
    for (currentArgument = listOfArgs.listIterator(); 
        currentArgument.hasNext();) {      
      String argString = currentArgument.next();
      if (argString.startsWith("-")) {
        String elementId = argString.substring(1);
        parseArgumentCharacters(elementId);
      } 
      else {
        currentArgument.previous();
        break;
      }
    }
  }

  private void parseArgumentCharacters(String argChars) throws ArgsException {
    for (int i = 0; i < argChars.length(); i++)
      parseArgumentCharacter(argChars.charAt(i));
  }

  private void parseArgumentCharacter(char argChar) throws ArgsException {
    ArgumentMarshaler marshaler = marshalers.get(argChar);
    if (marshaler == null) {
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
    } 
    else {
      argsFound.add(argChar);
      try {
        marshaler.set(currentArgument);
      } 
      catch (ArgsException err) {
        err.setErrorArgumentId(argChar);
        throw err;
      }
    }
  }

  // functions required in unit tests
  public boolean hasArgument(char arg) {
    return argsFound.contains(arg);
  }

  public int nextArgument() {
    return currentArgument.nextIndex();
  }

  public boolean getBoolean(char arg) {
    return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String getString(char arg) {
    return StringArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public int getInt(char arg) {
    return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public double getDouble(char arg) {
    return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String[] getStringArray(char arg) {
    return StringArrayArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public Map<String, String> getMap(char arg) {
    return MapArgumentMarshaler.getValue(marshalers.get(arg));
  }
}
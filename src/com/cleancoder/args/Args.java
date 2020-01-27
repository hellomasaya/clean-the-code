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
    parseListofArguments();
  }

  private void parseSchema() throws ArgsException {
    for (String element : argSchema.split(","))
      if (!element.isBlank())
        parseSchemaElement(element.trim());
  }

  private void parseSchemaElement(String element) throws ArgsException {
    int index = 0;
    char elementId = element.charAt(index);
    validateSchemaElementId(elementId);
    putIntoMarshaler(element);
  }

  private void validateSchemaElementId(char elementId) throws ArgsException {
    if (!Character.isLetter(elementId))
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
  }

  private void putIntoMarshaler(String element) throws ArgsException {
    int index = 0;
    char elementId = element.charAt(index);                              
    String elementSymbol = element.substring(1);
    Map<String, ArgumentMarshaler> marshalersMappedToSymbols = matchMarshaler();

    if (elementSymbol.isBlank())
      marshalers.put(elementId, new BooleanArgumentMarshaler());
    else if(marshalersMappedToSymbols.containsKey(elementSymbol))
      marshalers.put(elementId, marshalersMappedToSymbols.get(elementSymbol));
    else
      throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementSymbol);
  }

  private Map<String, ArgumentMarshaler> matchMarshaler(){
    final Map<String, ArgumentMarshaler> marshalersMappedToSymbols = new HashMap<String, ArgumentMarshaler>(){{
      put("#", new IntegerArgumentMarshaler());
      put("##", new DoubleArgumentMarshaler());
      put("*", new StringArgumentMarshaler());
      put("[*]", new StringArrayArgumentMarshaler());
      put("&", new MapArgumentMarshaler());
    }};;
    return marshalersMappedToSymbols;
  }

  private void parseListofArguments() throws ArgsException {
    for (currentArgument = listOfArgs.listIterator(); 
        currentArgument.hasNext();){      
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
      setMarshaler(argChars.charAt(i));
  }

  private void setMarshaler(char argChar) throws ArgsException {
    ArgumentMarshaler matchedMarshaler = marshalers.get(argChar);
    if (matchedMarshaler == null)
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
    else {
      argsFound.add(argChar);
      try {
        matchedMarshaler.set(currentArgument);
      } 
      catch (ArgsException err) {
        err.setErrorArgumentId(argChar);
        throw err;
      }
    }
  }

  public boolean hasFoundArgument(char arg) {
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
package com.cleancoder.args;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

public class MapArgumentMarshaler implements ArgumentMarshaler {
  private Map<String, String> map = new HashMap<>();

  public void set(Iterator<String> currentArgument) throws ArgsException {
    try {
      String[] mapEntries = currentArgument.next().split(",");
      for (String entry : mapEntries)
        parseEntry(entry);
    } catch (NoSuchElementException err) {
      throw new ArgsException(MISSING_MAP);
    }
  }

  public void parseEntry(String entry) throws ArgsException{
    String[] entryComponents = entry.split(":");
    if (entryComponents.length != 2)
      throw new ArgsException(MALFORMED_MAP);
      putInMap(entryComponents);
  }

  public void putInMap(String[] entryComponents) throws ArgsException{
    map.put(entryComponents[0], entryComponents[1]);
  }

  public static Map<String, String> getValue(ArgumentMarshaler argsMarshaler) {
    if (argsMarshaler != null && argsMarshaler instanceof MapArgumentMarshaler)
      return ((MapArgumentMarshaler) argsMarshaler).map;
    else
      return new HashMap<>();
  }
}
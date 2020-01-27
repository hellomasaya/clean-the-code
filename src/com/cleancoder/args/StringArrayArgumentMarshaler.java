package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class StringArrayArgumentMarshaler implements ArgumentMarshaler {
  private List<String> strings = new ArrayList<String>();

  public void set(Iterator<String> currentArgument) throws ArgsException {
    try {
      strings.add(currentArgument.next());
    } catch (NoSuchElementException err) {
      throw new ArgsException(MISSING_STRING);
    }
  }

  public static String[] getValue(ArgumentMarshaler argsMarshaler) {
    if (argsMarshaler != null && argsMarshaler instanceof StringArrayArgumentMarshaler)
      return ((StringArrayArgumentMarshaler) argsMarshaler).strings.toArray(new String[0]);
    else
      return new String[0];
  }
}

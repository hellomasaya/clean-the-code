package com.cleancoder.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.cleancoder.args.ArgsException.ErrorCode.MISSING_STRING;

public class StringArgumentMarshaler implements ArgumentMarshaler {
  private String stringValue = "";

  public void set(Iterator<String> currentArgument) throws ArgsException {
    try {
      stringValue = currentArgument.next();
    } catch (NoSuchElementException exception) {
      throw new ArgsException(MISSING_STRING);
    }
  }

  public static String getValue(ArgumentMarshaler argsMarshaler) {
    if (argsMarshaler != null && argsMarshaler instanceof StringArgumentMarshaler)
      return ((StringArgumentMarshaler) argsMarshaler).stringValue;
    else
      return "";
  }
}

package com.cleancoder.args;

import java.util.Iterator;

public class BooleanArgumentMarshaler implements ArgumentMarshaler {
  private boolean booleanValue = false;

  public void set(Iterator<String> currentArgument) throws ArgsException {
    booleanValue = true;
  }

  public static boolean getValue(ArgumentMarshaler argsMarshaler) {
    if (argsMarshaler != null && argsMarshaler instanceof BooleanArgumentMarshaler)
      return ((BooleanArgumentMarshaler) argsMarshaler).booleanValue;
    else
      return false;
  }
}

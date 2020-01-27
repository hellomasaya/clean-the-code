package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class IntegerArgumentMarshaler implements ArgumentMarshaler {
  private int intValue = 0;

  public void set(Iterator<String> currentArgument) throws ArgsException {
    String argValue = null;
    try {
      argValue = currentArgument.next();
      intValue = Integer.parseInt(argValue);
    } catch (NoSuchElementException err) {
      throw new ArgsException(MISSING_INTEGER);
    } catch (NumberFormatException err) {
      throw new ArgsException(INVALID_INTEGER, argValue);
    }
  }

  public static int getValue(ArgumentMarshaler argsMarshaler) {
    if (argsMarshaler != null && argsMarshaler instanceof IntegerArgumentMarshaler)
      return ((IntegerArgumentMarshaler) argsMarshaler).intValue;
    else
      return 0;
  }
}

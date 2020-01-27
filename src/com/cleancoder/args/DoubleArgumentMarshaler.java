package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class DoubleArgumentMarshaler implements ArgumentMarshaler {
  private double doubleValue = 0;

  public void set(Iterator<String> currentArgument) throws ArgsException {
    String argValue = null;
    try {
      argValue = currentArgument.next();
      doubleValue = Double.parseDouble(argValue);
    } catch (NoSuchElementException err) {
      throw new ArgsException(MISSING_DOUBLE);
    } catch (NumberFormatException err) {
      throw new ArgsException(INVALID_DOUBLE, argValue);
    }
  }

  public static double getValue(ArgumentMarshaler argsMarshaler) {
    if (argsMarshaler != null && argsMarshaler instanceof DoubleArgumentMarshaler)
      return ((DoubleArgumentMarshaler) argsMarshaler).doubleValue;
    else
      return 0.0;
  }
}

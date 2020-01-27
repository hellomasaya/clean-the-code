package com.cleancoder.args;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Map;

import static com.cleancoder.args.ArgsException.ErrorCode.*;
import static org.junit.Assert.*;

public class ArgsTest {

  public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ArgsTest.class);
      for (Failure failure : result.getFailures())
         System.out.println(failure.toString());
      System.out.println(result.wasSuccessful());
  }

  @Test
  public void testCreateWithNoSchemaOrArguments() throws Exception {
    Args args = new Args("", new String[0]);
    assertEquals(0, args.nextArgument());
  }


  @Test
  public void testWithNoSchemaButWithOneArgument() throws Exception {
    try {
      new Args("", new String[]{"-x"});
      fail();
    } catch (ArgsException e) {
      assertEquals(UNEXPECTED_ARGUMENT, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  public void testWithNoSchemaButWithMultipleArguments() throws Exception {
    try {
      new Args("", new String[]{"-x", "-y"});
      fail();
    } catch (ArgsException e) {
      assertEquals(UNEXPECTED_ARGUMENT, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }

  }

  @Test
  public void testNonLetterSchema() throws Exception {
    try {
      new Args("*", new String[]{});
      fail("Args constructor should have thrown exception");
    } catch (ArgsException e) {
      assertEquals(INVALID_ARGUMENT_NAME, e.getErrorCode());
      assertEquals('*', e.getErrorArgumentId());
    }
  }

  @Test
  public void testInvalidArgumentFormat() throws Exception {
    try {
      new Args("f~", new String[]{});
      fail("Args constructor should have throws exception");
    } catch (ArgsException e) {
      assertEquals(INVALID_ARGUMENT_FORMAT, e.getErrorCode());
      assertEquals('f', e.getErrorArgumentId());
    }
  }

  @Test
  public void testSimpleBooleanPresent() throws Exception {
    Args args = new Args("x", new String[]{"-x"});
    assertEquals(true, args.getBoolean('x'));
    assertEquals(1, args.nextArgument());
  }

  @Test
  public void testSimpleStringPresent() throws Exception {
    Args args = new Args("x*", new String[]{"-x", "param"});
    assertTrue(args.hasFoundArgument('x'));
    assertEquals("param", args.getString('x'));
    assertEquals(2, args.nextArgument());
  }

  @Test
  public void testMissingStringArgument() throws Exception {
    try {
      new Args("x*", new String[]{"-x"});
      fail();
    } catch (ArgsException e) {
      assertEquals(MISSING_STRING, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  public void testSpacesInFormat() throws Exception {
    Args args = new Args("x, y", new String[]{"-xy"});
    assertTrue(args.hasFoundArgument('x'));
    assertTrue(args.hasFoundArgument('y'));
    assertEquals(1, args.nextArgument());
  }

  @Test
  public void testSimpleIntPresent() throws Exception {
    Args args = new Args("x#", new String[]{"-x", "42"});
    assertTrue(args.hasFoundArgument('x'));
    assertEquals(42, args.getInt('x'));
    assertEquals(2, args.nextArgument());
  }

  @Test
  public void testInvalidInteger() throws Exception {
    try {
      new Args("x#", new String[]{"-x", "Forty two"});
      fail();
    } catch (ArgsException e) {
      assertEquals(INVALID_INTEGER, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
      assertEquals("Forty two", e.getErrorParameter());
    }

  }

  @Test
  public void testSimpleNegativeIntPresent() throws Exception {
    Args args = new Args("x#", new String[]{"-x", "-42"});
    assertTrue(args.has('x'));
    assertEquals(-42, args.getInt('x'));
    assertEquals(2, args.nextArgument());
  }

  @Test
  public void testMissingInteger() throws Exception {
    try {
      new Args("x#", new String[]{"-x"});
      fail();
    } catch (ArgsException e) {
      assertEquals(MISSING_INTEGER, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  public void testSimpleDoublePresent() throws Exception {
    Args args = new Args("x##", new String[]{"-x", "42.3"});
    assertTrue(args.hasFoundArgument('x'));
    assertEquals(42.3, args.getDouble('x'), .001);
  }

  @Test
  public void testInvalidDouble() throws Exception {
    try {
      new Args("x##", new String[]{"-x", "Forty two"});
      fail();
    } catch (ArgsException e) {
      assertEquals(INVALID_DOUBLE, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
      assertEquals("Forty two", e.getErrorParameter());
    }
  }

  @Test
  public void testMissingDouble() throws Exception {
    try {
      new Args("x##", new String[]{"-x"});
      fail();
    } catch (ArgsException e) {
      assertEquals(MISSING_DOUBLE, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  public void testStringArray() throws Exception {
    Args args = new Args("x[*]", new String[]{"-x", "alpha"});
    assertTrue(args.hasFoundArgument('x'));
    String[] result = args.getStringArray('x');
    assertEquals(1, result.length);
    assertEquals("alpha", result[0]);
  }

  @Test
  public void testMissingStringArrayElement() throws Exception {
    try {
      new Args("x[*]", new String[] {"-x"});
      fail();
    } catch (ArgsException e) {
      assertEquals(MISSING_STRING,e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  public void manyStringArrayElements() throws Exception {
    Args args = new Args("x[*]", new String[]{"-x", "alpha", "-x", "beta", "-x", "gamma"});
    assertTrue(args.hasFoundArgument('x'));
    String[] result = args.getStringArray('x');
    assertEquals(3, result.length);
    assertEquals("alpha", result[0]);
    assertEquals("beta", result[1]);
    assertEquals("gamma", result[2]);
  }

  @Test
  public void MapArgument() throws Exception {
    Args args = new Args("f&", new String[] {"-f", "key1:val1,key2:val2"});
    assertTrue(args.hasFoundArgument('f'));
    Map<String, String> map = args.getMap('f');
    assertEquals("val1", map.get("key1"));
    assertEquals("val2", map.get("key2"));
  }

  @Test
  public void malformedMapArgument() throws Exception {
    try{
    Args args = new Args("f&", new String[] {"-f", "key1:val1,key2"});
    Map<String, String> map = args.getMap('f');
    }
    catch (ArgsException e){
      assertEquals(MALFORMED_MAP, e.getErrorCode());
      assertEquals('f', e.getErrorArgumentId());
    }
  }
 
  @Test
  public void malformedMapArgumentExcess() throws Exception {
    try{
    Args args = new Args("f&", new String[] {"-f", "key1:val1,key2:value2:extra"});
    Map<String, String> map = args.getMap('f');
    }
    catch (ArgsException e){
      assertEquals(MALFORMED_MAP, e.getErrorCode());
      assertEquals('f', e.getErrorArgumentId());
    }
  }

  @Test
  public void oneMapArgument() throws Exception {
    Args args = new Args("f&", new String[] {"-f", "key1:val1"});
    assertTrue(args.hasFoundArgument('f'));
    Map<String, String> map = args.getMap('f');
    assertEquals("val1", map.get("key1"));
  }

  @Test
  public void testExtraArguments() throws Exception {
    Args args = new Args("x,y*", new String[]{"-x", "-y", "alpha", "beta"});
    assertTrue(args.getBoolean('x'));
    assertEquals("alpha", args.getString('y'));
    assertEquals(3, args.nextArgument());
  }

  @Test
  public void testExtraArgumentsThatLookLikeFlags() throws Exception {
    Args args = new Args("x,y", new String[]{"-x", "alpha", "-y", "beta"});
    assertTrue(args.hasFoundArgument('x'));
    assertFalse(args.hasFoundArgument('y'));
    assertTrue(args.getBoolean('x'));
    assertFalse(args.getBoolean('y'));
    assertEquals(1, args.nextArgument());
  }
}
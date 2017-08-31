/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyc.model.objects;

import com.cyc.model.objects.MethodObj;
import com.cyc.model.objects.ArgumentObj;
import com.cyc.kb.KbCollection;
import com.cyc.kb.KbCollectionFactory;
import com.cyc.kb.KbPredicate;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author vijay
 */
public class MethodObjTest {
  
  public MethodObjTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of Constructor, of class MethodObj.
   */
  @Test
  public void testMethodObjStringKbCollectionFuncTypeInteger_1() throws Exception {
    System.out.println("testMethodObjStringKbCollectionFuncTypeInteger_1");
    MethodObj instance = new MethodObj("cyclistPrimaryProject", KbCollectionFactory.get("HumanCyclist"), MethodObj.FuncType.GET, null);
    System.out.println("Method Body: " + instance.getMethodBody());
  }
  
  @Test
  public void testMethodObjStringKbCollectionFuncTypeInteger_2() throws Exception {
    System.out.println("testMethodObjStringKbCollectionFuncTypeInteger_2");
    MethodObj instance = new MethodObj("Gram", KbCollectionFactory.get("Mass"), MethodObj.FuncType.FACTORY, null);
    System.out.println("Method Body: " + instance.getMethodBody());
    System.out.println("Entire Method: " + instance.toStringClass());
  }
  
  /* *
   * Test of getArgPos method, of class MethodObj.
   * /
  @Test
  public void testGetArgPos() {
    System.out.println("getArgPos");
    MethodObj instance = new MethodObj();
    Integer expResult = null;
    Integer result = instance.getArgPos();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of setArgPos method, of class MethodObj.
   * /
  @Test
  public void testSetArgPos() {
    System.out.println("setArgPos");
    Integer argPos = null;
    MethodObj instance = new MethodObj();
    instance.setArgPos(argPos);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of getCtxStr method, of class MethodObj.
   * /
  @Test
  public void testGetCtxStr() {
    System.out.println("getCtxStr");
    MethodObj instance = new MethodObj();
    String expResult = "";
    String result = instance.getCtxStr();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of setCtxStr method, of class MethodObj.
   * /
  @Test
  public void testSetCtxStr() {
    System.out.println("setCtxStr");
    String ctxStr = "";
    MethodObj instance = new MethodObj();
    instance.setCtxStr(ctxStr);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of getConfigArgPos method, of class MethodObj.
   * /
  @Test
  public void testGetConfigArgPos() {
    System.out.println("getConfigArgPos");
    MethodObj instance = new MethodObj();
    Integer expResult = null;
    Integer result = instance.getConfigArgPos();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of getReturnValue method, of class MethodObj.
   * /
  @Test
  public void testGetReturnValue() {
    System.out.println("getReturnValue");
    MethodObj instance = new MethodObj();
    ArgumentObj expResult = null;
    ArgumentObj result = instance.getReturnValue();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of setReturnValue method, of class MethodObj.
   * /
  @Test
  public void testSetReturnValue() {
    System.out.println("setReturnValue");
    ArgumentObj returnValue = null;
    MethodObj instance = new MethodObj();
    instance.setReturnValue(returnValue);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of getPredicate method, of class MethodObj.
   * /
  @Test
  public void testGetPredicate() {
    System.out.println("getPredicate");
    MethodObj instance = new MethodObj();
    KbPredicate expResult = null;
    KbPredicate result = instance.getPredicate();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of getArguments method, of class MethodObj.
   * /
  @Test
  public void testGetArguments() {
    System.out.println("getArguments");
    MethodObj instance = new MethodObj();
    List expResult = null;
    List result = instance.getArguments();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of setArguments method, of class MethodObj.
   * /
  @Test
  public void testSetArguments() {
    System.out.println("setArguments");
    List<ArgumentObj> arguments = null;
    MethodObj instance = new MethodObj();
    instance.setArguments(arguments);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of addArgument method, of class MethodObj.
   * /
  @Test
  public void testAddArgument() {
    System.out.println("addArgument");
    ArgumentObj argument = null;
    MethodObj instance = new MethodObj();
    instance.addArgument(argument);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of getfType method, of class MethodObj.
   * /
  @Test
  public void testGetfType() {
    System.out.println("getfType");
    MethodObj instance = new MethodObj();
    MethodObj.FuncType expResult = null;
    MethodObj.FuncType result = instance.getfType();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of setfType method, of class MethodObj.
   * /
  @Test
  public void testSetfType() {
    System.out.println("setfType");
    MethodObj.FuncType fType = null;
    MethodObj instance = new MethodObj();
    instance.setfType(fType);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of getJavadoc method, of class MethodObj.
   * /
  @Test
  public void testGetJavadoc() {
    System.out.println("getJavadoc");
    MethodObj instance = new MethodObj();
    List expResult = null;
    List result = instance.getJavadoc();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of getExceptions method, of class MethodObj.
   * /
  @Test
  public void testGetExceptions() {
    System.out.println("getExceptions");
    MethodObj instance = new MethodObj();
    List expResult = null;
    List result = instance.getExceptions();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of toString method, of class MethodObj.
   * /
  @Test
  public void testToString() {
    System.out.println("toString");
    MethodObj instance = new MethodObj();
    String expResult = "";
    String result = instance.toString();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of toStringClass method, of class MethodObj.
   * /
  @Test
  public void testToStringClass() throws Exception {
    System.out.println("toStringClass");
    MethodObj instance = new MethodObj();
    String expResult = "";
    String result = instance.toStringClass();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of getMethodBody method, of class MethodObj.
   * /
  @Test
  public void testGetMethodBody() throws Exception {
    System.out.println("getMethodBody");
    MethodObj instance = new MethodObj();
    String expResult = "";
    String result = instance.getMethodBody();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of determineName method, of class MethodObj.
   * /
  @Test
  public void testDetermineName() {
    System.out.println("determineName");
    String predStr = "";
    MethodObj.FuncType func = null;
    String expResult = "";
    String result = MethodObj.determineName(predStr, func);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of hashCode method, of class MethodObj.
   * /
  @Test
  public void testHashCode() {
    System.out.println("hashCode");
    MethodObj instance = new MethodObj();
    int expResult = 0;
    int result = instance.hashCode();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /* *
   * Test of equals method, of class MethodObj.
   * /
  @Test
  public void testEquals() {
    System.out.println("equals");
    Object obj = null;
    MethodObj instance = new MethodObj();
    boolean expResult = false;
    boolean result = instance.equals(obj);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  */
}
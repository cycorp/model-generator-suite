/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyc.library.model;

import com.cyc.kb.KBCollection;
import com.cyc.kb.client.KBCollectionImpl;
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
public class CriteriaMethodObjTest {
  
  public CriteriaMethodObjTest() {
  }
  
  @BeforeClass
  public static void setUpClass() throws Exception {
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
   * Test of getConstraintLiteral method, of class CriteriaMethodObj.
   */
  @Test
  public void testGetConstraintLiteral() {
    System.out.println("getConstraintLiteral");
    CriteriaMethodObj instance = null;
    List expResult = null;
    List result = instance.getConstraintLiteral();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setConstraintLiteral method, of class CriteriaMethodObj.
   */
  @Test
  public void testSetConstraintLiteral() {
    System.out.println("setConstraintLiteral");
    List<String> constraintLiterals = null;
    CriteriaMethodObj instance = null;
    instance.setConstraintLiteral(constraintLiterals);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of addConstraintLiteral method, of class CriteriaMethodObj.
   */
  @Test
  public void testAddConstraintLiteral() {
    System.out.println("addConstraintLiteral");
    String constraintLiteral = "";
    CriteriaMethodObj instance = null;
    instance.addConstraintLiteral(constraintLiteral);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of toTypeClassString method, of class CriteriaMethodObj.
   */
  @Test
  public void testToTypeClassString() throws Exception {
    System.out.println("toTypeClassString");
    CriteriaMethodObj instance = new CriteriaMethodObj("gizmoObservedToBeBrokenOnDate", KBCollectionImpl.get("Gizmo"), MethodObj.FuncType.TYPE_GET_LIST);
    String result = instance.toString();
    System.out.println(result);
    // TODO review the generated test code and remove the default call to fail.
    //fail("The test case is a prototype.");
  }
}

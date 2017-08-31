package com.cyc.model.templates;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
abstract public class BaseTemplateTest {
  
  public BaseTemplateTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() throws Exception {
  }
  
  @After
  public void tearDown() throws Exception {
  }
  
  
  // Public
  
  public void assertEqualsVerbose(String expected, String result) {
    System.out.println("--");
    System.out.println("EXPECTED:");
    System.out.println(expected);
    System.out.println("--");
    System.out.println("RESULT:");
    System.out.println(result);
    System.out.println("--");
    assertEquals(expected, result);
  }
  
  public void assertEqualsFileContents(File expectedFile, String result) throws IOException {
    String expected = FileUtils.readFileToString(expectedFile, "utf-8");
    assertEqualsVerbose(expected, result);
  }
  
  public void assertEqualsFileContents(File expectedFile, List<String> results) throws IOException {
    final StringBuilder sb = new StringBuilder();
    for (String result : results) {
      if (sb.length() > 0) {
        sb.append("\n\n");
      }
      sb.append(result);
    }
    assertEqualsFileContents(expectedFile, sb.toString());
  }
  
  
  // Protected
  
  protected String getBaseTestFilePath() {
    return "src/test/resources/templates/" + this.getClass().getSimpleName();
  }
  
  protected File getTestFile(String qualifier) {
    return new File(getBaseTestFilePath() + "-" + qualifier + ".txt");
  }
  
  
  // Fields
  
  public static final String LF = "\n";
}

package com.cyc.library.mapi.templates;

import com.cyc.base.CycConnectionException;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

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
  public static void setUpClass() throws CycConnectionException {
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
    System.out.println("--");
    System.out.println("RESULT:");
    System.out.println(result);
    System.out.println("--");
    assertEquals(FileUtils.readFileToString(expectedFile, "utf-8"), result);
  }
  
  
  // Protected
  
  protected String getBaseTestFilePath() {
    return "src/test/resources/templates/" + this.getClass().getSimpleName();
  }
  
  protected File getTestFile(String localPath) {
    return new File(getBaseTestFilePath() + "/" + localPath);
  }
  
  
  // Fields
  
  public static final String LF = "\n";
}

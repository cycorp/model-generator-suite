package ${package};

import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

  public AppTest() {
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
  
  
  // Tests
  
  @Test
  public void testloadXML() {
    try {
      App app = new App();
      app.runExample();
    } catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(false);
    }
    assertTrue(true);
  }
}

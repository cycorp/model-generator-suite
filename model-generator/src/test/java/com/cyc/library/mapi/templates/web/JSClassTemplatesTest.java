package com.cyc.library.mapi.templates.web;

import com.cyc.library.mapi.templates.BaseTemplateTest;
import com.cyc.library.mapi.templates.ClassTemplateTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class JSClassTemplatesTest extends BaseTemplateTest {
  
  @Before
  public void setUp() throws Exception {
    super.setUp();
    this.templates = new JSClassTemplates(ClassTemplateTest.createClassObj(), "NAMESPACE", "appDir");
  }
  
  @Test
  public void testPrintSummary() {
    templates.printSummary();
    assertTrue(true);
    assertTrue(false);
  }
  
  @Test
  public void testGenerateString() throws Exception {
    final List<String> results = templates.generateString();
    for (String result : results) {
      System.out.println();
      System.out.println(result);
    }
    assertEquals(0, results.size());
    assertTrue(false);
  }
  
  
  // Internal
  
  private JSClassTemplates templates;
}

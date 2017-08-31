package com.cyc.model.templates.web;

import com.cyc.model.templates.BaseTemplateTest;
import com.cyc.model.templates.ClassTemplateTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class JsClassTemplatesTest extends BaseTemplateTest {
  
  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();
    this.templates = new JsClassTemplates(ClassTemplateTest.createClassObj(), "NAMESPACE", "appDir");
  }
  
  @Test
  public void testPrintSummary() {
    templates.printSummary();
    assertTrue(true); // TODO: could probably be a little more sophisticated...
  }
  
  @Test
  public void testGenerateString() throws Exception {
    final List<String> results = templates.generateString();
    assertEquals(5, results.size());
    assertEqualsFileContents(getTestFile("result-list"), results);
  }
  
  
  // Internal
  
  private JsClassTemplates templates;
}

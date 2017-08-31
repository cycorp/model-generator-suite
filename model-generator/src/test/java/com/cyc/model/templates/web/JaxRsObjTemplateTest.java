package com.cyc.model.templates.web;

import com.cyc.model.templates.BaseTemplateTest;
import com.cyc.model.templates.ClassTemplateTest;
import com.cyc.kb.exception.KbException;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author nwinant
 */
public class JaxRsObjTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testGenerateString() throws IOException, KbException, Exception {
    final JaxRsObjTemplate template = new JaxRsObjTemplate(ClassTemplateTest.createClassObj());
    assertEqualsFileContents(getTestFile("result"), template.generateString());
  }
}

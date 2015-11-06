package com.cyc.library.mapi.templates.web;

import com.cyc.library.mapi.templates.BaseTemplateTest;
import com.cyc.library.mapi.templates.ClassTemplateTest;
import com.cyc.kb.exception.KBApiException;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author nwinant
 */
public class JaxRSObjTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testGenerateString() throws IOException, KBApiException, Exception {
    final JaxRSObjTemplate template = new JaxRSObjTemplate(ClassTemplateTest.createClassObj());
    assertEqualsFileContents(getTestFile("generate-string.txt"), template.generateString());
  }
}

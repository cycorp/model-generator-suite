package com.cyc.model.templates;

import com.cyc.kb.exception.KbException;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author nwinant
 */
public class InterfaceObjTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testGenerateString() throws IOException, KbException, Exception {
    final InterfaceObjTemplate template = new InterfaceObjTemplate(InterfaceTemplateTest.createInterfaceObj());
    assertEqualsFileContents(getTestFile("result"), template.generateString());
  }
}

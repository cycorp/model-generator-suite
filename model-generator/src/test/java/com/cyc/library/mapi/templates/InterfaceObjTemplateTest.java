package com.cyc.library.mapi.templates;

import com.cyc.kb.exception.KBApiException;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author nwinant
 */
public class InterfaceObjTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testGenerateString() throws IOException, KBApiException, Exception {
    final InterfaceObjTemplate template = new InterfaceObjTemplate(InterfaceTemplateTest.createInterfaceObj());
    assertEqualsFileContents(getTestFile("generate-string.txt"), template.generateString());
  }
}

package com.cyc.library.mapi.templates;

import com.cyc.kb.exception.KBApiException;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author nwinant
 */
public class ClassObjTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testGenerateString() throws IOException, KBApiException, Exception {
    final ClassObjTemplate template = new ClassObjTemplate(ClassTemplateTest.createClassObj());
    assertEqualsFileContents(getTestFile("generate-string.txt"), template.generateString());
  }
}

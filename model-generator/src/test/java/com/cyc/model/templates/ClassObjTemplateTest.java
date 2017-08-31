package com.cyc.model.templates;

import com.cyc.kb.exception.KbException;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author nwinant
 */
public class ClassObjTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testGenerateString() throws IOException, KbException, Exception {
    final ClassObjTemplate template = new ClassObjTemplate(ClassTemplateTest.createClassObj());
    assertEqualsFileContents(getTestFile("result"), template.generateString());
  }
}

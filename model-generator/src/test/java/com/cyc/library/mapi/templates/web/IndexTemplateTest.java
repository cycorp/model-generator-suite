package com.cyc.library.mapi.templates.web;

import com.cyc.library.mapi.templates.BaseTemplateTest;
import com.cyc.library.mapi.xml.XMLProjectLoader;
import com.cyc.library.mapi.xml.XMLProjectLoaderTest;
import com.cyc.library.model.ProjectObj;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class IndexTemplateTest extends BaseTemplateTest {
  
  @Test
  public void test() throws Exception {
    final XMLProjectLoader loader = new XMLProjectLoader(XMLProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XMLProjectLoaderTest.BASE_EXAMPLES_DIR + "/CycModelDescriptionExample1.xml");
    IndexTemplate template = new IndexTemplate(project);
    System.out.println(template.getTemplateFileName());
    System.out.println(template.getTargetFile());
    System.out.println(template.generateString());
    assertTrue(true);
  }
}

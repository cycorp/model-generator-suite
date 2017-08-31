package com.cyc.model.templates.web;

import com.cyc.model.templates.web.IndexTemplate;
import com.cyc.model.templates.BaseTemplateTest;
import com.cyc.model.xml.XmlProjectLoader;
import com.cyc.model.xml.XmlProjectLoaderTest;
import com.cyc.model.objects.ProjectObj;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class IndexTemplateTest extends BaseTemplateTest {
  
  @Test
  public void test() throws Exception {
    final XmlProjectLoader loader = new XmlProjectLoader(XmlProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XmlProjectLoaderTest.CMD_VALID);
    IndexTemplate template = new IndexTemplate(project);
    System.out.println(template.getTemplateFileName());
    System.out.println(template.getTargetFile());
    System.out.println(template.generateString());
    assertTrue(true);
  }
}

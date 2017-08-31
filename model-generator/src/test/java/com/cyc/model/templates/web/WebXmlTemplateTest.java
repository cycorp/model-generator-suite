package com.cyc.model.templates.web;

import com.cyc.model.templates.web.WebXmlTemplate;
import com.cyc.model.templates.BaseTemplateTest;
import com.cyc.model.xml.XmlProjectLoader;
import com.cyc.model.xml.XmlProjectLoaderTest;
import com.cyc.model.objects.ProjectObj;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class WebXmlTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testGetTargetFile() throws Exception {
    final XmlProjectLoader loader = new XmlProjectLoader(XmlProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XmlProjectLoaderTest.CMD_VALID);
    final WebXmlTemplate template = new WebXmlTemplate(project);
    System.out.println(template.getTargetFile());
    assertEquals(new File("src/main/webapp/WEB-INF/web.xml"), template.getTargetFile());
  }
  
  @Test
  public void testGenerateString() throws Exception {
    final XmlProjectLoader loader = new XmlProjectLoader(XmlProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XmlProjectLoaderTest.CMD_VALID);
    final WebXmlTemplate template = new WebXmlTemplate(project);
    assertEqualsFileContents(getTestFile("result"), template.generateString());
  }
}
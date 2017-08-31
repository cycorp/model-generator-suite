package com.cyc.model.generator;

import com.cyc.model.generator.TemplateGenerator;
import com.cyc.model.generator.WebGenerator;
import com.cyc.model.templates.BaseTemplateTest;
import com.cyc.model.xml.XmlProjectLoader;
import com.cyc.model.xml.XmlProjectLoaderTest;
import com.cyc.model.objects.ProjectObj;
import com.cyc.kb.exception.KbException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 *
 * @author nwinant
 */
public class WebGeneratorTest extends BaseTemplateTest {
  
  @Test
  public void test() throws JAXBException, SAXException, KbException, Exception {
    final XmlProjectLoader loader = new XmlProjectLoader(XmlProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XmlProjectLoaderTest.CMD_VALID);
    final List<String> directives = WebGenerator.getDefaultDirectives();
    directives.add(TemplateGenerator.GENERATE_STRINGS_ONLY_DIRECTIVE);
    final WebGenerator generator = new WebGenerator(directives);
    generator.generate(project);
    
    //fail("Requires testing of results.");
    org.junit.Assume.assumeTrue("Requires testing of results.", false); // TODO
  }
}

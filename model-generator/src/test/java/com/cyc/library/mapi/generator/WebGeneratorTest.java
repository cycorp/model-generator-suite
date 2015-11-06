package com.cyc.library.mapi.generator;

import com.cyc.library.mapi.templates.BaseTemplateTest;
import com.cyc.library.mapi.xml.XMLProjectLoader;
import com.cyc.library.mapi.xml.XMLProjectLoaderTest;
import com.cyc.library.model.ProjectObj;
import com.cyc.kb.exception.KBApiException;
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
  public void test() throws JAXBException, SAXException, KBApiException, Exception {
    final XMLProjectLoader loader = new XMLProjectLoader(XMLProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XMLProjectLoaderTest.BASE_EXAMPLES_DIR + "/CycModelDescriptionExample1.xml");
    final List<String> directives = WebGenerator.getDefaultDirectives();
    directives.add(TemplateGenerator.GENERATE_STRINGS_ONLY_DIRECTIVE);
    final WebGenerator generator = new WebGenerator(directives);
    generator.generate(project);
    assertTrue(true);
  }
}

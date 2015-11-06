package com.cyc.library.mapi.generator;

import com.cyc.library.mapi.templates.BaseTemplateTest;
import com.cyc.library.mapi.xml.XMLProjectLoader;
import com.cyc.library.mapi.xml.XMLProjectLoaderTest;
import com.cyc.library.model.ProjectObj;
import com.cyc.kb.exception.KBApiException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 *
 * @author nwinant
 */
public class ModelGeneratorTest extends BaseTemplateTest {
  
  @Test
  public void test() throws JAXBException, SAXException, KBApiException, Exception {
    final XMLProjectLoader loader = new XMLProjectLoader(XMLProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XMLProjectLoaderTest.BASE_EXAMPLES_DIR + "/CycModelDescriptionExample1.xml");
//    final List<String> directives = ModelGenerator.getDefaultDirectives();
    final List<String> directives = new ArrayList<String>();
    directives.add(ModelGenerator.GENERATE_MODEL_INTERFACES_DIRECTIVE);
    directives.add(ModelGenerator.GENERATE_MODEL_CLASSES_DIRECTIVE);
    directives.add(TemplateGenerator.GENERATE_STRINGS_ONLY_DIRECTIVE);
    final ModelGenerator generator = new ModelGenerator(directives);
    generator.generate(project);
    assertTrue(false);
  }
}

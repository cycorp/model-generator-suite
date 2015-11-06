package com.cyc.library.mapi.templates;

import com.cyc.library.model.ClassObj;
import com.cyc.kb.exception.KBApiException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class ClassTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testIsSufficientlyConfigured() throws IOException {
    final ClassTemplate template = new ClassTemplate();
    assertFalse(template.isSufficientlyConfigured());
    template.setPackageName("com.cyc.template");
    assertFalse(template.isSufficientlyConfigured());
    template.addImports(Arrays.asList("java.io.File", "java.io.IOException"));
    assertFalse(template.isSufficientlyConfigured());
    template.setVisibility("protected");
    assertFalse(template.isSufficientlyConfigured());
    template.setName("SomeClass");
    assertTrue(template.isSufficientlyConfigured());
    template.setProperty(ClassTemplate.METHODS_PROP, Arrays.asList("method1", "method2"));
    assertTrue(template.isSufficientlyConfigured());
    template.setProperty("quantity", 1);
    assertTrue(template.isSufficientlyConfigured());
  }
  
  @Test
  public void testGetTargetFile() throws IOException {
    final ClassTemplate template = new ClassTemplate();
    template.setPackageName("com.cyc.template");
    template.addImports(Arrays.asList("java.io.File", "java.io.IOException"));
    template.setVisibility("protected");
    template.setName("SomeClass");
    System.out.println(template.getTargetFile());
    assertEquals(new File("target/generated-sources/mapi/com/cyc/template/SomeClassImpl.java"), template.getTargetFile());
  }
  
  @Test
  public void testDefaults() throws IOException {
    final ClassTemplate template = new ClassTemplate();
    assertFalse(template.hasProperty(JavaTemplate.PACKAGE_PROP));
    assertFalse(template.hasProperty(JavaTemplate.IMPORTS_PROP));
    assertEquals("public", template.getProperty(JavaTemplate.VISIBILITY_PROP));
    assertFalse(template.hasProperty(JavaTemplate.NAME_PROP));
    assertFalse(template.hasProperty(JavaTemplate.METHODS_PROP));
  }
  
  @Test
  public void testGenerateString() throws IOException, KBApiException, Exception {
    final ClassTemplate template = new ClassTemplate();
    template.populateFromObj(createClassObj());
    assertEqualsFileContents(getTestFile("generate-string.txt"), template.generateString());
  }
  
  
  // Public
  
  public static ClassObj createClassObj() throws KBApiException, Exception {
    ClassObj obj = new ClassObj(InterfaceTemplateTest.createInterfaceObj());
    obj.setSuperClass(null);
    return obj;
  }
}

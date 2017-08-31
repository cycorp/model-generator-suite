package com.cyc.model.templates;

import com.cyc.kb.KbCollectionFactory;
import com.cyc.model.templates.InterfaceTemplate;
import com.cyc.model.objects.InterfaceObj;
import com.cyc.model.objects.MethodObj;
import com.cyc.model.objects.MethodObj.FuncType;
import com.cyc.kb.exception.KbException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class InterfaceTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testIsSufficientlyConfigured() throws IOException {
    final InterfaceTemplate template = new InterfaceTemplate();
    assertFalse(template.isSufficientlyConfigured());
    template.setPackageName("com.cyc.template");
    assertFalse(template.isSufficientlyConfigured());
    template.addImports(Arrays.asList("java.io.File", "java.io.IOException"));
    assertFalse(template.isSufficientlyConfigured());
    template.setVisibility("protected");
    assertFalse(template.isSufficientlyConfigured());
    template.setName("SomeClass");
    assertTrue(template.isSufficientlyConfigured());
  }
  
  @Test
  public void testIsObjSufficientlyConfigured() throws IOException, KbException, Exception {
    final InterfaceTemplate template = new InterfaceTemplate();
    template.populateFromObj(createInterfaceObj());
    assertTrue(template.isSufficientlyConfigured());
  }
  
  @Test
  public void testGetTargetFile() throws IOException {
    final InterfaceTemplate template = new InterfaceTemplate();
    template.setPackageName("com.cyc.template");
    template.addImports(Arrays.asList("java.io.File", "java.io.IOException"));
    template.setVisibility("protected");
    template.setName("SomeClass");
    System.out.println(template.getTargetFile());
    assertEquals(new File("target/generated-sources/mapi/com/cyc/template/SomeClass.java"), template.getTargetFile());
  }
  
  @Test
  public void testGenerateString() throws IOException, KbException, Exception {
    final InterfaceTemplate template = new InterfaceTemplate();
    template.populateFromObj(createInterfaceObj());
    assertEqualsFileContents(getTestFile("result"), template.generateString());
  }
    
  @Test
  public void testCreateInterfaceObj() throws IOException, KbException, Exception {
    System.out.println("testCreateInterfaceObj");
    final InterfaceObj iObj = createInterfaceObj();
    assertNotNull(iObj);
    System.out.println("InterfaceObj: " + iObj);
  }
  
  // Protected
  
  static protected InterfaceObj createInterfaceObj() throws KbException, Exception {
    InterfaceObj obj = new InterfaceObj();
    obj.setPackageName("com.cyc.template");
    obj.setImports(new HashSet(Arrays.asList("java.io.File", "java.io.IOException")));
    obj.setCycName("SomeClass");
    obj.setVisibility("protected");
    obj.setRelationType("isa");
    List<MethodObj> methods = new ArrayList<MethodObj>();
    /*
    MethodObj method = new MethodObj("groupsReviewer", KbCollectionFactory.get("HumanCyclist"), FuncType.GET);
    method.
    */
    methods.add(new MethodObj("groupsReviewer", KbCollectionFactory.get("HumanCyclist"), FuncType.GET));
    methods.add(new MethodObj("groupsReviewer", KbCollectionFactory.get("HumanCyclist"), FuncType.SET));
    methods.add(new MethodObj("cyclistPrimaryProject", KbCollectionFactory.get("HumanCyclist"), FuncType.GET));
    methods.add(new MethodObj("cyclistPrimaryProject", KbCollectionFactory.get("HumanCyclist"), FuncType.TYPE_GET_LIST));
    obj.setMethods(methods);
    return obj;
  }
}

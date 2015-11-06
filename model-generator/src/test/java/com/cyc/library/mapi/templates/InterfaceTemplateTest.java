package com.cyc.library.mapi.templates;

import com.cyc.library.model.InterfaceObj;
import com.cyc.library.model.MethodObj;
import com.cyc.library.model.MethodObj.FuncType;
import com.cyc.kb.KBCollection;
import com.cyc.kb.client.KBCollectionImpl;
import com.cyc.kb.exception.KBApiException;
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
  public void testIsObjSufficientlyConfigured() throws IOException, KBApiException, Exception {
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
  public void testGenerateString() throws IOException, KBApiException, Exception {
    final InterfaceTemplate template = new InterfaceTemplate();
    template.populateFromObj(createInterfaceObj());
    assertEqualsFileContents(getTestFile("generate-string.txt"), template.generateString());
  }
  
  
  // Protected
  
  static protected InterfaceObj createInterfaceObj() throws KBApiException, Exception {
    InterfaceObj obj = new InterfaceObj();
    obj.setPackageName("com.cyc.template");
    obj.setImports(new HashSet(Arrays.asList("java.io.File", "java.io.IOException")));
    obj.setCycName("SomeClass");
    obj.setVisibility("protected");
    List<MethodObj> methods = new ArrayList<MethodObj>();
    /*
    MethodObj method = new MethodObj("gizmoObservedToBeOperationalOnDate", KBCollection.get("Gizmo"), FuncType.GET);
    method.
    */
    methods.add(new MethodObj("gizmoObservedToBeOperationalOnDate", KBCollectionImpl.get("Gizmo"), FuncType.GET));
    methods.add(new MethodObj("gizmoObservedToBeOperationalOnDate", KBCollectionImpl.get("Gizmo"), FuncType.SET));
    methods.add(new MethodObj("gizmoObservedToBeBrokenOnDate", KBCollectionImpl.get("Gizmo"), FuncType.GET));
    methods.add(new MethodObj("gizmoObservedToBeBrokenOnDate", KBCollectionImpl.get("Gizmo"), FuncType.TYPE_GET_LIST));
    obj.setMethods(methods);
    return obj;
  }
}

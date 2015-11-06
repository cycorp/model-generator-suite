package com.cyc.library.model;

import com.cyc.library.mapi.ContextMap;
import com.cyc.base.CycAccess;
import com.cyc.base.CycAccessManager;
import com.cyc.kb.Context;
import com.cyc.kb.Fact;
import com.cyc.kb.FirstOrderCollection;
import com.cyc.kb.KBCollection;
import com.cyc.kb.KBIndividual;
import com.cyc.kb.KBObject;
import com.cyc.kb.KBPredicate;
import com.cyc.kb.SecondOrderCollection;
import com.cyc.library.mapi.AbstractCycModelObject;
import com.cyc.library.mapi.exceptions.ModelAPIException;
import com.cyc.library.mapi.SimpleContextMap;
import com.cyc.query.Query;
import com.cyc.kb.client.KBObjectImpl;
import com.cyc.kb.exception.KBApiException;
import com.cyc.library.mapi.exceptions.ModelAPIRuntimeException;
import com.cyc.query.QueryFactory;
import com.cyc.query.exception.QueryConstructionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassObj extends ModelObj {

  final private Set<String> imports = new HashSet<String>();
  private List<InterfaceObj> impls = new ArrayList<InterfaceObj>();
  private InterfaceObj primaryImpl;
  // TODO: We might want to reserve this variable name to the 
  // genls Collection that is also generated in a given project, which will
  // stand in a super class relation with the current class.
  private String superclass;
  private String dirName;
  private ContextMap contextMap;
  // Builds the constructor logic. It will enforce the class-object 
  // "relationType" specified in the InterfaceObj.
  private String membershipLogic;
  
  private static String KBI = KBIndividual.class.getSimpleName();
  private static String KBC = KBCollection.class.getSimpleName();
  private static String CONT = Context.class.getSimpleName();

  public ClassObj(InterfaceObj io, ContextMap contextMap) throws ModelGeneratorException {
    this.setPrimaryImpl(io);
    this.setDirName("cycImpl/");
    this.setContextMap(contextMap);
    /*
    imports.add(KBCollection.class.getCanonicalName());
    imports.add(FirstOrderCollection.class.getCanonicalName());
    imports.add(SecondOrderCollection.class.getCanonicalName());
    imports.add(Context.class.getCanonicalName());
    imports.add(KBIndividual.class.getCanonicalName());
    imports.add(Predicate.class.getCanonicalName());
    imports.add(Fact.class.getCanonicalName());
    */ 
    imports.add(CycAccessManager.class.getCanonicalName());
    
    imports.add(KBObject.class.getPackage().getName()+".*");//"com.cyc.kb.*");
    imports.add(KBObjectImpl.class.getPackage().getName()+".*");//"com.cyc.kb.client.*");
    imports.add(ModelAPIException.class.getCanonicalName());
    imports.add(ModelAPIRuntimeException.class.getCanonicalName());
    imports.add(KBApiException.class.getCanonicalName());

    // TODO: Change this...
    imports.add(Query.class.getCanonicalName());
    imports.add(QueryFactory.class.getCanonicalName());
    imports.add(QueryConstructionException.class.getCanonicalName());


    imports.add(ArrayList.class.getCanonicalName()); // "java.util.ArrayList"
    imports.add(List.class.getCanonicalName()); // "java.util.List"
    imports.add(Set.class.getCanonicalName()); // "java.util.List"
    imports.add(Collection.class.getCanonicalName()); // "java.util.Collection"
    imports.add(ContextMap.class.getCanonicalName());
    imports.add(SimpleContextMap.class.getCanonicalName());
    imports.add(AbstractCycModelObject.class.getCanonicalName());
    imports.add(this.getPrimaryImpl().getPackageName() + ".iface." + this.getPrimaryImpl().getName());
    
    if (this.getPrimaryImpl().getExtendsInterface().equals("Empty")){
      //this.setSuperClass(AbstractCycModelObject.class.getSimpleName());
      this.setSuperClass(this.getPrimaryImpl().getInstanceType() + "Impl");
    } else {
      this.setSuperClass(this.getPrimaryImpl().getExtendsInterface() + "Impl");
    }

    /*
     this.instance = ${class_obj.PrimaryImpl.InstanceName};
    
     // This if statement is the logic that verifies membership in a class (isa, genls, genlPreds, etc)
     if (!individual.checkSuperType(contexts.getCollectionContext(), core)) {
     // only if its a new constant, which we don't have a way of knowing with current SAPI,
     // assert it as a Thingamabob
     //assignSupertypes(core);
     this.instance.superTypes(contexts.getCollectionContext(), core);
     this.instance.superTypes(Context.get("UniversalVocabularyMt"), core);
     }
     */
    String logic = "";
    if (getPrimaryImpl().getRelationType().equals("isa")) {
      if (getPrimaryImpl().getCollectionOrder() != 99) {
        // logic = logic + "  this.instance = " + getPrimaryImpl().getInstanceName() + ";\n";
        logic = logic + "  if (! this.isInstanceOf(" + getName() + ".getClassType(), contexts.getCollectionContext())) {\n";
        logic = logic + "    // only if its a new constant, which we don't have a way of knowing with current SAPI, assert it as a " + getPrimaryImpl().getCycName() + "\n";
        logic = logic + "    this.instantiates(" + getName() + ".getClassType(), contexts.getCollectionContext());\n";
        logic = logic + "    this.instantiates(" + getName() + ".getClassType(), ContextImpl.get(\"UniversalVocabularyMt\"));\n";
        logic = logic + "  }\n";
      } else {
        // logic = logic + "  this.instance = " + getPrimaryImpl().getInstanceName() + ";\n";
        //logic = logic + "  if (!CycAccessManager.get().getCurrentAccess().getInspectorTool().isa(this.getClassType().getCore(), " + getName() + ".getClassType().getCore(), contexts.getCollectionContext().getCore())) {\n";
        logic = logic + "  if (! this.isInstanceOf(" + getName() + ".getClassType(), contexts.getCollectionContext())) {\n";        
        logic = logic + "    // only if its a new constant, which we don't have a way of knowing with current SAPI, assert it as a " + getPrimaryImpl().getCycName() + "\n";
        logic = logic + "    // KBObject does not have \"isInstanceOf\" so using CycAccess. When Term is available use that instead.";
        logic = logic + "    this.addFact(contexts.getCollectionContext(), KBPredicateImpl.get(\"#$isa\"), 1, (Object) " + getName() + ".getClassType());\n";
        logic = logic + "    this.addFact(ContextImpl.get(\"UniversalVocabularyMt\"), KBPredicateImpl.get(\"#$isa\"), 1, (Object) " + getName() + ".getClassType());\n";
        logic = logic + "  }\n";
      }
    } else if (getPrimaryImpl().getRelationType().equals("genls")) {
      // logic = logic + "  this.instance = " + getPrimaryImpl().getInstanceName() + ";\n";
      logic = logic + "  if (! this.isGeneralizationOf(" + getName() + ".getClassType(), contexts.getCollectionContext())) {\n";
      logic = logic + "    // only if its a new constant, which we don't have a way of knowing with current SAPI, assert it as a " + getPrimaryImpl().getCycName() + "\n";
      logic = logic + "    this.addGeneralization(" + getName() + ".getClassType(), contexts.getCollectionContext());\n";
      logic = logic + "    this.addGeneralization(" + getName() + ".getClassType(), ContextImpl.get(\"UniversalVocabularyMt\"));\n";
      logic = logic + "  }\n";
    } else if (getPrimaryImpl().getRelationType().equals("quotedIsa")) {
      // logic = logic + "  this.instance = " + getPrimaryImpl().getInstanceName() + ";\n";
      logic = logic + "  if (! this.isQuotedInstanceOf(" + getName() + ".getClassType(), contexts.getCollectionContext())) {\n";
      logic = logic + "    // only if its a new constant, which we don't have a way of knowing with current SAPI, assert it as a " + getPrimaryImpl().getCycName() + "\n";
      logic = logic + "    this.addQuotedIsa(" + getName() + ".getClassType(), contexts.getCollectionContext());\n";
      logic = logic + "    this.addQuotedIsa(" + getName() + ".getClassType(), ContextImpl.get(\"UniversalVocabularyMt\"));\n";
      logic = logic + "  }\n";
    } else {
      throw new ModelGeneratorException("The predicate " + getPrimaryImpl().getRelationType() + " based collection is not yet supported.");
    }
    this.setMembershipLogic(logic);
  }

  public ClassObj(InterfaceObj io) throws ModelGeneratorException {
    this(io, null);
  }

  @Deprecated
  public String generateConstructor() {

    String constNameStr = primaryImpl.getName();
    String constLCaseStr = Character.toLowerCase(constNameStr.charAt(0)) + constNameStr.substring(1, constNameStr.length());

    String constStr = "";

    constStr = constStr + "private static " + KBC + " core = null;\n\n";

    constStr = constStr + "private " + KBI + " instance;\n\n";

    constStr = constStr + "public " + constNameStr + "Impl(String ctxStr, String " + constLCaseStr + "Str) throws Exception {\n";
    constStr = constStr + "  this(Context.get(ctxStr), new " + KBI + "(" + constLCaseStr + "Str));\n";
    constStr = constStr + "}\n";
    constStr = constStr + "\n";

    constStr = constStr + "public " + constNameStr + "Impl(" + CONT + "  ctx, " + KBI + " a" + constNameStr + ") throws Exception {\n";
    constStr = constStr + "  if (core == null){\n";
    constStr = constStr + "     core = new " + KBC + "(\"" + primaryImpl.getCycName() + "\");\n";
    constStr = constStr + "  }\n";
    constStr = constStr + "\n";
    //constStr = constStr + "  Context uvmt = Context.get(\"UniversalVocabularyMt\");\n";
    //constStr = constStr + "\n";
    //constStr = constStr + "  if (a" + constNameStr + ".checkSuperType(\"OrionAssetMt\", \"" + primaryImpl.getCycName() + "\")) {\n";
    constStr = constStr + "  if (a" + constNameStr + ".checkSuperType(ctx, core)) {\n";
    constStr = constStr + "    instance = a" + constNameStr + ";\n";
    constStr = constStr + "  } else { // only if its a new constant, which we don't have a way of knowing with current SAPI, assert it as a " + constNameStr + "\n";
    //constStr = constStr + "    a" + constNameStr + ".superTypes(\"OrionAssetMt\", \"" + primaryImpl.getCycName() + "\");\n";
    constStr = constStr + "    a" + constNameStr + ".superTypes(ctx, core);\n";
    constStr = constStr + "    " + CONT + " uvmt = " + CONT + ".get(\"UniversalVocabularyMt\");\n";
    constStr = constStr + "    a" + constNameStr + ".superTypes(uvmt, core);\n";
    constStr = constStr + "    instance = a" + constNameStr + ";\n";
    constStr = constStr + "  }\n";
    constStr = constStr + "}\n";
    constStr = constStr + "\n";


    constStr = constStr + "public List<" + constNameStr + "> allSibling" + constNameStr + "s (String ctxStr) throws Exception {\n";
    constStr = constStr + "  List<" + KBI + "> inds = core.subTypes(ctxStr, " + KBI + ".class);\n";
    constStr = constStr + "  List<" + constNameStr + "> " + constLCaseStr + "s = new ArrayList<" + constNameStr + ">();\n";
    constStr = constStr + "  " + CONT + " uvmt = " + CONT + ".get(\"UniversalVocabularyMt\");\n";
    constStr = constStr + "\n";
    constStr = constStr + "  for (" + KBI + " ind : inds){\n";
    constStr = constStr + "    " + constLCaseStr + "s.add(new " + constNameStr + "Impl(uvmt, ind));\n";
    constStr = constStr + "  }\n";
    constStr = constStr + "\n";
    constStr = constStr + "  return " + constLCaseStr + "s;\n";
    constStr = constStr + "}\n";
    constStr = constStr + "\n";

    constStr = constStr + "public " + KBI + " getInstance() {\n";
    constStr = constStr + "  return instance;\n";
    constStr = constStr + "}\n";

    return constStr;
  }

  public List<InterfaceObj> getImpls() {
    return impls;
  }

  public void setImpls(List<InterfaceObj> impls) {
    this.impls = impls;
  }

  public void setImpl(InterfaceObj impl) {
    this.impls.add(impl);
  }

  public InterfaceObj getPrimaryImpl() {
    return primaryImpl;
  }

  public List<InterfaceObj> getAllImpls() {
    final List<InterfaceObj> all = new ArrayList(getImpls());
    all.add(getPrimaryImpl());
    return all;
  }

  public void setPrimaryImpl(InterfaceObj primaryImpl) {
    this.primaryImpl = primaryImpl;
    this.imports.addAll(primaryImpl.getImports());
    this.setName(primaryImpl.getName() + "Impl");
  }

  public String getFullPackageName() {
    return getPrimaryImpl().getPackageName() + ".cycImpl";
  }

  public Set<String> getImports() {
    return this.imports;
  }

  public String getDirName() {
    return dirName;
  }

  public void setDirName(String dirName) {
    this.dirName = dirName;
  }

  public ContextMap getContextMap() {
    return this.contextMap;
  }

  public void setContextMap(ContextMap contextMap) {
    this.contextMap = contextMap;
  }

  public String getSuperClass() {
    return this.superclass;
  }

  public void setSuperClass(String superclass) {
    this.superclass = superclass;
  }

  public String getMembershipLogic() {
    return membershipLogic;
  }

  public void setMembershipLogic(String membershipLogic) {
    this.membershipLogic = membershipLogic;
  }

  /**
   * This returns an ordered list of the Predicates which underly the class's
   * MethodObjs, with no duplicate elements. This is valuable because a single
   * predicate may be the basis for multiple MethodObjs (getters, setters, etc.)
   *
   * @return
   */
  public List<KBPredicate> getMethodPredicates() {
    Set<KBPredicate> set = new HashSet<KBPredicate>();
    for (MethodObj method : getMethods()) {
      set.add(method.getPredicate());
    }
    List<KBPredicate> list = new ArrayList(set);
    Collections.sort(list, new Comparator<KBPredicate>() {
      @Override
      public int compare(KBPredicate o1, KBPredicate o2) {
        if ((o2 == null) || !(o2 instanceof KBPredicate)) {
          return 1;
        }
        return o1.toString().compareTo(o2.toString());
      }
    });
    return list;
  }

  public List<MethodObj> getMethods() {
    Set<MethodObj> set = new HashSet<MethodObj>();
    for (InterfaceObj iface : getAllImpls()) {
      for (MethodObj method : iface.getMethods()) {
        set.add(method);
      }
    }
    List<MethodObj> list = new ArrayList(set);
    //Collections.sort(list);
    return list;
  }

  @Deprecated
  public String toString() {
    String classStr = "/**\n"
            + " * This code was autogenerated by ModelGenerator program. The ModelGenerator\n"
            + " * creates Java Interfaces and Classes by introspecting the Cyc Knowledge Base.\n"
            + " * It is strongly recommended not to make any changes to this file. \n"
            + " * This will allow regeneration of code when the Cyc KB model changes with fewer\n"
            + " * downstream changes. Please implement subclasses of the generated class to add \n"
            + " * functionality.\n"
            + " * \n"
            + " * The class corresponds to " + this.getCycName() + " in the Cyc KB. \n"
            + " * <p>\n"
            + " * \n"
            + " * @author ModelGenerator\n"
            + " * @author Vijay Raj\n"
            + " * @version \"%I%, %G%\"\n"
            + " * @since 0.90\n"
            + " */\n\n";
    // By convention, the package that Classes (implementations of interfaces) will reside in .cyc package
    classStr = classStr + "package " + this.getPrimaryImpl().getPackageName() + "." + this.getDirName().replaceFirst("\\/", "") + ";\n\n\n";

    for (String i : imports) {
      classStr = classStr + "import " + i + ";\n";
    }

    classStr = classStr + "\n\npublic class " + this.getName();
    classStr = classStr + " implements " + this.getPrimaryImpl().getName();
    classStr = classStr + " {\n\n";

    classStr = classStr + generateConstructor();

    for (MethodObj mo : this.getPrimaryImpl().getMethods()) {
      try {
        classStr = classStr + "    " + mo.toStringClass() + "\n\n";
      } catch (Exception e) {
        // This is deprecated, so just ignore.
      }
    }

    classStr = classStr + " public String toString () {\n";
    classStr = classStr + "   return instance.toString();\n";
    classStr = classStr + " }\n";

    classStr = classStr + "}\n";
    return classStr;
  }
}

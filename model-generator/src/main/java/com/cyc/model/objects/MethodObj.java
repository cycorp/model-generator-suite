package com.cyc.model.objects;

import com.cyc.kb.Context;
import com.cyc.kb.ContextFactory;
import static com.cyc.kb.ContextFactory.UV_MT;
import com.cyc.kb.KbFunction;
import com.cyc.kb.KbCollection;
import com.cyc.kb.KbCollectionFactory;
import com.cyc.kb.KbFunctionFactory;
import com.cyc.kb.KbPredicate;
import com.cyc.kb.KbPredicateFactory;
import com.cyc.kb.Relation;
import com.cyc.kb.RelationFactory;
import com.cyc.kb.client.KbCollectionImpl;
import com.cyc.kb.client.KbUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class MethodObj extends ModelObj {

  private static String PRED = KbPredicate.class.getSimpleName();
  private static String CONTEXT = Context.class.getSimpleName();
  private static String FUNC = KbFunction.class.getSimpleName();
  private static String PRED_FACTORY = KbPredicateFactory.class.getSimpleName();
  private static String CONTEXT_FACTORY = ContextFactory.class.getSimpleName();
  private static String FUNC_FACTORY = KbFunctionFactory.class.getSimpleName();
  
  /**
   * Instead of calling System.out.println(), please call log(). It calls
   * System.out.println(), but can be suppressed by setting:
   *
   * VERBOSE = false.
   *
   * This is useful when other classes (unit tests, generators, etc.) are
   * emitting a lot of output.
   */
  private void log(String msg) {
    if (VERBOSE) {
      System.out.println(msg);
    }
  }
  private static final boolean VERBOSE = true;
  //private List<String> argumentTypes = new ArrayList<String>();
  private List<ArgumentObj> arguments = new ArrayList<ArgumentObj>();
  private ArgumentObj returnValue;
  private Integer argPos;
  private String ctxStr;
  private Integer configArgPos;

  public enum FuncType {

    SET, GET, TYPE_GET_LIST, FACTORY, SENT
  };
  private FuncType fType;

  public MethodObj() {
  }

  public MethodObj(String predStr, KbCollection c, FuncType funcType) throws Exception {
    this(predStr, c, funcType, null);
  }

  public MethodObj(String predStr, KbCollection classColOfMethod, FuncType funcType, Integer configArgPos) throws Exception {
    this.configArgPos = configArgPos;
    this.setVisibility("public");
    //setCycName calls this.setName() with some cleaning!
    this.setCycName(predStr);
    this.setfType(funcType);
    
    if (this.getfType().equals(FuncType.FACTORY)){
      this.setBelongsToClass("static");
    }

    if (configArgPos != null) {
      this.setArgPos(configArgPos);
      this.setName(this.determineName(getName() + "_arg" + getArgPos(), funcType));
    } else {
      this.setName(this.determineName(getName(), funcType));
    }

    // With a predicate an instance of "this" Class is NOT returned
    // With a function an instance of "this" Class IS returned

    Relation p = RelationFactory.get(predStr);
    Set<KbCollection> genlsOfClassCol = new HashSet<KbCollection>();
    genlsOfClassCol.addAll(classColOfMethod.getGeneralizations(UV_MT));
    //cset.addAll(c.allGeneralizations());
    genlsOfClassCol.add(classColOfMethod);
    log("C generalizations: " + genlsOfClassCol);
      
    if (KbPredicateFactory.existsAsType(predStr)) {

      p = KbPredicateFactory.get(predStr);

      log("Analyzing predicate: " + p + " Function Type: " + this.getfType());

      // TODO: SemanticAPI should support "EverythingPSC" for getFacts
      Collection<String> comments = p.getComments(UV_MT);
      if (comments.size() > 0) {
        this.setCycComment(comments.iterator().next());
      }
      int pArity = p.getArity();
      if (pArity > 2 && !this.getfType().equals(FuncType.SET)) {
        log("Predicates with arity > 2 are handled currently for FuncType.SET/SENT only.");
        //throw new ModelGeneratorException("Predicates with arity > 2 are handled currently for FuncType.SET/SENT only.");
      }

      log("Analyzing predicate: " + p);


      //int foundClassInPos = -1;
      for (Integer i = 1; i <= pArity; i++) {
        // TODO: There shouldn't be multiple argIsa for same arg num,
        // but what if there is. Need to handle that. 
        // How about argIsa defined in Mts other than UVMt.
        Collection<KbCollection> ac = p.getArgGenl(i, UV_MT);
        String rel;
        if (ac.isEmpty()) {
          ac = p.getArgIsa(i, UV_MT);
          rel = "isa";
        } else {
          rel = "genls";
        }

        log("Predicate: " + p + " Arg Pos: " + i + " Collections: " + ac);
        KbCollection firstConstraint = ac.iterator().next();
        int argMod = 0;
        if (firstConstraint.isInstanceOf(ModelConstants.getInstance().ListTypeByMemType)
                && !firstConstraint.isAtomic()){
          argMod = 2;
          // reset firstConstraint
          firstConstraint = firstConstraint.<KbCollection>getArgument(1);
        } else if (firstConstraint.isInstanceOf(ModelConstants.getInstance().SetOrColType)
                && !firstConstraint.isAtomic()
                && firstConstraint.<KbFunction>getArgument(0).equals(ModelConstants.getInstance().SetOfTypeFn)) {
          argMod = 3;
          firstConstraint = firstConstraint.<KbCollection>getArgument(1);
        } else if (firstConstraint.equals(ModelConstants.getInstance().List)) {
            argMod = 2;
            firstConstraint = KbCollectionFactory.findOrCreate("JavaObject");
        } else if (firstConstraint.equals(ModelConstants.getInstance().SetMath)) {
            argMod = 3;
            firstConstraint = KbCollectionFactory.findOrCreate("JavaObject");
        }
        
        if (genlsOfClassCol.contains(firstConstraint) && configArgPos == null) {
          //foundClassInPos = i;
          if (this.getArgPos() == null) {
            this.setArgPos(i);
          } else {
            String msg = "More than one arguments of predicate " + p + " satisfy argIsa at position: " + i;
            log(msg);
            throw new ModelGeneratorException(msg);
          }
        } else {
          // configArgPos is the argument position of the "this" object.
          // we are NOT going to return in get methods
          // and DO NOT take as input in the set method.
          if (i != configArgPos) {
            //TODO: How does this scale beyond BinaryPredicates?
            //This code will keep adding return types
            if (this.getfType().equals(FuncType.GET)) {
              // TODO: No need to create new InterfaceObj that will be thrown away, try to find
              // it from the ProjectObj somehow using ac.get(0).toString() .
              // TODO: When argGenls is supported, passing in the "genls" in InterfaceObj will 
              // generate correct return type for the Interface/Class
              this.setReturnValue(new ArgumentObj(new InterfaceObj(firstConstraint.toString(), rel), -1, argMod));
            } else if (this.getfType().equals(FuncType.SET)) {
              this.addArgument(new ArgumentObj(new InterfaceObj(firstConstraint.toString(), rel), i, argMod));
            } else if (this.getfType().equals(FuncType.SENT)) {
              this.addArgument(new ArgumentObj(new InterfaceObj(firstConstraint.toString(), rel), i, argMod));
              this.setReturnValue(new ArgumentObj(new InterfaceObj("CycLSentence", "isa"), -1, 0));
            }
          }

        }
      }
    } else if (KbFunctionFactory.existsAsType(predStr)) {
      p = KbFunctionFactory.get(predStr);
      log("Analyzing function: " + p);
      
      if (p.isInstanceOf(ModelConstants.getInstance().VarArityFunc)){
        log("Function: " + p + " is a variable arity function.");
        Collection<KbCollection> argIsaCol = p.<KbCollection>getValues(ModelConstants.getInstance().argsIsa, 1, 2, UV_MT);
        log("argsIsa: " + argIsaCol);
        if (!argIsaCol.isEmpty()) {
          KbCollection minArgIsaCol = KbUtils.minCol(argIsaCol);
          log("Minimally general collection being used: " + minArgIsaCol);
          this.addArgument(new ArgumentObj(new InterfaceObj(minArgIsaCol.toString(), "isa"), 1, 1/*argMod=1*/));
        } else {
          log("Args isa for: " + p + " was empty.");
        }
      } else {
        Integer pArity;
        try {
          pArity = p.getArityMin();
        } catch (IllegalArgumentException iae){
          pArity = p.getArity();
        }

        log("Arity of Function " + p + " is " + pArity);

        for (Integer i = 1; i <= pArity; i++) {
          Collection<KbCollection> ac = null;
          ac = p.getArgIsa(i, UV_MT);
          if (ac == null || ac.isEmpty()){
            ac = p.getValues(ModelConstants.getInstance().argsIsa, 1, 2, UV_MT);
          }
          log("Function Arg: ArgIsas: " + ac);
          KbCollection firstConstraint = ac.iterator().next();
          log("Function Arg: First Constraint: " + firstConstraint);
          this.addArgument(new ArgumentObj(new InterfaceObj(firstConstraint.toString(), "isa"), i));
        }  
      }

      Collection<KbCollection> resIsaCols = ((KbFunction) p).getResultIsa(UV_MT);
      KbCollection tightestConstraint = KbCollectionImpl.getMinCol(resIsaCols);
      log("Result: ResultIsas: " + resIsaCols);  
      log("Result: Tightest Constraint: " + tightestConstraint);
      
      if (!Collections.disjoint(resIsaCols, genlsOfClassCol)){
        this.setReturnValue(new ArgumentObj(new InterfaceObj(classColOfMethod.toString(), "isa"), -1));
      } else {
        throw new ModelGeneratorException("None of the return value constraints " + resIsaCols + " is a genls of any of " + genlsOfClassCol + " of the class " + classColOfMethod);
      }
    } else {
      String msg = "No Relation (Predicate or Function) found for str: " + predStr + "\n";
      log(msg);
      throw new ModelGeneratorException(msg);
    }

    //if (foundClassInPos == -1){
    if (this.getArgPos() == null) {
      String message = "Could not find the location of " + classColOfMethod + " in predicate " + p;
      log(message);
      if (p instanceof KbPredicate){
        throw new ModelGeneratorException(message);
      }
    } else if (this.getfType().equals(FuncType.GET)
            && this.getReturnValue() == null) {
      String msg = "A get method for Predicate " + p + " needs a return value. Unable to find it.";
      log(msg);
      throw new ModelGeneratorException(msg);
    } else if (this.getfType().equals(FuncType.SET)
            && this.getArguments().isEmpty()) {
      String msg = "A set method for Predicate " + p + " needs atleast one argument in addition to Context. Unable to find it.";
      log(msg);
      throw new ModelGeneratorException(msg);
    } else {
      log("Arguments: " + this.getArguments());
    }
    log("Arguments: " + this.getArguments());
    log("Return value: " + this.getReturnValue());
  }

  public final Integer getArgPos() {
    return argPos;
  }

  public final void setArgPos(Integer argPos) {
    this.argPos = argPos;
  }

  public final String getCtxStr() {
    return ctxStr;
  }

  public final void setCtxStr(String ctxStr) {
    this.ctxStr = ctxStr;
  }

  public final Integer getConfigArgPos() {
    return this.configArgPos;
  }

  public final ArgumentObj getReturnValue() {
    return returnValue;
  }

  public final void setReturnValue(ArgumentObj returnValue) {
    this.returnValue = returnValue;
  }

  public final KbPredicate getPredicate() {
    /*
     * TODO: this method should be modified or eliminated once MethodObj
     *       extends ModelObj<Predicate>
     */
    try {
      return KbPredicateFactory.get(getCycName());
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public final List<ArgumentObj> getArguments() {
    return arguments;
  }

  public final void setArguments(List<ArgumentObj> arguments) {
    this.arguments = arguments;
  }

  public final void addArgument(ArgumentObj argument) {
    this.arguments.add(argument);
  }

  public final FuncType getfType() {
    return fType;
  }

  public final void setfType(FuncType fType) {
    this.fType = fType;
  }

  /**
   * Returns a multi-line javadoc header
   *
   * @return
   */
  public List<String> getJavadoc() {
    return Arrays.asList(
            "This method corresponds to " + this.getCycName() + " in the Cyc KB.",
            "",
            "<p>" + (this.getCycComment() != null ? this.getCycComment() : ""));
  }

  public List<String> getExceptions() {
    return Arrays.asList(
            "ModelException");
  }

  @Override
  @Deprecated
  public String toString() {
    String methodStr = "/**\n"
            + " * The method corresponds to " + this.getCycName() + " in the Cyc KB. \n"
            + " * <p>\n"
            + " * " + (this.getCycComment() != null ? this.getCycComment() : "") + "\n"
            + " * \n"
            + " */\n";

    methodStr = methodStr + this.getVisibility();
    methodStr = methodStr + " abstract " + (this.getReturnValue() != null ? this.getReturnValue().getType() : "void");
    methodStr = methodStr + " " + this.getName();
    methodStr = methodStr + " (" + CONTEXT + " ctx";
    int count = 0;
    for (ArgumentObj arg : this.getArguments()) {
      //methodStr = methodStr + arg.getType() + " " + arg.getName() + (count != this.getArguments().size()-1 ? ", " : "");
      methodStr = methodStr + ", " + arg.getType() + " " + arg.getName();
      count++;
    }
    methodStr = methodStr + ")";
    methodStr = methodStr + " throws ModelException;";

    //log(methodStr);

    return methodStr;
  }

  public String toStringClass() throws ModelGeneratorException {
    String methodStr = "/**\n"
            + " * The method corresponds to " + this.getCycName() + " in the Cyc KB. \n"
            + " * <p>\n"
            + " * " + (this.getCycComment() != null ? this.getCycComment() : "") + "\n"
            + " * \n"
            + " */\n";
    methodStr = methodStr + this.getVisibility();
    methodStr = methodStr + " " + (this.getReturnValue() != null ? this.getReturnValue().getType() : "void");
    methodStr = methodStr + " " + this.getName();
    methodStr = methodStr + " (" + CONTEXT + " ctx";
    int count = 0;
    for (ArgumentObj arg : this.getArguments()) {
      //methodStr = methodStr + arg.getType() + " " + arg.getName() + (count != this.getArguments().size()-1 ? ", " : "");
      methodStr = methodStr + ", " + arg.getType() + " " + arg.getName();
      count++;
    }
    methodStr = methodStr + ")";
    methodStr = methodStr + " throws ModelException";
    methodStr = methodStr + "\n{\n";
    methodStr = methodStr + getMethodBody();
    methodStr = methodStr + "}\n";
    methodStr = methodStr + "   ";
    return methodStr;
  }

  public String getMethodBody() throws ModelGeneratorException {
    String methodStr = "    try {\n";
    if (!this.getfType().equals(FuncType.FACTORY)) {
      if (this.getReturnValue() == null) {
        // methodStr = methodStr + "  Context ctx = Context.get(\"OrionEventHistoryMt\");\n";      
        // Needs getCycName
        // methodStr = methodStr + "      " + PRED + " p = " + PRED + ".get(\"" + this.getCycName() + "\");\n\n";
        methodStr = methodStr + "      " + determineSentenceNameFromSetName(this.getName()) + "(";
        int count = 0;
        for (ArgumentObj arg : this.getArguments()) {
          // TODO: Both branches look alike, what is the difference?
          if (arg.isSObject()) {
            methodStr = methodStr + arg.getName() + (count != this.getArguments().size() - 1 ? ", " : "");
          } else {
            methodStr = methodStr + arg.getName() + (count != this.getArguments().size() - 1 ? ", " : "");
          }
          count++;
        }
        methodStr = methodStr + ").assertIn(ctx);\n";
      } else  if // (this.getReturnValue() == null)
              (this.getReturnValue() !=null && this.getReturnValue().getSObjectType().equals("Sentence"))
      {
        // Needs getCycName
        methodStr = methodStr + "      " + PRED + " p = " + PRED_FACTORY + ".get(\"" + this.getCycName() + "\");\n\n";
        methodStr = methodStr + "      return this.getSentence(p, " + this.getArgPos() + ", ";
        int count = 0;
        for (ArgumentObj arg : this.getArguments()) {
          // TODO: Both branches look alike, what is the difference?
          if (arg.isSObject()) {
            methodStr = methodStr + arg.getName() + (count != this.getArguments().size() - 1 ? ", " : "");
          } else {
            methodStr = methodStr + arg.getName() + (count != this.getArguments().size() - 1 ? ", " : "");
          }
          count++;
        }
        methodStr = methodStr + ");\n";
      }
      // Case where this is a "getter", there is a return value.
      else {
        // Needs getCycName
        methodStr = methodStr + "      " + PRED + " p = " + PRED_FACTORY + ".get(\"" + this.getCycName() + "\");\n";
        methodStr = methodStr + "      Collection<Fact> fl = this.getFacts(p, " + this.getArgPos() + ", ctx);\n";
        methodStr = methodStr + "      if (!fl.iterator().hasNext()){\n";
        methodStr = methodStr + "        return null;\n";
        methodStr = methodStr + "      } else {\n";

        // There is a return value and the predicate is binary, so we don't expect any arguments
        // other than Context.
        if (this.getArguments().isEmpty()) {
          //methodStr = methodStr + "    return fl.get(0).getArgument(2, Date.class);\n";
          ArgumentObj getret = this.getReturnValue();

          String modifiedRetTypeStr = getret.getSObjectType();
          if (getret.getArgMod() == 1) {
              modifiedRetTypeStr = modifiedRetTypeStr + "[]"; // Untested
          } else if (getret.getArgMod() == 2) {
              modifiedRetTypeStr = "List<" + modifiedRetTypeStr + ">";
          } else if (getret.getArgMod() == 3) {
              modifiedRetTypeStr = "Set<" + modifiedRetTypeStr + ">";
          }

          // TODO: Find the right implementation for "getret.getType()". Now we are blindly adding "Impl". Ideally some 
          // interfaces will not be "primary interface", i.e. there will not be any class with "their name + impl". 
          // In such a case this will fail. 
          if (getret.isSObject()) {
            methodStr = methodStr + "        return fl.iterator().next().<" + modifiedRetTypeStr + ">getArgument(" + ((this.getArgPos() == 1) ? 2 : 1) + ");\n";
          } else {
            methodStr = methodStr + "        Context uvmt = " + CONTEXT_FACTORY + ".get(\"UniversalVocabularyMt\");\n";
            methodStr = methodStr + "        return new " + getret.getType() + "Impl" + "(uvmt, fl.iterator().next().<" + modifiedRetTypeStr + ">getArgument(" + ((this.getArgPos() == 1) ? 2 : 1) + "));\n";
          }
          methodStr = methodStr + "        }\n";
        } // TODO: There is a return value and there are > 0 arguments
        // Predicate arity > 2 (Binary) not handled currently
        else {
          throw new ModelGeneratorException("Predicates with arity > 2 are not handled currently.");
        }
      }
    } else {

      String inputStr = this.getArguments().get(0).getName();
      
      ArgumentObj getret = this.getReturnValue();
      log("Get Ret for: " + getret.getCycName());
      log("Get Ret for: " + getret.isSObject());
      if (!getret.isSObject()) {
        methodStr = methodStr + "        " + getret.getSObjectType() + " temp = " + FUNC_FACTORY + ".get(\"" + this.getCycName() + "\")"
                + ".findOrCreateFunctionalTerm"
                + "(" + getret.getSObjectType() + ".class, " ; //+ inputStr + ");\n"; 
        
        int count = 0;
        for (ArgumentObj arg : this.getArguments()) {
          if (arg.isSObject()) {
            methodStr = methodStr + arg.getName() + (count != this.getArguments().size() - 1 ? ", " : "");
          } else {
            methodStr = methodStr + arg.getName() + (count != this.getArguments().size() - 1 ? ", " : "");
          }
          count++;
        }
        methodStr = methodStr + ");\n"; 
        methodStr = methodStr + "        return new " + getret.getType() + "Impl(temp);\n";
      }
    }

    methodStr = methodStr + "    } catch (Exception e) {\n";
    methodStr = methodStr + "      throw new ModelException(e.getMessage(), e);\n";
    methodStr = methodStr + "    }\n";
    return methodStr;
  }

  protected static String determineName(String predStr, FuncType func) {
    final String baseName = predStr.substring(0, 1).toUpperCase() + predStr.substring(1);
    if (FuncType.GET.equals(func)) {
      return "get" + baseName;
    } else if (FuncType.TYPE_GET_LIST.equals(func)) {
      return "all" + baseName + "_type";
    } else if (FuncType.SET.equals(func)) {
      return "set" + baseName;
    } else if (FuncType.SENT.equals(func)) {
      return "sentence" + baseName;
    }
    return baseName;
  }
  
  private static String determineSentenceNameFromSetName(String name){
    if (name.substring(0, 3).equals("set")){
      return name.replaceFirst("^set", "sentence");
    } else {
      return name;
    }
  }

  @Override
  public int hashCode() {
    int hash = super.hashCode();
    hash = 13 * hash + (this.getArguments() != null ? this.getArguments().hashCode() : 0);
    hash = 13 * hash + (this.getReturnValue() != null ? this.getReturnValue().hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    if (! super.equals(obj)){
      return false;
    }
    final MethodObj other = (MethodObj) obj;
    if (this.getArguments() != other.getArguments() && (this.getArguments() == null || !this.getArguments().equals(other.getArguments()))) {
      return false;
    }
    if (this.getReturnValue() != other.getReturnValue() && (this.getReturnValue() == null || !this.getReturnValue().equals(other.getReturnValue()))) {
      return false;
    }
    return true;
  }
}

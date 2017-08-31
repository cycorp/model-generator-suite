/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyc.model.objects;

import com.cyc.kb.KbCollection;
import com.cyc.kb.KbPredicate;
import com.cyc.kb.KbPredicateFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author vijay
 */
public class CriteriaMethodObj extends MethodObj {

  private List<String> constraintLiterals = new ArrayList<String>();
  private List<ArgumentObj> predicateTypes = new ArrayList<ArgumentObj>();

  public CriteriaMethodObj(String predStr, KbCollection c, FuncType funcType) throws Exception {
    this.setVisibility("public");
    this.setCycName(predStr);
    this.setfType(funcType);

    ArgumentObj retVal = new ArgumentObj();
    retVal.setType("List<List<Object>>");
    this.setReturnValue(retVal);

    KbPredicate p = KbPredicateFactory.get(predStr);
    int pArity = p.getArity();

    //int foundClassInPos = -1;
    String predicateLiteral = "(" + predStr;
    for (int i = 1; i <= pArity; i++) {
      java.util.Collection<KbCollection> ac = p.getArgIsa(i, "UniversalVocabularyMt");

      Set<KbCollection> cset = new HashSet<KbCollection>();
      cset.addAll(c.getGeneralizations("UniversalVocabularyMt"));
      cset.add(c);

      System.out.println("Arg Pos: " + i + " Collections: " + ac);
      System.out.println("C generalizations: " + cset);
      
        KbCollection firstArgConstraint = ac.iterator().next();
      // ac.get(0), what happens when there are multiple arg constraints
      if (cset.contains(firstArgConstraint)) {
        //foundClassInPos = i;
        this.setArgPos(i);
        if (this.getfType().equals(FuncType.TYPE_GET_LIST)) {
          ArgumentObj argObj = new ArgumentObj(new InterfaceObj(c.toString()), i);

          // Predicate Literals
          // This is the (isa ?X Dog) constraints for Class/Collection #$Dog
          //this.addConstraintLiteral("(isa ?X" + i + " " + ac.get(0).toString() + ")");
          this.addConstraintLiteral("(isa ?X" + argObj.getQueryVarName() + " " + c.toString() + ")");
          predicateLiteral = predicateLiteral + " ?X" + argObj.getQueryVarName();

          // Predicate Types
          this.addPredicateTypes(argObj);

        } else {
          throw new Exception("CriteriaMethodObj does not support FuncType of " + funcType);
        }
      } else {
        if (this.getfType().equals(FuncType.TYPE_GET_LIST)) {
          ArgumentObj argObj = new ArgumentObj(new InterfaceObj(firstArgConstraint.toString()), i);

          // Predicate Literals
          //predicateLiteral = predicateLiteral + " ?X_" + ac.get(0).toString();
          predicateLiteral = predicateLiteral + " ?X" + argObj.getQueryVarName();

          // Predicate Types
          this.addPredicateTypes(argObj);

        } else {
          throw new Exception("CriteriaMethodObj does not support FuncType of " + funcType);
        }
      }
    }
    predicateLiteral = predicateLiteral + " )";
    this.addConstraintLiteral(predicateLiteral);

    //if (foundClassInPos == -1){
    if (this.getArgPos() == null) {
      System.out.println("Could not find the location of " + c + " in predicate " + p);
    } else {
      System.out.println("Arguments: " + this.getArguments());
    }

  }
  
  // TODO: Change to original name toTypeCriteriaString
  // For now..
  /**
   *
   * @return
   */
  @Deprecated
  @Override
  public String toStringClass() {
    String typeCriteria = "";
    typeCriteria = "/**\n"
            + " * The method corresponds to " + this.getCycName() + " in the Cyc KB. \n"
            + " * <p>\n"
            + " * \n"
            + " */\n";
    typeCriteria = typeCriteria + this.getVisibility();
    typeCriteria = typeCriteria + " " + (this.getReturnValue() != null ? this.getReturnValue().getType() : "void");
    typeCriteria = typeCriteria + " " + this.getName();
    typeCriteria = typeCriteria + " (Context ctx";
    int count = 0;
    for (ArgumentObj arg : this.getArguments()) {
      //methodStr = methodStr + arg.getType() + " " + arg.getName() + (count != this.getArguments().size()-1 ? ", " : "");
      typeCriteria = typeCriteria + ", " + arg.getType() + " " + arg.getName();
      count++;
    }
    typeCriteria = typeCriteria + ")";
    typeCriteria = typeCriteria + " throws Exception";
    typeCriteria = typeCriteria + "\n{\n";
    typeCriteria =  typeCriteria + getMethodBody();
    typeCriteria = typeCriteria + "}\n";
    typeCriteria = typeCriteria + "   ";
    return typeCriteria;
  }
  
  @Override
  public String getMethodBody() {
    String typeCriteria = "";
//    typeCriteria = typeCriteria + "    Context uvmt = Context.get(\"UniversalVocabularyMt\");\n";
    
    String returnVariable = "bindingsList";
    
    typeCriteria = typeCriteria + "  List<List<Object>> " + returnVariable + " = new ArrayList<List<Object>>();\n";
    typeCriteria = typeCriteria + "  try {";
    // Use sentences instead of strings
    /*
       String qStr = "(and  ";
  qStr = qStr + "  (isa ?XSKSA1 StructuredKnowledgeSource) ";
  qStr = qStr + "  (mappingMt ?XSKSA1 ?XSKSIMMA2 ) ";
  qStr = qStr + ")";
    
  RestrictedVariable rv = StructuredKnowledgeSourceImpl.getCore().toInstanceRestrictedVariable();
  Sentence s = new Sentence(Predicate.get("mappingMt"), rv, new Variable("XSKSIMMA2"));
     */
    typeCriteria = typeCriteria + "  String qStr = \"(and  \";\n";
    for (String contraint : this.getConstraintLiteral()) {
      typeCriteria = typeCriteria + "  qStr = qStr + \"  " + contraint + " \";\n";
    }
    typeCriteria = typeCriteria + "  qStr = qStr + \")\";\n";
    
    typeCriteria = typeCriteria + "  Query q = QueryFactory.getQuery(qStr, ctx.toString());\n";
    
    typeCriteria = typeCriteria + "    while(q.getResultSet().next()){\n";
    typeCriteria = typeCriteria + "      List<Object> listTemp = new ArrayList<Object>();\n";
    
    for (ArgumentObj arg : this.getPredicateTypes()) {
      if (arg.isSObject()) {
        typeCriteria = typeCriteria + "       listTemp.add( q.get (\"?X" + arg.getQueryVarName() + "\", " + arg.getType() + ".class));\n";
      } else {
        typeCriteria = typeCriteria + "       listTemp.add( new " + arg.getType() + "Impl" + "(contexts, q.getResultSet().getKbObject(\"?X" + arg.getQueryVarName() + "\", " + arg.getSObjectType() + ".class)));\n";
      }
    }

    typeCriteria = typeCriteria + "      " + returnVariable + ".add(listTemp);\n";
    typeCriteria = typeCriteria + "    }\n"
            + "   } catch (QueryConstructionException ex) {\n" +
"      throw new ModelException (ex.getMessage(), ex);\n" +
"    } catch (KbException ex) {\n" +
"      throw new ModelException (ex.getMessage(), ex);\n" +
"    }\n\n";
    typeCriteria = typeCriteria + "  return " + returnVariable + ";\n";
    

    /*  
     public static Map<HumanCyclist, List<java.util.Date>> cyclistPrimaryProject(Context ctx) throws Exception {
     Predicate p = new Predicate("cyclistPrimaryProject");

     Map<HumanCyclist, List<java.util.Date>> all_dates = new HashMap<HumanCyclist, List<java.util.Date>>();
    
     String qStr = "(and (isa ?X HumanCyclist) "
     + "(cyclistPrimaryProject ?X ?Y))";
    
     Query q = new Query(ctx.toString(), qStr);
     while(q.next()){
     HumanCyclist thingamabobTemp = new HumanCyclistImpl(ctx, q.get("?X", KbIndividual.class));
     Date dateTemp = q.get("?Y", Date.class);
     if (all_dates.containsKey(thingamabobTemp)) {
     all_dates.get(thingamabobTemp).add(dateTemp);
     } else {
     List<java.util.Date>listTemp = new ArrayList<java.util.Date>();
     listTemp.add(dateTemp);
     all_dates.put(thingamabobTemp, listTemp);
     }
     }
    
     return all_dates;
     }*/
    return typeCriteria;
  }
  
  
  public List<String> getConstraintLiteral() {
    return constraintLiterals;
  }

  public void setConstraintLiteral(List<String> constraintLiterals) {
    this.constraintLiterals = constraintLiterals;
  }

  public void addConstraintLiteral(String constraintLiteral) {
    this.constraintLiterals.add(constraintLiteral);
  }

  public List<ArgumentObj> getPredicateTypes() {
    return predicateTypes;
  }

  public void setPredicateTypes(List<ArgumentObj> predicateTypes) {
    this.predicateTypes = predicateTypes;
  }

  public void addPredicateTypes(ArgumentObj predicateType) {
    this.predicateTypes.add(predicateType);
  }

  @Override
  public void setCycName(String cycName) {
		this.cycName = cycName;
		String tName = cycName.replaceAll("\\W+", "") + "_type";
		this.setName(tName);
	}
}

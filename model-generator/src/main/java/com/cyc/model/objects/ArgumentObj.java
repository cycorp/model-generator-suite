package com.cyc.model.objects;

import com.cyc.kb.Context;
import com.cyc.kb.KbCollection;
import com.cyc.kb.KbCollectionFactory;
import com.cyc.kb.KbIndividual;
import com.cyc.kb.Relation;
import com.cyc.kb.Sentence;
import com.cyc.kb.exception.KbException;

public class ArgumentObj extends ModelObj {

  private String type;
  private String sObjectType;
  // TODO: Move it into a sub-class and use "name" of ModelObj in sub class to
  // refer to queryVarName
  // Used in CriteriaMethod to create a good ?X{VAR NAME} in Query construction
  private String queryVarName;
  // The interface that this ArgumentObj will accept
  // TODO: The InterfaceObj has to be aware of all the classes that implement it
  // the argument object, if its in a return position, it has to know the 
  // constructor of the class that implements this InterfaceObj.
  private InterfaceObj io;
  // Argument Modifier. 
  // Currently argument modifiers are applied for 
  private int argMod;
  
  // This method allows for casting the returned object either into an SObject or
  // creating as a new of of a Class
  public String getSObjectType() {
    return sObjectType;
  }

  public void setSObjectType(String sObjectType) {
    this.sObjectType = sObjectType;
  }

  public ArgumentObj() {
  }

  public ArgumentObj(InterfaceObj io, int cycArgPos, int argMod) throws KbException {
    this.setArgMod(argMod);
    this.setIo(io);
    this.setCycName(io.getCycName(), cycArgPos);
  }
  
  public ArgumentObj(InterfaceObj io, int cycArgPos) throws KbException {
    this(io, cycArgPos, 0);
  }
  
  @Deprecated
  public ArgumentObj(String cycName, int cycArgPos) throws KbException {
    this.setCycName(cycName, cycArgPos);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getQueryVarName() {
    return queryVarName;
  }

  public void setQueryVarName(String queryVarName) {
    this.queryVarName = queryVarName;
  }

  public InterfaceObj getIo() {
    return io;
  }

  public void setIo(InterfaceObj io) {
    this.io = io;
  }

  public int getArgMod() {
    return argMod;
  }

  public void setArgMod(int argMod) {
    this.argMod = argMod;
  }  
  
  public void setCycName(String cycName, int cycArgPos) throws KbException {
    super.setCycName(cycName);

    String argAppend = "";
    if (this.getArgMod() == 0) {

    } /*Variable Arity: Generates "..." */else if (this.getArgMod() == 1) {
      argAppend = "_var";
    } else if (this.getArgMod() == 2) {
      argAppend = "_list";
    } else if (this.getArgMod() == 3) {
      argAppend = "_set";
    }
      
    if (cycName.equals("Individual")) {
      this.setType(KbIndividual.class.getSimpleName());
      if (cycArgPos != -1) {
        super.setName("i_arg" + Integer.toString(cycArgPos) + argAppend);
      } else {
        super.setName("i_arg_ret" + argAppend);
      }
      this.setSObjectType(KbIndividual.class.getSimpleName());
      this.setQueryVarName("IA" + Integer.toString(cycArgPos));
    } else if (cycName.equals("Collection")) {
      this.setType(KbCollection.class.getSimpleName());
      if (cycArgPos != -1) {
        super.setName("c_arg" + Integer.toString(cycArgPos) + argAppend);
      } else {
        super.setName("c_arg_ret" + argAppend);
      }
      this.setSObjectType(KbCollection.class.getSimpleName());
      this.setQueryVarName("CA" + Integer.toString(cycArgPos));
    } else if (cycName.equals("Microtheory")) {
      this.setType(Context.class.getSimpleName());
      if (cycArgPos != -1) {
        super.setName("ctx_arg" + Integer.toString(cycArgPos) + argAppend);
      } else {
        super.setName("ctx_arg_ret" + argAppend);
      }
      this.setSObjectType(Context.class.getSimpleName());
      this.setQueryVarName("CTXA" + Integer.toString(cycArgPos));
    } else if (cycName.equals("Relation")) {
      this.setType(Relation.class.getSimpleName());
      if (cycArgPos != -1) {
        super.setName("rel_arg" + Integer.toString(cycArgPos) + argAppend);
      } else {
        super.setName("rel_arg_ret" + argAppend);
      }
      this.setSObjectType(Relation.class.getSimpleName());
      this.setQueryVarName("RELA" + Integer.toString(cycArgPos));
    } else if (cycName.equals("CycLSentence")) {
      this.setType(Sentence.class.getSimpleName());
      if (cycArgPos != -1) {
        super.setName("s_arg" + Integer.toString(cycArgPos) + argAppend);
      } else {
        super.setName("s_arg_ret" + argAppend);
      }
      this.setSObjectType(Sentence.class.getSimpleName());
      this.setQueryVarName("SA" + Integer.toString(cycArgPos));
    } else if (cycName.equals("Date")) {
      this.setType("java.util.Date");
      if (cycArgPos != -1) {
        super.setName("d_arg" + Integer.toString(cycArgPos) + argAppend);
      } else {
        super.setName("d_arg_ret" + argAppend);
      }
      this.setSObjectType("Date");
      this.setQueryVarName("DA" + Integer.toString(cycArgPos));
    } else if (KbCollectionFactory.get("CharacterString").isGeneralizationOf(KbCollectionFactory.get(cycName)) )
            //(cycName.equals("CharacterString") || cycName.equals("SubLString")) 
    {
      this.setType("java.lang.String");
      if (cycArgPos != -1) {
        super.setName("str_arg" + Integer.toString(cycArgPos) + argAppend);
      } else {
        super.setName("str_arg_ret" + argAppend);
      }
      this.setSObjectType("String");
      this.setQueryVarName("SA" + Integer.toString(cycArgPos));
    } //else if (cycName.equals("PositiveInteger")) { // new KbCollection(cycName).isInstanceOf("PositiveInteger")
    else if (KbCollectionFactory.get("Integer").isGeneralizationOf(KbCollectionFactory.get(cycName))
            // TODO: Hack!! NumericQuantity can be a range or a number. Handle that.
            || KbCollectionFactory.get("NumericInterval").isGeneralizationOf(KbCollectionFactory.get(cycName)) 
            ){
      this.setType("java.lang.Integer");
      if (cycArgPos != -1) {
        super.setName("i_arg" + Integer.toString(cycArgPos) + argAppend);
      } else {
        super.setName("i_arg_ret" + argAppend);
      }
      this.setSObjectType("Integer");
      this.setQueryVarName("IA" + Integer.toString(cycArgPos));
    }
    else if (cycName.equals("JavaObject") ) {

        this.setType("Object");
        if (cycArgPos != -1) {
            super.setName("o_arg" + Integer.toString(cycArgPos) + argAppend);
        } else {
            super.setName("o_arg_ret" + argAppend);
        }
        this.setSObjectType("Object");
        this.setQueryVarName("O" + Integer.toString(cycArgPos));
    }
    else {
      // Should allow non-ascii JAVA characters
      String argType = cycName.replaceAll("\\W+", "");
      this.setType(argType);
      /*
       * if (cycName.equals("KBCollection")){
       * this.setSObjectType("KbIndividual"); } else if
       * (cycName.equals("SecondOrderCollection")){
       * this.setSObjectType("KBCollection"); } else if
       * (cycName.equals("Predicate")){ this.setSObjectType("Predicate");
       * } else if (cycName.equals("Function")){
       * this.setSObjectType("Function"); } else if
       * (cycName.equals("Relation")){ this.setSObjectType("Relation"); }
       * else if (cycName.equals("Microtheory")){ // NOTE:The "Context"
       * instead of Microtheory this.setSObjectType("Context");
       }
       */
      //this.setSObjectType("KbIndividual");
      this.setSObjectType(this.getIo().getInstanceType());

      
        // Set to -1 for return values
      if (cycArgPos == -1) {
        String argName = argType.replaceAll("[a-z0-9_]+", "").toLowerCase() + "_arg_ret" + argAppend;
        super.setName(argName);
      } else {
        String argName = argType.replaceAll("[a-z0-9_]+", "").toLowerCase() + "_arg" + Integer.toString(cycArgPos) + argAppend;
        super.setName(argName);
      }
      
      
      this.setQueryVarName(argType.replaceAll("[a-z0-9_]+", "") + "A" + Integer.toString(cycArgPos));
    }
  }

  public boolean isSObject() {
    //TODO: Add Microtheory (should map to KbAPI Context class), Predicate, KbCollection, when tests for those are added.
    if (type.equals("KbCollection") 
            || type.equals("KbIndividual") 
            || type.equals("Context")
            || type.equals("Relation")
            || type.equals("Sentence") 
            || type.equals("java.util.Date") 
            || type.equals("java.lang.String") 
            || type.equals("java.lang.Integer")
//            || type.equals("java.util.List")
            || type.equals("Object")
            ) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return "ArgumentObj [type=" + type + ", getName()=" + getName()
            + ", getCycName()=" + getCycName() + "]";
  }

  @Override
  public int hashCode() {
    int hash = super.hashCode();
    hash = 79 * hash + (this.getType() != null ? this.getType().hashCode() : 0);
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
    final ArgumentObj other = (ArgumentObj) obj;
    if ((this.getType() == null) ? (other.getType() != null) : !this.getType().equals(other.getType())) {
      return false;
    }
    return true;
  }
  
  
}

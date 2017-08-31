package com.cyc.model.objects;

import com.cyc.base.annotation.CycObjectLibrary;
import com.cyc.base.annotation.CycTerm;
import com.cyc.kb.KbCollection;
import com.cyc.kb.KbCollectionFactory;
import com.cyc.kb.KbFunction;
import com.cyc.kb.KbFunctionFactory;
import com.cyc.kb.KbPredicate;
import com.cyc.kb.KbPredicateFactory;
import com.cyc.kb.exception.KbException;
import com.cyc.kb.exception.KbRuntimeException;


/**
 *
 * @author vijay
 */
@CycObjectLibrary(accessor="getInstance")
public class ModelConstants {
  
  private static ModelConstants instance;
  
  @CycTerm(cycl="#$SetOfTypeFn")
  public final KbFunction SetOfTypeFn = KbFunctionFactory.get("SetOfTypeFn");
  
  @CycTerm(cycl="#$argsIsa")
  public final KbPredicate argsIsa = KbPredicateFactory.get("argsIsa");
  
  @CycTerm(cycl="VariableArityFunction")
  public final KbCollection VarArityFunc = KbCollectionFactory.get("VariableArityFunction");
  
  @CycTerm(cycl="ListTypeByMemberType")
  public final KbCollection ListTypeByMemType = KbCollectionFactory.get("ListTypeByMemberType");
  
  @CycTerm(cycl="SetOrCollectionType")
  public final KbCollection SetOrColType = KbCollectionFactory.get("SetOrCollectionType");

    @CycTerm(cycl="Set-Mathematical")
    public final KbCollection SetMath = KbCollectionFactory.get("Set-Mathematical");

    @CycTerm(cycl="List")
    public final KbCollection List = KbCollectionFactory.get("List");

  private ModelConstants() throws KbException {
    super();
  }

  /**
   * This is not part of the KB API
   */ 

  public static ModelConstants getInstance() throws KbRuntimeException {
    try {
      if (instance == null) {
        instance = new ModelConstants();
      }
      return instance;
    } catch (KbException e) {
      throw new KbRuntimeException(
          "Once of the private final fields in com.cyc.library.model.ModelConstants could not be instantiated, can not proceed further.",
          e);
    }
  }
}

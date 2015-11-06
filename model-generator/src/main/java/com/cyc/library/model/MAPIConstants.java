/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cyc.library.model;

import com.cyc.base.annotation.CycObjectLibrary;
import com.cyc.base.annotation.CycTerm;
import com.cyc.kb.Context;
import com.cyc.kb.KBCollection;
import com.cyc.kb.KBFunction;
import com.cyc.kb.KBPredicate;
import com.cyc.kb.client.ContextImpl;
import com.cyc.kb.client.KBCollectionImpl;
import com.cyc.kb.client.KBFunctionImpl;
import com.cyc.kb.client.KBPredicateImpl;
import com.cyc.kb.exception.KBApiException;
import com.cyc.kb.exception.KBApiRuntimeException;


/**
 *
 * @author vijay
 */
@CycObjectLibrary(accessor="getInstance")
public class MAPIConstants {
  
  private static MAPIConstants instance;
  
  @CycTerm(cycl="#$SetOfTypeFn")
  public final KBFunction SetOfTypeFn = KBFunctionImpl.get("SetOfTypeFn");
  
  @CycTerm(cycl="#$argsIsa")
  public final KBPredicate argsIsa = KBPredicateImpl.get("argsIsa");
  
  @CycTerm(cycl="VariableArityFunction")
  public final KBCollection VarArityFunc = KBCollectionImpl.get("VariableArityFunction");
  
  @CycTerm(cycl="ListTypeByMemberType")
  public final KBCollection ListTypeByMemType = KBCollectionImpl.get("ListTypeByMemberType");
  
  @CycTerm(cycl="SetOrCollectionType")
  public final KBCollection SetOrColType = KBCollectionImpl.get("SetOrCollectionType");

    @CycTerm(cycl="Set-Mathematical")
    public final KBCollection SetMath = KBCollectionImpl.get("Set-Mathematical");

    @CycTerm(cycl="List")
    public final KBCollection List = KBCollectionImpl.get("List");

  private MAPIConstants() throws KBApiException {
    super();
  }

  /**
   * This is not part of the KB API
   */ 

  public static MAPIConstants getInstance() throws KBApiRuntimeException {
    try {
      if (instance == null) {
        instance = new MAPIConstants();
      }
      return instance;
    } catch (KBApiException e) {
      throw new KBApiRuntimeException(
          "Once of the private final fields in com.cyc.library.model.MAPIConstants could not be instantiated, can not proceed further.",
          e);
    }
  }
}

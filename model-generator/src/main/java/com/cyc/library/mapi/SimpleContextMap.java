package com.cyc.library.mapi;

import com.cyc.kb.Context;
import com.cyc.kb.Relation;
import com.cyc.kb.client.Constants;
import com.cyc.kb.client.ContextImpl;
import com.cyc.kb.config.KBAPIDefaultContext;
import com.cyc.kb.exception.KBApiException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nwinant
 */
public class SimpleContextMap extends KBAPIDefaultContext implements ContextMap {
  
  // Constructors
  
  public SimpleContextMap(Context collectionContext, Context defaultRelationContext, Map<Relation, Context> relationContexts) {
    super(Constants.uvMt(), Constants.inferencePSCMt());
    this.collectionContext = collectionContext;
    this.defaultRelationContext = defaultRelationContext;
    this.relationContexts = relationContexts;
  }
  
  public SimpleContextMap(Context collectionContext, Context defaultRelationContext) {
    this(collectionContext, defaultRelationContext, new HashMap<Relation, Context>());
  }
  
  public SimpleContextMap(String collectionContextString, String defaultRelationContextString) throws KBApiException {
    this(ContextImpl.get(collectionContextString), ContextImpl.get(defaultRelationContextString));
  }
  
  public SimpleContextMap(Context collectionContext) {
    this(collectionContext, null);
  }
  
  public SimpleContextMap(String collectionContextString) throws KBApiException {
    this(ContextImpl.get(collectionContextString));
  }
  
  
  // Static
  
  public static SimpleContextMap getDefaultContextMap() throws KBApiException {
    if (defaultContextMap == null){
      defaultContextMap = new SimpleContextMap(
            ContextImpl.get("UniversalVocabularyMt"), 
            ContextImpl.get("UniversalVocabularyMt"));
    }
    return defaultContextMap;
  }
  
  // Public

  @Override
  public Context getCollectionContext() {
    return this.collectionContext;
  }
  
  @Override
  public Context getRelationContext() {
    if (this.defaultRelationContext != null) {
      return this.defaultRelationContext;
    }
    return getCollectionContext();
  }
  
  public Map<Relation, Context> getRelationContexts() {
    return this.relationContexts;
  }
  
  @Override
  public Context getRelationContext(Relation relation) {
    if (getRelationContexts().get(relation) != null) {
      return getRelationContexts().get(relation);
    }
    return getRelationContext();
  }
  
  @Override
  public String toString() {
    return this.getCollectionContext().toString();
  }
  
  
  // Internal
  
  final private Context collectionContext;
  final private Context defaultRelationContext;
  final private Map<Relation, Context> relationContexts; 
  
  static private SimpleContextMap defaultContextMap = null;
}

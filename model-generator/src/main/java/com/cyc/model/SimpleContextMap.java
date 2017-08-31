package com.cyc.model;

import com.cyc.kb.Context;
import com.cyc.kb.ContextFactory;
import static com.cyc.kb.ContextFactory.INFERENCE_PSC;
import static com.cyc.kb.ContextFactory.UV_MT;
import com.cyc.kb.Relation;
import com.cyc.kb.client.config.KbDefaultContext;
import com.cyc.kb.exception.KbException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nwinant
 */
public class SimpleContextMap extends KbDefaultContext implements ContextMap {
  
  // Constructors
  
  public SimpleContextMap(Context collectionContext, Context defaultRelationContext, Map<Relation, Context> relationContexts) {
    super(UV_MT, INFERENCE_PSC);
    this.collectionContext = collectionContext;
    this.defaultRelationContext = defaultRelationContext;
    this.relationContexts = relationContexts;
  }
  
  public SimpleContextMap(Context collectionContext, Context defaultRelationContext) {
    this(collectionContext, defaultRelationContext, new HashMap<Relation, Context>());
  }
  
  public SimpleContextMap(String collectionContextString, String defaultRelationContextString) throws KbException {
    this(ContextFactory.get(collectionContextString), ContextFactory.get(defaultRelationContextString));
  }
  
  public SimpleContextMap(Context collectionContext) {
    this(collectionContext, null);
  }
  
  public SimpleContextMap(String collectionContextString) throws KbException {
    this(ContextFactory.get(collectionContextString));
  }
  
  
  // Static
  
  public static SimpleContextMap getDefaultContextMap() throws KbException {
    if (defaultContextMap == null){
      defaultContextMap = new SimpleContextMap(
            ContextFactory.get("UniversalVocabularyMt"), 
            ContextFactory.get("UniversalVocabularyMt"));
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

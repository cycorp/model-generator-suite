package com.cyc.model;

import com.cyc.kb.Context;
import com.cyc.kb.Relation;
import com.cyc.kb.DefaultContext;

/**
 *
 * @author nwinant
 */
public interface ContextMap extends DefaultContext {
  public Context getCollectionContext();
  public Context getRelationContext();
  public Context getRelationContext(Relation relation);
}

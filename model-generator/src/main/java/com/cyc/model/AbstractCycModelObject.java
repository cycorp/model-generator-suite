package com.cyc.model;

import com.cyc.model.exception.ModelException;

/**
 *
 * @author nwinant
 */
 public abstract class AbstractCycModelObject implements CycModelObject {
   
   // Constructors
   
   public AbstractCycModelObject(ContextMap contexts) throws ModelException {
     this.contexts = contexts;
     /*
     this.instance = individual;
     if (!individual.checkSuperType(contexts.getCollectionContext(), core)) {
       // only if its a new constant, which we don't have a way of knowing with current SAPI,
       // assert it as a HumanCyclist
       //assignSupertypes(core);
       this.instance.superTypes(contexts.getCollectionContext(), core);
       this.instance.superTypes(Context.get("UniversalVocabularyMt"), core);
     }
     */
   }
   
   
   // Public
   /*
   @Override
   public KbIndividual getInstance() {
     return this.instance;
   }
   
   @Override
   public String toString() {
     return getInstance().toString();
   }
   */
   
   public ContextMap getContexts() {
     return this.contexts;
   }
   
   
   // Protected
   
   /*
   protected void assignSupertypes(SCollection core) throws Exception {
     this.instance.superTypes(contexts.getCollectionContext(), core);
     this.instance.superTypes(Context.get("UniversalVocabularyMt"), core);
   }
   */
   
   
   // Internal
   
   // final private KbIndividual instance;
   protected ContextMap contexts;
}

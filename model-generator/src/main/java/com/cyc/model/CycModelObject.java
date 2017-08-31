package com.cyc.model;

import com.cyc.model.exception.ModelException;

/**
 *
 * @author nwinant
 */
public interface CycModelObject {
  //public KbIndividual getInstance();
  
  public void delete() throws ModelException;
}

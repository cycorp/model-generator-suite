package com.cyc.library.mapi;

import com.cyc.library.mapi.exceptions.ModelAPIException;

/**
 *
 * @author nwinant
 */
public interface CycModelObject {
  //public KBIndividual getInstance();
  
  public void delete() throws ModelAPIException;
}

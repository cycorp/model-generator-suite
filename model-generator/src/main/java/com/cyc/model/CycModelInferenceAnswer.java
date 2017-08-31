package com.cyc.model;

import com.cyc.base.exception.CycConnectionException;
import com.cyc.base.cycobject.CycVariable;
import com.cyc.baseclient.inference.DefaultResultSet;
import com.cyc.baseclient.inference.ResultSetInferenceAnswer;


/**
 * An InferenceAnswer implementation that returns bindings as Cyc Model objects
 * where possible.
 * @author baxter
 */
public abstract class CycModelInferenceAnswer extends ResultSetInferenceAnswer {

  public CycModelInferenceAnswer(DefaultResultSet resultSet, int answerId) {
    super(resultSet, answerId);
  }

  public CycModelInferenceAnswer(DefaultResultSet resultSet) {
    super(resultSet);
  }

  /**
   * Get the value to which the specified variable is bound.
   * The value will be returned as a Cyc Model object if possible,
   * otherwise it will be returned as in {@link ResultSetInferenceAnswer}.
   *
   * @param var
   * @return
   */
  // TODO: We have to throw a MAPI specific exception. Strictly comply.
  @Override
  public Object getBinding(CycVariable var) throws CycConnectionException {
    return mapifyIfPossible(super.getBinding(var));
  }
  

  protected abstract Object mapifyIfPossible(Object binding);
}

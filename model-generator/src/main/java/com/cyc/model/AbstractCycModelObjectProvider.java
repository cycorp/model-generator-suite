package com.cyc.model;

import com.cyc.model.exception.ModelException;
import com.cyc.model.exception.ModelRuntimeException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.cyc.base.exception.CycConnectionException;
import com.cyc.base.cycobject.CycVariable;
import com.cyc.base.inference.InferenceAnswer;
import com.cyc.baseclient.cycobject.CycVariableImpl;
import com.cyc.query.QueryResultSet;

/**
 *
 * @author nwinant
 */
abstract public class AbstractCycModelObjectProvider {
  
  protected CycVariable findBindingVariable(final InferenceAnswer answer) throws ModelException, IOException {
    
    try {
      Set<CycVariable> vars = answer.getBindings().keySet();
      if (vars.isEmpty()) {
        throw new ModelException("No bindings for InferenceAnswer; expected exactly 1.");
      }
      if (vars.size() > 1) {
        throw new ModelException("Too many bindings (" + vars.size() + ") for InferenceAnswer; expected exactly 1.");
      }
      return vars.iterator().next();
    } catch (CycConnectionException e) {
      throw new ModelRuntimeException(e.getMessage(), e);
    }

  }
  
  protected CycVariable findBindingVariable(final Collection<InferenceAnswer> answers) throws IOException, ModelException {
    if (!answers.isEmpty()) {
      return findBindingVariable(answers.iterator().next());
    }
    return null;
  }
  
  protected CycVariableImpl findBindingVariable(final QueryResultSet resultSet) throws ModelException {
    final List<String> colNames = resultSet.getColumnNames();
    if (colNames.isEmpty()) {
      throw new ModelException("No bindings for InferenceAnswer; expected exactly 1.");
    }
    if (colNames.size() > 1) {
      throw new ModelException("Too many bindings (" + colNames.size() + ") for InferenceResultSet; expected exactly 1.");
    }
    return new CycVariableImpl(colNames.get(0));
  }
}

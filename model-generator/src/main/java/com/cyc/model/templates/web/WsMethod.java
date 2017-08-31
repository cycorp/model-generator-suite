package com.cyc.model.templates.web;

import com.cyc.model.objects.ArgumentObj;
import com.cyc.model.objects.MethodObj;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nwinant
 */
public class WsMethod {
  
  public WsMethod(MethodObj obj) {
    this.obj = obj;
    this.args = new ArrayList<WSArg>();
    for (int i=0; i < obj.getArguments().size(); i++) {
      args.add(new WSArg(obj.getArguments().get(i), i));
    }
  }
  
  
  // Public
  
  public List<WSArg> getArguments() {
    return this.args;
  }
  
  public MethodObj getObj() {
    return this.obj;
  }
  
  public String getName() {
    return this.getObj().getName();
  }
  
  public String getPathName() {
    final String basename = this.getObj().getCycName();
    if (this.getObj().getConfigArgPos() != null) {
      return basename + "_arg" + this.getObj().getArgPos();
    }
    return basename;
  }
  
  public String getVisibility() {
    return this.getObj().getVisibility();
  }
  
  public String getfType() {
    return this.getObj().getfType().toString();
  }
  
  
  // Classes
  
  public static class WSArg {
    final private ArgumentObj obj;
    final private int pos;
    final private String name;
    
    public WSArg(ArgumentObj obj, int pos) {
      this.obj = obj;
      this.pos = pos;
      this.name = createName();
    }
    public ArgumentObj getObj() { return this.obj; }
        public String getName() { return this.name; }

    public String getType() {
      if ("java.util.Date".equals(obj.getType())) {
        return "com.cyc.library.restfulserviceutil.param.DateParam";
      }
      return obj.getType();
    }
    
    private String createName() {
      String[] tokens = getObj().getType().split("\\.");
      if (tokens.length > 0) {
        return camelCase(tokens[tokens.length - 1]);
      }
      return obj.getName();
    }
  }
  
  protected static String camelCase(String str) {
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }
  
  
  // Internal
  
  final private MethodObj obj;
  final private List<WSArg> args;
}

package com.cyc.library.model;

public class ModelObj {

  protected String name;
  protected String cycName;  // TODO: this should be a generic which extends KBObject
  protected String visibility;
  protected String cycComment;
  // Returns "static" or "" string
  protected String belongsToClass = "";

  // When generating Java code, please use getName. 
  // The valid character set of CycL includes "-" that is not a valid character
  // for Java names.
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public String getBelongsToClass() {
    return belongsToClass;
  }

  // This could just be a boolean.
  // Just choose to use a String, always set to "static" to assist Velocity
  // template. 
  // Perhaps giving this flexibility by avoiding hard-coding java vocabulary. 
  public void setBelongsToClass(String belongsToClass) {
    this.belongsToClass = belongsToClass;
  }
  
  // When generating Java code, please use getName. 
  // The valid character set of CycL includes "-" that is not a valid character
  // for Java names.
  public String getCycName() {
    return cycName;
  }

  // Derive the Java name (and set it) from the given CycL string.
  // This handles NATs also.
  public void setCycName(String cycName) {
    this.cycName = cycName;
    String tName = cycName.replaceAll("\\W+", "");
    this.setName(tName);
  }

  public String getCycComment() {
    return cycComment;
  }

  public void setCycComment(String cycComment) {
    this.cycComment = cycComment;
  }

  @Override
  public String toString() {
    return "ModelObj [name=" + name + ", cycName=" + cycName
            + ", visibility=" + visibility + "]";
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ModelObj other = (ModelObj) obj;
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    return true;
  }
  
  
}

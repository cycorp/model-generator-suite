package com.cyc.library.model;

import java.util.ArrayList;
import java.util.List;

public class PackageObj extends ModelObj {
	
	private List<InterfaceObj> interfaces = new ArrayList<InterfaceObj>();
	private List<ClassObj> classes = new ArrayList<ClassObj>();
	
	private String dirName;
	
	public PackageObj (String packageName){
		super.setName(packageName);
				
		String packageDir = packageName.replaceAll("\\.", "/");
		if (!packageDir.endsWith("/")) {packageDir = packageDir + "/";};
		this.setDirName(packageDir);
	}
	
	public List<InterfaceObj> getInterfaces() {
		return interfaces;
	}
	
	public void setInterfaces(List<InterfaceObj> interfaces) {
		this.interfaces = interfaces;
	}
	
	public void setInterfaces(InterfaceObj anInterface) {
		this.interfaces.add(anInterface);
	}
	
	public List<ClassObj> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassObj> classes) {
		this.classes = classes;
	}
	
	public void setClasses(ClassObj aClass) {
		this.classes.add(aClass);
	}

	
	public String getDirName() {
		return dirName;
	}
	
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	
	

}

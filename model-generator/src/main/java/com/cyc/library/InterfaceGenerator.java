package com.cyc.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cyc.library.model.ArgumentObj;
import com.cyc.library.model.InterfaceObj;
import com.cyc.library.model.MethodObj;
import com.cyc.kb.KBCollection;
import com.cyc.kb.KBPredicate;
import com.cyc.kb.client.*;


public class InterfaceGenerator {

	public static InterfaceObj generateInterface () throws Exception{
		
	  KBCollection c = KBCollectionImpl.get("Gizmo");
		
		List<InterfaceObj> iolist = new ArrayList<InterfaceObj>();
		
		InterfaceObj io = new InterfaceObj();
		io.setName("Gizmo");
		io.setVisibility("public");
		io.setPackageName("cycorp.gizmostatus");
/*
		MethodObj m0 = new MethodObj();
		m0.setVisibility("public");
		m0.setName("allSibling" + io.getName() + "s");
		m0.setArguments("String");
		m0.setReturnValue("List<" + io.getName() + ">");
		io.setMethods(m0);
*/
		MethodObj m1 = new MethodObj();
		m1.setVisibility("public");
		m1.setName("gizmoObservedToBeOperationalOnDate");
		m1.setReturnValue(new ArgumentObj("Date", -1));
		io.setMethods(m1);
		
		io.setMethods(generateMethodObjFromPredicate("gizmoObservedToBeOperationalOnDate", c));
		
		MethodObj m3 = new MethodObj();
		m3.setVisibility("public");
		m3.setName("gizmoObservedToBeBrokenOnDate");
		m3.setReturnValue(new ArgumentObj("Date", -1));
		io.setMethods(m3);
		
		io.setMethods(generateMethodObjFromPredicate("gizmoObservedToBeBrokenOnDate", c));
		
		iolist.add(io);
		
		return io;
	}
	
	public static MethodObj generateMethodObjFromPredicate(String predStr, KBCollection c) throws Exception{
		
		MethodObj m = new MethodObj();
		m.setVisibility("public");
		m.setName(predStr);
		
		KBPredicate p = KBPredicateImpl.get(predStr);
		int pArity = p.getArity();
        
		//int foundClassInPos = -1;
		for (int i=1; i<=pArity; i++){
			java.util.Collection<KBCollection> ac = p.getArgIsa(i, "UniversalVocabularyMt");
			
			Set<KBCollection> cset = new HashSet<KBCollection>();
			cset.addAll(c.getGeneralizations("UniversalVocabularyMt"));
			cset.add(c);
			
			System.out.println("Arg Pos: " + i + " Collections: " + ac);
			System.out.println("C generalizations: " + cset);
			KBCollection firstArgConstraint = ac.iterator().next();
			if (cset.contains(firstArgConstraint)){
				//foundClassInPos = i;
				m.setArgPos(i);
			} else {
				m.addArgument(new ArgumentObj(firstArgConstraint.toString(), i));

				/*
				if (ac.get(0).toString().equals("Date")){
					m.setArgumentTypes("java.util.Date");
				} else {
					m.setArgumentTypes(ac.get(0).toString());
				}*/
			}
		}
		
		//if (foundClassInPos == -1){
		if (m.getArgPos() == null){
			System.out.println("Could not find the location of " + c + " in predicate " + p);
		} else {
			System.out.println("Arguments: " + m.getArguments());
		}

		return m;
	}
	
}

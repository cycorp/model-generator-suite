package com.cyc.model.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cyc.model.objects.MethodObj.FuncType;
import com.cyc.kb.KbCollectionFactory;
import com.cyc.model.xml.XmlProjectLoaderTest;

public class ControllerObjTest {
	

	@Before
	public void setUp() throws Exception {
		System.out.println("Starting");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Stopping");
	}
	
	@Test
	public void test() {
		
		try {

			String workspace = XmlProjectLoaderTest.WORKSPACE;
			String project = "autogenThingamabob"; 
			ProjectObj aProject = new ProjectObj(workspace, project);
			
			String packageName = "com.cyc.library.autogenThingamabob";
			PackageObj aPackage = new PackageObj(packageName);
			
			aProject.setPackages(aPackage);
			
			//********* Interface 1
			String cycCollection1 = "HumanCyclist";
			InterfaceObj io1 = new InterfaceObj(cycCollection1);
			//io.setName(cycCollection1);
			io1.setVisibility("public");
			io1.setPackageName(packageName);
			
			
			io1.setMethods(new MethodObj("cyclistPrimaryProject", KbCollectionFactory.get(cycCollection1), FuncType.SET));
			io1.setMethods(new MethodObj("cyclistPrimaryProject", KbCollectionFactory.get(cycCollection1), FuncType.GET));
                        io1.setMethods(new CriteriaMethodObj("cyclistPrimaryProject", KbCollectionFactory.get(cycCollection1), FuncType.TYPE_GET_LIST));

			io1.setMethods(new MethodObj("groupsReviewer", KbCollectionFactory.get(cycCollection1), FuncType.SET));
			io1.setMethods(new MethodObj("groupsReviewer", KbCollectionFactory.get(cycCollection1), FuncType.GET));

			aPackage.setInterfaces(io1);
			
			ClassObj co1 = new ClassObj(io1);
			aPackage.setClasses(co1);

			//aProject.generateProject();

			System.out.println("Start Controller");
			ControllerObj con1 = new ControllerObj(io1);
			System.out.println(con1.toString());
			System.out.println("End Controller");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}

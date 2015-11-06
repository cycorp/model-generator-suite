package com.cyc.library;

import com.cyc.base.CycAccess;
import java.io.BufferedWriter;
import java.io.FileWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cyc.library.model.ClassObj;
import com.cyc.library.model.CriteriaMethodObj;
import com.cyc.library.model.InterfaceObj;
import com.cyc.library.model.MethodObj;
import com.cyc.library.model.MethodObj.FuncType;
import com.cyc.library.model.PackageObj;
import com.cyc.library.model.ProjectObj;
import com.cyc.kb.KBCollection;
import com.cyc.kb.client.*;

public class InterfaceGeneratorTest {

    CycAccess cyc = null;

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
            InterfaceObj io = InterfaceGenerator.generateInterface();
            System.out.println(io);

            ClassObj co = new ClassObj(io);
            //co.setPrimaryImpl(io);
            System.out.println(co.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
 @Test
    public void testQuick() {

   try {
   InterfaceObj io = new InterfaceObj("Event");
   
    io.setMethods(new MethodObj("subEvents", KBCollectionImpl.get("Event"), FuncType.GET, 1));

   } catch (Exception e) {
     e.printStackTrace();
   }

   
 }
    @Test
    public void testUserManagement() {
        try {

            String workspace = "/fastscratch/vijay/nb_workspace_jan_2013";
            if (!workspace.endsWith("/")) {
                workspace = workspace + "/";
            };

            String project = "autogenUM";
            if (!project.endsWith("/")) {
                project = project + "/";
            };

            String javadefaultdir = "src/main/java";
            if (!javadefaultdir.endsWith("/")) {
                javadefaultdir = javadefaultdir + "/";
            };

            String packageName = "com.cyc.library.autogenUM";

            String packageDir = packageName.replaceAll("\\.", "/");
            if (!packageDir.endsWith("/")) {
                packageDir = packageDir + "/";
            };

            String interfacesDir = workspace + project + javadefaultdir + packageDir;
            String classDir = interfacesDir + "cyc/";

            System.out.println("Interface dir: " + interfacesDir);
            System.out.println("Class dir: " + classDir);

            String interfaceName = "Cyc-BasedProject";

            InterfaceObj io = new InterfaceObj("autogenUM", packageName, interfaceName);

            //io.setName(interfaceName);
            io.setVisibility("public");
            //io.setPackageName(packageName);

            /*
             * MethodObj m1 = new MethodObj(); m1.setVisibility("public");
             * m1.setName("cyclistPrimaryProject");
             * m1.setReturnValue("java.util.Date"); io.setMethods(m1);
             */

            /*
             * io.setMethods(InterfaceGenerator.generateMethodObjFromPredicate("cyclistPrimaryProject",
             * KBCollection.get("Cyc-BasedProject")));
             *
             * BufferedWriter out = new BufferedWriter(new
             * FileWriter(interfacesDir + interfaceName + ".java"));
             * out.write(io.toString()); out.close();
             */
            System.out.println(io);

            ClassObj co = new ClassObj(io);

            io.setMethods(InterfaceGenerator.generateMethodObjFromPredicate("cyclistPrimaryProject", KBCollectionImpl.get("Cyc-BasedProject")));

            BufferedWriter out = new BufferedWriter(new FileWriter(classDir + interfaceName + "Impl.java"));
            out.write(co.toString());
            out.close();

            System.out.println(co);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testUserManagementHumanCyclist() {
        try {


            String workspace = "/fastscratch/vijay/nb_workspace_jan_2013";
            if (!workspace.endsWith("/")) {
                workspace = workspace + "/";
            };

            String project = "autogenUM";
            if (!project.endsWith("/")) {
                project = project + "/";
            };

            String javadefaultdir = "src/main/java";
            if (!javadefaultdir.endsWith("/")) {
                javadefaultdir = javadefaultdir + "/";
            };

            String packageName = "com.cyc.library.autogenUM";

            String packageDir = packageName.replaceAll("\\.", "/");
            if (!packageDir.endsWith("/")) {
                packageDir = packageDir + "/";
            };

            String interfacesDir = workspace + project + javadefaultdir + packageDir;
            String classDir = interfacesDir + "cyc/";

            System.out.println("Interface dir: " + interfacesDir);
            System.out.println("Class dir: " + classDir);

            String interfaceName = "HumanCyclist";

            InterfaceObj io = new InterfaceObj();

            io.setName(interfaceName);
            io.setVisibility("public");
            io.setPackageName("com.cyc.library.autogenUM");

            //io.setMethods(InterfaceGenerator.generateMethodObjFromPredicate("cyclistPrimaryProject", KBCollection.get("HumanCyclist")));

            io.setMethods(new MethodObj("cyclistPrimaryProject", KBCollectionImpl.get("HumanCyclist"), FuncType.SET));

            /*
             * BufferedWriter out = new BufferedWriter(new
             * FileWriter(interfacesDir + interfaceName + ".java"));
             * out.write(io.toString()); out.close();
             */

            /*
             * MethodObj m1 = new MethodObj(); m1.setVisibility("public");
             * m1.setName("cyclistPrimaryProject"); m1.setReturnValue(new
             * ArgumentObj("Date", -1)); io.setMethods(m1);
             */

            io.setMethods(new MethodObj("cyclistPrimaryProject", KBCollectionImpl.get("HumanCyclist"), FuncType.GET));


            io.setMethods(new MethodObj("groupsReviewer", KBCollectionImpl.get("HumanCyclist"), FuncType.SET));

            io.setMethods(new MethodObj("groupsReviewer", KBCollectionImpl.get("HumanCyclist"), FuncType.GET));




            //io.setMethods(InterfaceGenerator.generateMethodObjFromPredicate("cyclistPrimaryProject", KBCollection.get("HumanCyclist")));
            //io.setMethods(new MethodObj("cyclistPrimaryProject", KBCollection.get("HumanCyclist")));

            System.out.println(io);

            ClassObj co = new ClassObj(io);


            BufferedWriter out = new BufferedWriter(new FileWriter(classDir + interfaceName + "Impl.java"));
            out.write(co.toString());
            out.close();


            System.out.println(co.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testUserManagementCycBasedProject() {
        try {


            String workspace = "/fastscratch/vijay/stsworkspace";
            if (!workspace.endsWith("/")) {
                workspace = workspace + "/";
            };

            String project = "autogenUM";
            if (!project.endsWith("/")) {
                project = project + "/";
            };

            String javadefaultdir = "src/main/java";
            if (!javadefaultdir.endsWith("/")) {
                javadefaultdir = javadefaultdir + "/";
            };

            String packageName = "com.cyc.library.autogenUM";

            String packageDir = packageName.replaceAll("\\.", "/");
            if (!packageDir.endsWith("/")) {
                packageDir = packageDir + "/";
            };

            String interfacesDir = workspace + project + javadefaultdir + packageDir;
            String classDir = interfacesDir + "cyc/";

            System.out.println("Interface dir: " + interfacesDir);
            System.out.println("Class dir: " + classDir);

            String interfaceName = "CycBasedProject";

            InterfaceObj io = new InterfaceObj();

            io.setName(interfaceName);
            io.setVisibility("public");
            io.setPackageName("com.cyc.library.autogenUM");

            //io.setMethods(InterfaceGenerator.generateMethodObjFromPredicate("cyclistPrimaryProject", KBCollection.get("HumanCyclist")));

            io.setMethods(new MethodObj("cyclistPrimaryProject", KBCollectionImpl.get("Cyc-BasedProject"), FuncType.SET));

            /*
             * BufferedWriter out = new BufferedWriter(new
             * FileWriter(interfacesDir + interfaceName + ".java"));
             * out.write(io.toString()); out.close();
             */

            io.setMethods(new MethodObj("cyclistPrimaryProject", KBCollectionImpl.get("Cyc-BasedProject"), FuncType.GET));

            //io.setMethods(InterfaceGenerator.generateMethodObjFromPredicate("cyclistPrimaryProject", KBCollection.get("HumanCyclist")));
            //io.setMethods(new MethodObj("cyclistPrimaryProject", KBCollection.get("HumanCyclist")));

            System.out.println(io);

            ClassObj co = new ClassObj(io);
            BufferedWriter out = new BufferedWriter(new FileWriter(classDir + interfaceName + "Impl.java"));
            out.write(co.toString());
            out.close();

            System.out.println(co.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testUserManagementFullProject() {
        try {

            String workspace = "/fastscratch/vijay/nb_workspace_jan_2013";
            String project = "autogenUM";
            ProjectObj aProject = new ProjectObj(workspace, project);

            String packageName = "com.cyc.library.autogenUM";
            PackageObj aPackage = new PackageObj(packageName);

            aProject.setPackages(aPackage);

            //********* Interface 1
            String cycCollection1 = "HumanCyclist";
            InterfaceObj io1 = new InterfaceObj(cycCollection1);
            //io.setName(cycCollection1);
            io1.setVisibility("public");
            io1.setPackageName("com.cyc.library.autogenUM");


            io1.setMethods(new MethodObj("cyclistPrimaryProject", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("cyclistPrimaryProject", KBCollectionImpl.get(cycCollection1), FuncType.GET));
            io1.setMethods(new MethodObj("groupsReviewer", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("groupsReviewer", KBCollectionImpl.get(cycCollection1), FuncType.GET));

            aPackage.setInterfaces(io1);

            ClassObj co1 = new ClassObj(io1);
            aPackage.setClasses(co1);

            //****** Interface 2
            String cycCollection2 = "Cyc-BasedProject";
            InterfaceObj io2 = new InterfaceObj(cycCollection2);
            io2.setVisibility("public");
            io2.setPackageName("com.cyc.library.autogenUM");

            io2.setMethods(new MethodObj("cyclistPrimaryProject", KBCollectionImpl.get(cycCollection2), FuncType.SET));
            io2.setMethods(new MethodObj("cyclistPrimaryProject", KBCollectionImpl.get(cycCollection2), FuncType.GET));
            aPackage.setInterfaces(io2);

            ClassObj co2 = new ClassObj(io2);
            aPackage.setClasses(co2);

            //****** Interface 3
            String cycCollection3 = "CycorpSpecialInterestGroup";
            InterfaceObj io3 = new InterfaceObj(cycCollection3);
            io3.setVisibility("public");
            io3.setPackageName("com.cyc.library.autogenUM");

            io3.setMethods(new MethodObj("groupsReviewer", KBCollectionImpl.get(cycCollection3), FuncType.SET));
            io3.setMethods(new MethodObj("groupsReviewer", KBCollectionImpl.get(cycCollection3), FuncType.GET));
            aPackage.setInterfaces(io3);

            ClassObj co3 = new ClassObj(io3);
            aPackage.setClasses(co3);


            aProject.generateProject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testThingamabobFullProject() {
        try {

            String workspace = "/fastscratch/vijay/nb_workspace_jan_2013";
            String project = "Thingamabob_API2";
            ProjectObj aProject = new ProjectObj(workspace, project);

            String packageName = "com.cyc.api.thingamabob";
            PackageObj aPackage = new PackageObj(packageName);

            aProject.setPackages(aPackage);

            //********* Interface 1
            String cycCollection1 = "Gizmo";
            InterfaceObj io1 = new InterfaceObj(cycCollection1);
            //io.setName(cycCollection1);
            io1.setVisibility("public");
            io1.setPackageName(packageName);


            io1.setMethods(new MethodObj("gizmoObservedToBeOperationalOnDate", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("gizmoObservedToBeOperationalOnDate", KBCollectionImpl.get(cycCollection1), FuncType.GET));
            io1.setMethods(new CriteriaMethodObj("gizmoObservedToBeOperationalOnDate", KBCollectionImpl.get(cycCollection1), FuncType.TYPE_GET_LIST));

            io1.setMethods(new MethodObj("gizmoObservedToBeBrokenOnDate", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("gizmoObservedToBeBrokenOnDate", KBCollectionImpl.get(cycCollection1), FuncType.GET));

            aPackage.setInterfaces(io1);

            ClassObj co1 = new ClassObj(io1);
            aPackage.setClasses(co1);

            aProject.generateProject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testTimeIntervalProject() {
        try {

            String workspace = "/fastscratch/vijay/stsworkspace";
            String project = "autogenTimeInterval";
            ProjectObj aProject = new ProjectObj(workspace, project);

            String packageName = "com.cyc.library.TimeInterval";
            PackageObj aPackage = new PackageObj(packageName);

            aProject.setPackages(aPackage);

            //********* Interface 1
            String cycCollection1 = "TimeInterval";
            InterfaceObj io1 = new InterfaceObj(cycCollection1);
            //io.setName(cycCollection1);
            io1.setVisibility("public");
            io1.setPackageName(packageName);


            //io1.setMethods(new MethodObj("startingDate", KBCollection.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("startingDate", KBCollectionImpl.get(cycCollection1), FuncType.GET));
            //io1.setMethods(new MethodObj("endingDate", KBCollection.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("endingDate", KBCollectionImpl.get(cycCollection1), FuncType.GET));

            io1.setMethods(new MethodObj("mtTimeIndex", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("mtTimeIndex", KBCollectionImpl.get(cycCollection1), FuncType.GET));

            aPackage.setInterfaces(io1);

            ClassObj co1 = new ClassObj(io1);
            aPackage.setClasses(co1);

            //********* Interface 1
            String cycCollection2 = "TemporalMicrotheory";
            InterfaceObj io2 = new InterfaceObj(cycCollection2);
            //io.setName(cycCollection1);
            io2.setVisibility("public");
            io2.setPackageName(packageName);

            io2.setMethods(new MethodObj("mtTimeIndex", KBCollectionImpl.get(cycCollection2), FuncType.SET));
            io2.setMethods(new MethodObj("mtTimeIndex", KBCollectionImpl.get(cycCollection2), FuncType.GET));

            aPackage.setInterfaces(io2);

            ClassObj co2 = new ClassObj(io2);
            aPackage.setClasses(co2);

            aProject.generateProject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testSCGTestProject() {
        try {

            String workspace = "/fastscratch/vijay/stsworkspace";
            String project = "autogenTesting";
            ProjectObj aProject = new ProjectObj(workspace, project);

            String packageName = "com.cyc.library.autogenTesting";
            PackageObj aPackage = new PackageObj(packageName);

            aProject.setPackages(aPackage);

            //********* Interface 1
            String cycCollection1 = "KBContentTest-FullySpecified";
            InterfaceObj io1 = new InterfaceObj(cycCollection1);
            //io.setName(cycCollection1);
            io1.setVisibility("public");
            io1.setPackageName(packageName);


            io1.setMethods(new MethodObj("testSuiteTrackingSpreadsheetURL", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("testSuiteTrackingSpreadsheetURL", KBCollectionImpl.get(cycCollection1), FuncType.GET));
            //io1.setMethods(new MethodObj("endingDate", KBCollection.get(cycCollection1), FuncType.SET));
            //io1.setMethods(new MethodObj("endingDate", KBCollection.get(cycCollection1), FuncType.GET));

            //io1.setMethods(new MethodObj("mtTimeIndex", KBCollection.get(cycCollection1), FuncType.SET));
            //io1.setMethods(new MethodObj("mtTimeIndex", KBCollection.get(cycCollection1), FuncType.GET));

            aPackage.setInterfaces(io1);

            ClassObj co1 = new ClassObj(io1);
            aPackage.setClasses(co1);

            //********* Interface 1
			/*
             * String cycCollection2 = "TemporalMicrotheory"; InterfaceObj io2 =
             * new InterfaceObj(cycCollection2); //io.setName(cycCollection1);
             * io2.setVisibility("public"); io2.setPackageName(packageName);
             *
             * io2.setMethods(new MethodObj("mtTimeIndex", new
             * KBCollection(cycCollection2), FuncType.SET)); io2.setMethods(new
             * MethodObj("mtTimeIndex", KBCollection.get(cycCollection2),
             * FuncType.GET));
             *
             * aPackage.setInterfaces(io2);
             *
             * ClassObj co2 = new ClassObj(io2); aPackage.setClasses(co2);
             */

            aProject.generateProject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testExperimentLoop() {
        try {

            String workspace = "/fastscratch/vijay/stsworkspace";
            String project = "autogenTesting";
            ProjectObj aProject = new ProjectObj(workspace, project);

            String packageName = "com.cyc.library.autogenTesting";
            PackageObj aPackage = new PackageObj(packageName);

            aProject.setPackages(aPackage);

            //********* Interface 1
            String cycCollection1 = "KBContentTest-FullySpecified";
            InterfaceObj io1 = new InterfaceObj(cycCollection1);
            //io.setName(cycCollection1);
            io1.setVisibility("public");
            io1.setPackageName(packageName);


            io1.setMethods(new MethodObj("testSuiteTrackingSpreadsheetURL", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("testSuiteTrackingSpreadsheetURL", KBCollectionImpl.get(cycCollection1), FuncType.GET));
            //io1.setMethods(new MethodObj("endingDate", KBCollection.get(cycCollection1), FuncType.SET));
            //io1.setMethods(new MethodObj("endingDate", KBCollection.get(cycCollection1), FuncType.GET));

            //io1.setMethods(new MethodObj("mtTimeIndex", KBCollection.get(cycCollection1), FuncType.SET));
            //io1.setMethods(new MethodObj("mtTimeIndex", KBCollection.get(cycCollection1), FuncType.GET));

            aPackage.setInterfaces(io1);

            ClassObj co1 = new ClassObj(io1);
            aPackage.setClasses(co1);

            //********* Interface 1
			/*
             * String cycCollection2 = "TemporalMicrotheory"; InterfaceObj io2 =
             * new InterfaceObj(cycCollection2); //io.setName(cycCollection1);
             * io2.setVisibility("public"); io2.setPackageName(packageName);
             *
             * io2.setMethods(new MethodObj("mtTimeIndex", new
             * KBCollection(cycCollection2), FuncType.SET)); io2.setMethods(new
             * MethodObj("mtTimeIndex", KBCollection.get(cycCollection2),
             * FuncType.GET));
             *
             * aPackage.setInterfaces(io2);
             *
             * ClassObj co2 = new ClassObj(io2); aPackage.setClasses(co2);
             */

            aProject.generateProject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testSKSI() {
        try {

            String workspace = "/fastscratch/vijay/nb_workspace_jan_2013";
            String project = "SKSIModel";
            ProjectObj aProject = new ProjectObj(workspace, project);

            String packageName = "com.cyc.library.sksimodel";
            PackageObj aPackage = new PackageObj(packageName);

            aProject.setPackages(aPackage);

            //********* Interface 1
            String cycCollection1 = "StructuredKnowledgeSource";
            InterfaceObj io1 = new InterfaceObj(cycCollection1);
            io1.setVisibility("public");
            io1.setPackageName(packageName);


            io1.setMethods(new MethodObj("structuredKnowledgeSourceName", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("structuredKnowledgeSourceName", KBCollectionImpl.get(cycCollection1), FuncType.GET));

            io1.setMethods(new MethodObj("subProtocolForSKS", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("subProtocolForSKS", KBCollectionImpl.get(cycCollection1), FuncType.GET));

            io1.setMethods(new MethodObj("userNameForSKS", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("userNameForSKS", KBCollectionImpl.get(cycCollection1), FuncType.GET));

            io1.setMethods(new MethodObj("subKS-Direct", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("subKS-Direct", KBCollectionImpl.get(cycCollection1), FuncType.GET));


            aPackage.setInterfaces(io1);

            ClassObj co1 = new ClassObj(io1);
            aPackage.setClasses(co1);

            //********* Interface 1
			/*
             * String cycCollection2 = "TemporalMicrotheory"; InterfaceObj io2 =
             * new InterfaceObj(cycCollection2); //io.setName(cycCollection1);
             * io2.setVisibility("public"); io2.setPackageName(packageName);
             *
             * io2.setMethods(new MethodObj("mtTimeIndex", new
             * KBCollection(cycCollection2), FuncType.SET)); io2.setMethods(new
             * MethodObj("mtTimeIndex", KBCollection.get(cycCollection2),
             * FuncType.GET));
             *
             * aPackage.setInterfaces(io2);
             *
             * ClassObj co2 = new ClassObj(io2); aPackage.setClasses(co2);
             */

            aProject.generateProject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    @Test
    public void testEvent() {
        try {

            String workspace = "/fastscratch/vijay/nb_workspace_jan_2013";
            String project = "EventAPI";
            ProjectObj aProject = new ProjectObj(workspace, project);

            String packageName = "com.cyc.library.eventapi";
            PackageObj aPackage = new PackageObj(packageName);

            aProject.setPackages(aPackage);

            //********* Interface 1
            String cycCollection1 = "Event";
            InterfaceObj io1 = new InterfaceObj(cycCollection1);
            io1.setVisibility("public");
            io1.setPackageName(packageName);


            //io1.setMethods(new MethodObj("occursDuring", KBCollection.get(cycCollection1), FuncType.SET));
            //io1.setMethods(new MethodObj("occursDuring", KBCollection.get(cycCollection1), FuncType.GET));

            io1.setMethods(new MethodObj("dateOfEvent", KBCollectionImpl.get(cycCollection1), FuncType.SET));
            io1.setMethods(new MethodObj("dateOfEvent", KBCollectionImpl.get(cycCollection1), FuncType.GET));


            aPackage.setInterfaces(io1);

            ClassObj co1 = new ClassObj(io1);
            aPackage.setClasses(co1);

            //********* Interface 1
			/*
             * String cycCollection2 = "TemporalMicrotheory"; InterfaceObj io2 =
             * new InterfaceObj(cycCollection2); //io.setName(cycCollection1);
             * io2.setVisibility("public"); io2.setPackageName(packageName);
             *
             * io2.setMethods(new MethodObj("mtTimeIndex", new
             * KBCollection(cycCollection2), FuncType.SET)); io2.setMethods(new
             * MethodObj("mtTimeIndex", KBCollection.get(cycCollection2),
             * FuncType.GET));
             *
             * aPackage.setInterfaces(io2);
             *
             * ClassObj co2 = new ClassObj(io2); aPackage.setClasses(co2);
             */

            aProject.generateProject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    

    @Test
    public void testBrownBag() {
        try {

            String workspace = "/fastscratch/vijay/nb_workspace_jan_2013";
            String project = "BrownBagAPI";
            ProjectObj aProject = new ProjectObj(workspace, project);

            String packageName = "com.cyc.library.brownbagapi";
            PackageObj aPackage = new PackageObj(packageName);

            aProject.setPackages(aPackage);

            //********* Interface 1
            //String cycCollection1 = "SocialOccurrence";
            String cycCollection1 = "CycorpBrownBagLunch";
            InterfaceObj io1 = new InterfaceObj(cycCollection1);
            io1.setVisibility("public");
            io1.setPackageName(packageName);

            String cycCollection2 = "Person";
            InterfaceObj io2 = new InterfaceObj(cycCollection2);
            io2.setVisibility("public");
            io2.setPackageName(packageName);

            //io1.setMethods(new MethodObj("occursDuring", KBCollection.get(cycCollection1), FuncType.SET));
            //io1.setMethods(new MethodObj("occursDuring", KBCollection.get(cycCollection1), FuncType.GET));

            //io1.setMethods(new MethodObj("presenter", KBCollection.get(cycCollection1), FuncType.SET));
            //io1.setMethods(new MethodObj("presenter", KBCollection.get(cycCollection1), FuncType.GET));

            String cycCollection3 = "DescribingSomething";
            InterfaceObj io3 = new InterfaceObj(cycCollection3);
            io3.setVisibility("public");
            io3.setPackageName(packageName);

            //io3.setMethods(new MethodObj("textOfDescription", KBCollection.get(cycCollection3), FuncType.SET));
            //io3.setMethods(new MethodObj("textOfDescription", KBCollection.get(cycCollection3), FuncType.GET));

            //io3.setMethods(new MethodObj("describedObject", KBCollection.get(cycCollection3), FuncType.SET));
            //io3.setMethods(new MethodObj("describedObject", KBCollection.get(cycCollection3), FuncType.GET));

                        
            String cycCollection4 = "TakingAFoodOrder";
            InterfaceObj io4 = new InterfaceObj(cycCollection4);
            io4.setVisibility("public");
            io4.setPackageName(packageName);
            
            io4.setMethods(new MethodObj("ordersMenuItem", KBCollectionImpl.get(cycCollection4), FuncType.SET));
            io4.setMethods(new MethodObj("ordersMenuItem", KBCollectionImpl.get(cycCollection4), FuncType.GET));

 //           io4.setMethods(new MethodObj("menuItemOrdered", KBCollection.get(cycCollection4), FuncType.SET));
 //           io4.setMethods(new MethodObj("menuItemOrdered", KBCollection.get(cycCollection4), FuncType.GET));

            io4.setMethods(new MethodObj("menuOrderForEvent", KBCollectionImpl.get(cycCollection4), FuncType.SET));
            io4.setMethods(new MethodObj("menuOrderForEvent", KBCollectionImpl.get(cycCollection4), FuncType.GET));

            aPackage.setInterfaces(io1);
            aPackage.setInterfaces(io2);
            aPackage.setInterfaces(io3);
            aPackage.setInterfaces(io4);


            ClassObj co1 = new ClassObj(io1);
            aPackage.setClasses(co1);
            
            ClassObj co2 = new ClassObj(io2);
            aPackage.setClasses(co2);

            ClassObj co3 = new ClassObj(io3);
            aPackage.setClasses(co3);
            
            ClassObj co4 = new ClassObj(io4);
            aPackage.setClasses(co4);
            //********* Interface 1
			/*
             * String cycCollection2 = "TemporalMicrotheory"; InterfaceObj io2 =
             * new InterfaceObj(cycCollection2); //io.setName(cycCollection1);
             * io2.setVisibility("public"); io2.setPackageName(packageName);
             *
             * io2.setMethods(new MethodObj("mtTimeIndex", new
             * KBCollection(cycCollection2), FuncType.SET)); io2.setMethods(new
             * MethodObj("mtTimeIndex", KBCollection.get(cycCollection2),
             * FuncType.GET));
             *
             * aPackage.setInterfaces(io2);
             *
             * ClassObj co2 = new ClassObj(io2); aPackage.setClasses(co2);
             */

            aProject.generateProject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

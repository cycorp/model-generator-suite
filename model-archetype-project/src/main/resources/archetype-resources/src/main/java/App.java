package ${package};

import com.cyc.query.exception.QueryConstructionException;
import java.io.IOException;
import com.cyc.kb.exception.KBApiException;
import com.cyc.library.mapi.exceptions.ModelAPIException;
/*
import com.cyc.kb.client.SentenceImpl;
import com.cyc.kb.exception.KBObjectNotFoundException;
import com.cyc.library.mapi.ContextMap;
import com.cyc.query.Query;
import com.cyc.query.QueryFactory;
import java.util.List;

// Generated classes:
import ${package}.cycImpl.CycBasedProjectImpl;
import ${package}.cycImpl.HumanCyclistImpl;
import ${package}.iface.CycBasedProject;
import ${package}.iface.HumanCyclist;
import ${package}.provider.HumanCyclistProvider;
*/

/**
 * Basic usage example
 *
 */
public class App {
  
  public App() {
  }
  
  protected void runExample() throws ModelAPIException, KBApiException, QueryConstructionException, IOException, Exception {
    /*
    System.out.println("We're going to do some simple user management, ensuring that all users "
            + "(cyclists) in the KB have a primary project to work on. Let's begin...\n");
    
    ContextMap contexts = CycBasedProjectImpl.getDefaultContexts();
    System.out.println("Collection context: " + contexts.getCollectionContext());
    System.out.println("Relation context:   " + contexts.getRelationContext());
    
    CycBasedProject generalCycKE = new CycBasedProjectImpl("UniversalVocabularyMt", "GeneralCycKE");
    System.out.println("\nWe can retrieve individual projects directly: " + generalCycKE);
    
    System.out.println("\nOr we can retrieve all existing projects within the specified context:");
    for (CycBasedProject project : CycBasedProjectImpl.getAllObjects(contexts)) {
      System.out.println(" - " + project);
    }
    
    System.out.println("\nLet's try to retrieve a non-existent project...");
    try {
      CycBasedProject nonExistentProject = new CycBasedProjectImpl("UniversalVocabularyMt", "NonExistentProject");
      System.out.println("nonExistentProject: " + nonExistentProject);
    } catch (KBObjectNotFoundException ex) {
      System.out.println("Unsurprisingly, NonExistentProject doesn't exist.");
    }
    
    CycBasedProject tempProject = new CycBasedProjectImpl("TempProject_" + System.currentTimeMillis());
    System.out.println("\nNow we've created a new project: " + tempProject);
    
    System.out.println("\nWe can see that " + tempProject + " is now included amongst all existing projects:");
    for (CycBasedProject project : CycBasedProjectImpl.getAllObjects(contexts)) {
      System.out.println(" - " + project);
    }
    
    System.out.println("\nNow, let's look up all cyclists, and their primary projects:");
    final List<? extends HumanCyclist> cyclists = HumanCyclistImpl.getAllObjects(contexts);
    for (HumanCyclist cyclist : cyclists) {
      System.out.println(" - " + cyclist + " : " + cyclist.getCyclistPrimaryProject());
    }
    
    System.out.println("\nWe can retrieve cyclists (or projects, any Model representations) based on arbitrary queries.");
    System.out.println("Let's do a query for all cyclists without a primary project, and assign them to " + tempProject + ":");
    Query cyclistsWithoutPrimaryProjects = QueryFactory.getQuery(
            new SentenceImpl(
            "  (and \n"
            + "  (isa ?CYCLIST HumanCyclist) \n"
            + "  (unknownSentence \n"
            + "    (thereExists ?PROJECT \n"
            + "      (cyclistPrimaryProject ?CYCLIST ?PROJECT))))"), 
            contexts.getCollectionContext());
        
    final List<HumanCyclist> unassignedCyclists1 = HumanCyclistProvider.get().find( cyclistsWithoutPrimaryProjects);
    for (HumanCyclist cyclist : unassignedCyclists1) {
      System.out.println(" - " + cyclist + " : " + cyclist.getCyclistPrimaryProject());
      cyclist.setCyclistPrimaryProject(tempProject);
    }
    
    System.out.println("\nNow that we've set primary projects for those cyclists, let's try that query again...");
    try {
      final List<HumanCyclist> unassignedCyclists2 = HumanCyclistProvider.get().find(cyclistsWithoutPrimaryProjects);
      for (HumanCyclist cyclist : unassignedCyclists2) {
        System.out.println(" - " + cyclist + " : " + cyclist.getCyclistPrimaryProject());
      }
      System.out.println("\nNumber of cyclists without a primary project: " + unassignedCyclists2.size());
    } catch (ModelAPIException ex) {
      System.out.println("Absolutely no results found!");
    }
    
    System.out.println("\nLet's see that original list again:");
    for (HumanCyclist cyclist : cyclists) {
      System.out.println(" - " + cyclist + " : " + cyclist.getCyclistPrimaryProject());
    }
    
    System.out.println("\nNow, let's delete " + tempProject + "...");
    tempProject.delete();
    
    System.out.println("\nWe can see that it has been removed from all of the projects in the KB:");
    for (CycBasedProject project : CycBasedProjectImpl.getAllObjects(contexts)) {
      System.out.println(" - " + project);
    }
    
    System.out.println("\nAnd those cyclists no longer have it as their primary project:");
    for (HumanCyclist cyclist : cyclists) {
      System.out.println(" - " + cyclist + " : " + cyclist.getCyclistPrimaryProject());
    }
    
    System.out.println("\n... Example concluded!");
    */
  }
  
  public static void main(String[] args) {
    try {
      final App app = new App();
      app.runExample();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.exit(0);
    }
  }
}

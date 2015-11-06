package com.cyc.library.mapi;

import com.cyc.base.CycAccess;
import com.cyc.base.CycConnectionException;
import com.cyc.kb.Context;
import com.cyc.kb.KBPredicate;
import com.cyc.kb.client.*;
import com.cyc.kb.exception.KBApiException;
import java.io.IOException;
import java.net.UnknownHostException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class SimpleContextMapTest {
  
  public SimpleContextMapTest() {
  }
  
  @BeforeClass
  public static void setUpClass() throws CycConnectionException {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }
  
  
  // Tests
  
  @Test
  public void testCollectionContext() throws KBApiException {
    final Context collectionCtx = ContextImpl.get("UniversalVocabularyMt");
    final ContextMap map = new SimpleContextMap(collectionCtx);
    assertEquals(collectionCtx, map.getCollectionContext());
    assertEquals(collectionCtx, map.getRelationContext(KBPredicateImpl.get("gizmoObservedToBeOperationalOnDate")));
  }
  
  @Test
  public void testDefaultRelationContext() throws KBApiException {
    final Context collectionCtx = ContextImpl.get("UniversalVocabularyMt");
    final Context defaultPredCtx = ContextImpl.get("EverythingPSC");
    final ContextMap map = new SimpleContextMap(collectionCtx, defaultPredCtx);
    assertFalse(collectionCtx.equals(defaultPredCtx));
    assertEquals(collectionCtx, map.getCollectionContext());
    assertEquals(defaultPredCtx, map.getRelationContext(KBPredicateImpl.get("gizmoObservedToBeOperationalOnDate")));
  }
  
  @Test
  public void testRelationContexts() throws KBApiException {
    final Context collectionCtx = ContextImpl.get("UniversalVocabularyMt");
    final Context defaultPredCtx = ContextImpl.get("EverythingPSC");
    final Context Relation1Ctx = ContextImpl.get("ShellGulfOfMexicoAssetsCollectorMt");
    final KBPredicate predicate1 = KBPredicateImpl.get("gizmoObservedToBeOperationalOnDate");
    final KBPredicate predicate2 = KBPredicateImpl.get("gizmoObservedToBeBrokenOnDate");
    final ContextMap map = new SimpleContextMap(collectionCtx, defaultPredCtx);
    ((SimpleContextMap) map).getRelationContexts().put(predicate1, Relation1Ctx);
    assertFalse(collectionCtx.equals(defaultPredCtx));
    assertFalse(collectionCtx.equals(Relation1Ctx));
    assertFalse(defaultPredCtx.equals(Relation1Ctx));
    assertFalse(predicate1.equals(predicate2));
    assertEquals(collectionCtx, map.getCollectionContext());
    assertEquals(Relation1Ctx, map.getRelationContext(predicate1));
    assertEquals(defaultPredCtx, map.getRelationContext(predicate2));
  }
}

package com.cyc.model;

import com.cyc.model.SimpleContextMap;
import com.cyc.model.ContextMap;
import com.cyc.kb.Context;
import com.cyc.kb.ContextFactory;
import com.cyc.kb.KbPredicate;
import com.cyc.kb.KbPredicateFactory;
import com.cyc.kb.exception.KbException;
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
  public static void setUpClass() {
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
  public void testCollectionContext() throws KbException {
    final Context collectionCtx = ContextFactory.get("UniversalVocabularyMt");
    final ContextMap map = new SimpleContextMap(collectionCtx);
    assertEquals(collectionCtx, map.getCollectionContext());
    assertEquals(collectionCtx, map.getRelationContext(KbPredicateFactory.get("cyclistPrimaryProject")));
  }
  
  @Test
  public void testDefaultRelationContext() throws KbException {
    final Context collectionCtx = ContextFactory.get("UniversalVocabularyMt");
    final Context defaultPredCtx = ContextFactory.get("EverythingPSC");
    final ContextMap map = new SimpleContextMap(collectionCtx, defaultPredCtx);
    assertFalse(collectionCtx.equals(defaultPredCtx));
    assertEquals(collectionCtx, map.getCollectionContext());
    assertEquals(defaultPredCtx, map.getRelationContext(KbPredicateFactory.get("cyclistPrimaryProject")));
  }
  
  @Test
  public void testRelationContexts() throws KbException {
    final Context collectionCtx = ContextFactory.get("UniversalVocabularyMt");
    final Context defaultPredCtx = ContextFactory.get("EverythingPSC");
    final Context Relation1Ctx = ContextFactory.get("CyclistsMt");
    final KbPredicate predicate1 = KbPredicateFactory.get("cyclistPrimaryProject");
    final KbPredicate predicate2 = KbPredicateFactory.get("groupsReviewer");
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

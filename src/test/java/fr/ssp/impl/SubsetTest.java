package fr.ssp.impl;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubsetTest {
  private static SubsetV1 emptyV1;
  private static SubsetV2 emptyV2;
  private static SubsetV1 complexV1;
  private static SubsetV2 complexV2;
  private static final long VALUE1 = 2;
  private static final long VALUE2 = 3;
  private static final long VALUE3 = 5;

  @BeforeEach
  public void setUp() {
    // Création des ensembles vides
    emptyV1 = new SubsetV1();
    emptyV2 = new SubsetV2();

    // Création des ensembles complexes : (2 + 3) + 5 = 10
    complexV1 = new SubsetV1(new SubsetV1(new SubsetV1(VALUE1), VALUE2), VALUE3);
    complexV2 = new SubsetV2(new SubsetV2(new SubsetV2(VALUE1), VALUE2), VALUE3);
  }

  @Test
  public void testEmptySubsets() {
    assertEquals("Empty subsets should have same sum", emptyV1.getSum(), emptyV2.getSum());
    assertEquals("Empty subsets should have same cardinality", emptyV1.getCardinality(), emptyV2.getCardinality());
  }

  @Test
  public void testComplexSubsets() {
    assertEquals("Complex subsets should have same sum", complexV1.getSum(), complexV2.getSum());
    assertEquals("Complex subsets should have same cardinality", complexV1.getCardinality(),
        complexV2.getCardinality());
  }

  @Test
  public void testShow() {
    // Test avec ensemble vide
    assertTrue("Empty V1 should contain 'set'", emptyV1.show().contains("set"));
    assertTrue("Empty V2 should contain 'set'", emptyV2.show().contains("set"));

    // Test avec ensembles complexes
    String s1Show = complexV1.show();
    String s2Show = complexV2.show();

    assertTrue("V1 show should contain sum", s1Show.contains("10"));
    assertTrue("V2 show should contain sum", s2Show.contains("10"));

    assertTrue("V1 show should start with 'Subset('", s1Show.startsWith("Subset("));
    assertTrue("V2 show should start with 'Subset('", s2Show.startsWith("Subset("));
    assertTrue("V1 show should end with ')'", s1Show.endsWith(")"));
    assertTrue("V2 show should end with ')'", s2Show.endsWith(")"));
    assertTrue("V1 show should contain 'with sum'", s1Show.contains("with sum"));
    assertTrue("V2 show should contain 'with sum'", s2Show.contains("with sum"));
  }

  // Modifier les autres tests pour utiliser complexV1 et complexV2...
  @Test
  public void testEncapsulation() {
    SubsetV1 s1a = new SubsetV1(10);
    s1a.encapsulate(complexV1);

    SubsetV2 s2a = new SubsetV2(10);
    s2a.encapsulate(complexV2);

    assertEquals("Encapsulated subsets should have same sum", s1a.getSum(), s2a.getSum());
    assertEquals("Encapsulated subsets should have same cardinality", s1a.getCardinality(), s2a.getCardinality());

    //
    s1a.encapsulate(complexV1);
    s2a.encapsulate(complexV2);

    assertEquals("Encapsulated subsets should have same sum", s1a.getSum(), s2a.getSum());
    assertEquals("Encapsulated subsets should have same cardinality", s1a.getCardinality(), s2a.getCardinality());

    assertThrows(IllegalArgumentException.class, () -> {
      s1a.encapsulate(new SubsetV1(12));
    });
    assertThrows(IllegalArgumentException.class, () -> {
      s1a.encapsulate(null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      s2a.encapsulate(new SubsetV2(12));
    });
    assertThrows(IllegalArgumentException.class, () -> {
      s2a.encapsulate(null);
    });
  }

  @Test
  public void testEncapsulationEmptySubsets() {
    SubsetV1 targetV1 = new SubsetV1();
    targetV1.encapsulate(emptyV1);
    assertTrue("Target V1 should equal original empty V1 after encapsulation", targetV1.equals(emptyV1));

    SubsetV2 targetV2 = new SubsetV2();
    targetV2.encapsulate(emptyV2);
    assertTrue("Target V2 should equal original empty V2 after encapsulation", targetV2.equals(emptyV2));
  }

  @Test
  public void testEncapsulationComplexSubsets() {
    SubsetV1 targetV1 = new SubsetV1(10);
    targetV1.encapsulate(complexV1);
    assertTrue("Target V1 should contain complex V1 after encapsulation", targetV1.contains(complexV1));

    SubsetV2 targetV2 = new SubsetV2(10);
    targetV2.encapsulate(complexV2);
    assertTrue("Target V2 should contain complex V2 after encapsulation", targetV2.contains(complexV2));
  }

  @Test
  public void testEncapsulationIndependence() {
    SubsetV1 targetV1 = new SubsetV1(10);
    targetV1.encapsulate(complexV1);
    complexV1.encapsulate(new SubsetV1(10));
    assertTrue("Target V1 should equal modified original V1 after encapsulation", targetV1.equals(complexV1));

    SubsetV2 targetV2 = new SubsetV2(10);
    targetV2.encapsulate(complexV2);
    complexV2.encapsulate(new SubsetV2(10));
    assertTrue("Target V2 should equal modified original V2 after encapsulation", targetV2.equals(complexV2));
  }

  @Test
  public void testEncapsulationNestedSubsets() {
    SubsetV1 nestedV1 = new SubsetV1(new SubsetV1(new SubsetV1(VALUE1), VALUE2), VALUE3);
    SubsetV1 targetV1 = new SubsetV1(10);
    targetV1.encapsulate(nestedV1);
    assertTrue("Target V1 should contain nested V1 after encapsulation", targetV1.contains(nestedV1));

    SubsetV2 nestedV2 = new SubsetV2(new SubsetV2(new SubsetV2(VALUE1), VALUE2), VALUE3);
    SubsetV2 targetV2 = new SubsetV2(10);
    targetV2.encapsulate(nestedV2);
    assertTrue("Target V2 should contain nested V2 after encapsulation", targetV2.contains(nestedV2));
  }

  @Test
  public void testEqualsWithNestedSubsets() {
    // Sous-ensembles imbriqués identiques
    SubsetV1 nestedV1 = new SubsetV1(new SubsetV1(new SubsetV1(VALUE1), VALUE2), VALUE3);
    SubsetV2 nestedV2 = new SubsetV2(new SubsetV2(new SubsetV2(VALUE1), VALUE2), VALUE3);
    assertFalse("Nested V1 should equal Nested V2", nestedV1.equals(nestedV2));
    assertFalse("Nested V2 should equal Nested V1", nestedV2.equals(nestedV1));
  }

  @Test
  public void testEqualsWithDifferentStructures() {
    // Sous-ensembles de tailles différentes mais même somme
    SubsetV1 differentStructureV1 = new SubsetV1(new SubsetV1(new SubsetV1(4), 1), 5);
    SubsetV2 differentStructureV2 = new SubsetV2(new SubsetV2(new SubsetV2(4), 1), 5);
    assertFalse("Different structure V1 should not equal Complex V1", differentStructureV1.equals(complexV1));
    assertFalse("Different structure V2 should not equal Complex V2", differentStructureV2.equals(complexV2));
  }

  @Test
  public void testEqualsAfterEncapsulation() {
    // Encapsulation et comparaison
    SubsetV1 encapsulatedV1 = new SubsetV1(10);
    encapsulatedV1.encapsulate(complexV1);
    SubsetV2 encapsulatedV2 = new SubsetV2(10);
    encapsulatedV2.encapsulate(complexV2);
    assertFalse("Encapsulated V1 should not equal Complex V1", encapsulatedV1.equals(complexV1));
    assertFalse("Encapsulated V2 should not equal Complex V2", encapsulatedV2.equals(complexV2));
  }

  @Test
  public void testEqualsWithModifiedSubsets() {
    // Modification après création
    SubsetV1 modifiedV1 = new SubsetV1(new SubsetV1(new SubsetV1(VALUE1), VALUE2), VALUE3);
    SubsetV2 modifiedV2 = new SubsetV2(new SubsetV2(new SubsetV2(VALUE1), VALUE2), VALUE3);
    modifiedV1.encapsulate(new SubsetV1(10));
    modifiedV2.encapsulate(new SubsetV2(10));
    assertFalse("Modified V1 should not equal original Complex V1", modifiedV1.equals(complexV1));
    assertFalse("Modified V2 should not equal original Complex V2", modifiedV2.equals(complexV2));
  }

  @Test
  public void testCloningEmptySubsets() {
    SubsetV1 cloneEmptyV1 = new SubsetV1();
    cloneEmptyV1.clone(emptyV1);
    assertTrue("Cloned empty V1 should equal original empty V1", cloneEmptyV1.equals(emptyV1));

    SubsetV2 cloneEmptyV2 = new SubsetV2();
    cloneEmptyV2.clone(emptyV2);
    assertTrue("Cloned empty V2 should equal original empty V2", cloneEmptyV2.equals(emptyV2));
  }

  @Test
  public void testCloningComplexSubsets() {
    SubsetV1 cloneComplexV1 = new SubsetV1();
    cloneComplexV1.clone(complexV1);
    assertTrue("Cloned complex V1 should equal original complex V1", cloneComplexV1.equals(complexV1));
    assertNotSame("Cloned complex V1 should not be the same reference as original", cloneComplexV1, complexV1);

    SubsetV2 cloneComplexV2 = new SubsetV2();
    cloneComplexV2.clone(complexV2);
    assertTrue("Cloned complex V2 should equal original complex V2", cloneComplexV2.equals(complexV2));
    assertNotSame("Cloned complex V2 should not be the same reference as original", cloneComplexV2, complexV2);
  }

  @Test
  public void testCloneIndependence() {
    SubsetV1 cloneV1 = new SubsetV1();
    cloneV1.clone(complexV1);
    complexV1.encapsulate(new SubsetV1(10));
    assertFalse("Clone V1 should not equal modified original V1", cloneV1.equals(complexV1));

    SubsetV2 cloneV2 = new SubsetV2();
    cloneV2.clone(complexV2);
    complexV2.encapsulate(new SubsetV2(10));
    assertFalse("Clone V2 should not equal modified original V2", cloneV2.equals(complexV2));
  }

  @Test
  public void testCloningNestedSubsets() {
    SubsetV1 nestedV1 = new SubsetV1(new SubsetV1(new SubsetV1(VALUE1), VALUE2), VALUE3);
    SubsetV1 cloneNestedV1 = new SubsetV1();
    cloneNestedV1.clone(nestedV1);
    assertTrue("Cloned nested V1 should equal original nested V1", cloneNestedV1.equals(nestedV1));

    SubsetV2 nestedV2 = new SubsetV2(new SubsetV2(new SubsetV2(VALUE1), VALUE2), VALUE3);
    SubsetV2 cloneNestedV2 = new SubsetV2();
    cloneNestedV2.clone(nestedV2);
    assertTrue("Cloned nested V2 should equal original nested V2", cloneNestedV2.equals(nestedV2));
  }

  @Test
  public void testDynamicProgrammingSubsetCreation() {
    // Test de création de sous-ensembles avec des valeurs spécifiques
    SubsetV1 subsetV1 = new SubsetV1();
    subsetV1 = new SubsetV1(subsetV1, VALUE1);
    subsetV1 = new SubsetV1(subsetV1, VALUE2);
    assertEquals("SubsetV1 should have correct sum", VALUE1 + VALUE2, subsetV1.getSum());

    SubsetV2 subsetV2 = new SubsetV2();
    subsetV2 = new SubsetV2(subsetV2, VALUE1);
    subsetV2 = new SubsetV2(subsetV2, VALUE2);
    assertEquals("SubsetV2 should have correct sum", VALUE1 + VALUE2, subsetV2.getSum());
  }

  @Test
  public void testDynamicProgrammingEncapsulation() {
    // Test de l'encapsulation dans le contexte de la programmation dynamique
    SubsetV1 subsetV1 = new SubsetV1(VALUE1);
    SubsetV1 encapsulatedV1 = new SubsetV1(subsetV1, VALUE2);
    assertFalse("Encapsulated V1 should contain original subset", encapsulatedV1.contains(subsetV1));

    SubsetV2 subsetV2 = new SubsetV2(VALUE1);
    SubsetV2 encapsulatedV2 = new SubsetV2(subsetV2, VALUE2);
    assertFalse("Encapsulated V2 should contain original subset", encapsulatedV2.contains(subsetV2));
  }

  @Test
  public void testDynamicProgrammingEquality() {
    // Test de l'égalité après plusieurs opérations
    SubsetV1 subsetV1a = new SubsetV1(VALUE1);
    SubsetV1 subsetV1b = new SubsetV1(VALUE1);
    assertTrue("SubsetV1 instances with same values should be equal", subsetV1a.equals(subsetV1b));

    SubsetV2 subsetV2a = new SubsetV2(VALUE1);
    SubsetV2 subsetV2b = new SubsetV2(VALUE1);
    assertTrue("SubsetV2 instances with same values should be equal", subsetV2a.equals(subsetV2b));
  }

  @Test
  public void testDynamicProgrammingSatisfiesBounds() {
    // Test de la méthode satisfiesBounds
    SubsetV1 subsetV1 = new SubsetV1(VALUE1);
    assertTrue("SubsetV1 should satisfy bounds", subsetV1.satisfiesBounds(VALUE1 - 1, VALUE1 + 1));

    SubsetV2 subsetV2 = new SubsetV2(VALUE1);
    assertTrue("SubsetV2 should satisfy bounds", subsetV2.satisfiesBounds(VALUE1 - 1, VALUE1 + 1));
  }
}
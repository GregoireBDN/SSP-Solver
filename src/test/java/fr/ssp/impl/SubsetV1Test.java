package fr.ssp.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Set;

/**
 * Tests pour l'implémentation SubsetV1
 */
public class SubsetV1Test {

  @Test
  public void testBasicOperations() {
    SubsetV1 s1 = new SubsetV1();
    assertEquals(0, s1.getSum());
    assertEquals(1, s1.getCardinality());

    SubsetV1 s2 = new SubsetV1(5);
    assertEquals(5, s2.getSum());
    assertEquals(1, s2.getCardinality());

    SubsetV1 s3 = new SubsetV1(s2, 3);
    assertEquals(8, s3.getSum());
    assertEquals(1, s3.getCardinality());

    Set<Long> values = s3.getValues();
    assertEquals(2, values.size());
    assertTrue(values.contains(5L));
    assertTrue(values.contains(3L));
  }

  @Test
  public void testEncapsulate() {
    // Créer deux sous-ensembles avec la même somme mais des valeurs différentes
    SubsetV1 s1 = new SubsetV1(5);
    SubsetV1 s2 = new SubsetV1(3);
    SubsetV1 s3 = new SubsetV1(s2, 2); // s3 a une somme de 5

    s1.encapsulate(s3);
    assertEquals(5, s1.getSum());
    assertEquals(2, s1.getCardinality()); // Un ensemble {5} et un ensemble {3,2}
  }

  @Test
  public void testEquals() {
    SubsetV1 s1 = new SubsetV1(5);
    SubsetV1 s2 = new SubsetV1(5);
    SubsetV1 s3 = new SubsetV1(3);

    assertTrue(s1.equals(s2));
    assertFalse(s1.equals(s3));

    SubsetV1 s4 = new SubsetV1(s1, 3);
    SubsetV1 s5 = new SubsetV1(s2, 3);

    assertTrue(s4.equals(s5));
  }
}
package fr.ssp.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Set;

/**
 * Tests pour l'implémentation SubsetV2
 */
public class SubsetV2Test {

  @Test
  public void testBasicOperations() {
    SubsetV2 s1 = new SubsetV2();
    assertEquals(0, s1.getSum());
    assertEquals(0, s1.getCardinality());

    SubsetV2 s2 = new SubsetV2(5);
    assertEquals(5, s2.getSum());
    assertEquals(1, s2.getCardinality());

    SubsetV2 s3 = new SubsetV2(s2, 3);
    assertEquals(8, s3.getSum());
    assertEquals(2, s3.getCardinality());

    Set<Long> values = s3.getValues();
    assertEquals(2, values.size());
    assertTrue(values.contains(5L));
    assertTrue(values.contains(3L));
  }

  @Test
  public void testEncapsulate() {
    SubsetV2 s1 = new SubsetV2(5);
    SubsetV2 s2 = new SubsetV2(5);
    s1.encapsulate(s2);
    assertEquals(5, s1.getSum());
    assertEquals(1, s1.getCardinality()); // Pas de doublon

    // Créer un autre sous-ensemble avec la même somme
    SubsetV2 s3 = new SubsetV2(3);
    SubsetV2 s4 = new SubsetV2(s3, 2); // s4 a une somme de 5
    s1.encapsulate(s4);
    assertEquals(5, s1.getSum());
    assertEquals(2, s1.getCardinality()); // Ajout d'un nouveau sous-ensemble
  }

  @Test
  public void testNormalize() {
    // Créer un sous-ensemble avec des doublons potentiels
    SubsetV2 s1 = new SubsetV2(5);

    // Créer un autre sous-ensemble avec la même somme mais des valeurs différentes
    SubsetV2 s2 = new SubsetV2(2);
    SubsetV2 s4 = new SubsetV2(s2, 3); // s4 a une somme de 5

    // Ajouter manuellement s4 à s1 sans utiliser encapsulate
    s1.addSubset(s4);

    // Normaliser
    s1.normalize();

    // Vérifier que la cardinalité est correcte après normalisation
    assertEquals(2, s1.getCardinality()); // {5}, {2,3}
  }
}
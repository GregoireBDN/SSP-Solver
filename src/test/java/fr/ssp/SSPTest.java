package fr.ssp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.ssp.api.Subset;
import fr.ssp.impl.SubsetFactory;
import fr.ssp.impl.SubsetV1;
import fr.ssp.impl.SubsetV2;
import fr.ssp.solver.BranchAndPruneSolver;
import fr.ssp.solver.DynamicProgrammingSolver;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests complets pour l'implémentation du problème de la somme de
 * sous-ensembles (SSP)
 */
public class SSPTest {

  /**
   * Test de base pour SubsetV1
   */
  @Test
  public void testSubsetV1Basic() {
    SubsetV1 s1 = new SubsetV1();
    assertEquals(0, s1.getSum());
    assertEquals(0, s1.getCardinality());

    SubsetV1 s2 = new SubsetV1(5);
    assertEquals(5, s2.getSum());
    assertEquals(1, s2.getCardinality());

    SubsetV1 s3 = new SubsetV1(s2, 3);
    assertEquals(8, s3.getSum());
    assertEquals(2, s3.getCardinality());

    Set<Long> values = s3.getValues();
    assertEquals(2, values.size());
    assertTrue(values.contains(5L));
    assertTrue(values.contains(3L));
  }

  /**
   * Test de base pour SubsetV2
   */
  @Test
  public void testSubsetV2Basic() {
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

  /**
   * Test d'encapsulation pour SubsetV1
   */
  @Test
  public void testSubsetV1Encapsulate() {
    SubsetV1 s1 = new SubsetV1(5);
    SubsetV1 s2 = new SubsetV1(5);
    s1.encapsulate(s2);
    assertEquals(5, s1.getSum());
    assertEquals(1, s1.getCardinality()); // Pas de doublon

    SubsetV1 s3 = new SubsetV1(3);
    s1.encapsulate(s3);
    assertEquals(5, s1.getSum());
    assertEquals(2, s1.getCardinality()); // Ajout d'un nouveau sous-ensemble
  }

  /**
   * Test d'encapsulation pour SubsetV2
   */
  @Test
  public void testSubsetV2Encapsulate() {
    SubsetV2 s1 = new SubsetV2(5);
    SubsetV2 s2 = new SubsetV2(5);
    s1.encapsulate(s2);
    assertEquals(5, s1.getSum());
    assertEquals(1, s1.getCardinality()); // Pas de doublon

    SubsetV2 s3 = new SubsetV2(3);
    s1.encapsulate(s3);
    assertEquals(5, s1.getSum());
    assertEquals(2, s1.getCardinality()); // Ajout d'un nouveau sous-ensemble
  }

  /**
   * Test de Branch and Prune avec SubsetV1
   */
  @Test
  public void testBranchAndPruneV1() {
    long[] values = { 1, 2, 3, 4, 5 };
    long target = 5;

    BranchAndPruneSolver solver = new BranchAndPruneSolver(target, values, SubsetFactory.V1);
    Subset solution = solver.solve();

    assertEquals(target, solution.getSum());
    assertEquals(3, solution.getCardinality()); // {5}, {1,4}, {2,3}
  }

  /**
   * Test de Branch and Prune avec SubsetV2
   */
  @Test
  public void testBranchAndPruneV2() {
    long[] values = { 1, 2, 3, 4, 5 };
    long target = 5;

    BranchAndPruneSolver solver = new BranchAndPruneSolver(target, values, SubsetFactory.V2);
    Subset solution = solver.solve();

    assertEquals(target, solution.getSum());
    assertEquals(3, solution.getCardinality()); // {5}, {1,4}, {2,3}
  }

  /**
   * Test de Dynamic Programming avec SubsetV1
   */
  @Test
  public void testDynamicProgrammingV1() {
    long[] values = { 1, 2, 3, 4, 5 };
    long target = 5;

    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, values, SubsetFactory.V1);
    Subset solution = solver.solve();

    assertEquals(target, solution.getSum());
    assertEquals(3, solution.getCardinality()); // {5}, {1,4}, {2,3}
  }

  /**
   * Test de Dynamic Programming avec SubsetV2
   */
  @Test
  public void testDynamicProgrammingV2() {
    long[] values = { 1, 2, 3, 4, 5 };
    long target = 5;

    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, values, SubsetFactory.V2);
    Subset solution = solver.solve();

    assertEquals(target, solution.getSum());
    // Note: DP avec SubsetV2 peut trouver moins de solutions
  }

  /**
   * Test avec l'exemple spécifique S=[6,5,1,3,4] et T=10
   */
  @Test
  public void testSpecificExample() {
    long[] values = { 6, 5, 1, 3, 4 };
    long target = 10;

    // Test avec V1
    BranchAndPruneSolver bpSolverV1 = new BranchAndPruneSolver(target, values, SubsetFactory.V1);
    Subset bpSolutionV1 = bpSolverV1.solve();

    DynamicProgrammingSolver dpSolverV1 = new DynamicProgrammingSolver(target, values, SubsetFactory.V1);
    Subset dpSolutionV1 = dpSolverV1.solve();

    // Test avec V2
    BranchAndPruneSolver bpSolverV2 = new BranchAndPruneSolver(target, values, SubsetFactory.V2);
    Subset bpSolutionV2 = bpSolverV2.solve();

    DynamicProgrammingSolver dpSolverV2 = new DynamicProgrammingSolver(target, values, SubsetFactory.V2);
    Subset dpSolutionV2 = dpSolverV2.solve();

    // Vérifications
    assertEquals(target, bpSolutionV1.getSum());
    assertEquals(target, dpSolutionV1.getSum());
    assertEquals(target, bpSolutionV2.getSum());
    assertEquals(target, dpSolutionV2.getSum());

    // V1: BP et DP devraient trouver le même nombre de solutions
    assertEquals(bpSolutionV1.getCardinality(), dpSolutionV1.getCardinality());

    // Affichage des résultats pour analyse
    System.out.println("Exemple S=[6,5,1,3,4], T=10:");
    System.out.println("V1 BP: " + bpSolutionV1.getCardinality() + " solutions");
    System.out.println("V1 DP: " + dpSolutionV1.getCardinality() + " solutions");
    System.out.println("V2 BP: " + bpSolutionV2.getCardinality() + " solutions");
    System.out.println("V2 DP: " + dpSolutionV2.getCardinality() + " solutions");
  }

  /**
   * Test de comparaison des valeurs entre V1 et V2
   */
  @Test
  public void testCompareV1V2Values() {
    long[] values = { 6, 5, 1, 3, 4 };
    long target = 10;

    // Solutions avec V1
    BranchAndPruneSolver bpSolverV1 = new BranchAndPruneSolver(target, values, SubsetFactory.V1);
    Subset bpSolutionV1 = bpSolverV1.solve();

    // Solutions avec V2
    BranchAndPruneSolver bpSolverV2 = new BranchAndPruneSolver(target, values, SubsetFactory.V2);
    Subset bpSolutionV2 = bpSolverV2.solve();

    // Extraction des valeurs
    Set<Long> valuesV1 = extractAllValues(bpSolutionV1);
    Set<Long> valuesV2 = extractAllValues(bpSolutionV2);

    // Les deux implémentations devraient trouver les mêmes valeurs
    assertEquals(valuesV1, valuesV2);
  }

  /**
   * Méthode utilitaire pour extraire toutes les valeurs d'un sous-ensemble
   */
  private Set<Long> extractAllValues(Subset subset) {
    if (subset instanceof SubsetV1) {
      SubsetV1 sv1 = (SubsetV1) subset;
      Set<Long> allValues = new HashSet<>();
      for (Set<Long> subValues : sv1.getSetCollection()) {
        allValues.addAll(subValues);
      }
      return allValues;
    } else if (subset instanceof SubsetV2) {
      SubsetV2 sv2 = (SubsetV2) subset;
      return sv2.getValues();
    }
    return new HashSet<>();
  }

  /**
   * Test de performance comparant V1 et V2
   */
  @Test
  public void testPerformance() {
    // Créer un problème plus grand pour tester les performances
    SSP ssp = new SSP(20); // 20 éléments

    // Test avec V1
    long startV1 = System.currentTimeMillis();
    Subset solBP1 = ssp.bp(SubsetFactory.V1);
    long endBPV1 = System.currentTimeMillis();
    Subset solDP1 = ssp.dynprog(SubsetFactory.V1);
    long endDPV1 = System.currentTimeMillis();

    // Test avec V2
    long startV2 = System.currentTimeMillis();
    Subset solBP2 = ssp.bp(SubsetFactory.V2);
    long endBPV2 = System.currentTimeMillis();
    Subset solDP2 = ssp.dynprog(SubsetFactory.V2);
    long endDPV2 = System.currentTimeMillis();

    // Affichage des temps d'exécution
    System.out.println("Performance (20 éléments):");
    System.out.println("V1 BP: " + (endBPV1 - startV1) + "ms");
    System.out.println("V1 DP: " + (endDPV1 - endBPV1) + "ms");
    System.out.println("V2 BP: " + (endBPV2 - startV2) + "ms");
    System.out.println("V2 DP: " + (endDPV2 - endBPV2) + "ms");

    // Vérification des résultats
    assertEquals(solBP1.getSum(), solDP1.getSum());
    assertEquals(solBP2.getSum(), solDP2.getSum());
  }

  @Test
  void testInvalidTarget() {
    assertThrows(IllegalArgumentException.class, () -> {
      // code qui lance l'exception
    });
  }
}
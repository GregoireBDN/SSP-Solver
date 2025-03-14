package fr.ssp.solver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import fr.ssp.api.Subset;
import fr.ssp.impl.SubsetFactory;

/**
 * Tests pour l'algorithme Dynamic Programming
 */
public class DynamicProgrammingSolverTest {

  @Test
  public void testWithSubsetV1() {
    long[] values = { 1, 2, 3, 4, 5 };
    long target = 5;

    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, values, SubsetFactory.V1);
    Subset solution = solver.solve();

    assertEquals(target, solution.getSum());
    assertEquals(3, solution.getCardinality()); // {5}, {1,4}, {2,3}
  }

  @Test
  public void testWithSubsetV2() {
    long[] values = { 1, 2, 3, 4, 5 };
    long target = 5;

    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, values, SubsetFactory.V2);
    Subset solution = solver.solve();

    assertEquals(target, solution.getSum());
    // Note: DP avec SubsetV2 peut trouver moins de solutions
  }

  @Test
  public void testSpecificExample() {
    long[] values = { 6, 5, 1, 3, 4 };
    long target = 10;

    DynamicProgrammingSolver solverV1 = new DynamicProgrammingSolver(target, values, SubsetFactory.V1);
    Subset solutionV1 = solverV1.solve();

    DynamicProgrammingSolver solverV2 = new DynamicProgrammingSolver(target, values, SubsetFactory.V2);
    Subset solutionV2 = solverV2.solve();

    assertEquals(target, solutionV1.getSum());
    assertEquals(target, solutionV2.getSum());

    // Affichage pour analyse
    System.out.println("DP - V1: " + solutionV1.getCardinality() + " solutions");
    System.out.println("DP - V2: " + solutionV2.getCardinality() + " solutions");
  }
}
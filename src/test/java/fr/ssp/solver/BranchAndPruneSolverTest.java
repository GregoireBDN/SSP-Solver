package fr.ssp.solver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import fr.ssp.api.Subset;
import fr.ssp.impl.SubsetFactory;

/**
 * Tests pour l'algorithme Branch and Prune
 */
public class BranchAndPruneSolverTest {

  @Test
  public void testWithSubsetV1() {
    long[] values = { 1, 2, 3, 4, 5 };
    long target = 5;

    BranchAndPruneSolver solver = new BranchAndPruneSolver(target, values, SubsetFactory.V1);
    Subset solution = solver.solve();

    assertEquals(target, solution.getSum());
    assertEquals(3, solution.getCardinality()); // {5}, {1,4}, {2,3}
  }

  @Test
  public void testWithSubsetV2() {
    long[] values = { 1, 2, 3, 4, 5 };
    long target = 5;

    BranchAndPruneSolver solver = new BranchAndPruneSolver(target, values, SubsetFactory.V2);
    Subset solution = solver.solve();

    assertEquals(target, solution.getSum());
    assertEquals(3, solution.getCardinality()); // {5}, {1,4}, {2,3}
  }

  @Test
  public void testSpecificExample() {
    long[] values = { 6, 5, 1, 3, 4 };
    long target = 10;

    BranchAndPruneSolver solverV1 = new BranchAndPruneSolver(target, values, SubsetFactory.V1);
    Subset solutionV1 = solverV1.solve();

    BranchAndPruneSolver solverV2 = new BranchAndPruneSolver(target, values, SubsetFactory.V2);
    Subset solutionV2 = solverV2.solve();

    assertEquals(target, solutionV1.getSum());
    assertEquals(target, solutionV2.getSum());

    // Après normalisation, les deux implémentations devraient trouver le même
    // nombre de solutions
    assertEquals(solutionV1.getCardinality(), solutionV2.getCardinality());
  }
}
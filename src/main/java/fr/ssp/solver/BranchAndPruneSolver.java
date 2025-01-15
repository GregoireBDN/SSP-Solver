package fr.ssp.solver;

import fr.ssp.api.Subset;
import fr.ssp.impl.SubsetFactory;

public class BranchAndPruneSolver extends SSPSolver {

  public BranchAndPruneSolver(long target, long[] original, SubsetFactory factory) {
    super(target, original, factory);
  }

  @Override
  public Subset solve() {
    Subset solutions = createSubset();
    long total = totalSum();
    bpRec(0, createSubset(), total, solutions);
    return solutions;
  }

  private void bpRec(int i, Subset partial, long total, Subset solutions) {
    if (!partial.satisfiesBounds(target - total, target)) {
      return;
    }

    if (partial.getSum() == target) {
      if (solutions.getSum() == 0) {
        solutions.clone(partial);
      } else {
        solutions.encapsulate(partial);
      }
      return;
    }

    if (i == original.length) {
      return;
    }

    total -= original[i];
    bpRec(i + 1, partial, total, solutions);
    bpRec(i + 1, createSubset(partial, original[i]), total, solutions);
  }
}
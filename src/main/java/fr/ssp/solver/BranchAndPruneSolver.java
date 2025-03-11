package fr.ssp.solver;

import fr.ssp.api.Subset;
import fr.ssp.impl.SubsetFactory;
import fr.ssp.impl.SubsetV2;

public class BranchAndPruneSolver extends SSPSolver {

  public BranchAndPruneSolver(long target, long[] original, SubsetFactory factory) {
    super(target, original, factory);
  }

  @Override
  public Subset solve() {
    Subset solutions = createSubset();
    long total = totalSum();

    bpRec(0, createSubset(), total, solutions);

    // Normaliser le résultat si c'est une instance de SubsetV2
    if (solutions instanceof SubsetV2) {
      ((SubsetV2) solutions).normalize();
    }

    return solutions;
  }

  private void bpRec(int i, Subset partial, long total, Subset solutions) {
    System.err
        .println("bpRec index=" + i + ", partial.sum=" + partial.getSum() + ", total=" + total + ", target=" + target);

    if (!partial.satisfiesBounds(target - total, target)) {
      System.err.println("  Élagué par satisfiesBounds");
      return;
    }

    if (partial.getSum() == target) {
      System.err.println("  Solution trouvée! Somme=" + partial.getSum());
      if (solutions.getSum() == 0) {
        solutions.clone(partial);
      } else {
        solutions.encapsulate(partial);
      }
      return;
    }

    if (i == original.length) {
      System.err.println("  Fin du tableau atteinte");
      return;
    }

    System.err.println("  Exploration sans valeur " + original[i]);
    total -= original[i];
    bpRec(i + 1, partial, total, solutions);

    System.err.println("  Exploration avec valeur " + original[i]);
    bpRec(i + 1, createSubset(partial, original[i]), total, solutions);
  }

}
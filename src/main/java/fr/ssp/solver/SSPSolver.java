package fr.ssp.solver;

import fr.ssp.api.Subset;
import fr.ssp.impl.SubsetFactory;

public abstract class SSPSolver {
  protected long target;
  protected long[] original;
  protected final SubsetFactory factory;

  public SSPSolver(long target, long[] original, SubsetFactory factory) {
    this.target = target;
    this.original = original.clone();
    this.factory = factory;
  }

  public abstract Subset solve();

  protected long totalSum() {
    long sum = 0;
    for (long value : original) {
      sum += value;
    }
    return sum;
  }

  /**
   * Create a new empty subset
   * 
   * @return a new empty subset
   */
  protected Subset createSubset() {
    return factory.createSubset();
  }

  protected Subset createSubset(Subset other, long value) {
    return factory.createSubset(other, value);
  }

  protected Subset addValue(Subset subset, long value) {
    return factory.createSubset(subset, value);
  }
}
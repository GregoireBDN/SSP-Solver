package fr.ssp.solver;

import fr.ssp.api.Subset;
import fr.ssp.impl.SubsetFactory;
import java.util.*;

public class DynamicProgrammingSolver extends SSPSolver {

  public DynamicProgrammingSolver(long target, long[] original, SubsetFactory factory) {
    super(target, original, factory);
  }

  @Override
  public Subset solve() {
    Map<Long, Subset> Sums = new HashMap<>();
    Sums.put(0L, createSubset());

    long total = totalSum();

    for (int i = 0; i < original.length; i++) {
      List<Subset> newSets = new ArrayList<>();
      total -= original[i];

      for (long sum : Sums.keySet()) {
        Subset sub = Sums.get(sum);
        Subset newSet = createSubset(sub, original[i]);
        newSets.add(newSet);
      }

      for (Subset sub : newSets) {
        long sum = sub.getSum();
        if (Sums.containsKey(sum)) {
          Sums.get(sum).encapsulate(sub);
        } else if (sub.satisfiesBounds(target - total, target)) {
          Sums.put(sum, sub);
        }
      }
    }

    return Sums.get(target);
  }
}
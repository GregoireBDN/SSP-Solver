package fr.ssp.impl;

import fr.ssp.api.Subset;

public enum SubsetFactory {
  V1 {
    @Override
    public Subset createSubset() {
      return new SubsetV1();
    }

    @Override
    public Subset createSubset(long value) {
      return new SubsetV1(value);
    }

    @Override
    public Subset createSubset(Subset other, long value) {
      if (!(other instanceof SubsetV1)) {
        throw new IllegalArgumentException("Expected SubsetV1 instance");
      }
      return new SubsetV1((SubsetV1) other, value);
    }
  },
  V2 {
    @Override
    public Subset createSubset() {
      return new SubsetV2();
    }

    @Override
    public Subset createSubset(long value) {
      return new SubsetV2(value);
    }

    @Override
    public Subset createSubset(Subset other, long value) {
      if (!(other instanceof SubsetV2)) {
        throw new IllegalArgumentException("Expected SubsetV2 instance");
      }
      return new SubsetV2((SubsetV2) other, value);
    }
  };

  public abstract Subset createSubset();

  public abstract Subset createSubset(long value);

  public abstract Subset createSubset(Subset other, long value);
}

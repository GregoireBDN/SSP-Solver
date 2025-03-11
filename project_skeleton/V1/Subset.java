
/* Subset in Java
 *
 * for an object-oriented approach to the SSP
 *
 * AM
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class Subset {
  // attributs
  private Set<HashSet<Long>> set; // set of subsets of integers
  private long sum; // sum of these integers

  // constructor for empty subset
  public Subset() {
    this.set = new HashSet<HashSet<Long>>();
    this.set.add(new HashSet<Long>());
    this.sum = 0;
  }

  // constructor for subset with only one integer
  public Subset(long value) {
    this.set = new HashSet<HashSet<Long>>();
    HashSet<Long> subset = new HashSet<Long>(1);
    subset.add(value);
    this.set.add(subset);
    this.sum = value;
  }

  // constructor for subset from an existing subset and a new integer
  public Subset(Subset subset, long value) throws IllegalArgumentException {
    if (subset == null)
      throw new IllegalArgumentException("Subset cannot be null; use the other constructor");
    this.set = new HashSet<HashSet<Long>>(subset.set.size());
    for (Set<Long> sub : subset.set) {
      if (sub.contains(value))
        throw new IllegalArgumentException("Subset already contains the new value");
      HashSet<Long> newSet = new HashSet<Long>(sub.size() + 1);
      newSet.addAll(sub);
      newSet.add(value);
      this.set.add(newSet);
    }
    this.sum = subset.sum + value;
  }

  // getter for the cardinality of this Subset (number of encapsulated sets)
  public int getCardinality() {
    return this.set.size();
  }

  // getter for the sum of the subsets
  public long getSum() {
    return this.sum;
  }

  // (hard) cloning another subset so that this will correspond to it
  public void clone(Subset other) throws IllegalArgumentException {
    if (other == null)
      throw new IllegalArgumentException("Other subset cannot be null");
    this.sum = other.sum;
    this.set = new HashSet<HashSet<Long>>(other.set.size());
    for (HashSet<Long> sub : other.set)
      this.set.add(new HashSet<Long>(sub));
  }

  // (hard) encapsulating another subset in this (the sum must correspond to
  // this.sum)
  public void encapsulate(Subset other) throws IllegalArgumentException {
    if (other == null)
      throw new IllegalArgumentException("Other subset cannot be null");
    if (this.sum != other.sum)
      throw new IllegalArgumentException("Cannot encapsulate: this sum differs from other's sum");
    for (HashSet<Long> sub : other.set)
      this.set.add(new HashSet<Long>(sub));
  }

  // verifying whether this.sum satisfies some given bounds
  public boolean satisfiesBounds(long lb, long ub) {
    return lb <= this.sum && this.sum <= ub;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    boolean isSubset = (o instanceof Subset);
    if (!isSubset)
      return false;
    Subset subset = (Subset) o;
    if (this.sum != subset.sum)
      return false;
    if (this.set.size() != subset.set.size())
      return false;
    return this.set.equals(subset.set);
  }

  @Override
  public int hashCode() {
    int hash = (int) this.sum;
    for (Set<Long> subset : this.set)
      hash = hash + subset.hashCode();
    return hash;
  }

  @Override
  public String toString() {
    String s = this.sum + " = ";
    for (Set<Long> sub : this.set) {
      s = s + "\n (";
      Iterator<Long> it = sub.iterator();
      while (it.hasNext()) {
        s = s + it.next();
        if (it.hasNext())
          s = s + ",";
      }
      s = s + ")";
    }
    return s;
  }

  // show (aka toString in Julia style)
  public String show() {
    String s = "Subset(" + this.set.size() + " set";
    if (this.set.size() > 1)
      s = s + "s";
    return s + " with sum " + this.sum + ")";
  }

  // main
  public static void main(String[] args) throws Exception {
    Subset S0 = new Subset();
    System.out.println(S0);
    Subset S1 = new Subset(S0, 3);
    Subset S2 = new Subset(10);
    Subset S3 = new Subset(S1, 7);
    System.out.println(S2);
    System.out.println(S3);
    S3.encapsulate(S2);
    System.out.println(S3);
    Subset S4 = new Subset(new Subset(S1, 2), 5);
    S4.encapsulate(S3);
    System.out.println(S4);
    System.out.println("hashcode " + S4.hashCode());
    Subset S5 = new Subset();
    S5.clone(S4);
    System.out.println(S5);
    System.out.println("cardinality " + S5.getCardinality());
    System.out.println("hashcode " + S5.hashCode());
    System.out.println("equals: " + S4.equals(S5));
  }
}

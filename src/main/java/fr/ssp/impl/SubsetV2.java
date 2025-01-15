package fr.ssp.impl;

/* Subset in Java
 *
 * for an object-oriented approach to the SSP
 *
 * AM
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import fr.ssp.api.Subset;

/**
 * SubsetV2 is an implementation of the Subset interface.
 */
public class SubsetV2 implements Subset {

   /*
    * attributes
    */
   private Set<SubsetV2> set; // set of reference subsets
   private long sum; // common sum for all subsets + last integer

   /*
    * constructor for empty subset
    */
   public SubsetV2() {
      this.set = new HashSet<SubsetV2>();
      this.sum = 0;
   }

   /*
    * constructor for subset with only one integer
    * 
    * @param value the value to add
    */
   public SubsetV2(long value) {
      this.set = new HashSet<SubsetV2>();
      this.set.add(new SubsetV2());
      this.sum = value;
   }

   /*
    * constructor for subset from an existing subset and a new integer
    * 
    * @param subset the subset to add
    * 
    * @param value the value to add
    * 
    * @throws IllegalArgumentException if the subset is null or the value is
    * already in the subset
    */
   public SubsetV2(SubsetV2 subset, long value) throws IllegalArgumentException {
      if (subset == null)
         throw new IllegalArgumentException("Subset cannot be null; use constructor for empty Subset");
      if (subset.getValues().contains(value))
         throw new IllegalArgumentException("Value " + value + " already in subset");
      this.set = new HashSet<SubsetV2>();
      this.set.add(subset);
      this.sum = subset.getSum() + value;
   }

   /*
    * getter for the sum of the subsets
    * 
    * @return the sum
    */
   public long getSum() {
      return this.sum;
   }

   /*
    * getter for the cardinality of the Subset (number of encapsulated sets)
    * 
    * @return the cardinality
    */
   public int getCardinality() {
      int cardinality = 0;
      for (SubsetV2 sub : this.getSubsets()) {
         if (sub.getSubsets().isEmpty()) {
            cardinality = cardinality + 1;
         } else {
            cardinality = cardinality + sub.getCardinality();
         }
      }
      if (cardinality == 0) {
         cardinality = 1;
      }
      return cardinality;
   }

   /*
    * getter for the values of the subsets
    * 
    * @return the values
    */
   public Set<Long> getValues() {
      Set<Long> values = new HashSet<Long>();
      if (!this.getSubsets().isEmpty()) {
         Iterator<SubsetV2> it = this.getSubsets().iterator();
         SubsetV2 parent = it.next();
         long value = this.getSum() - parent.getSum();
         if (value != 0) {
            values.add(value);
         }
         values.addAll(parent.getValues());
      }
      return values;
   }

   /*
    * getter for the subsets
    * 
    * @return the subsets
    */
   public Set<SubsetV2> getSubsets() {
      return this.set;
   }

   /*
    * add a subset to the set
    * 
    * @param subset the subset to add
    */
   public void addSubset(SubsetV2 subset) {
      this.set.add(subset);
   }

   /*
    * check if this subset contains another subset
    * 
    * @param other the subset to check
    * 
    * @throws IllegalArgumentException if the subset is null
    */
   public boolean contains(SubsetV2 other) {
      if (other == null) {
         return false;
      }
      for (SubsetV2 sub : other.getSubsets()) {
         if (!this.getSubsets().contains(sub)) {
            return false;
         }
      }
      return true;
   }

   /*
    * clone a subset
    * 
    * @param other the subset to clone
    * 
    * @throws IllegalArgumentException if the subset is null
    */
   @Override
   public void clone(Subset other) throws IllegalArgumentException {
      if (other instanceof SubsetV2) {
         this.clone((SubsetV2) other);
      } else {
         throw new IllegalArgumentException("Other subset cannot be cloned");
      }
   }

   /*
    * clone a subset
    * 
    * @param other the subset to clone
    * 
    * @throws IllegalArgumentException if the subset is null
    */
   public void clone(SubsetV2 other) throws IllegalArgumentException {
      if (other == null)
         throw new IllegalArgumentException("Other subset cannot be null");
      this.sum = other.getSum();
      this.set = new HashSet<SubsetV2>();
      if (!other.getSubsets().isEmpty())
         this.getSubsets().addAll(other.getSubsets());
   }

   /*
    * encapsulate a subset
    * 
    * @param other the subset to encapsulate
    * 
    * @throws IllegalArgumentException if the subset is null
    */
   @Override
   public void encapsulate(Subset other) throws IllegalArgumentException {
      if (other instanceof SubsetV2) {
         this.encapsulate((SubsetV2) other);
      } else {
         throw new IllegalArgumentException("Other subset cannot be encapsulated");
      }
   }

   /*
    * encapsulate a subset
    * 
    * @param other the subset to encapsulate
    * 
    * @throws IllegalArgumentException if the subset is null
    */
   public void encapsulate(SubsetV2 other) throws IllegalArgumentException {
      if (other == null) {
         throw new IllegalArgumentException("Other subset cannot be null");
      }
      if (this.getSum() != other.getSum()) {
         throw new IllegalArgumentException("Cannot encapsulate: this sum differs from other's sum");
      } else {
         for (SubsetV2 sub : other.getSubsets()) {
            if (!this.getSubsets().contains(sub)) {
               this.set.add(sub);
               System.out.println("Added subset: " + sub.show());
            }
         }
      }
   }

   /*
    * verifying whether this.sum satisfies some given bounds
    * 
    * @param lb the lower bound
    * 
    * @param ub the upper bound
    * 
    * @return true if the sum satisfies the bounds, false otherwise
    */
   public boolean satisfiesBounds(long lb, long ub) {
      return lb <= this.sum && this.sum <= ub;
   }

   /*
    * check if this subset is equal to another subset
    * 
    * @param o the other subset
    * 
    * @return true if the subsets are equal, false otherwise
    */
   @Override
   public boolean equals(Object o) {
      if (o == null)
         return false;
      boolean isSubset = (o instanceof SubsetV2);
      if (!isSubset)
         return false;
      SubsetV2 other = (SubsetV2) o;
      return this.getValues().containsAll(other.getValues());
   }

   /*
    * get the hash code of the subset
    * 
    * @return the hash code
    */
   @Override
   public int hashCode() {
      int hash = (int) this.sum;
      for (SubsetV2 subset : this.set)
         hash = hash + subset.hashCode();
      return hash;
   }

   /*
    * get the string representation of the subset
    * 
    * @param prefix the prefix to add
    * 
    * @return the string representation
    */
   public String toString(String prefix) {
      if (this.getSubsets().isEmpty())
         return prefix + ")";
      String s = "";
      Iterator<SubsetV2> it = this.getSubsets().iterator();
      while (it.hasNext()) {
         SubsetV2 sub = it.next();
         String comma = "";
         if (!sub.getSubsets().isEmpty())
            comma = ",";
         long value = this.getSum() - sub.getSum();
         if (value != 0) {
            s = s + sub.toString(prefix + value + comma);
         } else {
            s = s + sub.toString(prefix);
         }
         if (it.hasNext())
            s = s + "\n";
      }
      return s;
   }

   /*
    * get the string representation of the subset
    * 
    * @return the string representation
    */
   @Override
   public String toString() {
      String s = this.sum + " =\n";
      return s + this.toString("  (");
   }

   /*
    * show (aka toString in Julia style)
    * 
    * @return the string representation
    */
   public String show() {
      String s = "Subset(";
      int nsets = this.getCardinality();
      if (nsets == 0)
         return s + "empty)";
      s = s + nsets + " set";
      if (nsets > 1)
         s = s + "s";
      return s + " with sum " + this.sum + ")";
   }

   // main
   public static void main(String[] args) throws Exception {

      // System.out.println("---------------S0-----------------");
      // Subset S0 = new Subset();
      // System.out.println(S0);
      // System.out.println("---------------S1-----------------");
      // Subset S1 = new Subset(S0, 3);
      // System.out.println(S1);
      // System.out.println("---------------S2-----------------");
      // Subset S2 = new Subset(10);
      // System.out.println(S2);
      // System.out.println("---------------S3-----------------");
      // Subset S3 = new Subset(S1, 7);
      // System.out.println(S3);
      // System.out.println("---------------S3.encapsulate(S2)-----------------");
      // S3.encapsulate(S2);
      // System.out.println(S3);
      // System.out.println("---------------S4-----------------");
      // Subset S4 = new Subset(new Subset(S1, 2), 5);
      // S4.encapsulate(S3);
      // System.out.println(S4);
      // System.out.println("---------------S5-----------------");
      // Subset S5 = new Subset();
      // S5.clone(S4);
      // System.out.println(S5);
      // System.out.println("---------------cardinality-----------------");
      // System.out.println("cardinality " + S5.getCardinality());
      // System.out.println("---------------hashcode-----------------");
      // System.out.println("hashcode " + S5.hashCode());
      // System.out.println("---------------equals-----------------");
      // System.out.println("equals: " + S4.equals(S5));
      // SubsetV2 S0 = new SubsetV2();
      // System.out.println("S0 " + S0.show());
      // System.out.println(S0);
      // SubsetV2 S1 = new SubsetV2(S0, 3);
      // SubsetV2 S2 = new SubsetV2(10);
      // SubsetV2 S3 = new SubsetV2(S1, 7);
      // System.out.println("S2 " + S2.show());
      // System.out.println(S2);
      // System.out.println("S3 " + S3.show());
      // System.out.println(S3);
      // S3.encapsulate(S2);
      // System.out.println("S3 " + S3.show());
      // System.out.println(S3);
      // SubsetV2 S4 = new SubsetV2(new SubsetV2(S1, 2), 5);
      // S4.encapsulate(S3);
      // System.out.println("S4 " + S4.show());
      // System.out.println(S4);
      // SubsetV2 S5 = new SubsetV2();
      // S5.clone(S4);
      // System.out.println("S5 " + S5.show());
      // System.out.println(S5);
      // S5.encapsulate((new SubsetV2(new SubsetV2(S0, 4), 6)));
      // System.out.println("S5 " + S5.show());
      // System.out.println(S5);
      // SubsetV2 S6 = new SubsetV2(S4, 1);
      // SubsetV2 tmp = new SubsetV2(S4, 1);
      // S6.encapsulate(tmp);
      // S6.encapsulate(tmp);
      // System.out.println("S6 " + S6.show());
      // System.out.println(S6);
      // System.out.println("S6 contains S4? " + S6.contains(S4));
      SubsetV1 s1 = new SubsetV1(new SubsetV1(new SubsetV1(3), 5), 2);
      SubsetV1 s2 = new SubsetV1();
      s1.encapsulate(s2);
      System.out.println(s1);
   }
}

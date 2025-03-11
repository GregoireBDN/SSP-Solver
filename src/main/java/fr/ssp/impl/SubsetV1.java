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
 * SubsetV1 is an implementation of the Subset interface.
 */
public class SubsetV1 implements Subset {
   // attributes
   public Set<HashSet<Long>> set; // set of subsets of integers
   private long sum; // sum of these integers

   /*
    * constructor for empty subset
    */
   public SubsetV1() {
      this.set = new HashSet<HashSet<Long>>();
      this.set.add(new HashSet<Long>());
      this.sum = 0;
   }

   /*
    * constructor for subset with only one integer
    */
   public SubsetV1(long value) {
      this.set = new HashSet<HashSet<Long>>();
      HashSet<Long> subset = new HashSet<Long>(1);
      subset.add(value);
      this.set.add(subset);
      this.sum = value;
   }

   /*
    * constructor for subset from an existing subset and a new integer
    */
   public SubsetV1(SubsetV1 subset, long value) throws IllegalArgumentException {
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

   /*
    * getter for the cardinality of this Subset (number of encapsulated sets)
    */
   public int getCardinality() {
      return this.set.size();
   }

   /*
    * getter for the sum of the subsets
    */
   public long getSum() {
      return this.sum;
   }

   /*
    * cloning another subset so that this will correspond to it
    * 
    * @param other the subset to clone
    * 
    * @throws IllegalArgumentException if the subset is null
    */
   @Override
   public void clone(Subset other) throws IllegalArgumentException {
      if (other instanceof SubsetV1) {
         this.clone((SubsetV1) other);
      } else {
         throw new IllegalArgumentException("Other subset cannot be cloned");
      }
   }

   public void clone(SubsetV1 other) throws IllegalArgumentException {
      if (other == null)
         throw new IllegalArgumentException("Other subset cannot be null");
      this.sum = other.sum;
      this.set = new HashSet<HashSet<Long>>(other.set.size());
      for (HashSet<Long> sub : other.set)
         this.set.add(new HashSet<Long>(sub));
   }

   /*
    * encapsulating another subset in this (the sum must correspond to this.sum)
    * 
    * @param other the subset to encapsulate
    * 
    * @throws IllegalArgumentException if the subset is null or the sum does not
    * correspond
    */
   @Override
   public void encapsulate(Subset other) throws IllegalArgumentException {
      if (other instanceof SubsetV1) {
         this.encapsulate((SubsetV1) other);
      } else {
         throw new IllegalArgumentException("Other subset cannot be encapsulated");
      }
   }

   public void encapsulate(SubsetV1 other) throws IllegalArgumentException {
      if (other == null)
         throw new IllegalArgumentException("Cannot encapsulate null subset");
      this.set.addAll(other.set);
   }

   /*
    * verifying whether this.sum satisfies some given bounds
    * 
    * @param lb lower bound
    * 
    * @param ub upper bound
    * 
    * @return true if this.sum satisfies the bounds, false otherwise
    */
   public boolean satisfiesBounds(long lb, long ub) {
      return lb <= this.sum && this.sum <= ub;
   }

   /*
    * checking if this subset is equal to another subset
    * 
    * @param o the subset to compare
    * 
    * @return true if this subset is equal to o, false otherwise
    */
   @Override
   public boolean equals(Object o) {
      if (o == null)
         return false;
      boolean isSubset = (o instanceof SubsetV1);
      if (!isSubset)
         return false;
      SubsetV1 subset = (SubsetV1) o;
      if (this.sum != subset.sum)
         return false;
      if (this.set.size() != subset.set.size())
         return false;
      return this.set.equals(subset.set);
   }

   /*
    * computing the hash code of this subset
    * 
    * @return the hash code
    */
   @Override
   public int hashCode() {
      int hash = (int) this.sum;
      for (Set<Long> subset : this.set)
         hash = hash + subset.hashCode();
      return hash;
   }

   /*
    * returning a string representation of this subset
    * 
    * @return the string representation
    */
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

   /*
    * returning a string representation of this subset in Julia style
    * 
    * @return the string representation
    */
   public String show() {
      String s = "Subset(" + this.set.size() + " set";
      if (this.set.size() > 1)
         s = s + "s";
      return s + " with sum " + this.sum + ")";
   }

   public boolean contains(SubsetV1 other) {
      if (other == null) {
         return false;
      }
      for (Set<Long> sub : other.set) {
         if (!this.set.contains(sub)) {
            return false;
         }
      }
      return true;
   }

   public Set<HashSet<Long>> getSetCollection() {
      return this.set;
   }

   /**
    * Retourne un ensemble contenant toutes les valeurs uniques de ce sous-ensemble
    * 
    * @return l'ensemble des valeurs
    */
   public Set<Long> getValues() {
      Set<Long> result = new HashSet<>();
      for (Set<Long> subset : this.set) {
         result.addAll(subset);
      }
      return result;
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

      // SubsetV1 S0 = new SubsetV1();
      // System.out.println("S0 " + S0.show());
      // System.out.println(S0);
      // SubsetV1 S1 = new SubsetV1(S0, 3);
      // SubsetV1 S2 = new SubsetV1(10);
      // SubsetV1 S3 = new SubsetV1(S1, 7);
      // System.out.println("S2 " + S2.show());
      // System.out.println(S2);
      // System.out.println("S3 " + S3.show());
      // System.out.println(S3);
      // S3.encapsulate(S2);
      // System.out.println("S3 " + S3.show());
      // System.out.println(S3);
      // SubsetV1 S4 = new SubsetV1(new SubsetV1(S1, 2), 5);
      // S4.encapsulate(S3);
      // System.out.println("S4 " + S4.show());
      // System.out.println(S4);
      // SubsetV1 S5 = new SubsetV1();
      // S5.clone(S4);
      // System.out.println("S5 " + S5.show());
      // System.out.println(S5);
      // S5.encapsulate((new SubsetV1(new SubsetV1(S0, 4), 6)));
      // System.out.println("S5 " + S5.show());
      // System.out.println(S5);
      // SubsetV1 S6 = new SubsetV1(S4, 1);
      // SubsetV1 tmp = new SubsetV1(S4, 1);
      // S6.encapsulate(tmp);
      // S6.encapsulate(tmp);
      // System.out.println("S6 " + S6.show());
      // System.out.println(S6);

      SubsetV1 s1 = new SubsetV1(new SubsetV1(new SubsetV1(3), 5), 2);
      SubsetV1 s2 = new SubsetV1(12);
      s1.encapsulate(s2);
      System.out.println(s1);
   }
}

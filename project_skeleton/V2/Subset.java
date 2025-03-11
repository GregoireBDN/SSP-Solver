
/* Subset in Java
 *
 * for an object-oriented approach to the SSP
 *
 * AM
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class Subset
{
   // static attribute
   private static int count = 0;

   // attributes
   private Set<Subset> set;  // set of reference subsets
   private long sum;  // common sum for all subsets + last integer
   private int hash;  // each Subset has a different hash code

   // constructor for empty subset
   public Subset()
   {
      this.set = new HashSet<Subset> ();
      this.sum = 0;
      this.hash = ++Subset.count;
   }

   // constructor for subset with only one integer
   public Subset(long value)
   {
      this.set = new HashSet<Subset> ();
      this.set.add(new Subset());
      this.sum = value;
      this.hash = ++Subset.count;
   }

   // constructor for subset from an existing subset and a new integer
   public Subset(Subset subset,long value) throws IllegalArgumentException
   {
      if (subset == null) throw new IllegalArgumentException("Subset cannot be null; use constructor for empty Subset");
      this.set = new HashSet<Subset> ();
      this.set.add(subset);
      this.sum = subset.sum + value;
      this.hash = ++Subset.count;
   }

   // getter for the cardinality of the Subset (number of encapsulated sets)
   public int getCardinality()
   {
      int cardinality = 0;
      for (Subset sub : this.set)
      {
         if (sub.set.isEmpty())
         {
            cardinality = cardinality + 1;
         }
         else
         {
            cardinality = cardinality + sub.getCardinality();
         }
      }
      return cardinality;
   }

   // getter for the sum of the subsets
   public long getSum()
   {
      return this.sum;
   }

   // verifying whether this Subset contains another
   public boolean contains(Subset other)
   {
      if (other == null)  return false;
      boolean answer = false;
      for (Subset sub : this.set)
      {
         if (sub.equals(other))  return true;
         answer = answer | sub.contains(other);
      }
      return answer;
   }

   // cloning another subset so that this will correspond to it
   public void clone(Subset other) throws IllegalArgumentException
   {
      if (other == null) throw new IllegalArgumentException("Other subset cannot be null");
      this.sum = other.sum;
      this.set = new HashSet<Subset> ();
      if (!other.set.isEmpty())  this.set.addAll(other.set);
   }

   // encapsulating another subset in this (the sum must correspond to this.sum)
   public void encapsulate(Subset other) throws IllegalArgumentException
   {
      if (other == null) throw new IllegalArgumentException("Other subset cannot be null");
      if (this.sum != other.sum) throw new IllegalArgumentException("Cannot encapsulate: this sum differs from other's sum");
      for (Subset sub : other.set)
      {
         if (sub.set.size() == 1)
         {
            Iterator<Subset> it = sub.set.iterator();
            if (it.next().set.isEmpty() && this.sum - sub.sum == other.sum)  continue;
         }
         if (this.contains(sub))  continue;
         this.set.add(sub);
      }
   }

   // verifying whether this.sum satisfies some given bounds
   public boolean satisfiesBounds(long lb,long ub)
   {
      return lb <= this.sum && this.sum <= ub;
   }

   @Override
   public boolean equals(Object o)
   {
      if (o == null)  return false;
      boolean isSubset = (o instanceof Subset);
      if (!isSubset)  return false;
      Subset other = (Subset) o;
      return this.hash == other.hash;
   }

   @Override
   public int hashCode()
   {
      return this.hash;
   }

   // private toString for recursive calls
   private String toString(String prefix)
   {
      if (this.set.isEmpty())  return prefix + ")";
      String s = "";
      Iterator<Subset> it = this.set.iterator();
      while (it.hasNext())
      {
         Subset sub = it.next();
         String comma = "";
         if (!sub.set.isEmpty())  comma = ",";
         s = s + sub.toString(prefix + (this.sum - sub.sum) + comma);
         if (it.hasNext())  s = s + "\n";
      }
      return s;
   }

   @Override
   public String toString()
   {
      String s = this.sum + " =\n";
      return s + this.toString("  (");
   }

   // show (aka toString in Julia style)
   public String show()
   {
      String s = "Subset(";
      int nsets = this.getCardinality();
      if (nsets == 0)  return s + "empty)";
      s = s + nsets + " set";
      if (nsets > 1)  s = s + "s";
      return s + " with sum " + this.sum + ")";
   }

   // main
   public static void main(String[] args) throws Exception
   {
      Subset S0 = new Subset();
      System.out.println("S0 " + S0.show());
      Subset S1 = new Subset(S0,3);
      Subset S2 = new Subset(10);
      Subset S3 = new Subset(S1,7);
      System.out.println("S2 " + S2.show());
      System.out.println(S2);
      System.out.println("S3 " + S3.show());
      System.out.println(S3);
      S3.encapsulate(S2);
      System.out.println("S3 " + S3.show());
      System.out.println(S3);
      Subset S4 = new Subset(new Subset(S1,2),5);
      S4.encapsulate(S3);
      System.out.println("S4 " + S4.show());
      System.out.println(S4);
      Subset S5 = new Subset();
      S5.clone(S4);
      System.out.println("S5 " + S5.show());
      System.out.println(S5);
      S5.encapsulate(new Subset(new Subset(new Subset(new Subset(S0,1),2),6),1));
      System.out.println("S5 " + S5.show());
      System.out.println(S5);
      Subset S6 = new Subset(new Subset(S4,1),2);
      Subset tmp = new Subset(new Subset(S4,2),1);
      S6.encapsulate(tmp);
      S6.encapsulate(tmp);
      System.out.println("S6 " + S6.show());
      System.out.println(S6);
      System.out.println("S6 contains S4? " + S6.contains(S4));
   }
}


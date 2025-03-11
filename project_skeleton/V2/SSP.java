
/* Subset Sum in Java
 *
 * BP vs dynamic programming
 *
 * AM
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class SSP {
  // attributs
  private long target;
  private long[] original;

  // constructor (easy instances)
  public SSP(int n) throws IllegalArgumentException {
    if (n <= 0)
      throw new IllegalArgumentException("SSP size cannot be nonpositive");
    if (n <= 2)
      throw new IllegalArgumentException("SSP size is too small");
    Random R = new Random();
    this.target = 1;
    this.original = new long[n];
    this.original[0] = 1;
    for (int i = 1; i < n; i++) {
      this.original[i] = i + 1;
      if (R.nextBoolean())
        this.target = this.target + this.original[i];
    }
  }

  // constructor (from text file)
  public SSP(String filename) throws IllegalArgumentException, FileNotFoundException {
    if (filename == null)
      throw new IllegalArgumentException("Given path/file is null");
    File input = new File(filename);
    if (!input.exists())
      throw new IllegalArgumentException("Given path/file does not exist");
    Scanner scan = new Scanner(input);
    IllegalArgumentException IAE = new IllegalArgumentException("Error while parsing input file");

    // number of elements in the original set
    if (!scan.hasNext())
      throw IAE;
    int size = scan.nextInt();
    if (size <= 0)
      throw IAE;
    this.original = new long[size];

    // target
    if (!scan.hasNext())
      throw IAE;
    this.target = scan.nextLong();

    // the original set
    for (int i = 0; i < size; i++) {
      if (!scan.hasNext())
        throw IAE;
      this.original[i] = scan.nextLong();
    }
    scan.close();
  }

  // computing the sum of all integers
  public long totalSum() {
    int n = this.original.length;
    long sum = this.original[0];
    for (int i = 1; i < n; i++)
      sum = sum + this.original[i];
    return sum;
  }

  // dynamic programming approach
  public Subset dynprog() {
    // preparing the map
    Map<Long, Subset> Sums = new HashMap<Long, Subset>();
    Long zero = 0L;
    Sums.put(zero, new Subset());

    // computing the total sum
    long total = this.totalSum();

    // main loop (all integers)
    for (int i = 0; i < this.original.length; i++) {
      // temporary list of new subsets
      List<Subset> newSets = new ArrayList<Subset>();

      // updating the sum of remaining integers
      total = total - this.original[i];

      // creating the new subsets
      for (long sum : Sums.keySet()) {
        Subset sub = Sums.get(sum);
        Subset newSet = new Subset(sub, this.original[i]);
        newSets.add(newSet);
      }

      // merging or adding the new subsets in the map
      for (Subset sub : newSets) {
        long sum = sub.getSum();
        if (Sums.containsKey(sum)) {
          Sums.get(sum).encapsulate(sub);
        } else if (sub.satisfiesBounds(this.target - total, this.target)) {
          Sums.put(sum, sub);
        }
      }
    }

    // the solution (if it exists) has key equal to the target
    return Sums.get(this.target);
  }

  // branch-and-prune (recursive, private)
  private void bpRec(int i, Subset partial, long total, Subset solutions) {
    // are we out of bounds yet?
    if (!partial.satisfiesBounds(this.target - total, this.target))
      return;

    // did we find a new solution already?
    if (partial.getSum() == this.target) {
      if (solutions.getSum() == 0)
        solutions.clone(partial);
      else
        solutions.encapsulate(partial);
      return;
    }

    // did we use all integers already?
    if (i == this.original.length)
      return;
    total = total - this.original[i];

    // recursive call without original[i]
    this.bpRec(i + 1, partial, total, solutions);

    // recursive call with original[i]
    this.bpRec(i + 1, new Subset(partial, this.original[i]), total, solutions);
  }

  // branch-and-prune
  public Subset bp() {
    Subset solutions = new Subset();
    long total = this.totalSum();
    this.bpRec(0, new Subset(), total, solutions);
    return solutions;
  }

  @Override
  public String toString() {
    return "SSP(n = " + this.original.length + "; target = " + this.target + ")";
  }

  // showing the target
  public String showTarget() {
    return "Target is " + this.target;
  }

  // showing the integers in the original set
  public String showIntegers() {
    String s = "Original set = [";
    for (int i = 0; i < this.original.length; i++) {
      s = s + this.original[i];
      if (i + 1 < this.original.length)
        s = s + ",";
    }
    return s + "]";
  }

  // main
  public static void main(String[] args) throws Exception {
    // SSP ssp = new SSP("inst_n25.txt");
    SSP ssp = new SSP(15);
    System.out.println(ssp);
    System.out.println(ssp.showIntegers());
    System.out.println(ssp.showTarget());
    System.out.println();

    System.out.print("Running bp ... ");
    long start = System.currentTimeMillis();
    Subset sol1 = ssp.bp();
    long end = System.currentTimeMillis();
    System.out.println("OK; time " + (end - start));
    System.out.println(sol1.show());
    if (sol1.getCardinality() < 100)
      System.out.println(sol1);
    System.out.println();

    System.out.print("Running dynprog ... ");
    start = System.currentTimeMillis();
    Subset sol2 = ssp.dynprog();
    end = System.currentTimeMillis();
    System.out.println("OK; time " + (end - start));
    System.out.println(sol2.show());
    if (sol2.getCardinality() < 100)
      System.out.println(sol2);
    System.out.println();

    System.out.println("The two solutions are identical : " + sol1.equals(sol2));
  }
}

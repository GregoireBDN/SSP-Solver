package fr.ssp;

/* Subset Sum in Java
 *
 * BP vs dynamic programming
 *
 * AM
 */

import fr.ssp.api.Subset;
import fr.ssp.impl.SubsetFactory;
import fr.ssp.solver.BranchAndPruneSolver;
import fr.ssp.solver.DynamicProgrammingSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * SSP
 */
public class SSP {

  /**
   * target
   */
  private long target;

  /**
   * original
   */
  private final long[] original;
  private static final long DEFAULT_SEED = 2L;
  private long seed;

  /**
   * constructor (easy instances) avec seed par défaut
   */
  public SSP(int n) throws IllegalArgumentException {
    this(n, DEFAULT_SEED);
  }

  /**
   * constructor (easy instances) avec seed configurable
   */
  public SSP(int n, long seed) throws IllegalArgumentException {
    if (n <= 0)
      throw new IllegalArgumentException("SSP size cannot be nonpositive");
    if (n <= 2)
      throw new IllegalArgumentException("SSP size is too small");

    this.seed = seed;
    Random R = new Random(this.seed);

    this.target = 1;
    this.original = new long[n];
    this.original[0] = 1;
    for (int i = 1; i < n; i++) {
      this.original[i] = i + 1;
      if (R.nextBoolean())
        this.target = this.target + this.original[i];
    }
  }

  /**
   * constructor (from text file)
   */
  public SSP(String filename) throws IllegalArgumentException, FileNotFoundException {
    if (filename == null)
      throw new IllegalArgumentException("Given path/file is null");
    File input = new File(filename);
    if (!input.exists())
      throw new IllegalArgumentException("Given path/file does not exist");
    try (Scanner scan = new Scanner(input)) {
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
    }
  }

  /**
   * computing the sum of all integers
   * 
   * @return the sum of all integers
   */
  public long totalSum() {
    int n = this.original.length;
    long sum = this.original[0];
    for (int i = 1; i < n; i++)
      sum = sum + this.original[i];
    return sum;
  }

  /**
   * toString
   */
  @Override
  public String toString() {
    return "SSP(n = " + this.original.length + "; target = " + this.target + ")";
  }

  /**
   * showing the target
   */
  public String showTarget() {
    return "Target is " + this.target;
  }

  /**
   * showing the integers in the original set
   */
  public String showIntegers() {
    String s = "Original set = [";
    for (int i = 0; i < this.original.length; i++) {
      s = s + this.original[i];
      if (i + 1 < this.original.length)
        s = s + ",";
    }
    return s + "]";
  }

  /**
   * dynamic programming approach
   */
  public Subset dynprog(SubsetFactory factory) {
    return new DynamicProgrammingSolver(target, original, factory).solve();
  }

  /**
   * branch-and-prune
   */
  public Subset bp(SubsetFactory factory) {
    return new BranchAndPruneSolver(target, original, factory).solve();
  }

  /**
   * main
   */
  public static void main(String[] args) throws Exception {
    SSP ssp = new SSP(15);
    System.out.println(ssp);
    System.out.println(ssp.showIntegers());
    System.out.println(ssp.showTarget());
    System.out.println();

    // Test avec V1
    System.out.println("Testing with SubsetV1:");
    System.out.print("Running bp ... ");
    long start = System.currentTimeMillis();
    Subset solBP1 = ssp.bp(SubsetFactory.V1);
    long end = System.currentTimeMillis();
    System.out.println("OK; time " + (end - start));
    System.out.println(solBP1.show());
    if (solBP1.getCardinality() < 100)
      System.out.println(solBP1);
    System.out.println();

    System.out.print("Running dynprog ... ");
    start = System.currentTimeMillis();
    Subset solDP1 = ssp.dynprog(SubsetFactory.V1);
    end = System.currentTimeMillis();
    System.out.println("OK; time " + (end - start));
    System.out.println(solDP1.show());
    if (solDP1.getCardinality() < 100)
      System.out.println(solDP1);
    System.out.println();

    // Test avec V2
    System.out.println("\nTesting with SubsetV2:");
    System.out.print("Running bp ... ");
    start = System.currentTimeMillis();
    Subset solBP2 = ssp.bp(SubsetFactory.V2);
    end = System.currentTimeMillis();
    System.out.println("OK; time " + (end - start));
    System.out.println(solBP2.show());
    if (solBP2.getCardinality() < 100)
      System.out.println(solBP2);
    System.out.println();

    System.out.print("Running dynprog ... ");
    start = System.currentTimeMillis();
    Subset solDP2 = ssp.dynprog(SubsetFactory.V2);
    end = System.currentTimeMillis();
    System.out.println("OK; time " + (end - start));
    System.out.println(solDP2.show());
    if (solDP2.getCardinality() < 100)
      System.out.println(solDP2);
    System.out.println();

    // Comparaison des résultats
    System.out.println("Solutions comparison:");
    System.out.println("V1 BP vs DP: " + solBP1.equals(solDP1));
    System.out.println("V2 BP vs DP: " + solBP2.equals(solDP2));
    System.out.println("V1 vs V2 (BP): " + solBP1.equals(solBP2));
    System.out.println("V1 vs V2 (DP): " + solDP1.equals(solDP2));
  }
}
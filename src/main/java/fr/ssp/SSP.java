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
import java.util.Arrays;

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
  public final long[] original;
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
   * Constructeur avec un tableau de valeurs et une cible spécifiés
   * 
   * @param values tableau des valeurs à utiliser
   * @param target valeur cible à atteindre
   */
  public SSP(long[] values, long target) {
    if (values == null)
      throw new IllegalArgumentException("Values array cannot be null");

    this.original = new long[values.length];
    System.arraycopy(values, 0, this.original, 0, values.length);
    this.target = target;
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
    System.err.println("SSP.bp - target: " + target);
    System.err.println("SSP.bp - valeurs: " + Arrays.toString(original));

    Subset result = new BranchAndPruneSolver(target, original, factory).solve();
    System.err.println("SSP.bp - résultat: " + result.getSum());
    return result;
  }

  /**
   * Définit les valeurs du tableau original
   * 
   * @param values les nouvelles valeurs
   */
  public void setValues(long[] values) {
    if (values == null)
      throw new IllegalArgumentException("Values array cannot be null");

    if (values.length != this.original.length)
      throw new IllegalArgumentException("New values array must have the same length as original");

    System.arraycopy(values, 0, this.original, 0, values.length);
  }

  /**
   * main
   */
  public static void main(String[] args) throws Exception {
    SSP ssp = new SSP(10);
    System.out.println("=== Configuration du problème ===");
    System.out.println(ssp);
    System.out.println(ssp.showIntegers());
    System.out.println(ssp.showTarget());
    System.out.println();

    // Test avec V1
    System.out.println("=== Test avec SubsetV1 (implémentation avec Set<HashSet<Long>>) ===");
    System.out.print("Exécution de Branch and Prune ... ");
    long start = System.currentTimeMillis();
    Subset solBP1 = ssp.bp(SubsetFactory.V1);
    long end = System.currentTimeMillis();
    System.out.println("OK");
    System.out.println("Temps d'exécution: " + (end - start) + "ms");
    System.out.println("Résultat: " + solBP1.show());
    System.out.println();

    System.out.print("Exécution de Dynamic Programming ... ");
    start = System.currentTimeMillis();
    Subset solDP1 = ssp.dynprog(SubsetFactory.V1);
    end = System.currentTimeMillis();
    System.out.println("OK");
    System.out.println("Temps d'exécution: " + (end - start) + "ms");
    System.out.println("Résultat: " + solDP1.show());
    System.out.println();

    // Test avec V2
    System.out.println("=== Test avec SubsetV2 (implémentation récursive) ===");
    System.out.print("Exécution de Branch and Prune ... ");
    start = System.currentTimeMillis();
    Subset solBP2 = ssp.bp(SubsetFactory.V2);
    end = System.currentTimeMillis();
    System.out.println("OK");
    System.out.println("Temps d'exécution: " + (end - start) + "ms");
    System.out.println("Résultat: " + solBP2.show());
    System.out.println();

    System.out.print("Exécution de Dynamic Programming ... ");
    start = System.currentTimeMillis();
    Subset solDP2 = ssp.dynprog(SubsetFactory.V2);
    end = System.currentTimeMillis();
    System.out.println("OK");
    System.out.println("Temps d'exécution: " + (end - start) + "ms");
    System.out.println("Résultat: " + solDP2.show());
    System.out.println();

    // Comparaison des résultats
    System.out.println("=== Comparaison des solutions ===");
    System.out.println("SubsetV1 - BP vs DP: " +
        (solBP1.equals(solDP1) ? "IDENTIQUES ✓" : "DIFFÉRENTES ✗"));
    System.out.println("SubsetV2 - BP vs DP: " +
        (solBP2.equals(solDP2) ? "IDENTIQUES ✓" : "DIFFÉRENTES ✗"));

    // Comparaison du nombre de solutions
    System.out.println("\n=== Nombre de solutions trouvées ===");
    System.out.println("SubsetV1 - BP: " + solBP1.getCardinality() + " sous-ensembles");
    System.out.println("SubsetV1 - DP: " + solDP1.getCardinality() + " sous-ensembles");
    System.out.println("SubsetV2 - BP: " + solBP2.getCardinality() + " sous-ensembles");
    System.out.println("SubsetV2 - DP: " + solDP2.getCardinality() + " sous-ensembles");

    // Comparaison des cardinalités
    System.out.println("\nBP - V1 vs V2 (nombre): " +
        (solBP1.getCardinality() == solBP2.getCardinality() ? "MÊME NOMBRE ✓ (" + solBP1.getCardinality() + ")"
            : "NOMBRE DIFFÉRENT ✗ (V1: " + solBP1.getCardinality() + ", V2: " + solBP2.getCardinality() + ")"));
    System.out.println("DP - V1 vs V2 (nombre): " +
        (solDP1.getCardinality() == solDP2.getCardinality() ? "MÊME NOMBRE ✓ (" + solDP1.getCardinality() + ")"
            : "NOMBRE DIFFÉRENT ✗ (V1: " + solDP1.getCardinality() + ", V2: " + solDP2.getCardinality() + ")"));
  }
}
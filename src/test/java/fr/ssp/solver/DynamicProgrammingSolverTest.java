package fr.ssp.solver;

import fr.ssp.api.Subset;
import fr.ssp.impl.SubsetFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DynamicProgrammingSolverTest {

  @Test
  public void testSimpleCaseV1() {
    long[] original = { 1, 2, 3, 4, 5 };
    long target = 9;
    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, original, SubsetFactory.V1);
    Subset result = solver.solve();
    assertNotNull(result, "Result should not be null");
    assertEquals(target, result.getSum(), "Result should have correct sum");
  }

  @Test
  public void testNoSolutionV1() {
    long[] original = { 1, 2, 3 };
    long target = 10;
    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, original, SubsetFactory.V1);
    Subset result = solver.solve();
    assertNull(result, "Result should be null when no solution exists");
  }

  @Test
  public void testExactMatchV1() {
    long[] original = { 1, 2, 3, 4, 5 };
    long target = 15;
    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, original, SubsetFactory.V1);
    Subset result = solver.solve();
    assertNotNull(result, "Result should not be null");
    assertEquals(target, result.getSum(), "Result should have correct sum");
  }

  @Test
  public void testLargeSetV1() {
    long[] original = new long[100];
    for (int i = 0; i < 100; i++) {
      original[i] = i + 1;
    }
    long target = 5050; // Sum of numbers from 1 to 100
    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, original, SubsetFactory.V1);
    Subset result = solver.solve();
    assertNotNull(result, "Result should not be null");
    assertEquals(target, result.getSum(), "Result should have correct sum");
  }

  @Test
  public void testSimpleCaseV2() {
    long[] original = { 1, 2, 3, 4, 5 };
    long target = 9;
    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, original, SubsetFactory.V2);
    Subset result = solver.solve();
    assertNotNull(result, "Result should not be null");
    assertEquals(target, result.getSum(), "Result should have correct sum");
  }

  @Test
  public void testNoSolutionV2() {
    long[] original = { 1, 2, 3 };
    long target = 10;
    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, original, SubsetFactory.V2);
    Subset result = solver.solve();
    assertNull(result, "Result should be null when no solution exists");
  }

  @Test
  public void testExactMatchV2() {
    long[] original = { 1, 2, 3, 4, 5 };
    long target = 15;
    DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target, original, SubsetFactory.V2);
    Subset result = solver.solve();
    assertNotNull(result, "Result should not be null");
    assertEquals(target, result.getSum(), "Result should have correct sum");
  }

  // @Test
  // public void testLargeSetV2() {
  // long[] original = new long[100];
  // for (int i = 0; i < 100; i++) {
  // original[i] = i + 1;
  // }
  // long target = 5050; // Sum of numbers from 1 to 100
  // DynamicProgrammingSolver solver = new DynamicProgrammingSolver(target,
  // original, SubsetFactory.V2);
  // Subset result = solver.solve();
  // assertNotNull(result, "Result should not be null");
  // assertEquals(target, result.getSum(), "Result should have correct sum");
  // }
}
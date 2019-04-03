package tetris;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s1_1, s1_2;
	private Piece s2_1, s2_2;
	private Piece l1_1, l1_2, l1_3, l1_4;
	private Piece l2_1, l2_2, l2_3, l2_4;
	private Piece sq;
	private Piece st1, st2;

	@Before
	public void setUp() throws Exception {
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		st1 = new Piece(Piece.STICK_STR);
		st2 = st1.computeNextRotation();
		
		l1_1 = new Piece(Piece.L1_STR);
		l1_2 = l1_1.computeNextRotation();
		l1_3 = l1_2.computeNextRotation();
		l1_4 = l1_3.computeNextRotation();
		
		l2_1 = new Piece(Piece.L2_STR);
		l2_2 = l2_1.computeNextRotation();
		l2_3 = l2_2.computeNextRotation();
		l2_4 = l2_3.computeNextRotation();
		
		s1_1 = new Piece(Piece.S1_STR);
		s1_2 = s1_1.computeNextRotation();
		
		s2_1 = new Piece(Piece.S2_STR);
		s2_2 = s2_1.computeNextRotation();
		
		sq = new Piece(Piece.SQUARE_STR); 

	}
	
	// Here are some sample tests to get you started
	
	@Test
	public void testSampleSize1() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
	
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		assertEquals(3, pyr3.getWidth());
		assertEquals(2, pyr3.getHeight());
		
		assertEquals(2, pyr4.getWidth());
		assertEquals(3, pyr4.getHeight());

		
		// Now try with some other piece, made a different way
		assertEquals(1, st1.getWidth());
		assertEquals(4, st1.getHeight());
		
		assertEquals(4, st2.getWidth());
		assertEquals(1, st2.getHeight());
	}
	
	
	@Test
	public void testSampleSize2() {
		// Piece L1
		assertEquals(2, l1_1.getWidth());
		assertEquals(3, l1_1.getHeight());
		
		assertEquals(3, l1_2.getWidth());
		assertEquals(2, l1_2.getHeight());
		
		assertEquals(2, l1_3.getWidth());
		assertEquals(3, l1_3.getHeight());
		
		assertEquals(3, l1_4.getWidth());
		assertEquals(2, l1_4.getHeight());
	}
	
	@Test
	public void testSampleSize3() {
		// Piece L2 tests
		assertEquals(2, l2_1.getWidth());
		assertEquals(3, l2_1.getHeight());
		
		assertEquals(3, l2_2.getWidth());
		assertEquals(2, l2_2.getHeight());
		
		assertEquals(2, l2_3.getWidth());
		assertEquals(3, l2_3.getHeight());
		
		assertEquals(3, l2_4.getWidth());
		assertEquals(2, l2_4.getHeight());
	}
	
	@Test
	public void testSampleSize4() {
		// Piece s1 tests
		assertEquals(3, s1_1.getWidth());
		assertEquals(2, s1_1.getHeight());
		
		assertEquals(2, s1_2.getWidth());
		assertEquals(3, s1_2.getHeight());
		
		// Piece s2 tests
		assertEquals(3, s2_1.getWidth());
		assertEquals(2, s2_1.getHeight());
		
		assertEquals(2, s2_2.getWidth());
		assertEquals(3, s2_2.getHeight());
	}
	
	@Test
	public void testSampleSize5() {
		// Piece square tests
		assertEquals(2, sq.getWidth());
		assertEquals(2, sq.getHeight());
		
	}
	
	
	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt1() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, pyr2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, pyr4.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, s1_2.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {1, 0, 0}, s2_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, s1_2.getSkirt()));
	}
	
	@Test
	public void testSampleSkirt2() {
		assertTrue(Arrays.equals(new int[] {0, 0}, l1_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l1_2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2, 0}, l1_3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1, 1}, l1_4.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0}, l2_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 1, 0}, l2_2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 2}, l2_3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l2_4.getSkirt()));
	}
	
	@Test
	public void testSampleSkirt3() {
		assertTrue(Arrays.equals(new int[] {0, 0}, sq.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, st1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, st2.getSkirt()));
	}
	
	
}

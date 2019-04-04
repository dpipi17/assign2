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
		st2.computeNextRotation();
		
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
	
	/*@Test(expected = RuntimeException.class)
	public void testError() {
		Piece err = new Piece("q w 0 1");
	} */
	
	
	
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
		assertTrue(Arrays.equals(new int[] {0, 1}, s2_2.getSkirt()));
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
	
	@Test
	public void testEqual1() {
		// pyramid tests
		assertEquals(true, pyr1.equals(pyr1));
		assertEquals(true, pyr1.equals(new Piece("0 0  1 0  1 1  2 0")));
		assertEquals(true, pyr1.equals(new Piece("0 0  1 0  2 0  1 1")));
		assertEquals(true, pyr1.equals(new Piece("0 0  2 0  1 1  1 0")));
		assertEquals(true, pyr1.equals(new Piece("0 0  2 0  1 0  1 1")));
		assertEquals(true, pyr1.equals(new Piece("0 0  1 1  1 0  2 0")));
		assertEquals(true, pyr1.equals(new Piece("0 0  1 1  2 0  1 0")));
		
		
		assertEquals(true, pyr1.equals(new Piece("1 0  1 1  2 0  0 0")));
		assertEquals(true, pyr1.equals(new Piece("1 0  1 1  0 0  2 0")));
		assertEquals(true, pyr1.equals(new Piece("1 1  0 0  2 0  1 0")));
		assertEquals(true, pyr1.equals(new Piece("2 0  0 0  1 1  1 0")));
		assertEquals(true, pyr1.equals(new Piece("1 1  2 0  0 0  1 0")));
		assertEquals(true, pyr1.equals(new Piece("2 0  0 0  1 0  1 1")));
		
		assertEquals(false, pyr1.equals(3));
		assertEquals(false, pyr1.equals(new Piece("0 0  1 0  2 0  1 1  1 1")));
		assertEquals(false, pyr1.equals(new Piece("0 0  4 0  1 1  2 0")));
		assertEquals(false, pyr1.equals(new Piece("0 0  2 0  1 1  0 1")));
		assertEquals(false, pyr1.equals(new Piece("0 0  0 2  1 0  1 1")));
		assertEquals(false,pyr1.equals(new Piece("0 0  0 0  1 0  1 1")));
	}
	
	@Test
	public void testEqual2() {
		// square tests
		assertEquals(true, sq.equals(new Piece("0 0  0 1  1 0  1 1")));
		assertEquals(true, sq.equals(new Piece("0 1  0 0  1 0  1 1")));
		assertEquals(true, sq.equals(new Piece("1 0  0 1  0 0  1 1")));
		assertEquals(true, sq.equals(new Piece("1 1  0 1  1 0  0 0")));
		
		assertEquals(false, sq.equals(new Piece("0 0  0 0  1 0  1 1")));
		assertEquals(false, sq.equals(new Piece("0 1  0 0  0 1  1 1")));
		assertEquals(false, sq.equals(new Piece("1 0  0 1  0 0  1 2")));
		assertEquals(false, sq.equals(new Piece("1 1  0 1  1 0  0 0  0 1")));		
	}
	
	@Test
	public void testEqual3() {
		// s1 tests
		assertEquals(true, s1_1.equals(new Piece("0 0	1 0	 1 1  2 1")));
		assertEquals(true, s1_1.equals(new Piece("1 1	1 0	 0 0  2 1")));
		assertEquals(true, s1_1.equals(new Piece("2 1	1 0	 1 1  0 0")));
		assertEquals(true, s1_1.equals(new Piece("1 1	0 0	 1 0  2 1")));
		
		assertEquals(false, s1_1.equals(new Piece("0 0  0 0  1 0  1 1")));
		assertEquals(false, s1_1.equals(new Piece("1 0  2 1  1 0  1 1  3 1")));
		assertEquals(false, s1_1.equals(new Piece("2 1  3 1  1 0  1 1")));
		assertEquals(false, s1_1.equals(new Piece("0 0	1 0	 1 0  2 1")));
		
		// s2 tests
		assertEquals(true, s2_1.equals(new Piece("0 1	1 1  1 0  2 0")));
		assertEquals(true, s2_1.equals(new Piece("1 1	0 1  1 0  2 0")));
		assertEquals(true, s2_1.equals(new Piece("2 0	1 1  1 0  0 1")));
		assertEquals(true, s2_1.equals(new Piece("0 1	1 0  1 1  2 0")));
		
		assertEquals(false, s2_1.equals(new Piece("0 1	1 1  1 0  2 0  1 1")));
		assertEquals(false, s2_1.equals(new Piece("0 1  1 1  1 1  2 1")));
		assertEquals(false, s2_1.equals(new Piece("0 1  1 0  2 0  2 1")));
		assertEquals(false, s2_1.equals(new Piece("0 1	1 1  1 0")));
					
	}
	
	@Test
	public void testEqual4() {
		// L1 tests
		assertEquals(true, l1_1.equals(new Piece("0 0	0 1	 0 2  1 0")));
		assertEquals(true, l1_1.equals(new Piece("0 2	0 1	 0 0  1 0")));
		assertEquals(true, l1_1.equals(new Piece("0 1	0 0	 1 0  0 2")));
		assertEquals(true, l1_1.equals(new Piece("0 0	0 2	 0 1  1 0")));
		
		assertEquals(false, l1_1.equals(new Piece("0 0	0 1	 0 2  1 0  0 1")));
		assertEquals(false, l1_1.equals(new Piece("0 0	0 1	 0 2")));
		assertEquals(false, l1_1.equals(new Piece("0 0	0 1	 0 1  1 0")));
		assertEquals(false, l1_1.equals(new Piece("0 0	0 1	 0 2  1 0  0 0  0 2")));
		
		// L2 tests
		assertEquals(true, l2_1.equals(new Piece("0 0	1 1 1 0	 1 2")));
		assertEquals(true, l2_1.equals(new Piece("1 0	0 0 1 2	 1 1")));
		assertEquals(true, l2_1.equals(new Piece("0 0	1 2 1 1	 1 0")));
		assertEquals(true, l2_1.equals(new Piece("1 1	0 0 1 0	 1 2")));
		
		assertEquals(false, l2_1.equals(new Piece("0 0	1 1 1 0	 1 2  1 1")));
		assertEquals(false, l2_1.equals(new Piece("0 0	1 1 0 0	 1 2")));
		assertEquals(false, l2_1.equals(new Piece("0 1	1 1  1 0")));
		assertEquals(false, l2_1.equals(new Piece("0 0	1 0 1 0	 1 2")));				
	}
	
	@Test
	public void testEqual5() {
		// stick tests
		assertEquals(true, st1.equals(new Piece("0 0	0 1	 0 2  0 3")));
		assertEquals(true, st1.equals(new Piece("0 1	0 0	 0 2  0 3")));
		assertEquals(true, st1.equals(new Piece("0 2	0 3	 0 0  0 1")));
		assertEquals(true, st1.equals(new Piece("0 3	0 2	 0 1  0 0")));
		
		assertEquals(false, st1.equals(new Piece("0 0	0 1	 0 2  0 3  0 1")));
		assertEquals(false, st1.equals(new Piece("0 0	0 1	 0 2")));
		assertEquals(false, st1.equals(new Piece("0 0	0 0	 0 2  0 3")));
		assertEquals(false, st1.equals(new Piece("0 4	0 1	 0 2  0 3")));
		
	}
	
	@Test
	public void fastRotation1() {
		// pyramid Tests
		Piece[] pieces = Piece.getPieces();
		Piece pyr1 = pieces[Piece.PYRAMID];
		Piece pyr2, pyr3, pyr4;
			
		assertTrue(pyr1.computeNextRotation().equals(pyr1.fastRotation()));
		pyr2 = pyr1.fastRotation();
		
		assertTrue(pyr2.computeNextRotation().equals(pyr2.fastRotation()));
		pyr3 = pyr2.fastRotation();
		
		assertTrue(pyr3.computeNextRotation().equals(pyr3.fastRotation()));
		pyr4 = pyr3.fastRotation();
		
		assertTrue(pyr4.computeNextRotation().equals(pyr4.fastRotation()));		
	}
	
	@Test
	public void fastRotation2() {
		// square Tests
		Piece[] pieces = Piece.getPieces();
		Piece sq = pieces[Piece.SQUARE];
		
		assertTrue(sq.computeNextRotation().equals(sq.fastRotation()));
		sq = sq.fastRotation();
		assertTrue(sq.computeNextRotation().equals(sq.fastRotation()));
	}
	
	
	@Test
	public void fastRotation3() {
		Piece[] pieces = Piece.getPieces();
		
		// s 1 Tests
		Piece s1_1 = pieces[Piece.S1];
		Piece s1_2;
		assertTrue(s1_1.computeNextRotation().equals(s1_1.fastRotation()));
		s1_2 = s1_1.fastRotation();
		assertTrue(s1_1.equals(s1_2.fastRotation()));
		
		// s 2 Tests
		Piece s2_1 = pieces[Piece.S2];
		Piece s2_2;
		
		assertTrue(s2_1.computeNextRotation().equals(s2_1.fastRotation()));
		s2_2 = s2_1.fastRotation();
		assertTrue(s2_1.equals(s2_2.fastRotation()));
	}
	
	@Test
	public void fastRotation4() {
		Piece[] pieces = Piece.getPieces();
		
		// L 1 Tests
		Piece l1_1 = pieces[Piece.L1];
		Piece l1_2, l1_3, l1_4;
		
		assertTrue(l1_1.computeNextRotation().equals(l1_1.fastRotation()));
		l1_2 = l1_1.fastRotation();
		assertTrue(l1_2.computeNextRotation().equals(l1_2.fastRotation()));
		l1_3 = l1_2.fastRotation();
		assertTrue(l1_3.computeNextRotation().equals(l1_3.fastRotation()));
		l1_4 = l1_3.fastRotation();
		assertTrue(l1_1.equals(l1_4.fastRotation()));
		
		// L 2 Tests
		Piece l2_1 = pieces[Piece.L2];
		Piece l2_2, l2_3, l2_4;
		
		assertTrue(l2_1.computeNextRotation().equals(l2_1.fastRotation()));
		l2_2 = l2_1.fastRotation();
		assertTrue(l2_2.computeNextRotation().equals(l2_2.fastRotation()));
		l2_3 = l2_2.fastRotation();
		assertTrue(l2_3.computeNextRotation().equals(l2_3.fastRotation()));
		l2_4 = l2_3.fastRotation();
		assertTrue(l2_1.equals(l2_4.fastRotation()));
	}
	
	
	@Test
	public void fastRotation5() {
		// stick Tests
		Piece[] pieces = Piece.getPieces();
		Piece st1 = pieces[Piece.STICK];
		Piece st2;
		
		assertTrue(st1.computeNextRotation().equals(st1.fastRotation()));
		st2 = st1.fastRotation();
		assertTrue(st1.equals(st2.fastRotation()));	
	}
	
	
}
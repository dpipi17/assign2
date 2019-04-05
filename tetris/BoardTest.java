package tetris;

import static org.junit.Assert.*;

import org.junit.*;

public class BoardTest {
	Board b;
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s2_1, s2_2;
	private Piece l1_1, l1_2, l1_3, l1_4;
	private Piece l2_1, l2_2, l2_3, l2_4;
	private Piece sq;
	private Piece st1, st2; 
	private Piece s, sRotated;
	Piece l;
	Piece[] pieces;
	// This shows how to build things in setUp() to re-use
	// across tests.

	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@Before
	public void setUp() throws Exception {
		b = new Board(3, 6);
		pieces = Piece.getPieces();

		pyr1 = pieces[Piece.PYRAMID];
		pyr2 = pyr1.fastRotation();
		pyr3 = pyr2.fastRotation();
		pyr4 = pyr3.fastRotation();
		
		st1 = pieces[Piece.STICK];
		st2 = st1.fastRotation();
		st2.fastRotation();
		
		l1_1 = pieces[Piece.L1];
		l1_2 = l1_1.fastRotation();
		l1_3 = l1_2.fastRotation();
		l1_4 = l1_3.fastRotation();
		
		l2_1 = pieces[Piece.L2];
		l2_2 = l2_1.fastRotation();
		l2_3 = l2_2.fastRotation();
		l2_4 = l2_3.fastRotation();
		
		s = pieces[Piece.S1];
		sRotated = s.fastRotation();
		
		s2_1 = pieces[Piece.S2];
		s2_2 = s2_1.fastRotation();
		
		sq = pieces[Piece.SQUARE];
		b.place(pyr1, 0, 0);
	}

	// Check the basic width/height/max after the one placement
	@Test
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}

	// Place sRotated into the board, then check some measures
	@Test
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	

	// Make more tests, by putting together longer series of
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.

	
	// Place sRotated into the board, then check some measures
	@Test
	public void testSample3() {
		b.commit();
		//System.out.println(b.toString());
		int result = b.place(sRotated, 0, 1);
		assertEquals(Board.PLACE_BAD, result);
		
		b.undo();
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(2, b.getMaxHeight());
		result = b.place(l1_1, 3, 4);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);
		
		b.undo();
		result = b.place(st1, 0, 1);
		assertEquals(Board.PLACE_OK, result);
		
		b.commit();
		assertEquals(5, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(5, b.getMaxHeight());
		result = b.place(sRotated, 1, 1);
		//System.out.println(b.toString());
		assertEquals(Board.PLACE_ROW_FILLED, result);
		
		assertEquals(5, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(5, b.getMaxHeight());
		
	}
	

	@Test
	public void testClearRows1() {
		b.clearRows();

		assertEquals(0, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(1, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));
		assertEquals(1, b.getMaxHeight());
		
	} 
	
	
	@Test
	public void testClearRows2() {
		Board b = new Board(6, 10);

		b.place(l1_2, 0, 0);
		b.commit();
		b.place(l1_2, 3, 0);
		b.commit();
		
		System.out.println(b.toString());
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(1, b.getColumnHeight(3));
		assertEquals(1, b.getColumnHeight(4));
		assertEquals(2, b.getColumnHeight(5));
		assertEquals(2, b.getMaxHeight());
		
		assertEquals(6, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		
		b.clearRows();
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(0, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(0, b.getColumnHeight(3));
		assertEquals(0, b.getColumnHeight(4));
		assertEquals(1, b.getColumnHeight(5));
		assertEquals(1, b.getMaxHeight());
		
		assertEquals(2, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));	
	} 

	@Test
	public void testClearRows3() {
		Board b = new Board(7, 10);
		b.place(l1_2, 0, 0);
		b.commit();
		b.place(st2, 3, 0);
		b.commit();
		b.place(sq, 0, 1);
		
		b.commit();
		b.place(st1, 0, 3);
		b.commit();
		b.place(l2_4, 3, 1);
		b.commit();
		b.place(sRotated, 5, 1);
		b.commit();
		
		System.out.println(b.toString());
		assertEquals(7, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(3, b.getColumnHeight(3));
		assertEquals(2, b.getColumnHeight(4));
		assertEquals(4, b.getColumnHeight(5));
		assertEquals(3, b.getColumnHeight(6));
		assertEquals(7, b.getMaxHeight());
		
		assertEquals(7, b.getRowWidth(0));
		assertEquals(7, b.getRowWidth(1));
		assertEquals(5, b.getRowWidth(2));
		assertEquals(2, b.getRowWidth(3));
		assertEquals(1, b.getRowWidth(4));
		assertEquals(1, b.getRowWidth(5));
		assertEquals(1, b.getRowWidth(6));
		
		b.clearRows();
		assertEquals(5, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(1, b.getColumnHeight(3));
		assertEquals(0, b.getColumnHeight(4));
		assertEquals(2, b.getColumnHeight(5));
		assertEquals(1, b.getColumnHeight(6));
		assertEquals(5, b.getMaxHeight());
		
		assertEquals(5, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
		assertEquals(1, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));
		assertEquals(0, b.getRowWidth(6));	
	} 
	
	@Test
	public void testClearRows4() {
		Board b = new Board(7, 10);

		b.place(l1_2, 0, 0);
		b.commit();
		
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(0, b.getColumnHeight(3));
		assertEquals(0, b.getColumnHeight(4));
		assertEquals(0, b.getColumnHeight(5));
		assertEquals(0, b.getColumnHeight(6));
		assertEquals(2, b.getMaxHeight());
		
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));
		assertEquals(0, b.getRowWidth(6));
		
		b.clearRows();
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(0, b.getColumnHeight(3));
		assertEquals(0, b.getColumnHeight(4));
		assertEquals(0, b.getColumnHeight(5));
		assertEquals(0, b.getColumnHeight(6));
		assertEquals(2, b.getMaxHeight());
		
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));
		assertEquals(0, b.getRowWidth(6));	
	} 
	
	@Test
	public void testUndo1() {
		//System.out.println(b.toString());
		Board b = new Board(7, 10);

		b.place(l1_2, 0, 0);
		
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(0, b.getColumnHeight(3));
		assertEquals(0, b.getColumnHeight(4));
		assertEquals(0, b.getColumnHeight(5));
		assertEquals(0, b.getColumnHeight(6));
		assertEquals(2, b.getMaxHeight());
		
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));
		assertEquals(0, b.getRowWidth(6));
		
		b.undo();
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(0, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(0, b.getColumnHeight(3));
		assertEquals(0, b.getColumnHeight(4));
		assertEquals(0, b.getColumnHeight(5));
		assertEquals(0, b.getColumnHeight(6));
		assertEquals(0, b.getMaxHeight());
		
		assertEquals(0, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));
		assertEquals(0, b.getRowWidth(6));
	}
	
	@Test
	public void testUndo2() {
		//System.out.println(b.toString());
		Board b = new Board(7, 10);

		b.place(l1_2, 0, 0);
		b.commit();
		b.place(st2, 3, 0);
		b.commit();
		b.place(sq, 0, 1);
		b.commit();
		b.place(st1, 0, 3);
		b.commit();
		b.place(l2_4, 3, 1);
		b.commit();
		b.place(sRotated, 5, 1);
		
		assertEquals(7, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(3, b.getColumnHeight(3));
		assertEquals(2, b.getColumnHeight(4));
		assertEquals(4, b.getColumnHeight(5));
		assertEquals(3, b.getColumnHeight(6));
		assertEquals(7, b.getMaxHeight());
		
		assertEquals(7, b.getRowWidth(0));
		assertEquals(7, b.getRowWidth(1));
		assertEquals(5, b.getRowWidth(2));
		assertEquals(2, b.getRowWidth(3));
		assertEquals(1, b.getRowWidth(4));
		assertEquals(1, b.getRowWidth(5));
		assertEquals(1, b.getRowWidth(6));
		
		b.undo();
		
		assertEquals(7, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(3, b.getColumnHeight(3));
		assertEquals(2, b.getColumnHeight(4));
		assertEquals(2, b.getColumnHeight(5));
		assertEquals(1, b.getColumnHeight(6));
		assertEquals(7, b.getMaxHeight());
		
		assertEquals(7, b.getRowWidth(0));
		assertEquals(6, b.getRowWidth(1));
		assertEquals(3, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
		assertEquals(1, b.getRowWidth(4));
		assertEquals(1, b.getRowWidth(5));
		assertEquals(1, b.getRowWidth(6));
	}
	
	@Test
	public void testDropHeight1() {
		Board b = new Board(7, 10);

		b.place(l1_2, 0, 0);
		b.commit();
		b.place(l1_2, 3, 0);
		b.commit();
		System.out.println(b);
		
		assertEquals(1, b.dropHeight(sq, 0));
		assertEquals(2, b.dropHeight(sq, 1));
		assertEquals(1, b.dropHeight(sq, 3));
		
		assertEquals(2, b.dropHeight(st2, 0));
		assertEquals(2, b.dropHeight(st2, 2));
		assertEquals(2, b.dropHeight(st2, 3));
		
		assertEquals(2, b.dropHeight(l2_4, 0));
		assertEquals(2, b.dropHeight(l2_4, 2));
	}
	
	
	@Test
	public void testDropHeight2() {
		Board b = new Board(7, 10);

		b.place(l1_2, 0, 0);
		b.commit();
		b.place(st2, 3, 0);
		b.commit();
		b.place(sq, 0, 1);
		b.commit();
		b.place(st1, 0, 3);
		b.commit();
		b.place(l2_4, 3, 1);
		b.commit();
		b.place(sRotated, 5, 1);
		b.commit();
		
		assertEquals(7, b.dropHeight(sq, 0));
		assertEquals(3, b.dropHeight(sq, 1));
		assertEquals(3, b.dropHeight(sq, 3));
		
		assertEquals(3, b.dropHeight(st2, 1));
		assertEquals(4, b.dropHeight(st2, 2));
		assertEquals(4, b.dropHeight(st2, 3));
		
		assertEquals(7, b.dropHeight(l2_4, 0));
		assertEquals(3, b.dropHeight(l2_4, 1));
		assertEquals(3, b.dropHeight(l2_4, 2));
		assertEquals(4, b.dropHeight(l2_4, 3));
		
		assertEquals(2, b.dropHeight(sRotated, 3));
		assertEquals(4, b.dropHeight(sRotated, 4));
		assertEquals(3, b.dropHeight(sRotated, 2));
		assertEquals(2, b.dropHeight(sRotated, 1));
	}
	

	
}

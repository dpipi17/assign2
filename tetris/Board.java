// Board.java
package tetris;

import java.util.Arrays;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * CS108 Tetris Board. Represents a Tetris board -- essentially a 2-d grid of
 * booleans. Supports tetris pieces and row clearing. Has an "undo" feature that
 * allows clients to add and remove pieces efficiently. Does not do any drawing
 * or have any idea of pixels. Instead, just represents the abstract 2-d board.
 */
public class Board {
	// Some ivars are stubbed out for you:
	private int width; 
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	private int[] widths;
	private int[] heights;
	private int maxHeight;

	private boolean[][] undo_grid;
	private int[] undo_widths;
	private int[] undo_heights;
	private int undo_maxHeight;

	// Here a few trivial methods are provided:

	/**
	 * Creates an empty board of the given width and height measured in blocks.
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		undo_grid = new boolean[width][height];
		committed = true;

		widths = new int[height];
		undo_widths = new int[height];

		heights = new int[width];
		undo_heights = new int[width];
		maxHeight = 0;
		undo_maxHeight = 0;
	}

	/**
	 * Returns the width of the board in blocks.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the board in blocks.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the max column height present in the board. For an empty board this
	 * is 0.
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	/**
	 * Checks the board for internal consistency -- used for debugging.
	 */
	public void sanityCheck() {
		if (DEBUG) {
			int realMaxHeight = 0;
			int[] realHeights = new int[width];
			int[] realWidths = new int[height];

			// finds Real heights array
			for (int i = 0; i < getWidth(); i++) {
				for (int j = getHeight() - 1; j >= 0; j--) {
					if (grid[i][j]) {
						realHeights[i] = j + 1;
						break;
					}
				}
			} 

			// finds Real widths array
			for (int j = 0; j < getHeight(); j++) {
				int currWidth = 0;
				for (int i = 0; i < getWidth(); i++) {
					if (grid[i][j]) {
						currWidth++;
					}
				}
				realWidths[j] = currWidth;
			}

			// gets realMaxHeigt
			for (int i = 0; i < width; i++) {
				realMaxHeight = Math.max(realMaxHeight, realHeights[i]);
			}

			if (realMaxHeight != getMaxHeight()) {
				throw new RuntimeException("getMaxHeight is incorrect");
			} else if (!Arrays.equals(widths, realWidths)) {
				throw new RuntimeException("widths array is incorrect");
			} else if (!Arrays.equals(heights, realHeights)) {
				throw new RuntimeException("heights array is incorrect");
			}
		}
	}

	/**
	 * Given a piece and an x, returns the y value where the piece would come to
	 * rest if it were dropped straight down at that x.
	 * 
	 * <p>
	 * Implementation: use the skirt and the col heights to compute this fast --
	 * O(skirt length).
	 */
	public int dropHeight(Piece piece, int x) {
		int result = 0;
		int[] skirt = piece.getSkirt();
		for (int i = 0; i < skirt.length; i++) {
			result = Math.max(result, heights[x + i] - skirt[i]);
		}

		return result;
	}

	/**
	 * Returns the height of the given column -- i.e. the y value of the highest
	 * block + 1. The height is 0 if the column contains no blocks.
	 */
	public int getColumnHeight(int x) {
		return heights[x];
	}

	/**
	 * Returns the number of filled blocks in the given row.
	 */
	public int getRowWidth(int y) {
		return widths[y];
	}

	// returns true if the invalid width/height area
	private boolean outOfBounds(int x, int y) {
		return (x >= width || x < 0 || y >= height || y < 0);
	}

	/**
	 * Returns true if the given block is filled in the board. Blocks outside of the
	 * valid width/height area always return true.
	 */
	public boolean getGrid(int x, int y) {
		return grid[x][y] || outOfBounds(x, y);
	}

	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 * Attempts to add the body of a piece to the board. Copies the piece blocks
	 * into the board grid. Returns PLACE_OK for a regular placement, or
	 * PLACE_ROW_FILLED for a regular placement that causes at least one row to be
	 * filled.
	 * 
	 * <p>
	 * Error cases: A placement may fail in two ways. First, if part of the piece
	 * may falls out of bounds of the board, PLACE_OUT_BOUNDS is returned. Or the
	 * placement may collide with existing blocks in the grid in which case
	 * PLACE_BAD is returned. In both error cases, the board may be left in an
	 * invalid state. The client can use undo(), to recover the valid, pre-place
	 * state.
	 */
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed)
			throw new RuntimeException("place commit problem");

		int result = PLACE_OK;
		committed = false;
		backUp();

		TPoint[] body = piece.getBody();
		for (int i = 0; i < body.length; i++) {
			TPoint currPt = body[i];
			int newX = x + currPt.x;
			int newY = y + currPt.y;

			if (outOfBounds(newX, newY)) {
				return PLACE_OUT_BOUNDS;
			}

			if (getGrid(newX, newY)) {
				return PLACE_BAD;
			}

			grid[newX][newY] = true;
			heights[newX] = Math.max(getColumnHeight(newX), newY + 1);

			maxHeight = Math.max(maxHeight, getColumnHeight(newX));

			widths[newY]++;
			if (getRowWidth(newY) == getWidth()) {
				result = PLACE_ROW_FILLED;
			}
		}

		sanityCheck();
		return result;
	}

	// method to backUp arrays for undo operation
	private void backUp() {
		System.arraycopy(widths, 0, undo_widths, 0, widths.length);
		System.arraycopy(heights, 0, undo_heights, 0, heights.length);
		undo_maxHeight = maxHeight;

		for (int i = 0; i < grid.length; i++) {
			System.arraycopy(grid[i], 0, undo_grid[i], 0, grid[i].length);
		}

	}

	/**
	 * Deletes rows that are filled all the way across, moving things above down.
	 * Returns the number of rows cleared.
	 */
	public int clearRows() {
		int rowsCleared = 0;
		
		int newRow = 0;
		for (int row = 0; row < getMaxHeight(); row++) {
			boolean isClearRow = getRowWidth(row) == getWidth();

			if (isClearRow) {
				rowsCleared++;
			} else {
				if (newRow != row) {
					for (int col = 0; col < getWidth(); col++) {
						grid[col][newRow] = grid[col][row];
					}
					widths[newRow] = widths[row];
				}
				newRow++;
			}
		}
		
		for(int row = newRow; row < getMaxHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				grid[col][row] = false;
				heights[col] = 0;
			}
			widths[row] = 0;	
		}
		
		maxHeight -= rowsCleared;
		for (int i = 0; i < getWidth(); i++) {
			for (int j = getHeight() - 1; j >= 0; j--) {
				if (grid[i][j]) {
					heights[i] = j + 1;
					break;
				}
			}
		} 
		committed = false;
		sanityCheck();
		return rowsCleared;
	}

	/**
	 * Reverts the board to its state before up to one place and one clearRows(); If
	 * the conditions for undo() are not met, such as calling undo() twice in a row,
	 * then the second undo() does nothing. See the overview docs.
	 */
	public void undo() {
		// if committed is true, method should do nothing.
		if (!committed) {
			maxHeight = undo_maxHeight;

			int[] tmp_widths = widths;
			widths = undo_widths;
			undo_widths = tmp_widths;

			int[] tmp_heights = heights;
			heights = undo_heights;
			undo_heights = tmp_heights;

			boolean[][] tmp_grid = grid;
			grid = undo_grid;
			undo_grid = tmp_grid;
			commit();
		}
		sanityCheck();
	}

	/**
	 * Puts the board in the committed state.
	 */
	public void commit() {
		committed = true;
	}

	/*
	 * Renders the board state as a big String, suitable for printing. This is the
	 * sort of print-obj-state utility that can help see complex state change over
	 * time. (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height - 1; y >= 0; y--) {
			buff.append('|');
			for (int x = 0; x < width; x++) {
				if (getGrid(x, y))
					buff.append('+');
				else
					buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x = 0; x < width + 2; x++)
			buff.append('-');
		return (buff.toString());
	}
}

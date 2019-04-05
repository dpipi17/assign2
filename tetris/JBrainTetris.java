package tetris;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class JBrainTetris extends JTetris {
	JCheckBox brainMode;
	Brain br;
	JCheckBox AnimateFall;
	private int countTemp;
	Brain.Move bestMove;
	
	
	/**
	 * Creates a new JBrainTetris;
	 * 
	 */
	JBrainTetris(int pixels) {
		//uses its parent constructor.
		super(pixels);
		
		// creates  DefaultBrain object;
		br = new DefaultBrain();
		
	}
	
	@Override
	public JComponent createControlPanel() {
		JPanel newPanel = (JPanel)super.createControlPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		
		newPanel.add(new JLabel("Brain:")); 
		brainMode = new JCheckBox("Brain active", false); 
		newPanel.add(brainMode);
		
		AnimateFall = new JCheckBox("Animate Falling", true); 
		newPanel.add(AnimateFall);

		
		return newPanel;
	}
	
	@Override
	public void tick(int verb) {
		
		if(verb == JTetris.DOWN && brainMode.isSelected()) {
			
			// new Piece situation
			if(countTemp != count) {
				countTemp = count;
				// return board in committed situation
				board.undo();
				bestMove = br.bestMove(board, currentPiece, board.getHeight(), bestMove);
			}
			
			if(bestMove != null) {
				changes(bestMove);
			}
		}
		
		// call parent's tick function
		super.tick(verb);
	}
	
	
	//  brain may do up to one rotation and one left/right move each time 
	private void changes(Brain.Move bestMove) {
		// if current Piece and best situation piece is not same, program should rotate current piece.
		if(!currentPiece.equals(bestMove.piece)) {
			super.tick(ROTATE);
		}
		
		// piece needs to move right
		if(bestMove.x > currentX) {
			super.tick(RIGHT);
		} else if(bestMove.x < currentX) { // piece needs to move left
			super.tick(LEFT);
		} else  if((bestMove.x == currentX && !AnimateFall.isSelected()) && currentY > bestMove.y) {
			// if animateFall is not selected, program drops piece to its best position
			super.tick(DROP);
		}
		
	}
	/**
	 Creates a frame with a JBrainTetris.
	*/
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		JBrainTetris tetris = new JBrainTetris(16);
		JFrame frame = JTetris.createFrame(tetris);
		frame.setVisible(true);
	}

}

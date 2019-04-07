package tetris;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;

public class JBrainTetris extends JTetris {
	JCheckBox brainMode;
	Brain br;
	JCheckBox AnimateFall;
	private int countTemp;
	
	Brain.Move bestMove;
	Brain.Move badMove;
	
	
	JLabel ok;
	JPanel little;
	JSlider adversary;
	
	/**
	 * Creates a new JBrainTetris;
	 * 
	 */
	JBrainTetris(int pixels) {
		//uses its parent's constructor.
		super(pixels);
		
		// creates  DefaultBrain object;
		br = new DefaultBrain();
		
	}
	
	@Override
	public JComponent createControlPanel() {
		JPanel newPanel = (JPanel)super.createControlPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		
		// make a little panel, put a JSlider in it. JSlider responds to getValue() 
		little = new JPanel(); 
		little.add(new JLabel("Adversary:")); 
		adversary = new JSlider(0, 100, 0);    // min, max, current 
		adversary.setPreferredSize(new Dimension(100,15)); 
		little.add(adversary);
		newPanel.add(little);
		
		JPanel okPan = new JPanel();
		ok = new JLabel("ok");
		okPan.add(ok);
		newPanel.add(okPan);
		
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
	
	
	@Override
	public Piece pickNextPiece() {
		Piece nextPiece = super.pickNextPiece();
		int advVal = adversary.getValue();
		
		if(random.nextInt(99) >= advVal) {	
			//update ok status
			ok.setText("ok");
			return nextPiece;
		} else {
			ok.setText("*ok*");
			// current worst score
			double largestScore = Integer.MIN_VALUE; 
			
			for(int i = 0; i < Piece.getPieces().length ; i++) {
				Piece currPiece = Piece.getPieces()[i];
				badMove = br.bestMove(board, currPiece, board.getHeight(), badMove);
				if(badMove == null) return nextPiece;
				
				if(badMove.score > largestScore) {
					nextPiece = currPiece;
					largestScore = badMove.score;
				}
			}
			
			return nextPiece;
			
		}
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

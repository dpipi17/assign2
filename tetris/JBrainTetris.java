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
		brainMode = new JCheckBox("Brain active"); 
		newPanel.add(brainMode);

		
		return newPanel;
	}

	
	/**
	 Creates a frame with a JBrainTetris.
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		JBrainTetris tetris = new JBrainTetris(16);
		JFrame frame = JTetris.createFrame(tetris);
		frame.setVisible(true);
	}

}

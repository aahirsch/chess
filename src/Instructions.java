import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Instructions extends JOptionPane {

	JFrame frame;
	String msg = "Welcome to Chess!\n\nChess is a two-player game"
			+ " in which both players use the mouse\n"
			+ "to select the piece they want to move\n"
			+ "and the desired square to move to."
			+ "\nThe move will only work if it is a legal chess\n"
			+ "move (many advanced chess logics have been programmed in--\n"
			+ "will leave it to the user to figure out which have not!)"
			+ "\nand the move is on turn.  If you make an ~oops!~  click 'undo.' \n\n"
			+ "Click \"OK\" to play.\n\nGood Luck!";

	public Instructions(JFrame frame) {
//		insButton = new JButton("text");
//		insButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				select(insButton);
//			}
//		});
		this.frame = frame;
		this.setVisible(true);
		this.setLocation(300, 300);
		this.setPreferredSize(new Dimension(800, 800));
		this.setVisible(true);
		
		JOptionPane.showMessageDialog(frame, msg, "Instructions", JOptionPane.OK_OPTION);
	}

	private void select(JButton insButton) {
		this.setVisible(false);
		frame.setVisible(true);
	}

	public Instructions getIns() {
		return this;
	}

}

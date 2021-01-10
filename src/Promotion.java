import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class Promotion extends JOptionPane {

	String msg = "Which piece would you like?";
	JFrame frame;

	public Promotion(JFrame frame) {
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

	public Promotion getIns() {
		return this;
	}

}

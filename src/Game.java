

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {

	public void run() {

		// Top-level frame in which game components live
		final JFrame frame = new JFrame("Chess");
		frame.setLocation(300, 300);
		frame.setPreferredSize(new Dimension(800,800));
		frame.setVisible(false);
		

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Running Chess...");
		status_panel.add(status);

		// Main playing area
		final GameBoard board = new GameBoard(status);
		
        //Instructions window
		//final JOptionPane frame2 = new JOptionPane();
        final Instructions insButton = new Instructions(frame);
        frame.add(board, BorderLayout.CENTER);
//		frame2.setLocation(300, 300);
//		frame2.setPreferredSize(new Dimension(800,800));
//		frame2.setVisible(true);
//        frame2.add(insButton.getIns(), BorderLayout.CENTER);


		frame.add(board.getBoard(), BorderLayout.CENTER);
		frame.repaint();

		// undo button
		final JPanel control_panel = new JPanel();
		final JButton undo = new JButton("Oops! Undo");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.boardModel.playerUndo();
				board.refreshBoard();
				board.repaint();
			}
		});
		control_panel.add(undo);
		frame.add(control_panel, BorderLayout.NORTH);

		//new game button
//		final JButton newGame = new JButton("New Game");
//		newGame.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				GameBoard board = new GameBoard(status);
//		        frame.add(board, BorderLayout.CENTER);
//			}
//		});
//		frame.add(newGame);

		// Note here that when we add an action listener to the reset button, we define
		// it as an
		// anonymous inner class that is an instance of ActionListener with its
		// actionPerformed()
		// method overridden. When the button is pressed, actionPerformed() will be
		// called.
//        final JButton reset = new JButton("Reset");
//        reset.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                window.reset();
//            }
//        });
//        control_panel.add(reset);

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
//        court.reset();
	}
	

	/**
	 * Main method run to start and run the game. Initializes the GUI elements
	 * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
	 * this in your final submission.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
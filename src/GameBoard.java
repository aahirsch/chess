

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GameBoard extends JPanel {

	public BoardModel boardModel;
	private Images images;
	ImageIcon piecePic;
	JLabel icon;
	private JPanel board;
	final String IMAGE_DIR = "/images/";
	private JButton[][] grid = new JButton[8][8];
	private JButton selected1 = null;
	private JButton selected2 = null;

	public GameBoard(JLabel status) {
		boardModel = new BoardModel();
		board = new JPanel();
		board.setLayout(new GridLayout(8, 8, 0, 0));
		board.setPreferredSize(new Dimension(800, 800));
		board.setVisible(true);
		images = new Images();
		newGame();
	}

	public void newGame() {
		boardModel.newGame();
		String[][] strBoard = boardModel.getBoard();

		for (int row = 7; row >= 0; row--) {
			for (int col = 0; col < 8; col++) {
				JButton b = new JButton();
				b.setOpaque(true);
				b.setBorderPainted(false);
				b.setMargin(new Insets(0, 0, 0, 0));
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						select(b);
					}
				});
				grid[row][col] = b;
				if ((row + col) % 2 == 0) {
					grid[row][col].setBackground(Color.PINK);
					grid[row][col].setForeground(Color.PINK);
				} else {
					grid[row][col].setBackground(Color.LIGHT_GRAY);
					grid[row][col].setForeground(Color.LIGHT_GRAY);
				}
				board.add("Row: " + Integer.toString(row) + " Col: " + Integer.toString(col),
						grid[row][col]);
			}
		}
		refreshBoard();
	}

	public void refreshBoard() {
		String[][] strBoard = boardModel.getBoard();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (strBoard[row][col] != null) {
					grid[row][col].setIcon(images.getImage(strBoard[row][col].trim()));
				} else {
					grid[row][col].setIcon(null);
				}
			}
		}
	}

	public JPanel getBoard() {
		return board;
	}

	public void select(JButton b) {
		Dimension currSq = null;
		Dimension nextSq = null;
		if (selected1 == null) {
			selected1 = b;
			selected1.setContentAreaFilled(false);
			return;
		} else if (selected2 == null) {
			selected2 = b;
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if (grid[row][col] == selected1) {
						currSq = new Dimension(row, col);
					}
					if (grid[row][col] == selected2) {
						nextSq = new Dimension(row, col);
						boardModel.printDim(nextSq);
					}
				}
			}
			move(currSq, nextSq);
			selected1.setContentAreaFilled(true);
			selected1 = null;
			selected2 = null;
		}
	}

	private void move(Dimension currSq, Dimension nextSq) {
		System.out.println(boardModel.isLegalMove(currSq, nextSq));
		refreshBoard();
		this.repaint();
		boardModel.printBoard();
		boardModel.printTurn();
	}
}



import java.awt.Dimension;

public class King extends GamePiece {

	private BoardModel board;
	private boolean hasMoved;
	private final Piece pieceType = Piece.KING;

	public King(Color color, BoardModel board) {
		super(color, board);
		this.board = board;
		hasMoved = false;
	}
	
	public King(Color color, BoardModel board, Boolean hasMoved) {
		super(color, board);
		this.board = board;
		this.hasMoved = hasMoved;
	}

	@Override
	public boolean move(Dimension currSq, Dimension nextSq) {
		int row1 = getCurrRow(currSq);
		int col1 = getCurrCol(currSq);
		int row2 = getNextRow(nextSq);
		int col2 = getNextCol(nextSq);
		if (Math.abs(row2 - row1) <= 1 && Math.abs(col2 - col1) <= 1 
				&& Math.abs(row2 - row1 + col2 - col1) > 0) {
			hasMoved = true;
			return board.isClearPath(row1, col1, row2, col2);
		} else if (!hasMoved) {
			if (board.castle(currSq, nextSq, row1, col1, row2, col2)) {
				hasMoved = true;
				return true;
			}
		}
		return false;
	}

	
	public boolean hasMoved() {
		return hasMoved;
	}
}

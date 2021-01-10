

import java.awt.Dimension;

public class Rook extends GamePiece {

	private BoardModel board;
	private boolean hasMoved; // if piece has moved yet this game
	private final Piece pieceType = Piece.ROOK;

	public Rook(Color color, BoardModel board) {
		super(color, board);
		this.board = board;
		hasMoved = false;
	}
	
	public Rook(Color color, BoardModel board, boolean hasMoved) {
		super(color, board);
		this.board = board;
		this.hasMoved = hasMoved;
	}
	
    public Rook(Color color, BoardModel board, int numMoves, boolean promo) {
        super(color, board, numMoves, promo);
        this.board = board;
    }

	@Override
	public boolean move(Dimension currSq, Dimension nextSq) {
		int row1 = getCurrRow(currSq);
		int col1 = getCurrCol(currSq);
		int row2 = getNextRow(nextSq);
		int col2 = getNextCol(nextSq);
		if ((row2 == row1 || col2 == col1) && board.isClearPath(row1, col1, row2, col2)) {
			hasMoved = true;
			return true;
		}
		return false;
	}
	
	public boolean castle(int row1, int col1, Dimension nextSq) {
		int row2 = getNextRow(nextSq);
		int col2 = getNextCol(nextSq);
		if (!hasMoved && board.isClearPath(row1, col1, row2, col2)) {
			hasMoved = true;
			return true;
		}
		return false;
	}

	public boolean hasMoved() {
		return hasMoved;
	}
	
}

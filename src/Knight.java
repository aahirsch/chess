

import java.awt.Dimension;

public class Knight extends GamePiece {

	private BoardModel board;
	private final Piece pieceType = Piece.KING;
	
	public Knight(Color color, BoardModel board) {
		super(color, board);
		this.board = board;
	}

    public Knight(Color color, BoardModel board, int numMoves, boolean promo) {
        super(color, board, numMoves, promo);
        this.board = board;
    }

	@Override
	public boolean move(Dimension currSq, Dimension nextSq) {
		int row1 = getCurrRow(currSq);
		int col1 = getCurrCol(currSq);
		int row2 = getNextRow(nextSq);
		int col2 = getNextCol(nextSq);
		return ((Math.abs(row2 - row1) == 2 && Math.abs(col2 - col1) == 1)
				|| (Math.abs(col2 - col1) == 2 && Math.abs(row2 - row1) == 1));
	}

}

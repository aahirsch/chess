
import java.awt.Dimension;

public class Queen extends GamePiece {

	BoardModel board;
	private final Piece pieceType = Piece.QUEEN;
	
	public Queen(Color color, BoardModel board) {
		super(color, board);
		this.board = board;
	}
	
    public Queen(Color color, BoardModel board, int numMoves, boolean promo) {
        super(color, board, numMoves, promo);
        this.board = board;
    }
	
	@Override
	public boolean move(Dimension currSq, Dimension nextSq) {
		int row1 = getCurrRow(currSq);
		int col1 = getCurrCol(currSq);
		int row2 = getNextRow(nextSq);
		int col2 = getNextCol(nextSq);
		if (row1 != row2 && col1 != col2 && Math.abs(row1 - row2) != Math.abs(col1 - col2)) {
			return false;
		}
		return board.isClearPath(row1, col1, row2, col2);
	}

}

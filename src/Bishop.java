
import java.awt.Dimension;

public class Bishop extends GamePiece {

	private BoardModel board;
	private final Piece pieceType = Piece.BISHOP;
	
	public Bishop(Color color, BoardModel board) {
		super(color, board);
		this.board = board;
	}
	
   public Bishop(Color color, BoardModel board, int numMoves, boolean promo) {
        super(color, board, numMoves, promo);
        this.board = board;
    }
	
	@Override
	public boolean move(Dimension currSq, Dimension nextSq) {
		int row1 = getCurrRow(currSq);
		int col1 = getCurrCol(currSq);
		int row2 = getNextRow(nextSq);
		int col2 = getNextCol(nextSq);
		return (Math.abs(row2 - row1) == Math.abs(col2 - col1) && board.isClearPath(row1, col1, row2, col2));
	}
}

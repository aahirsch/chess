
import java.awt.Dimension;

public abstract class GamePiece {
	
	private Piece pieceType;
	private final Color COLOR;
	private final BoardModel board;
	private boolean hasMoved;
	int numMoves;
	private boolean promo;
	
	public GamePiece(Color color, BoardModel board) {
		this.board = board;
		this.COLOR = color;
	}
	
    public GamePiece(Color color, BoardModel board, int numMoves, boolean promo) {
        this.board = board;
        this.COLOR = color;
        this.numMoves = numMoves;
        this.promo = promo;
    }
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	public Color getColor() {
		return COLOR;
	}
	
	public Piece getPieceType() {
		return pieceType;
	}
	
    public int getNumMoves() {
        return numMoves;
    }
    
    void setNumMoves(int numMoves) {
        this.numMoves = numMoves;
    }
	
	public int getCurrRow(Dimension currSq) {
		return (int) currSq.getWidth();
	}
	
	public int getCurrCol(Dimension currSq) {
		return (int) currSq.getHeight();
	}
	
	public int getNextRow(Dimension nextSq) {
		return (int) nextSq.getWidth();
	}
	
	public int getNextCol(Dimension nextSq) {
		return (int) nextSq.getHeight();
	}
	
	public abstract boolean move(Dimension currSq, Dimension nextSq);
	
}

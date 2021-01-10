

import java.awt.Dimension;
import java.util.Set;
import java.util.TreeSet;

public class Move {

	private Dimension currSq;
	private Dimension nextSq;
	private GamePiece take;
	private Set<Dimension> hasMovedPieces;
	private Set<Dimension> enPassantPawns;
	private boolean takeEnPassant;
	private boolean takeHasMoved;
	private boolean pieceEnPassant;
	private boolean pieceHasMoved;
	private GamePiece piece;
	private Piece takePieceType;
	private Piece pieceType;
	private Color color;

	public Move(Dimension currSq, Dimension nextSq, boolean pieceHasMoved, GamePiece take) {
		this.currSq = currSq;
		this.nextSq = nextSq;
		this.pieceHasMoved = pieceHasMoved;
		this.take = take;
		if (take != null) {
			takeHasMoved = take.hasMoved();
			takePieceType = take.getPieceType();
			color = take.getColor();
			if (take instanceof Pawn) {
				takeEnPassant = ((Pawn) take).getEnPassant();
			}
		} else {
			takePieceType = Piece.NONE;
		}
	}
	
	public Move(Dimension currSq, Dimension nextSq, GamePiece piece, boolean pieceHasMoved, GamePiece take) {
		this.currSq = currSq;
		this.nextSq = nextSq;
		this.piece = piece;
		this.pieceHasMoved = piece.hasMoved();
		this.piece.setNumMoves(piece.getNumMoves() + 1);
		if (this.piece == null) {
			System.out.println("movenull");
		}
		this.take = take;
		if (take != null) {
			takeHasMoved = take.hasMoved();
			takePieceType = take.getPieceType();
			color = take.getColor();
			if (take instanceof Pawn) {
				takeEnPassant = ((Pawn) take).getEnPassant();
			}
		} else {
			takePieceType = Piece.NONE;
		}
		
//		this.currSq = currSq;
//		this.nextSq = nextSq;
//		this.piece = piece;
//		this.pieceType = piece.getPieceType();
//		this.pieceHasMoved = pieceHasMoved;
//		this.take = take;
//		if (piece instanceof Pawn) {
//			this.pieceEnPassant = ((Pawn) piece).getEnPassant();
//		}
//		if (take instanceof Pawn) {
//			takeHasMoved = take.hasMoved();
//			takePieceType = Piece.PAWN;
//			color = take.getColor();
//			takeEnPassant = ((Pawn) take).getEnPassant();
//		} else if (take instanceof Rook) {
//			takeHasMoved = take.hasMoved();
//			takePieceType = Piece.ROOK;
//			color = take.getColor();
//		} else if (take instanceof Knight) {
//			takeHasMoved = take.hasMoved();
//			takePieceType = Piece.KNIGHT;
//			color = take.getColor();
//		} else if (take instanceof Bishop) {
//			takeHasMoved = take.hasMoved();
//			takePieceType = Piece.BISHOP;
//			color = take.getColor();
//		} else if (take instanceof Queen) {
//			takeHasMoved = take.hasMoved();
//			takePieceType = Piece.QUEEN;
//			color = take.getColor();
//		}
//		else {
//			takePieceType = Piece.NONE;
//		}
	}
	
	
	public Move(Dimension currSq, Dimension nextSq, GamePiece take, TreeSet<Dimension> hasMovedPieces, TreeSet<Dimension> enPassantPawns) {
		this.currSq = currSq;
		this.nextSq = nextSq;
		this.take = take;
		this.enPassantPawns = enPassantPawns;
		if (take != null) {
			takeHasMoved = take.hasMoved();
			takePieceType = take.getPieceType();
			color = take.getColor();
			if (take instanceof Pawn) {
				takeEnPassant = ((Pawn) take).getEnPassant();
			}
		} else {
			takePieceType = Piece.NONE;
		}
	}

	/*
	 * Getters
	 */
	public Dimension getCurrSq() {
		return currSq;
	}

	public Dimension getNextSq() {
		return nextSq;
	}

	public Piece getTakePieceType() {
		return takePieceType;
	}
	
	public Piece getPieceType() {
		return pieceType;
	}
	
	public GamePiece getPiece() {
		return piece;
	}
	
	public GamePiece getTake() {
		return take;
	}

	public Color getColor() {
		return color;
	}

	public boolean getTakeEnPassant() {
		return takeEnPassant;
	}

	public boolean takeHasMoved() {
		return takeHasMoved;
	}
	
	public boolean pieceHasMoved() {
		return pieceHasMoved;
	}

//	public GamePiece getTake() {
//		return take;
//	}

}


import java.awt.Dimension;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JOptionPane;

public class Pawn extends GamePiece {

    private BoardModel board;
    private boolean enP;
    private boolean hasMoved;
    private Piece pieceType = Piece.PAWN;
    private Set<GamePiece> enPiece;
//    private int numMoves;

    public Pawn(Color color, BoardModel board) {
        super(color, board);
        this.board = board;
        hasMoved = false;
        enP = false;
        enPiece = new TreeSet<GamePiece>();
    }

    public Pawn(Color color, BoardModel board, boolean hasMoved, boolean enP) {
        super(color, board);
        this.board = board;
        this.hasMoved = hasMoved;
        this.enP = enP;
    }

    public Pawn(Color color, BoardModel board, boolean hasMoved, boolean enP, int numMoves,
            boolean promo) {
        super(color, board, numMoves, promo);
        this.board = board;
        this.hasMoved = hasMoved;
        this.enP = enP;
    }

    @Override
    public boolean move(Dimension currSq, Dimension nextSq) {
        int col1 = getCurrCol(currSq);
        int row1 = getCurrRow(currSq);
        int col2 = getNextCol(nextSq);
        int row2 = getNextRow(nextSq);
        if (getColor() == Color.WHITE) {
            return whiteMove(row1, col1, row2, col2);
        } else {
            return blackMove(row1, col1, row2, col2);
        }
    }

    public boolean whiteMove(int row1, int col1, int row2, int col2) {
        if (col2 == col1) {
            if (board.isClearPath(row1, col1, row2 + 1, col2)) {
                if (row2 - row1 == 2) { // trying to move two squares
                    return moveTwo(row1, col1, row2, col2);
                } else if (row2 - row1 == 1) {
                    return moveOne(row2, new Dimension(row1, col1), new Dimension(row2, col2));
                }

//		if (!board.isPiece(new Dimension(row2, col2))) {
//			if ((row2 - row1 == 2) && (col2 == col1)) { // trying to move two squares
//				return moveTwo(row1, col1, row2, col2);
////			} else if (board.isEnP(new Dimension(row2 - 1, col2))) {
////				//TODO enPassant
//			} else if ((row2 - row1 == 1) && (col2 == col1)) { // trying to move one square
//				if (row2 == 7) {
//					return promotion(row1, col1, row2, col2);
//				} else {
//					return moveOne();
//				}
//			}
//		} else if ((row2 - row1 == 1) && (Math.abs(col2 - col1) == 1)) { // take on catty-corner
//																			// diagonal square
//			return take(row1, col1, row2, col2);
//		}
//		return false;
//	}
            }
        } else if ((row2 - row1 == 1) && (Math.abs(col2 - col1) == 1) && board.isPiece(
                new Dimension(row2, col2))) { // take on catty-corner diagonal
                                              // square
            if (board.getColor(new Dimension(row2, col2)) != getColor()) {
                return take(row1, col1, row2, col2);
            }

        }
        return false;
    }

    public boolean blackMove(int row1, int col1, int row2, int col2) {
        if (col2 == col1) {
            if (board.isClearPath(row1, col1, row2 - 1, col2)) {
                if (row2 - row1 == -2) { // trying to move two squares
                    return moveTwo(row1, col1, row2, col2);
                } else if (row2 - row1 == -1) {
                    return moveOne(row2, new Dimension(row1, col1), new Dimension(row2, col2));
                }
//		if (!board.isPiece(new Dimension(row2, col2))) {
//			if ((row2 - row1 == -2) && (col2 == col1)) { // trying to move two squares
//				return moveTwo(row1, col1, row2, col2);
//			} else if ((row2 - row1 == -1) && (col2 == col1)) { // trying to move one square
//				if (row2 == 0) {
//					return promotion(row1, col1, row2, col2);
//				} else {
//					return moveOne();
//				}
//			}
            }
        } else if ((row2 - row1 == -1) && (Math.abs(col2 - col1) == 1) && board.isPiece(
                new Dimension(row2, col2))) { // take on catty-corner diagonal
            if (board.getColor(new Dimension(row2, col2)) != getColor()) {
                return take(row1, col1, row2, col2);
            }

        }
        return false;
    }

    public boolean moveTwo(int row1, int col1, int row2, int col2) {
        if (numMoves == 0 && board.isClearPath(row1, col1, row2, col2)) { // only if first move by
                                                                         // pawn
            setHasMoved(true);
            enP = true;
            board.enPassantCheck(row2, col2);
            return true;
        }
        return false;
    }

    public boolean moveOne(int row2, Dimension currSq, Dimension nextSq) {
        setHasMoved(true);
        if (row2 == 7 || row2 == 0) {
            board.promotion(currSq, nextSq, this);
        }
        return true;
    }

    public boolean take(int row1, int col1, int row2, int col2) {
        hasMoved = true;
        return ((getColor()) != board.getColor(new Dimension(row2, col2)));
    }

//	private void enPassantCheck(int col2, int row2) {
//		GamePiece adjPieceR = board.getPiece(new Dimension(Math.min(col2 + 1, 7), row2));
//		GamePiece adjPieceL = board.getPiece(new Dimension(Math.max(col2 - 1, 0), row2));
//		if (adjPieceR != null) {
//			if (adjPieceR instanceof Pawn) {
//				Pawn pawn = (Pawn) adjPieceR;
//				pawn.enPassantSwitch();
//			}
//		}
//		if (adjPieceL != null) {
//			if (adjPieceL instanceof Pawn) {
//				Pawn pawn = (Pawn) adjPieceL;
//				pawn.enPassantSwitch();
//			}
//		}
//	}

    public boolean getEnPassant() {
        return enP;
    }

    public void enPassantSwitch() {
        enP = !enP;
    }

}

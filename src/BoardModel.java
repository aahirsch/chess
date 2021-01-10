import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Deque;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JOptionPane;

public class BoardModel {

    /*
     * TWEAKS TODO -TEST isCheckmate() -reset enPassant flag at end of turn -reset
     * is firstMove if undo requires it (just create hasMoved for all pieces and
     * save its status in Move -pawn promo
     * 
     * 
     */

    private GamePiece[][] board;
    private Deque<Move> moveList;
    private boolean whiteTurn;
    private Set<GamePiece> whitePieces;
    private Set<GamePiece> blackPieces;

    public BoardModel() {
        board = new GamePiece[8][8];
        whiteTurn = true;
        moveList = new LinkedList<Move>();
    }

    public void newGame() { // new game
        board = new GamePiece[8][8];
        whiteTurn = true;
        moveList = new LinkedList<Move>();

        // TO DO: set up starting position
        for (int row = 0; row < 8; row++) {
            Color color;
            if (row <= 1) {
                color = Color.WHITE;
            } else {
                color = Color.BLACK;
            }
            for (int col = 0; col < 8; col++) {
                if (row == 1 || row == 6) {
                    board[row][col] = new Pawn(color, this);
                } else if (row == 0 || row == 7) {
                    if (col == 0 || col == 7) {
                        board[row][col] = new Rook(color, this);
                    } else if (col == 1 || col == 6) {
                        board[row][col] = new Knight(color, this);
                    } else if (col == 2 || col == 5) {
                        board[row][col] = new Bishop(color, this);
                    } else if (col == 3) {
                        board[row][col] = new Queen(color, this);
                    } else if (col == 4) {
                        board[row][col] = new King(color, this);
                    }
                }
            }
        }
    }

    /*
     * LOCATIONAL/MOVE METHODS
     */
    public boolean isLegalMove(Dimension currSq, Dimension nextSq) {
        boolean tempTurn = whiteTurn;
        Color oppColor;
        if (getPiece(currSq) == null) {
            return false;
        }
        if (!whiteTurn) {
            oppColor = Color.WHITE;
        } else {
            oppColor = Color.BLACK;
        }
        boolean pieceHasMoved = getPiece(currSq).hasMoved();
        if ((tempTurn && getPiece(currSq).getColor() == Color.BLACK) || (!tempTurn && getPiece(
                currSq).getColor() == Color.WHITE)) {
            return false;
        }
        if (getPiece(nextSq) != null) {
            if ((tempTurn && getPiece(nextSq).getColor() == Color.WHITE) || (!tempTurn
                    && getPiece(nextSq).getColor() == Color.BLACK)) {
                return false;
            }
        }
        if (getPiece(currSq).move(currSq, nextSq)) {
            Move tryMove = new Move(currSq, nextSq, getPiece(currSq), pieceHasMoved, getPiece(
                    nextSq));
            moveList.push(tryMove);
            move(tryMove);
            if (((tempTurn && isCheck(Color.WHITE)) || (!tempTurn && isCheck(Color.BLACK)))
                    || currSq.equals(nextSq)) {
                undo(moveList.pop());
                return false;
            }
            turn();
//			if (isCheckmate(oppColor)) {
//				JOptionPane.showMessageDialog(null, "Checkmate!", "Game Over",
//						JOptionPane.OK_OPTION);
//			}
            return true;
        }
        return false;
    }

    /*
     * Doesn't change turns
     */
    public boolean isLegal(Dimension currSq, Dimension nextSq) {
        boolean tempTurn = whiteTurn;
        if (getPiece(currSq) == null) {
            return false;
        }
        boolean pieceHasMoved = getPiece(currSq).hasMoved();
        if ((tempTurn && getPiece(currSq).getColor() == Color.BLACK) || (!tempTurn && getPiece(
                currSq).getColor() == Color.WHITE)) {
            return false;
        }
        if (getPiece(nextSq) != null) {
            if ((tempTurn && getPiece(nextSq).getColor() == Color.WHITE) || (!tempTurn
                    && getPiece(nextSq).getColor() == Color.BLACK)) {
                return false;
            } else {
                if (getPiece(currSq).move(currSq, nextSq)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        if (getPiece(currSq).move(currSq, nextSq)) {
            Move tryMove = new Move(currSq, nextSq, getPiece(currSq), pieceHasMoved, getPiece(
                    nextSq));
            moveList.push(tryMove);
            move(tryMove);
            if (((tempTurn && isCheck(Color.WHITE)) || (!tempTurn && isCheck(Color.BLACK)))
                    || currSq.equals(nextSq)) {
                undo(moveList.pop());
                return false;
            }
            return true;
        }
        return false;
    }

    private Set<Move> playersLegalMoves(Color color) {
        Set<Move> moves = new TreeSet<Move>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GamePiece piece = (board[row][col]);
                if (piece != null) {
                    if (piece.getColor() == color) {
                        Dimension currSq = new Dimension(row, col);
                        for (int row2 = 0; row2 < 8; row2++) {
                            for (int col2 = 0; col2 < 8; col2++) {
                                Dimension nextSq = new Dimension(row2, col2);
                                GamePiece take = (board[row2][col2]);
                                Move move = new Move(currSq, nextSq, piece, piece.hasMoved(),
                                        take);
                                if (isLegal(currSq, nextSq)) {
                                    undo(move);
                                    moves.add(move);
                                    System.out.println("hit");
                                }
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    private boolean hasMoves(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GamePiece piece = (board[row][col]);
                if (piece != null) {
                    if (piece.getColor() == color) {
                        Dimension currSq = new Dimension(row, col);
                        for (int row2 = 0; row2 < 8; row2++) {
                            for (int col2 = 0; col2 < 8; col2++) {
                                Dimension nextSq = new Dimension(row2, col2);
                                GamePiece take = (board[row2][col2]);
                                Move move = new Move(currSq, nextSq, piece, piece.hasMoved(),
                                        take);
                                if (isLegal(currSq, nextSq)) {
                                    undo(move);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isClearPath(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            for (int col = 1; col < Math.abs(y2 - y1); col++) {
                if (board[x1][Math.min(y1, y2) + col] != null) {
                    return false;
                }
            }
        } else if (y1 == y2) {
            for (int row = 1; row < Math.abs(x2 - x1); row++) {
                if (board[Math.min(x1, x2) + row][y1] != null) {
                    return false;
                }
            }
        } else {
            for (int diag = 1; diag < Math.abs(y2 - y1); diag++) {
                if (x2 > x1 && y2 > y1) {
                    if (board[x1 + diag][y1 + diag] != null) {
                        return false;
                    }
                }
                if (x2 < x1 && y2 > y1) {
                    if (board[x1 - diag][y1 + diag] != null) {
                        return false;
                    }
                }
                if (x2 > x1 && y2 < y1) {
                    if (board[x1 + diag][y1 - diag] != null) {
                        return false;
                    }
                }
                if (x2 < x1 && y2 < y1) {
                    if (board[x1 - diag][y1 - diag] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void move(Move move) {
        board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
                .getHeight()] = board[(int) move.getCurrSq().getWidth()][(int) move.getCurrSq()
                        .getHeight()];
//		board[(int) move.getNextSq().getWidth()][(int) move.getNextSq().getHeight()] = move
//				.getPiece();
        board[(int) move.getCurrSq().getWidth()][(int) move.getCurrSq().getHeight()] = null;
    }

    private void undo(Move move) {
        if (moveList.isEmpty()) {
            return;
        } else {
            board[(int) move.getCurrSq().getWidth()][(int) move.getCurrSq()
                    .getHeight()] = board[(int) move.getNextSq().getWidth()][(int) move
                            .getNextSq().getHeight()];
            board[(int) move.getCurrSq().getWidth()][(int) move.getCurrSq().getHeight()]
                    .setHasMoved(move.pieceHasMoved());
            board[(int) move.getNextSq().getWidth()][(int) move.getNextSq().getHeight()] = move
                    .getTake();
//			                 						.getHeight()] =
//			if (move.getTakePieceType() == Piece.PAWN) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Pawn(move.getColor(), this, move.takeHasMoved(),
//								move.getTakeEnPassant());
//			} else if (move.getTakePieceType() == Piece.ROOK) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Rook(move.getColor(), this, move.takeHasMoved());
//			} else if (move.getTakePieceType() == Piece.KING) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new King(move.getColor(), this, move.takeHasMoved());
//			} else if (move.getTakePieceType() == Piece.KNIGHT) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Knight(move.getColor(), this);
//			} else if (move.getTakePieceType() == Piece.BISHOP) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Bishop(move.getColor(), this);
//			} else if (move.getTakePieceType() == Piece.QUEEN) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Queen(move.getColor(), this);
//			} else {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq().getHeight()] = null;
//			}
        }
    }

    public void playerUndo() {
        if (moveList.isEmpty()) {
            return;
        } else {
            Move move = moveList.pop();
            // Uncomment this and comment the next section to allow undo taking a piece
            // Also must make getTake method of Move public (breaks encapsulation)
            board[(int) move.getCurrSq().getWidth()][(int) move.getCurrSq()
                    .getHeight()] = board[(int) move.getNextSq().getWidth()][(int) move
                            .getNextSq().getHeight()];
            board[(int) move.getCurrSq().getWidth()][(int) move.getCurrSq().getHeight()]
                    .setHasMoved(move.pieceHasMoved());
            GamePiece piece = move.getPiece();
            piece.setNumMoves(piece.getNumMoves() - 1);
            board[(int) move.getNextSq().getWidth()][(int) move.getNextSq().getHeight()] = move
                    .getTake();

//			System.out.println(move.getTakePieceType());
//			board[(int) move.getCurrSq().getWidth()][(int) move.getCurrSq()
//					.getHeight()] = board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//							.getHeight()];
//			board[(int) move.getCurrSq().getWidth()][(int) move.getCurrSq().getHeight()]
//					.setHasMoved(move.pieceHasMoved());
//			if (move.getTakePieceType() == Piece.PAWN) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Pawn(move.getColor(), this, move.takeHasMoved(),
//								move.getTakeEnPassant());
//			} else if (move.getTakePieceType() == Piece.ROOK) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Rook(move.getColor(), this, move.takeHasMoved());
//			} else if (move.getTakePieceType() == Piece.KING) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new King(move.getColor(), this, move.takeHasMoved());
//			} else if (move.getTakePieceType() == Piece.KNIGHT) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Knight(move.getColor(), this);
//			} else if (move.getTakePieceType() == Piece.BISHOP) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Bishop(move.getColor(), this);
//			} else if (move.getTakePieceType() == Piece.QUEEN) {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq()
//						.getHeight()] = new Queen(move.getColor(), this);
//			} else {
//				board[(int) move.getNextSq().getWidth()][(int) move.getNextSq().getHeight()] = null;
//			}

            turn();
            printBoard();
            printTurn();
        }
    }

    public Dimension getLocation(GamePiece piece) {
        Dimension loc = new Dimension(-1, -1);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (piece.equals(board[row][col])) {
                    loc.setSize(row, col);
                }
            }
        }
        return loc;
    }

    public Dimension findKing(Color color) {
        Dimension loc = new Dimension(-1, -1);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GamePiece piece = (board[row][col]);
                if (piece != null) {
                    if (piece instanceof King && piece.getColor() == color) {
                        Dimension loc2 = new Dimension(row, col);
                        return loc2;
                    }
                }
            }
        }
        return loc;
    }

    // change to private!!
    private GamePiece getPiece(Dimension loc) {
        int row = (int) loc.getWidth();
        int col = (int) loc.getHeight();
        return board[row][col];
    }

    public boolean isPiece(Dimension loc) {
        return getPiece(loc) != null;
    }

    /*
     * Piece specific move methods
     */

    public void enPassantCheck(int col2, int row2) {
        GamePiece adjPieceR = getPiece(new Dimension(Math.min(col2 + 1, 7), row2));
        GamePiece adjPieceL = getPiece(new Dimension(Math.max(col2 - 1, 0), row2));
        if (adjPieceR != null) {
            if (adjPieceR instanceof Pawn) {
                Pawn pawn = (Pawn) adjPieceR;
                pawn.enPassantSwitch();
            }
        }
        if (adjPieceL != null) {
            if (adjPieceL instanceof Pawn) {
                Pawn pawn = (Pawn) adjPieceL;
                pawn.enPassantSwitch();
            }
        }
    }

    public boolean castle(Dimension currSq, Dimension nextSq, int row1, int col1, int row2,
            int col2) {
        if (Math.abs(col2 - col1) != 2) {
            return false;
        }
        GamePiece king = getPiece(currSq);
        Color kingColor = king.getColor();
        if (isCheck(king.getColor())) {
            return false;
        }
        if (col2 == 2 && getPiece(new Dimension(row2, 3)) == null) {
            Dimension midSq = new Dimension(row1, (col1 + col2) / 2);
            Dimension rookDim = new Dimension(row1, 0);
            if (!canAttack(kingColor, midSq) && !canAttack(kingColor, nextSq)) {
                if (getPiece(rookDim) != null) {
                    if (getPiece(rookDim) instanceof Rook) {
                        GamePiece rook = getPiece(rookDim);
                        if (king.getColor() == rook.getColor() && ((Rook) rook).castle(row1, 0,
                                new Dimension(row1, 3))) {
                            Move rookMove = new Move(rookDim, midSq, rook, rook.hasMoved(),
                                    getPiece(midSq));
                            moveList.push(rookMove);
                            move(rookMove);
                            return true;
                        }
                    }
                }
            }
        } else if (col2 == 6 && getPiece(new Dimension(row2, 5)) == null) {
            Dimension midSq = new Dimension(row1, (col1 + col2) / 2);
            Dimension rookDim = new Dimension(row1, 7);
            if (!canAttack(kingColor, midSq) && !canAttack(kingColor, nextSq)) {
                if (getPiece(rookDim) == null) {
                    return false;
                }
                if (getPiece(rookDim) instanceof Rook) {
                    Rook rook = (Rook) getPiece(rookDim);
                    if (rook.getColor() == king.getColor() && rook.castle(row1, 7, new Dimension(
                            row1, 5))) {
                        Move rookMove = new Move(rookDim, midSq, rook, rook.hasMoved(), getPiece(
                                midSq));
                        moveList.push(rookMove);
                        move(rookMove);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean promotion(Dimension currSq, Dimension nextSq, Pawn pawn) {
        Images image = new Images();
        Color color;
        if (whiteTurn) {
            color = Color.WHITE;
        } else {
            color = Color.BLACK;
        }
        // code from
        // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
        Object[] possibilities = { "Queen", "Rook", "Bishop", "Knight" };
        String s = (String) JOptionPane.showInputDialog(null, "Choose your piece: ",
                "I dub thee..", JOptionPane.QUESTION_MESSAGE, image.getImage("bQ"), possibilities,
                "Queen");

        // create piece based on choice.
        if ((s != null) && (s.length() > 0)) {
            if (s.equals("Queen")) {
                GamePiece queen = new Queen(color, this, pawn.getNumMoves(), true);
                Move move = new Move(currSq, nextSq, queen, true, getPiece(currSq));
                moveList.push(move);
                move(move);
                // board[(int) nextSq.getWidth()][(int) nextSq.getHeight()] = new Queen(color,
                // this);
                return true;
            } else if (s.equals("Rook")) {
                board[(int) nextSq.getWidth()][(int) nextSq.getHeight()] = new Rook(color, this,
                        pawn.getNumMoves(), true);
                return true;
            } else if (s.equals("Bishop")) {
                board[(int) nextSq.getWidth()][(int) nextSq.getHeight()] = new Bishop(color, this,
                        pawn.getNumMoves(), true);
                return true;
            } else if (s.equals("Knight")) {
                board[(int) nextSq.getWidth()][(int) nextSq.getHeight()] = new Knight(color, this,
                        pawn.getNumMoves(), true);
                return true;
            }
            return true;
        }
        return false;
    }

    /************************
     * Rules Methods
     ************************/

    private void turn() {
        whiteTurn = !whiteTurn;
    }

    public boolean isLegalCheck(Dimension currSq, Dimension nextSq) {
        if (getPiece(currSq).move(currSq, nextSq)) {
            return true;
        }
        return false;
    }

    /*
     * Whether color given is in check
     */
    public boolean isCheck(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GamePiece piece = (board[row][col]);
                if (piece != null) {
                    if (piece instanceof King) {
                        continue;
                    }
                    if (piece.getColor() != color) {
                        if (isLegalCheck(new Dimension(row, col), findKing(color))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canAttack(Color color, Dimension nextSq) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GamePiece piece = (board[row][col]);
                if (piece != null) {
                    if (!(piece instanceof King)) {
                        if (piece.getColor() != color) {
                            if (piece.move(new Dimension(row, col), nextSq)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(Color color) { // TODO
        return !hasMoves(color);
    }

    public TreeSet<Dimension> enPassantCheck() {
        TreeSet<Dimension> enPassantPawns = new TreeSet<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GamePiece piece = (board[row][col]);
                if (piece != null) {
                    if (piece instanceof Pawn) {
                        if (((Pawn) piece).getEnPassant()) {
                            enPassantPawns.add(new Dimension(row, col));
                        }
                    }
                }
            }
        }
        return enPassantPawns;
    }

    public boolean isEnP(Dimension loc) { // TODO
        if (getPiece(loc) instanceof Pawn) {
            return ((Pawn) getPiece(loc)).getEnPassant();
        }
        return false;
    }

    public TreeSet<Dimension> hasMovedCheck() {
        TreeSet<Dimension> hasMoved = new TreeSet<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GamePiece piece = (board[row][col]);
                if (piece != null) {
                    if (piece.hasMoved()) {
                        hasMoved.add(new Dimension(row, col));
                    }
                }
            }
        }
        return hasMoved;
    }

    public String[][] getBoard() {
        String[][] strBoard = new String[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String piece = "";
                if (board[row][col] == null) {
                    piece = null;
                } else if (board[row][col].getColor() == Color.WHITE) {
                    piece = piece + "w";
                } else if (board[row][col].getColor() == Color.BLACK) {
                    piece = piece + "b";
                }
                if (board[row][col] != null) {
                    if (board[row][col] instanceof Pawn) {
                        piece = piece + "P";
                    } else if (board[row][col] instanceof Rook) {
                        piece = piece + "R";
                    } else if (board[row][col] instanceof Knight) {
                        piece = piece + "N";
                    } else if (board[row][col] instanceof Bishop) {
                        piece = piece + "B";
                    } else if (board[row][col] instanceof Queen) {
                        piece = piece + "Q";
                    } else if (board[row][col] instanceof King) {
                        piece = piece + "K";
                    }
                }
                strBoard[row][col] = piece;
            }
        }
        return strBoard;
    }

    /*
     * Helpers
     */
    public Color getColor(Dimension loc) {
        System.out.println(board[(int) loc.getWidth()][(int) loc.getHeight()].getColor());
        return board[(int) loc.getWidth()][(int) loc.getHeight()].getColor();
    }

    // for testing!
    public void setPiece(int row, int col, GamePiece piece) {
        board[row][col] = piece;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public boolean setTurn(boolean turn) {
        whiteTurn = turn;
        return whiteTurn;
    }

    public BoardModel loadGame(String filename) {
        // TO DO: fix -- this very wrong -- doesn't belong in this file.. or maybe?
        return this;
    }

    public String saveGame() {
        // TO DO: fix
        String filename = "filename";
        return filename;
    }

    public void printDim(Dimension dim) {
        System.out.println("Row: " + dim.getWidth() + " Col: " + dim.getHeight());
    }

    public void printTurn() {
        if (whiteTurn) {
            System.out.println("WhiteTurn");
        }
        if (!whiteTurn) {
            System.out.println("BlackTurn");
        }
    }

    public void printPiece(GamePiece piece) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == piece) {
                    String pr = "";
                    if (board[row][col] instanceof Pawn) {
                        pr = pr + "P";
                    } else if (board[row][col] instanceof Rook) {
                        pr = pr + "R";
                    } else if (board[row][col] instanceof Knight) {
                        pr = pr + "N";
                    } else if (board[row][col] instanceof Bishop) {
                        pr = pr + "B";
                    } else if (board[row][col] instanceof Queen) {
                        pr = pr + "Q";
                    } else if (board[row][col] instanceof King) {
                        pr = pr + "K";
                    }
                    System.out.println(pr + ", Row: " + row + ", Col: " + col);
                    break;
                }
            }
        }
    }

    public void printBoard() {
        for (int row = 7; row >= -1; row--) {
            for (int col = 0; col < 8; col++) {
                String piece = "";
                if (row == -1) {
                    if (col == 0) {
                        System.out.print("  ");
                    }
                    System.out.print(col + "   ");
                    if (col == 7) {
                        System.out.println();
                    }
                } else if (col == 0) {
                    System.out.print(row + " ");
                }
                if (row >= 0) {
                    if (board[row][col] == null) {
                        piece = piece + "--";
                    } else if (board[row][col].getColor() == Color.WHITE) {
                        piece = piece + "w";
                    } else if (board[row][col].getColor() == Color.BLACK) {
                        piece = piece + "b";
                    }
                    if (board[row][col] != null) {
                        if (board[row][col] instanceof Pawn) {
                            piece = piece + "P";
                        } else if (board[row][col] instanceof Rook) {
                            piece = piece + "R";
                        } else if (board[row][col] instanceof Knight) {
                            piece = piece + "N";
                        } else if (board[row][col] instanceof Bishop) {
                            piece = piece + "B";
                        } else if (board[row][col] instanceof Queen) {
                            piece = piece + "Q";
                        } else if (board[row][col] instanceof King) {
                            piece = piece + "K";
                        }
                    }
                    System.out.print(piece + ", ");
                    if (col == 7) {
                        System.out.println();
                    }
                }
            }
        }
    }
}

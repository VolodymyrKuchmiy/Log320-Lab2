import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPUPlayer {
    private Piece piece;
    private Piece opponentPiece;
    private Move bestMove;
    private static final int INITIAL_DEPTH = 3;

    public CPUPlayer() {

    }

    public void setPiece(Piece piece) {
        this.piece = new Piece(piece.getNumero());
        this.opponentPiece = new Piece(0);
        if (piece.getNumero() == 4) {
            this.opponentPiece.setNumero(2);
        } else {
            this.opponentPiece.setNumero(4);
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public int minimaxAlphaBeta(Board board, int depth, int alpha, int beta, boolean isMaximizingPlayer,
            Piece playerPiece) {
        if (depth == 0 || board.isGameOver(piece) || board.isGameOver(opponentPiece)) {
            return board.evaluateGameState(piece);
        }
        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            System.out.println("PLAYER PIECE " + this.piece.getNumero());
            List<Case> playerCases = board.getPlayerCases(playerPiece);
            for (Case playerCase : playerCases) {
                List<Move> possibleMoves = board.movesPossibles(board.casesPossibles(playerCase), playerCase);
                for (Move move : possibleMoves) {
                    Board newBoard = copierBoard(board);
                    newBoard.movePiece(move);
                    System.out.println("Move de max: " + move.translateToServer() + " de "
                            + this.piece.getNumero());
                    newBoard.afficherBoardPrint();
                    int eval = minimaxAlphaBeta(newBoard, depth - 1, alpha, beta, false, opponentPiece);
                    System.out.println("Evaluation: " + eval);
                    if (eval > maxEval) {
                        maxEval = eval;
                        if (depth == INITIAL_DEPTH) {
                            System.out.println("Best move ajout");
                            this.bestMove = move;
                        }
                    }
                    alpha = Math.max(alpha, eval);
                    System.out.println("Alpha et beta: " + alpha + " " + beta);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            System.out
                    .println("OPPONENT PIECE: " + this.opponentPiece.getNumero());
            List<Case> opponentCases = board.getPlayerCases(playerPiece);
            for (Case opponentCase : opponentCases) {
                List<Move> possibleMoves = board.movesPossibles(board.casesPossibles(opponentCase), opponentCase);
                for (Move move : possibleMoves) {
                    Board newBoard = copierBoard(board);
                    newBoard.movePiece(move);
                    System.out.println("Move de min: " + move.translateToServer() + " de "
                            + opponentPiece.getNumero());
                    newBoard.afficherBoardPrint();
                    int eval = minimaxAlphaBeta(newBoard, depth - 1, alpha, beta, true, piece);
                    System.out.println("Evaluation: " + eval);
                    if (eval < minEval) {
                        minEval = eval;
                        if (depth == INITIAL_DEPTH) {
                            System.out.println("Best move ajout");
                            this.bestMove = move; // Store the best move at the initial depth
                        }
                    }
                    beta = Math.min(beta, eval);
                    System.out.println("Alpha et beta: " + alpha + " " + beta);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }

    public int minimax(Board board, int profondeur, boolean estMax, Piece pieceJoueur) {
        if (profondeur == 0 || board.isGameOver(piece) || board.isGameOver(opponentPiece)) {
            System.out.println("Game is over reached");
            return board.evaluateGameState(piece);
        }
        if (estMax) {
            int maxScore = Integer.MIN_VALUE;
            for (Case caseInspectee : board.getPlayerCases(pieceJoueur)) {
                for (Move moveInspectee : board.movesPossibles(board.getPlayerCases(pieceJoueur), caseInspectee)) {
                    Board newBoard = copierBoard(board);
                    newBoard.movePiece(moveInspectee);
                    int score = minimax(newBoard, profondeur - 1, !estMax, opponentPiece);
                    maxScore = Math.max(maxScore, score);
                    if (score > maxScore) {
                        this.bestMove = moveInspectee;
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (Case caseInspectee : board.getPlayerCases(pieceJoueur)) {
                for (Move moveInspectee : board.movesPossibles(board.getPlayerCases(pieceJoueur), caseInspectee)) {
                    Board newBoard = copierBoard(board);
                    newBoard.movePiece(moveInspectee);
                    int score = minimax(newBoard, profondeur - 1, estMax, piece);
                    minScore = Math.min(minScore, score);
                    if (score < minScore) {
                        this.bestMove = moveInspectee;
                    }
                }
            }
            return minScore;
        }
    }

    // Method to get the best move found by the minimaxAlphaBeta algorithm
    public Move getCurrentBestMove() {
        return bestMove;
    }

    public Board copierBoard(Board boardACopier) {
        Board boardFinal = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Case oldCase = boardACopier.board[i][j];
                boardFinal.board[i][j] = new Case(oldCase.getLettre(), oldCase.getNumero(), oldCase.getPiece(),
                        oldCase.getI(), oldCase.getJ());
            }
        }
        return boardFinal;
    }
}

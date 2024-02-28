import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPUPlayer {
    private Piece piece;
    private Map<Integer, Move> hashMapMoves = new HashMap<>();
    private Move currentBestMove = new Move(null, null);

    public Move getCurrentBestMove() {
        return currentBestMove;
    }

    public void setCurrentBestMove(Move currentBestMove) {
        this.currentBestMove = currentBestMove;
    }

    // Method to add an object to the HashMap
    public void addObject(int key, Move move) {
        hashMapMoves.put(key, move);
    }

    // Method to retrieve an object from the HashMap
    public Move getObject(int key) {
        return hashMapMoves.get(key);
    }

    // Getter for the entire HashMap (optional)
    public Map<Integer, Move> getHashMap() {
        return hashMapMoves;
    }

    public int getCleMin() {
        int minValue = Integer.MAX_VALUE;
        for (int key : this.hashMapMoves.keySet()) {
            if (key < minValue) {
                minValue = key;
            }
        }
        return minValue;
    }

    public int getCleMax() {
        int maxValue = Integer.MIN_VALUE;
        for (int key : this.hashMapMoves.keySet()) {
            if (key > maxValue) {
                maxValue = key;
            }
        }
        return maxValue;
    }

    public CPUPlayer() {
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public int minimax(Board board, int depth, int alpha, int beta, boolean isMaximizingPlayer, Piece playerPiece) {
        if (depth == 0 || board.isGameOver(piece)) { // a voir
            // System.out.println("board evalue avec alpha: " + alpha + ", beta: " + beta +
            // " et profondeur: " + depth);
            return board.evaluateGameState(piece);
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            List<Case> playerCases = board.getPlayerCases(piece);
            for (Case playerCase : playerCases) {
                System.out.println();
                board.afficherBoardPrint();
                List<Move> possibleMoves = board.movesPossibles(board.casesPossibles(playerCase), playerCase);
                for (Move move : possibleMoves) {
                    board.movePiece(move);
                    Board newBoard = board;
                    int eval = minimax(newBoard, depth - 1, alpha, beta, false, playerPiece);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (!hashMapMoves.containsKey(maxEval)) {
                        hashMapMoves.put(maxEval, move);
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
            }

            setCurrentBestMove(this.hashMapMoves.get(getCleMax()));
            System.out.println("MaxEval " + maxEval);
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            Piece opponentPiece;
            if (playerPiece.getNumero() == 2 && playerPiece.getNumero() != 0) {
                opponentPiece = new Piece(4); // fonction
            } else {
                opponentPiece = new Piece(2);
            }
            List<Case> opponentCases = board.getPlayerCases(opponentPiece);
            for (Case opponentCase : opponentCases) {
                // System.out.println("Case inspectee minmax: " + opponentCase.getLettre() +
                // opponentCase.getNumero()
                // + " pour la piece " + opponentPiece.getCase());
                System.out.println();
                board.afficherBoardPrint();
                List<Move> possibleMoves = board.movesPossibles(board.casesPossibles(opponentCase), opponentCase);
                for (Move move : possibleMoves) {
                    board.movePiece(move);
                    Board newBoard = board;
                    int eval = minimax(newBoard, depth - 1, alpha, beta, true, playerPiece);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);

                    if (!hashMapMoves.containsKey(minEval)) {
                        hashMapMoves.put(minEval, move);
                        // System.out.println(
                        // "Cle:" + this.getObject(minEval).getCaseDebut().getLettre()
                        // + this.getObject(minEval).getCaseDebut().getNumero()
                        // + this.getObject(minEval).getCaseFin().getLettre()
                        // + this.getObject(minEval).getCaseFin().getNumero());
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            setCurrentBestMove(this.hashMapMoves.get(getCleMin()));
            System.out.println("MinEval " + minEval);
            return minEval;
        }

    }
}

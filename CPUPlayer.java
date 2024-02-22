import java.util.List;

public class CPUPlayer {
    private Piece piece;

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
            System.out.println("board evalue");
            return board.evaluateGameState(piece);
        }
        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            List<Case> playerCases = board.getPlayerCases(piece);
            for (Case playerCase : playerCases) {
                List<Move> possibleMoves = board.movesPossibles(board.casesPossibles(playerCase), playerCase);
                for (Move move : possibleMoves) {
                    board.movePiece(move);
                    Board newBoard = board;
                    int eval = minimax(newBoard, depth - 1, alpha, beta, false, playerPiece);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
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
                List<Move> possibleMoves = board.movesPossibles(board.casesPossibles(opponentCase), opponentCase);
                for (Move move : possibleMoves) {
                    board.movePiece(move);
                    Board newBoard = board;
                    int eval = minimax(newBoard, depth - 1, alpha, beta, true, playerPiece);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }

    }
}

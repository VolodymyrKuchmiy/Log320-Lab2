
public class Test {

    // Class qui sert a faire des tests;
    public static void main(String[] args) {
        // TestServeur();
        Debug();
    }

    public static void Debug() {
        Board board = new Board();
        Case caseInitiale = new Case('A', 2, new Piece(4), 6, 0); // pour tester que le
        Case caseIntermidiaire = new Case('B', 1, new Piece(2), 7, 1);
        Case caseFinale = new Case('B', 2, new Piece(4), 6, 1);
        Move move1 = new Move(caseIntermidiaire, caseFinale);
        // board.movePiece(move1); // pour tester mouvements, et dans futur, evaluation
        // de movesPossibles
        board.afficherBoardPrint();
        CPUPlayer cpuPlayer = new CPUPlayer();
        cpuPlayer.setPiece(new Piece(4));
        CPUPlayer cpuPlayer2 = new CPUPlayer();
        cpuPlayer.minimax(board, 3, Integer.MAX_VALUE, Integer.MIN_VALUE, true,
                cpuPlayer.getPiece());
        // ControlleurServeur controlleurServeur = new ControlleurServeur();
        // controlleurServeur.TestServeur(board, cpuPlayer);
    }

}
public class Test {
    // Class qui sert a faire des tests;
    public static void main(String[] args) {
        Board board = new Board();
        Case caseInitiale = new Case('A', 2, new Piece(4), 6, 0); // pour tester que le
        Case caseFinale = new Case('D', 4, new Piece(4), 5, 3);
        Move move1 = new Move(caseInitiale, caseFinale);
        // Debugging
        // System.out.println("En haut est un board initialise, on fait un move
        // A2-B2:");
        board.movePiece(move1); // pour tester mouvements, et dans futur, evaluation de movesPossibles
        board.afficherBoardPrint();
        // board.obtenirNbPieces(caseFinale);
        board.movesPossibles(caseFinale);
    }

}
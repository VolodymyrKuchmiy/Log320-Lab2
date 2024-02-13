public class Test {
    // Class qui sert a faire des tests;
    public static void main(String[] args) {
        Board board = new Board();
        board.afficherBoardPrint();
        Case caseInitiale = new Case('A', 2, new Piece(4)); // pour tester que le mouvement de pieces fonctionne bien
        Case caseFinale = new Case('B', 2, new Piece(4));
        System.out.println("En haut est un board initialise, on fait un move A2-B2:");
        board.movePiece(caseInitiale, caseFinale);
        board.afficherBoardPrint();
    }

}
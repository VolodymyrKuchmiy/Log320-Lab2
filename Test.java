public class Test {
    // Class qui sert a faire des tests;
    public static void main(String[] args) {
        Board board = new Board();
        Case caseInitiale = new Case('A', 2, new Piece(4));
        Case caseFinale = new Case('B', 2, new Piece(4));
        board.movePiece(caseInitiale, caseFinale);
        board.afficherBoardPrint();
    }

}
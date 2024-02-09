public class Board {

    private static final int DIMENSION_LARGEUR = 8;
    private static final int DIMENSION_HAUTEUR = 8;
    private Case[][] board;

    public Board() {
        this.board = initialiserBoard();
    }

    public Case[][] initialiserBoard() {
        Case[][] board = new Case[DIMENSION_LARGEUR][DIMENSION_HAUTEUR];
        for (int i = 0; i < DIMENSION_LARGEUR; i++) {
            for (int j = 0; j < DIMENSION_HAUTEUR; j++) {
                //
            }
        }
        return board;
    }
}

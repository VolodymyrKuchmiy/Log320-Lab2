public class Board {

    private static final int DIMENSION_LARGEUR = 8;
    private static final int DIMENSION_HAUTEUR = 8;
    private Case[][] board;

    public Board() {
        this.board = initialiserBoard();
        this.afficherBoardPrint();
    }

    // fonction qui initialise le board. Elle remplit le board avec cases vides.
    public Case[][] initialiserBoard() {
        Case[][] board = new Case[DIMENSION_LARGEUR][DIMENSION_HAUTEUR]; // initialisation se fait de haut gauche vers
                                                                         // bas droite
        for (int i = DIMENSION_LARGEUR; i > 0; i--) { // on parcourt les lignes et on descend de 8 vers 1
            for (char lettre = 'A'; lettre <= 'H'; lettre++) { // on parcourt les colonnes et on monte de A vers H
                board[DIMENSION_LARGEUR - i][lettre - 65] = new Case(lettre, i, new Piece(0));
                System.out.println("Board [" + (DIMENSION_LARGEUR - i) + "][" + (lettre - 65) + "]"
                        + " -  Case cree: au ligne " + lettre
                        + " et au colonne " + i); // Aux fins de deboguage
            }
        }
        return board;
    }

    public Case[][] initialiserPieces() {

        return this.board;
    }

    public void afficherBoardPrint() {
        for (int i = DIMENSION_LARGEUR; i > 0; i--) { // on parcourt les lignes et on descend de 8 vers 1
            for (char lettre = 'A'; lettre <= 'H'; lettre++) { // on parcourt les colonnes et on monte de A vers H
                this.board[DIMENSION_LARGEUR - i][lettre - 65] = new Case(lettre, i, new Piece(0));
                this.board[DIMENSION_LARGEUR - i][lettre - 65].afficherPiecePrint(); // Aux fins de
                                                                                     // affichage
            }
            System.out.println();
        }
    }
}

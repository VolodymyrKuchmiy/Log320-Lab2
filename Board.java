public class Board {

    private static final int DIMENSION_LARGEUR = 8;
    private static final int DIMENSION_HAUTEUR = 8;
    private Case[][] board;

    public Board() {
        this.board = initialiserBoard();
        initialiserPieces();
        // afficherBoardPrint(); //debogguage
    }

    // fonction qui initialise le board. Elle remplit le board avec cases vides et
    // le retourne
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

    // methode qui ajoute piece noirs et rouges sur le board
    public Case[][] initialiserPieces() {
        // initialisation case rouge
        for (int i = 1; i < 7; i++) {
            this.board[i][0].setPiece(new Piece(4));
            this.board[i][7].setPiece(new Piece(4));
            this.board[0][i].setPiece(new Piece(2));
            this.board[7][i].setPiece(new Piece(2));
        }
        return this.board;
    }

    // fonction qui affiche en print toutes les pieces de board
    public void afficherBoardPrint() {
        for (int i = DIMENSION_LARGEUR; i > 0; i--) { // on parcourt les lignes et on descend de 8 vers 1
            for (char lettre = 'A'; lettre <= 'H'; lettre++) { // on parcourt les colonnes et on monte de A vers H
                this.board[DIMENSION_LARGEUR - i][lettre - 65].afficherPiecePrint(); // Aux fins de
                                                                                     // affichage
            }
            System.out.println();
        }
    }

    public Case getPiece(Case caseAtrouver) {
        Case caseTrouvee = null;
        for (int i = 0; i < DIMENSION_HAUTEUR; i++) {
            for (int j = 0; j < DIMENSION_LARGEUR; j++) {
                if (this.board[i][j].getLettre() == caseAtrouver.getLettre() &&
                        this.board[i][j].getNumero() == caseAtrouver.getNumero()) {
                    caseTrouvee = this.board[i][j];
                }
            }
        }
        return caseTrouvee;
    }

    public void movePiece(Case caseInitiale, Case caseFinale) {
        int pieceType = this.getPiece(caseInitiale).getNumero();
        this.getPiece(caseFinale).setNumero(pieceType); // mettre la piece sur caseFinale
        this.getPiece(caseInitiale).setNumero(0); // enlever la piece de la caseInitiale
    }
}

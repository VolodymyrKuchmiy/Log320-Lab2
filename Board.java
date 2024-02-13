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
        // initialisation case rouge et noir
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

    public int[] getCase(Case caseAtrouver) {
        // il y a une autre maniere de faire - avec retour de Case mais jsp comment le
        // bien implementer
        int[] coordonnees = new int[2]; // on utilise un tableau avec coordonees de la case
        for (int i = 0; i < DIMENSION_HAUTEUR; i++) {
            for (int j = 0; j < DIMENSION_LARGEUR; j++) {
                if (this.board[i][j].getLettre() == caseAtrouver.getLettre() &&
                        this.board[i][j].getNumero() == caseAtrouver.getNumero()) {
                    coordonnees[0] = i;
                    coordonnees[1] = j;
                    System.out.println("Case trouve: sur ligne" + i + ", colonne " + j + ", de valeur "
                            + this.board[i][j].getPiece().getNumero()); // pour debugging
                }
            }
        }
        return coordonnees;
    }

    // Temporairement c'est ça la gestion de mouvement. Ce n'est pas parfait car en
    // theorie ca permet de faire de moves illegales (Pour le moment)
    public void movePiece(Case caseInitiale, Case caseFinale) {
        int[] initialCords = getCase(caseInitiale);
        int[] finalCords = getCase(caseFinale);
        this.board[finalCords[0]][finalCords[1]].getPiece().setNumero(caseFinale.getPiece().getNumero());
        this.board[initialCords[0]][initialCords[1]].getPiece().setNumero(0); // supposant qu'apres le mouvement, la
                                                                              // case initiale sera vide
    }
}

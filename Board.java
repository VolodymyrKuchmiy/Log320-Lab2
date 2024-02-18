import java.util.HashMap;
import java.util.Map;

public class Board {

    private static final int DIMENSION_LARGEUR = 8;
    private static final int DIMENSION_HAUTEUR = 8;
    private Case[][] board;

    public Board() {
        this.board = initialiserBoard();
        initialiserPieces();
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

    // methode qui retourne coordonnees de case dans un board
    // sert a traduire la forme de case comme A8 en [0,0]
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

                    // System.out.println("Case trouve: sur ligne" + i + ", colonne " + j +
                    // ", de valeur "
                    // + this.board[i][j].getPiece().getNumero()); // pour debugging

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

    // pour obtenir un tableau de case voisines pour une case concrete.
    // on peut avoir besoin de ce methode pour condition de victoire.
    // retourne tableau autour de la case.
    public void obtenirCasesVoisins(Case caseInspectee) {

    }

    public int[] obtenirNbPieces(Case caseInspectee) {
        int[] moves = { 0, 0, 0, 0 }; // chaque case de tableau va indiquer combien de pieces se trouvent sur une
                                      // ligne specifique, par defaut a 0 car on compte la caseInspectee
        int[][] directions = { { 0, 1 }, { 1, 0 }, { -1, -1 }, { -1, 1 }, { 1, 1 }, { 1, -1 } };
        // un tableau des vecteurs de mouvement possibles

        // parcours en horizontale et en verticale
        for (int i = 0; i < DIMENSION_HAUTEUR; i++) {
            Case caseCouranteHorizontale = board[getCase(caseInspectee)[0]][i * directions[0][1]]; // parcours
                                                                                                   // horizontal
            Case caseCouranteVerticale = board[i * directions[1][0]][getCase(caseInspectee)[1]]; // parcours vertical
            if (caseCouranteHorizontale.getPiece().getNumero() == caseInspectee.getPiece().getNumero()) {
                // verification que case avec meme piece existe sur la ligne
                System.out.println("Case inspectee en H: " + getCase(caseCouranteHorizontale)[0] + " "
                        + getCase(caseCouranteHorizontale)[1]);
                moves[0]++; // incrementation de qtte cases en horizontal
            } else if (caseCouranteVerticale.getPiece().getNumero() == caseInspectee.getPiece().getNumero()) {
                System.out.println("Case inspectee en H: " + getCase(caseCouranteVerticale)[0] + " "
                        + getCase(caseCouranteVerticale)[1]);
                moves[1]++;
            }
        }
        System.out.println(moves[0]);
        System.out.println(moves[1]);
        return moves;
    }

    // retourne un tableau de moves possibles pour une piece concrete
    public Move[] movesPossibles(Piece piece) {
        Move[] listeMovesPossibles = new Move[DIMENSION_LARGEUR]; // on suppose que la quantite de moves possibles
                                                                  // maximum est 8 (2 horizontales, 2 verticales et 4
                                                                  // diagonales)
        // moves horizontales possibles

        // moves verticales possibles

        // moves diagonales possibles

        return listeMovesPossibles;
    }

}

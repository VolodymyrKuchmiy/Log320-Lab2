import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int DIMENSION_LARGEUR = 8;
    private static final int DIMENSION_HAUTEUR = 8;
    private Case[][] board;
    private int[] compteurPieces = { 12, 12 }; // chaque indice indique qtte pieces pour un joueur

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
                board[DIMENSION_LARGEUR - i][lettre - 65] = new Case(lettre, i, new Piece(0), DIMENSION_LARGEUR - i,
                        lettre - 'A');
                System.out.println("Board [" + (DIMENSION_LARGEUR - i) + "][" + (lettre - 65) + "]"
                        + " -  Case cree: au ligne " + lettre
                        + " et au colonne " + i + " dont coordonnees sont: "
                        + board[DIMENSION_LARGEUR - i][lettre - 65].getI()
                        + board[DIMENSION_LARGEUR - i][lettre - 65].getJ()); // Aux fins de deboguage
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

    public Case obtenirCaseAPartirCoordonnees(int x, int y) {
        Case caseCherchee = null;
        for (int k = 0; k < DIMENSION_HAUTEUR; k++) {
            for (int l = 0; l < DIMENSION_LARGEUR; l++) {
                if (this.board[k][l].getI() == x && this.board[k][l].getJ() == y) {
                    caseCherchee = this.board[k][l];
                }
            }
        }
        return caseCherchee;
    }

    // methode qui retourne coordonnees de case dans un board
    // sert a traduire la forme de case comme A8 en [0,0]
    // independant des attributs i et j de case parce que ces valeurs ont ete
    // ajoutees plus tard
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

    // Temporairement c'est ça la gestion de mouvement.
    public void movePiece(Move move) {
        int[] initialCords = getCase(move.getCaseDebut());
        int[] finalCords = getCase(move.getCaseFin());
        this.board[finalCords[0]][finalCords[1]].getPiece().setNumero(move.getCaseDebut().getPiece().getNumero());
        this.board[initialCords[0]][initialCords[1]].getPiece().setNumero(0); // supposant qu'apres le mouvement, la
                                                                              // case initiale sera vide
    }

    // pour obtenir un tableau de case voisines pour une case concrete.
    // on peut avoir besoin de ce methode pour condition de victoire.
    // retourne tableau autour de la case.
    public void obtenirCasesVoisins(Case caseInspectee) {
        int x = getCase(caseInspectee)[0];
        int y = getCase(caseInspectee)[1];

    }

    // methode extremment pas optimisee
    public int[] obtenirNbPieces(Case caseInspectee) {
        int[] longueurMoves = { 0, 0, 0, 0 }; // chaque case de tableau va indiquer combien de pieces se trouvent sur
                                              // une
        // ligne specifique, par defaut a 0 car on compte la caseInspectee
        int[][] directions = { { 0, 1 }, { 1, 0 }, { -1, -1 }, { -1, 1 }, { 1, 1 }, { 1, -1 } };

        int x = getCase(caseInspectee)[0];
        int y = getCase(caseInspectee)[1];

        // parcours en horizontale et en verticale
        for (int i = 0; i < DIMENSION_HAUTEUR; i++) {
            Case caseCouranteHorizontale = board[x][i * directions[0][1]]; // parcours horizontal
            Case caseCouranteVerticale = board[i * directions[1][0]][y]; // parcours vertical

            if (caseCouranteHorizontale != null && caseCouranteHorizontale.getPiece() != null
                    && caseCouranteHorizontale.getPiece().getNumero() != 0) {
                longueurMoves[0]++; // incrementation de qtte cases en horizontal
            }
            if (caseCouranteVerticale != null && caseCouranteVerticale.getPiece() != null
                    && caseCouranteVerticale.getPiece().getNumero() != 0) {
                longueurMoves[1]++; // incrementation de qtte cases en vertical
            }

            // vérification des diagonales
            // une diagonale est separee en deux et on parcours chaque separement
            if (horsBoard(x + i * directions[4][0], y + i * directions[4][1])) {
                Case caseCouranteDiagonale = board[x + i * directions[4][0]][y + i * directions[4][1]];
                if (caseCouranteDiagonale != null && caseCouranteDiagonale.getPiece() != null
                        && caseCouranteDiagonale.getPiece().getNumero() != 0) {
                    longueurMoves[2]++; // incrementation de qtte cases en diagonale bas droit
                }
            }
            if (horsBoard(x + i * directions[3][0], y + i * directions[3][1])) {
                Case caseCaseDiagonale = board[x + i * directions[3][0]][y + i * directions[3][1]];
                if (caseCaseDiagonale != null && caseCaseDiagonale.getPiece() != null
                        && caseCaseDiagonale.getPiece().getNumero() != 0) {
                    longueurMoves[3]++; // incrementation de qtte cases en diagonale haut droit
                }
            }
            if (horsBoard(x + i * directions[2][0], y + i * directions[2][1])) {
                Case caseCaseDiagonale = board[x + i * directions[2][0]][y + i * directions[2][1]];
                if (caseCaseDiagonale != null && caseCaseDiagonale.getPiece() != null
                        && caseCaseDiagonale.getPiece().getNumero() != 0
                        && getCase(caseInspectee)[0] != x + i * directions[2][0]
                        && getCase(caseInspectee)[1] != y + i * directions[2][1]) {
                    longueurMoves[2]++; // incrementation de qtte cases en diagonale haut gauche
                } // conditions de plus pour ne pas compter case inspectee une autre fois
            }
            if (horsBoard(x + i * directions[5][0], y + i * directions[5][1])) {
                Case caseCaseDiagonale = board[x + i * directions[5][0]][y + i * directions[5][1]];
                if (caseCaseDiagonale != null && caseCaseDiagonale.getPiece() != null
                        && caseCaseDiagonale.getPiece().getNumero() != 0
                        && getCase(caseInspectee)[0] != x + i * directions[5][0]
                        && getCase(caseInspectee)[1] != y + i * directions[5][1]) {
                    longueurMoves[3]++; // incrementation de qtte cases en diagonale bas gauche
                } // conditions de plus pour ne pas compter case inspectee une autre fois
            }
        }
        // pour deboguage
        System.out.println("Case inspectee: " + caseInspectee.getLettre() + caseInspectee.getNumero());
        System.out.println("Cases sur ligne horizontal: " + longueurMoves[0]);
        System.out.println("Cases sur ligne verticale: " + longueurMoves[1]);
        System.out.println("Cases sur la diagonale haut vers bas: " + longueurMoves[2]);
        System.out.println("Cases sur la diagonale bas vers haut: " + longueurMoves[3]);
        return longueurMoves;
    }

    private boolean horsBoard(int row, int column) {
        return row >= 0 && row < DIMENSION_HAUTEUR && column >= 0 && column < DIMENSION_LARGEUR;
    }

    private boolean respecteConditions(char lettre, int numero) {
        return (lettre >= 'A' && lettre <= 'H' && numero <= 8 && numero >= 1);
    }

    // retourne un tableau de longueurMoves possibles pour une piece concrete
    public List<Case> movesPossibles(Case caseInspectee) {

        List<Case> listeMovesPossibles = new ArrayList<Case>();
        int[] longueurMoves = obtenirNbPieces(caseInspectee);
        // Moves possibles
        for (int i = 0; i < longueurMoves.length; i++) {
            int qtteCasesMove = longueurMoves[i];
            char lettre = caseInspectee.getLettre();
            int numero = caseInspectee.getNumero();
            // en horizontale
            if (horsBoard(caseInspectee.getI(), caseInspectee.getJ() + qtteCasesMove) && i == 0 &&
                    respecteConditions((char) (caseInspectee.getLettre() + qtteCasesMove), caseInspectee.getNumero())) {// vers
                // droite
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre + qtteCasesMove));
                listeMovesPossibles.add(caseCourante);
            }
            if (horsBoard(caseInspectee.getI(), caseInspectee.getJ() - qtteCasesMove) && i == 0
                    && respecteConditions((char) (caseInspectee.getLettre() - qtteCasesMove),
                            caseInspectee.getNumero())) {// vers
                // gauche
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre - qtteCasesMove));
                listeMovesPossibles.add(caseCourante);
            }
            // en verticale
            if (horsBoard(caseInspectee.getI() - qtteCasesMove, caseInspectee.getJ()) && i == 1
                    && respecteConditions(caseInspectee.getLettre(), caseInspectee.getNumero() + qtteCasesMove)) {// vers
                                                                                                                  // haut
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() - qtteCasesMove);
                caseCourante.setNumero(numero + qtteCasesMove);
                listeMovesPossibles.add(caseCourante);
            }
            if (horsBoard(caseInspectee.getI() + qtteCasesMove, caseInspectee.getJ()) && i == 1
                    && respecteConditions(caseInspectee.getLettre(), caseInspectee.getNumero() - qtteCasesMove)) {// vers
                                                                                                                  // bas
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() + qtteCasesMove);
                caseCourante.setNumero(numero - qtteCasesMove);
                listeMovesPossibles.add(caseCourante);
            }
            // en diagonale haut vers bas
            if (horsBoard(caseInspectee.getI() + qtteCasesMove, caseInspectee.getJ() + qtteCasesMove) && i == 2
                    && respecteConditions((char) (caseInspectee.getLettre() + qtteCasesMove),
                            caseInspectee.getNumero() - qtteCasesMove)) {// vers
                // bas
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() + qtteCasesMove);
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre + qtteCasesMove));
                caseCourante.setNumero(numero - qtteCasesMove);
                listeMovesPossibles.add(caseCourante);
            }
            if (horsBoard(caseInspectee.getI() - qtteCasesMove, caseInspectee.getJ() - qtteCasesMove) && i == 2
                    && respecteConditions((char) (caseInspectee.getLettre() - qtteCasesMove),
                            caseInspectee.getNumero() + qtteCasesMove)) {// vers
                                                                         // haut
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() - qtteCasesMove);
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre - qtteCasesMove));
                caseCourante.setNumero(numero + qtteCasesMove);
                listeMovesPossibles.add(caseCourante);
            }
            // en diagonale bas vers haut
            if (horsBoard(caseInspectee.getI() + qtteCasesMove, caseInspectee.getJ() - qtteCasesMove) && i == 3
                    && respecteConditions((char) (caseInspectee.getLettre() - qtteCasesMove),
                            caseInspectee.getNumero() - qtteCasesMove)) {// vers
                // bas
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() + qtteCasesMove);
                caseCourante.setJ(caseInspectee.getJ() - qtteCasesMove);
                caseCourante.setLettre((char) (lettre - qtteCasesMove));
                caseCourante.setNumero(numero - qtteCasesMove);
                listeMovesPossibles.add(caseCourante);
            }
            if (horsBoard(caseInspectee.getI() - qtteCasesMove, caseInspectee.getJ() + qtteCasesMove) && i == 3
                    && respecteConditions((char) (caseInspectee.getLettre() + qtteCasesMove),
                            caseInspectee.getNumero() + qtteCasesMove)) {// vers
                // haut
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() - qtteCasesMove);
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre + qtteCasesMove));
                caseCourante.setNumero(numero + qtteCasesMove);
                listeMovesPossibles.add(caseCourante);
            }
        }
        for (int i = 0; i < listeMovesPossibles.size(); i++) {
            System.out.println("Move possible sur: " + listeMovesPossibles.get(i).getLettre()
                    + listeMovesPossibles.get(i).getNumero());
        }
        return listeMovesPossibles;
    }

}

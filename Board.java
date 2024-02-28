import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int DIMENSION_LARGEUR = 8;
    private static final int DIMENSION_HAUTEUR = 8;
    private Case[][] board;

    public Board() {
        this.board = initialiserBoard();
        initialiserPieces();
    }

    // methode pour copier board
    public Case[][] copierBoard(Board boardACopier) {
        Case[][] newBoard = new Case[DIMENSION_LARGEUR][DIMENSION_HAUTEUR];

        for (int i = 0; i < DIMENSION_HAUTEUR; i++) {
            for (int j = 0; j < DIMENSION_LARGEUR; j++) {
                Case caseACopier = boardACopier.obtenirCaseAPartirCoordonnees(i, j);
                newBoard[i][j] = new Case(caseACopier.getLettre(),
                        caseACopier.getNumero(),
                        caseACopier.getPiece(), i, j);
            }
        }
        return newBoard;
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
                // System.out.println("Board [" + (DIMENSION_LARGEUR - i) + "][" + (lettre - 65)
                // + "]"
                // + " - Case cree: au ligne " + lettre
                // + " et au colonne " + i + " dont coordonnees sont: "
                // + board[DIMENSION_LARGEUR - i][lettre - 65].getI()
                // + board[DIMENSION_LARGEUR - i][lettre - 65].getJ()); // Aux fins de deboguage
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
        // System.out.println("Case inspectee: " + caseInspectee.getLettre() +
        // caseInspectee.getNumero());
        // System.out.println("Cases sur ligne horizontal: " + longueurMoves[0]);
        // System.out.println("Cases sur ligne verticale: " + longueurMoves[1]);
        // System.out.println("Cases sur la diagonale haut vers bas: " +
        // longueurMoves[2]);
        // System.out.println("Cases sur la diagonale bas vers haut: " +
        // longueurMoves[3]);
        return longueurMoves;
    }

    private boolean horsBoard(int row, int column) {
        return row >= 0 && row < DIMENSION_HAUTEUR && column >= 0 && column < DIMENSION_LARGEUR;
    }

    private boolean respecteConditions(char lettre, int numero) {
        return (lettre >= 'A' && lettre <= 'H' && numero <= 8 && numero >= 1);
    }

    // retourne un tableau de longueurMoves possibles pour une piece concrete
    public List<Case> casesPossibles(Case caseInspectee) {

        List<Case> listeMovesPossibles = new ArrayList<Case>();
        int[] longueurMoves = obtenirNbPieces(caseInspectee);
        // Moves possibles
        for (int i = 0; i < longueurMoves.length; i++) {
            int qtteCasesMove = longueurMoves[i];
            char lettre = caseInspectee.getLettre();
            int numero = caseInspectee.getNumero();
            // en horizontale
            if (horsBoard(caseInspectee.getI(), caseInspectee.getJ() + qtteCasesMove) && i == 0 &&
                    respecteConditions((char) (caseInspectee.getLettre() + qtteCasesMove), caseInspectee.getNumero())
                    && caseInspectee != null) {// vers
                // droite
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre + qtteCasesMove));
                if (caseCourante != null && cheminContientCaseAdverse(caseInspectee, caseCourante, qtteCasesMove)) {
                    listeMovesPossibles.add(caseCourante);
                }
            }
            if (horsBoard(caseInspectee.getI(), caseInspectee.getJ() - qtteCasesMove) && i == 0
                    && respecteConditions((char) (caseInspectee.getLettre() - qtteCasesMove),
                            caseInspectee.getNumero())
                    && caseInspectee != null) {// vers
                // gauche
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre - qtteCasesMove));
                if (caseCourante != null && cheminContientCaseAdverse(caseInspectee, caseCourante, qtteCasesMove)) {
                    listeMovesPossibles.add(caseCourante);
                }
            }
            // en verticale
            if (horsBoard(caseInspectee.getI() - qtteCasesMove, caseInspectee.getJ()) && i == 1
                    && respecteConditions(caseInspectee.getLettre(), caseInspectee.getNumero() + qtteCasesMove)
                    && caseInspectee != null) {// vers
                // haut
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() - qtteCasesMove);
                caseCourante.setNumero(numero + qtteCasesMove);
                if (caseCourante != null && cheminContientCaseAdverse(caseInspectee, caseCourante, qtteCasesMove)) {
                    listeMovesPossibles.add(caseCourante);
                }
            }
            if (horsBoard(caseInspectee.getI() + qtteCasesMove, caseInspectee.getJ()) && i == 1
                    && respecteConditions(caseInspectee.getLettre(), caseInspectee.getNumero() - qtteCasesMove)
                    && caseInspectee != null) {// vers
                // bas
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() + qtteCasesMove);
                caseCourante.setNumero(numero - qtteCasesMove);
                if (caseCourante != null && cheminContientCaseAdverse(caseInspectee, caseCourante, qtteCasesMove)) {
                    listeMovesPossibles.add(caseCourante);
                }
            }
            // en diagonale haut vers bas
            if (horsBoard(caseInspectee.getI() + qtteCasesMove, caseInspectee.getJ() + qtteCasesMove) && i == 2
                    && respecteConditions((char) (caseInspectee.getLettre() + qtteCasesMove),
                            caseInspectee.getNumero() - qtteCasesMove)
                    && caseInspectee != null) {// vers
                // bas
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() + qtteCasesMove);
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre + qtteCasesMove));
                caseCourante.setNumero(numero - qtteCasesMove);
                if (caseCourante != null && cheminContientCaseAdverse(caseInspectee, caseCourante, qtteCasesMove)) {
                    listeMovesPossibles.add(caseCourante);
                }
            }
            if (horsBoard(caseInspectee.getI() - qtteCasesMove, caseInspectee.getJ() - qtteCasesMove) && i == 2
                    && respecteConditions((char) (caseInspectee.getLettre() - qtteCasesMove),
                            caseInspectee.getNumero() + qtteCasesMove)
                    && caseInspectee != null) {// vers
                // haut
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() - qtteCasesMove);
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre - qtteCasesMove));
                caseCourante.setNumero(numero + qtteCasesMove);
                if (caseCourante != null && cheminContientCaseAdverse(caseInspectee, caseCourante, qtteCasesMove)) {
                    listeMovesPossibles.add(caseCourante);
                }
            }
            // en diagonale bas vers haut
            if (horsBoard(caseInspectee.getI() + qtteCasesMove, caseInspectee.getJ() - qtteCasesMove) && i == 3
                    && respecteConditions((char) (caseInspectee.getLettre() - qtteCasesMove),
                            caseInspectee.getNumero() - qtteCasesMove)
                    && caseInspectee != null) {// vers
                // bas
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() + qtteCasesMove);
                caseCourante.setJ(caseInspectee.getJ() - qtteCasesMove);
                caseCourante.setLettre((char) (lettre - qtteCasesMove));
                caseCourante.setNumero(numero - qtteCasesMove);
                if (caseCourante != null && cheminContientCaseAdverse(caseInspectee, caseCourante, qtteCasesMove)) {
                    listeMovesPossibles.add(caseCourante);
                }
            }
            if (horsBoard(caseInspectee.getI() - qtteCasesMove, caseInspectee.getJ() + qtteCasesMove) && i == 3
                    && respecteConditions((char) (caseInspectee.getLettre() + qtteCasesMove),
                            caseInspectee.getNumero() + qtteCasesMove)
                    && caseInspectee != null) {// vers
                // haut
                Case caseCourante = new Case(caseInspectee.getLettre(), caseInspectee.getNumero(),
                        caseInspectee.getPiece(),
                        caseInspectee.getI(), caseInspectee.getJ());
                caseCourante.setI(caseInspectee.getI() - qtteCasesMove);
                caseCourante.setJ(caseInspectee.getJ() + qtteCasesMove);
                caseCourante.setLettre((char) (lettre + qtteCasesMove));
                caseCourante.setNumero(numero + qtteCasesMove);
                if (caseCourante != null && cheminContientCaseAdverse(caseInspectee, caseCourante, qtteCasesMove)) {
                    listeMovesPossibles.add(caseCourante);
                }
            }
        }
        for (int i = 0; i < listeMovesPossibles.size(); i++) {
            // System.out.println("Move possible sur: " +
            // listeMovesPossibles.get(i).getLettre()
            // + listeMovesPossibles.get(i).getNumero());
        }
        return listeMovesPossibles;
    }

    // methode qui regarde si la case destination a la meme piece que case inspectee
    // aussi on regarde s'il y a une case adverse sur le chemin
    private boolean cheminContientCaseAdverse(Case caseInspectee, Case caseDestination, int longueurMove) {
        boolean aucunAdversaire = true;
        if (caseInspectee != null) {
            int coordonneeI = getCase(caseInspectee)[0];
            int coordonneeJ = getCase(caseInspectee)[1];
            for (int i = 0; i < longueurMove - 1; i++) { // moins 1 pour pouvoir se deplacer vers la case qui contient
                                                         // piece
                                                         // adversaire
                int directionI = (caseDestination.getI() - caseInspectee.getI()) / longueurMove;
                int directionJ = (caseDestination.getJ() - caseInspectee.getJ()) / longueurMove;
                coordonneeI = coordonneeI + directionI;
                coordonneeJ = coordonneeJ + directionJ;
                Case caseCourante = obtenirCaseAPartirCoordonnees(coordonneeI, coordonneeJ);
                if (caseCourante != null && caseCourante.getPiece().getNumero() != caseInspectee.getPiece().getNumero()
                        && caseCourante.getPiece().getNumero() != 0) { // si casecourante contient case adverse
                    aucunAdversaire = false;
                }
            }
        }
        return aucunAdversaire;
    }

    public List<Move> movesPossibles(List<Case> casesPossibles, Case caseDebut) {
        List<Move> movePossibles = new ArrayList<>();
        for (Case case1 : casesPossibles) {
            Move newMove = new Move(caseDebut, case1);
            movePossibles.add(newMove);
        }
        return movePossibles;
    }

    public boolean areAllPiecesConnected(Piece playerPiece) {
        boolean[][] visited = new boolean[DIMENSION_HAUTEUR][DIMENSION_LARGEUR];
        int totalPieces = countTotalPlayerPieces(playerPiece);
        int connectedPieces = 0;

        // Trouver la première pièce du joueur et commencer la recherche à partir de là.
        for (int y = 0; y < DIMENSION_HAUTEUR; y++) {
            for (int x = 0; x < DIMENSION_LARGEUR; x++) {
                if (board[y][x].getPiece().getNumero() == playerPiece.getNumero()) {
                    connectedPieces = dfsConnectedPieces(x, y, visited, playerPiece);
                    // Si le nombre de pièces connectées trouvé est égal au total, toutes les pièces
                    // sont connectées.
                    return connectedPieces == totalPieces;
                }
            }
        }

        // Si aucune pièce n'est trouvée (ce qui est improbable dans le cadre normal du
        // jeu), considérer comme non connecté.
        return false;
    }

    private int dfsConnectedPieces(int x, int y, boolean[][] visited, Piece playerPiece) {
        // Base case: vérifier les limites et si la case a été visitée ou n'appartient
        // pas au joueur.
        if (x < 0 || y < 0 || x >= DIMENSION_LARGEUR || y >= DIMENSION_HAUTEUR || visited[y][x]
                || board[y][x].getPiece().getNumero() != playerPiece.getNumero()) {
            return 0;
        }

        visited[y][x] = true;
        int count = 1; // Compte cette pièce comme connectée.

        // Directions possibles pour se déplacer (horizontalement, verticalement, et
        // diagonalement).
        int[] dx = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int[] dy = { -1, -1, -1, 0, 0, 1, 1, 1 };

        // Parcourir toutes les directions.
        for (int i = 0; i < dx.length; i++) {
            count += dfsConnectedPieces(x + dx[i], y + dy[i], visited, playerPiece);
        }

        return count;
    }

    private int countTotalPlayerPieces(Piece playerPiece) {
        int count = 0;
        for (int y = 0; y < DIMENSION_HAUTEUR; y++) {
            for (int x = 0; x < DIMENSION_LARGEUR; x++) {
                if (board[y][x].getPiece().getNumero() == playerPiece.getNumero()) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isGameOver(Piece playerPiece) {
        return areAllPiecesConnected(playerPiece);
    }

    // voir si avantage numerique est mieux que desavantage numerique dans ce jeu
    public int evaluateGameState(Piece playerPiece) {
        int score = 0;
        // Calcul de la différence entre le nombre de pièces du joueur et de
        // l'adversaire
        int playerPiecesCount = countTotalPlayerPieces(playerPiece);
        Piece enemyPiece;
        if (playerPiece.getNumero() == 2 && playerPiece.getNumero() != 0) {
            enemyPiece = new Piece(4); // fonction
        } else {
            enemyPiece = new Piece(2);
        }
        int enemyPiecesCount = countTotalPlayerPieces(enemyPiece);

        // Prioriser les états où le joueur a un avantage numérique
        score += (playerPiecesCount - enemyPiecesCount) * 100;

        // Bonus si toutes les pièces du joueur sont connectées
        if (areAllPiecesConnected(playerPiece)) {
            score += 100000;
        }

        // Pénalité si toutes les pièces de l'adversaire sont connectées
        if (areAllPiecesConnected(enemyPiece)) {
            score -= 100000;
        }

        // Autres critères d'évaluation peuvent être ajoutés ici

        return score;
    }

    public List<Case> getPlayerCases(Piece playerPiece) {
        List<Case> playerCases = new ArrayList<>();
        for (int y = 0; y < DIMENSION_HAUTEUR; y++) {
            for (int x = 0; x < DIMENSION_LARGEUR; x++) {
                if (this.board[y][x].getPiece().getNumero() == playerPiece.getNumero()) {
                    playerCases.add(this.board[y][x]);
                }
            }
        }
        return playerCases;
    }

}

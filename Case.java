public class Case {

    private char lettre;
    private int numero;
    private Piece piece;
    private int i;
    private int j;

    public Case(char lettre, int numero, Piece piece, int i, int j) {
        this.lettre = lettre;
        this.numero = numero;
        this.piece = piece;
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return this.i;
    }

    public int getJ() {
        return this.j;
    }

    public char getLettre() {
        return this.lettre;
    }

    public int getNumero() {
        return this.numero;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setLettre(char lettre) {
        this.lettre = lettre;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    // Pour affichage de board dans Board
    public void afficherPiecePrint() {
        System.out.print(this.piece.getNumero() + " \t");
    }
}

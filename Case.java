public class Case {

    private char lettre;
    private int numero;
    private Piece piece;

    public Case(char lettre, int numero, Piece piece) {
        this.lettre = lettre;
        this.numero = numero;
        this.piece = piece;
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

    // Pour affichage de board dans Board
    public void afficherPiecePrint() {
        System.out.print(this.piece.getNumero() + " \t");
    }
}

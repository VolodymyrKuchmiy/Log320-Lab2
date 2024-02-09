public class Case {

    private String lettre;
    private String numero;
    private Piece piece;

    public Case(String lettre, String numero, Piece piece) {
        this.lettre = lettre;
        this.numero = numero;
        this.piece = piece;
    }

    public String getLettre() {
        return this.lettre;
    }

    public String getNumero() {
        return this.numero;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setLettre(String lettre) {
        this.lettre = lettre;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}

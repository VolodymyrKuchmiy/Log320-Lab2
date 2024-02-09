public class Piece {

    private int numero;

    public Piece(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return this.numero;
    }

    public String getCase() {
        String piece = "Undefined";
        switch (this.numero) {
            case (0):
                piece = "Case vide";
            case (2):
                piece = "Pion noir";
            case (4):
                piece = "Pion rouge";
        }
        return piece;
    }

}
public class Move {

    private Case caseDebut;
    private Case caseFin;

    public Move(Case caseDebut, Case caseFin) {
        this.caseDebut = caseDebut;
        this.caseFin = caseFin;
    }

    public void setCaseDebut(Case caseDebut) {
        this.caseDebut = caseDebut;
    }

    public void setCaseFin(Case caseFin) {
        this.caseFin = caseFin;
    }

    public Case getCaseDebut() {
        return this.caseDebut;
    }

    public Case getCaseFin() {
        return this.caseFin;
    }

    public String translateToServer() {
        return String.valueOf(caseDebut.getLettre()) + caseDebut.getNumero() + caseFin.getLettre()
                + caseFin.getNumero();
    }
}

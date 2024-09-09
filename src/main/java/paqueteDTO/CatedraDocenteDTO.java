package paqueteDTO;

public class CatedraDocenteDTO {

    public CatedraDocenteDTO(int legajoDocente, int codigoCatedra) {
        this.legajoDocente = legajoDocente;
        this.codigoCatedra = codigoCatedra;
    }

    public int getLegajoDocente() {
        return legajoDocente;
    }

    public void setLegajoDocente(int legajoDocente) {
        this.legajoDocente = legajoDocente;
    }

    public int getCodigoCatedra() {
        return codigoCatedra;
    }

    public void setCodigoCatedra(int codigoCatedra) {
        this.codigoCatedra = codigoCatedra;
    }
    private int legajoDocente;
    private int codigoCatedra;
}

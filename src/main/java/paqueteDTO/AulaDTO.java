package paqueteDTO;

public class AulaDTO {

    private int nroAula;
    private int capacidad;
    private boolean ocupada;

    public AulaDTO(int nroAula, int capacidad, boolean ocupada) {
        this.nroAula = nroAula;
        this.capacidad = capacidad;
        this.ocupada = ocupada;
    }

    public AulaDTO(int numeroAula, int capacidad) {
        this.nroAula = numeroAula;
        this.capacidad = capacidad;
    }

    public int getNroAula() {
        return nroAula;
    }

    public void setNroAula(int nroAula) {
        this.nroAula = nroAula;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

}

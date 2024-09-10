package paqueteDTO;

public class RecursoDTO {

    public RecursoDTO(int codigoRecurso, String DescripcionRecurso, int cantidadRecurso) {
        this.codigoRecurso = codigoRecurso;
        this.DescripcionRecurso = DescripcionRecurso;
        this.cantidadRecurso = cantidadRecurso;
    }

    public int getCodigoRecurso() {
        return codigoRecurso;
    }

    public void setCodigoRecurso(int codigoRecurso) {
        this.codigoRecurso = codigoRecurso;
    }

    public String getDescripcionRecurso() {
        return DescripcionRecurso;
    }

    public void setDescripcionRecurso(String DescripcionRecurso) {
        this.DescripcionRecurso = DescripcionRecurso;
    }

    public int getCantidadRecurso() {
        return cantidadRecurso;
    }

    public void setCantidadRecurso(int cantidadRecurso) {
        this.cantidadRecurso = cantidadRecurso;
    }
    int codigoRecurso;
    String DescripcionRecurso;
    int cantidadRecurso;
}

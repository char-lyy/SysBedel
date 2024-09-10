package paqueteDTO;

public class AulaDTO {
    private int numeroAulaDTO;
    private int capacidadAulaDTO;
    private String llaveAsignada;
    private boolean booleanAulaDTO;

    public AulaDTO(int numeroAulaDTO, int capacidadAulaDTO, String llaveAsignada, boolean booleanAulaDTO) {
        this.numeroAulaDTO = numeroAulaDTO;
        this.capacidadAulaDTO = capacidadAulaDTO;
        this.llaveAsignada = llaveAsignada;
        this.booleanAulaDTO = booleanAulaDTO;
    }

    
    public int getNumeroAulaDTO() {
        return numeroAulaDTO;
    }

    
    public void setNumeroAulaDTO(int numeroAulaDTO) {
        this.numeroAulaDTO = numeroAulaDTO;
    }

    public int getCapacidadAulaDTO() {
        return capacidadAulaDTO;
    }

    public void setCapacidadAulaDTO(int capacidadAulaDTO) {
        this.capacidadAulaDTO = capacidadAulaDTO;
    }

    public String getLlaveAsignada() {
        return llaveAsignada;
    }

    public void setLlaveAsignada(String llaveAsignada) {
        this.llaveAsignada = llaveAsignada;
    }

    public boolean isBooleanAulaDTO() {
        return booleanAulaDTO;
    }

    public void setBooleanAulaDTO(boolean booleanAulaDTO) {
        this.booleanAulaDTO = booleanAulaDTO;
    }
    
}

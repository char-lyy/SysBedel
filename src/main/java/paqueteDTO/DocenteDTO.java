/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteDTO;

public class DocenteDTO {

    public DocenteDTO(int legajoDocente, String nombreDocente, String apellidoDocente, String mailDocente, Boolean estadoDocente, String tituloDocente, int telefonoDocente) {
        this.legajoDocente = legajoDocente;
        this.nombreDocente = nombreDocente;
        this.apellidoDocente = apellidoDocente;
        this.mailDocente = mailDocente;
        this.estadoDocente = estadoDocente;
        this.tituloDocente = tituloDocente;
        this.telefonoDocente = telefonoDocente;
    }

    public int getLegajoDocente() {
        return legajoDocente;
    }

    public void setLegajoDocente(int legajoDocente) {
        this.legajoDocente = legajoDocente;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getApellidoDocente() {
        return apellidoDocente;
    }

    public void setApellidoDocente(String apellidoDocente) {
        this.apellidoDocente = apellidoDocente;
    }

    public String getMailDocente() {
        return mailDocente;
    }

    public void setMailDocente(String mailDocente) {
        this.mailDocente = mailDocente;
    }

    public Boolean getEstadoDocente() {
        return estadoDocente;
    }

    public void setEstadoDocente(Boolean estadoDocente) {
        this.estadoDocente = estadoDocente;
    }

    public String getTituloDocente() {
        return tituloDocente;
    }

    public void setTituloDocente(String tituloDocente) {
        this.tituloDocente = tituloDocente;
    }

    public int getTelefonoDocente() {
        return telefonoDocente;
    }

    public void setTelefonoDocente(int telefonoDocente) {
        this.telefonoDocente = telefonoDocente;
    }

    private int legajoDocente; 
    private String nombreDocente;
    private String apellidoDocente;
    private String mailDocente;
    private Boolean estadoDocente;
    private String tituloDocente;
    private int telefonoDocente;

    @Override
    public String toString() {
        return "DocenteDTO{" + "legajoDocente=" + legajoDocente + ", nombreDocente=" + nombreDocente + ", apellidoDocente=" + apellidoDocente + ", mailDocente=" + mailDocente + ", estadoDocente=" + estadoDocente + ", tituloDocente=" + tituloDocente + ", telefonoDocente=" + telefonoDocente + '}';
    }
    
    
}

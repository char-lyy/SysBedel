package paqueteDTO;

import utilidades.Fecha;
import utilidades.FechaTiempo;

public class CuentaDTO {

    private int id;
    private String passwordHash;
    private String email;
    private FechaTiempo createdAt;

    // Constructor vacío
    public CuentaDTO() {
    }

    // Constructor con parámetros
    public CuentaDTO(int id, String passwordHash, String email, FechaTiempo createdAt) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.email = email;
        this.createdAt = createdAt;
    }

    public CuentaDTO(int id, String passwordHash, FechaTiempo createdAt) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FechaTiempo getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(FechaTiempo createdAt) {
        this.createdAt = createdAt;
    }

}

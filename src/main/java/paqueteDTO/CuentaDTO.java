package paqueteDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public static boolean mailValido(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
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

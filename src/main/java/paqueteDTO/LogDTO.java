package paqueteDTO;

import utilidades.FechaTiempo;

public class LogDTO {
    private int id;
    private int userId;
    private FechaTiempo loginTime;
    private String ipAddress;
    private String status;

    // Constructor vacío
    public LogDTO() {}

    // Constructor con parámetros
    public LogDTO(int id, int userId, FechaTiempo loginTime, String ipAddress, String status) {
        this.id = id;
        this.userId = userId;
        this.loginTime = loginTime;
        this.ipAddress = ipAddress;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public FechaTiempo getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(FechaTiempo loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

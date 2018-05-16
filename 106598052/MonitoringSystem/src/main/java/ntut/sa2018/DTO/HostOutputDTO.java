package ntut.sa2018.DTO;

import ntut.sa2018.Others.Interface.IHostDTO;

public class HostOutputDTO implements IHostDTO {
    private String hostName;
    private String hostIp;
    private String status;
    private String lastCheck;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(String lastCheck) {
        this.lastCheck = lastCheck;
    }
}

package ntut.sa2018.DTO;

import ntut.sa2018.Others.Interface.IHostDTO;

public class HostInputDTO implements IHostDTO {
    public String hostName;
    public String hostIp;
    public String checkMethod;
    public int checkInterval;
}
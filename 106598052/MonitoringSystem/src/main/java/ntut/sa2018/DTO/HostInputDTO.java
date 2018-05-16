package ntut.sa2018.DTO;

import com.google.gson.Gson;
import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Others.Interface.IHostDTO;

import java.util.ArrayList;

public class HostInputDTO implements IHostDTO {
    public String hostName;
    public String hostIp;
    public String status;
    public String lastCheck;
    public String checkMethod;
    public int checkInterval;

}

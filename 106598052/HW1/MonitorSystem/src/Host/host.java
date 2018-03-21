package Host;

public class host {
    private String HostName;
    private String IP;
    private String FunctionName;
    private String Status;

    public host(final String hostName, final String ip, final String functionName){
        this.HostName = hostName;
        this.IP = ip;
        this.FunctionName = functionName;
    }

    public host(final String hostName, final String ip){
        this.HostName = hostName;
        this.IP = ip;
        this.FunctionName = null;
    }

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getFunctionName() {
        return FunctionName;
    }

    public void setFunctionName(String functionName) {
        FunctionName = functionName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}

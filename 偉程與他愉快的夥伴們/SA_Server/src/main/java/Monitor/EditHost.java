package Monitor;

import model.Host;

public class EditHost {
    private StorageBuilder hostRepository = StorageDirector.Build();
    public String Edit(String action,String ip,String name) {
        if(action.equals("create")){
            Host host = new Host();
            host.setHostIp(ip);
            host.setHostName(name);
            host.setStatus("error");
            host.setLastCheck("null");
            host.setCheckMethod(1);
            hostRepository.addHost(host);
        }else if(action.equals("delete")){
            hostRepository.deleteHost(ip);
        }
        String resultJSON = "{ \"result\" : true }";
        return resultJSON;
    }
}

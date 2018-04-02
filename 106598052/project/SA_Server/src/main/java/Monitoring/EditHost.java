package Monitoring;

import Repository.StorageRepository;
import config.StorageConfig;
import model.Host;

public class EditHost {
    private StorageRepository hostRepository = StorageConfig.Build();
    protected String Edit(String action,String ip,String name) {
        if(action.equals("create")){
            Host host = new Host();
            host.setHostIp(ip);
            host.setHostName(name);
            host.setStatus("error");
            host.setLastCheck("null");
            hostRepository.addHost(host);
        }else if(action.equals("delete")){
            hostRepository.deleteHost(ip);
        }

        String resultJSON = "{ \"result\" : true }";
        return resultJSON;
    }
}

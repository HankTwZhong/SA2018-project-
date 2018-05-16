package ntut.sa2018.UseCase;

import com.google.gson.Gson;
import ntut.sa2018.DTO.HostInputDTO;
import ntut.sa2018.DTO.HostOutputDTO;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

import java.util.ArrayList;

public class GetHostListUseCase {
    public ArrayList<HostOutputDTO> run(){
        //get the storage way
        StorageInterface hostRepository = StorageDirector.StorageBuild();
        //use the storage way to get the host list
        ArrayList<Host> hostList = hostRepository.getHost();
        ArrayList<HostOutputDTO> hostOutputDTOArrayList =new ArrayList<>();
        for(int i=0;i<hostList.size();i++){
            HostOutputDTO hostOutputDTO =new HostOutputDTO();
            hostOutputDTO.hostName=hostList.get(i).getHostName();
            hostOutputDTO.hostIp=hostList.get(i).getHostIP();
            hostOutputDTO.status=hostList.get(i).getHostStatus();
            hostOutputDTO.lastCheck=hostList.get(i).getLastCheck();
            hostOutputDTOArrayList.add(hostOutputDTO);
        }

        /*check host list
        for(Integer i=0 ; i<hostList.size(); i++){
            hostList.get(i).printHost();
        }*/
        return hostOutputDTOArrayList;
    }

    public String getHostListJson(ArrayList<HostOutputDTO> hostList){
        Gson gson = new Gson();
        String json = gson.toJson(hostList);
        return json;
    }




}

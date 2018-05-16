package ntut.sa2018.UseCase;

import com.google.gson.Gson;
import ntut.sa2018.DTO.HostOutputDTO;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

import java.util.ArrayList;

public class GetHostListUseCase {

    public ArrayList<Host> run(){
        //get the storage way
        StorageInterface hostRepository = StorageDirector.StorageBuild();
        //use the storage way to get the host list
        ArrayList<Host> hostArrayList = hostRepository.getHost();

        /*check host list
        for(Integer i=0 ; i<hostList.size(); i++){
            hostList.get(i).printHost();
        }*/
        return hostArrayList;
    }

    public String getHostListJson(ArrayList<Host> hostArrayList){
        ArrayList<HostOutputDTO> hostOutputDTOArrayList = new ArrayList<>();
        for(int i=0;i<hostArrayList.size();i++){
            try {
                HostOutputDTO hostOutputDTO = new HostOutputDTO();
                hostOutputDTO.setHostName(hostArrayList.get(i).getHostName());
                hostOutputDTO.setHostIp(hostArrayList.get(i).getHostIP());
                hostOutputDTO.setStatus(hostArrayList.get(i).getHostStatus());
                hostOutputDTO.setLastCheck(hostArrayList.get(i).getLastCheck());
                hostOutputDTOArrayList.add(hostOutputDTO);
            }
            catch (Exception ex){
                System.out.println("get host fails , ex = " + ex.toString());
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(hostOutputDTOArrayList);
        System.out.println("JSON= " + json);
        return json;
    }
}

package ntut.sa2018.UseCase;

import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

import java.util.ArrayList;

public class GetHostListUseCase {
    public ArrayList<Host> run(){
        //get the storage way
        StorageInterface hostRepository = StorageDirector.StorageBuild();
        //use the storage way to get the host list
        ArrayList<Host> hostList = hostRepository.getHost();

        /*check host list
        for(Integer i=0 ; i<hostList.size(); i++){
            hostList.get(i).printHost();
        }*/
        return hostList;
    }
}

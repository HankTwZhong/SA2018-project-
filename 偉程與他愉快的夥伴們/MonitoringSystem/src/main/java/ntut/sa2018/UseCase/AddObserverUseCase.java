package ntut.sa2018.UseCase;

import ntut.sa2018.Domain.Host.Host;

import java.util.ArrayList;

public class AddObserverUseCase {
    public void run(ArrayList<Host> hostList){
        for(Integer i=0 ; i<hostList.size() ; i++){
            for(Integer j=0; j<hostList.get(i).getContact().size();j++){
                hostList.get(i).addSubscriber(hostList.get(i).getContact().get(j));
            }
        }
    }
}

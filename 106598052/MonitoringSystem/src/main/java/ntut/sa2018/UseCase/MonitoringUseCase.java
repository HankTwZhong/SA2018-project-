package ntut.sa2018.UseCase;

import ntut.sa2018.DTO.HostInputDTO;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Others.MontioringHost.Montioring;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MonitoringUseCase {
    private GetHostListUseCase getHostUseCase = new GetHostListUseCase();
    private DeleteHostUseCase deleteHostUseCase = new DeleteHostUseCase();
    private AddHostUseCase addHostUseCase = new AddHostUseCase();
    private AddObserverUseCase addObserverUseCase = new AddObserverUseCase();

    private ArrayList<Host> hostList = getHostUseCase.run();

    public void run(){
        addObserverUseCase.run(hostList);
        Timer timer = new Timer();
        TimerTask Monitor = new Montioring(hostList);
        timer.schedule(Monitor, 1000, 1000);
    }

    public String getHostListJson(){
        return getHostUseCase.getHostListJson(hostList);
    }

    public String deleteHost(String hostip){
        /*delete a host in DB*/
        String result = deleteHostUseCase.delete(hostip);
        /*after delete host in DB, get the new host list*/
        hostList = getHostUseCase.run();
        run();
        return result;
    }

    public String addHost(HostInputDTO hostInputDTO){
        /*add a host in DB*/
        String result = addHostUseCase.create(hostInputDTO);
        /*after add host in DB, get the new host list*/
        hostList = getHostUseCase.run();
        run();
        return result;
    }
}

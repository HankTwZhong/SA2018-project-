package ntut.sa2018.UseCase;

import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.UseCase.MontioringHost.Montioring;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MonitoringUseCase {
    private static Timer timer;
    private static MonitoringUseCase instance = null;

    private MonitoringUseCase(){
        timer = new Timer();
    }

    public static MonitoringUseCase Use() {
        if (instance == null) {
            instance = new MonitoringUseCase();
        }
        return instance;
    }

    public void Start(){
        timer = new Timer();
        ArrayList<Host> hostArrayList = HostManagementUseCase.Use().GetHostList();
        for(Host host : hostArrayList) {
            TimerTask Monitor = new Montioring(host);
            timer.schedule(Monitor, 1000, host.getCheckInterval()*1000);
        }
    }

    public void ReStart(){
        timer.cancel();
        Start();
    }
}
package ntut.sa2018.UseCase;

import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Others.MontioringHost.Montioring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MonitoringUseCase {
    public void run(ArrayList<Host> hostList){
        Timer timer = new Timer();
        TimerTask Monitor = new Montioring(hostList);
        timer.schedule(Monitor, 1000, 1000);
    }
}

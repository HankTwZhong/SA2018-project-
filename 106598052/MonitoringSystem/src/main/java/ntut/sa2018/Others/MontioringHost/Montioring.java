package ntut.sa2018.Others.MontioringHost;

import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.UseCase.CheckHostUseCase;

import javax.management.monitor.Monitor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

public class Montioring extends TimerTask{
    private ArrayList<Host> hostList;
    private int initalTimer;
    public Montioring(ArrayList<Host> hostList){
        this.hostList = hostList;
        Calendar timer = Calendar.getInstance();
        this.initalTimer = timer.get(Calendar.SECOND);
    }
    @Override
    public void run() {
        Calendar timerNow = Calendar.getInstance();
        CheckHostUseCase checkHostUseCase = new CheckHostUseCase();
        int Timer = initalTimer - timerNow.get(Calendar.SECOND) + 60;
        for(Integer i=0 ; i<hostList.size() ; i++){
            if(Timer % hostList.get(i).getCheckInterval()==0){
                checkHostUseCase.run(hostList.get(i));
            }
        }
    }
}

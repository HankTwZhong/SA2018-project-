package ntut.sa2018.Others.MontioringHost;

import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.UseCase.CheckHostUseCase;

import javax.management.monitor.Monitor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

public class Montioring extends TimerTask{
    private Host host;
    private CheckHostUseCase checkHostUseCase;
    public Montioring(Host h){
        this.host = h;
        this.checkHostUseCase = new CheckHostUseCase();
    }
    @Override
    public void run() {
        checkHostUseCase.run(host);
    }
}

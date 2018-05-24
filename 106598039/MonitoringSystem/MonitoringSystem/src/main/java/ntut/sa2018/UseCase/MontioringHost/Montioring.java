package ntut.sa2018.UseCase.MontioringHost;

import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Controller.CheckHostMethod.CheckDirector;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

public class Montioring extends TimerTask{
    private Host host;
    public Montioring(Host h){
        this.host = h;
    }

    @Override
    public void run() {
        host.setHostStatus(CheckDirector.PingBuild(host.getCheckMethod()).execute(host.getHostIP()));
        host.setLastCheck(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(Calendar.getInstance().getTime()));
        //check host status has change or not
        System.out.println("host name = " + host.getHostName() + ", status = " + host.getHostStatus() + ", checktime = " + host.getLastCheck());
    }
}

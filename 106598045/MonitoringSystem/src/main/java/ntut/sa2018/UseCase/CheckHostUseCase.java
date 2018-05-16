package ntut.sa2018.UseCase;

import ntut.sa2018.Others.CheckHostMethod.CheckDirector;
import ntut.sa2018.Domain.Host.Host;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CheckHostUseCase {
    public void run(Host host){
        host.setHostStatus(CheckDirector.PingBuild(host.getCheckMethod()).execute(host.getHostIP()));
        host.setLastCheck(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(Calendar.getInstance().getTime()));
        //check host status has change or not
        System.out.println("host name = " + host.getHostName() + ", status = " + host.getHostStatus() + ", checktime = " + host.getLastCheck());
    }
}

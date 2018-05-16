package ntut.sa2018;

import ntut.sa2018.DTO.HostOutputDTO;
import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import ntut.sa2018.UseCase.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        /*get host list use case
        GetHostListUseCase getHostUseCase = new GetHostListUseCase();
        getHostUseCase.run();*/

        /*add host use case
        AddHostUseCase addHostUseCase = new AddHostUseCase();
        addHostUseCase.run("Local","140.124.181.15","ping",5);*/

        /*get host use case
        GetHostListUseCase getHostUseCase = new GetHostListUseCase();
        ArrayList<Host> hostList = getHostUseCase.run();
        CheckHostUseCase checkHostUseCase = new CheckHostUseCase();
        for(Integer i=0 ; i<hostList.size() ; i++){
            checkHostUseCase.run(hostList.get(i));
        }*/

        /*monitoring use case
        GetHostListUseCase getHostUseCase = new GetHostListUseCase();
        ArrayList<Host> hostList = getHostUseCase.run();
        MonitoringUseCase monitoringUseCase = new MonitoringUseCase();
        monitoringUseCase.run(hostList);*/

        /*add host with contact use case
        Contact contact = new ContactBuilder.newInstance().
                contactName("賴偉程").
                email("test@ntut.edu.tw").
                build();
        AddHostUseCase addHostUseCase = new AddHostUseCase();
        addHostUseCase.run("Local","140.124.181.11000","ping",5, contact);
        GetHostListUseCase getHostUseCase = new GetHostListUseCase();
        ArrayList<Host> hostList = getHostUseCase.run();*/

        /*add notify*/
        GetHostListUseCase getHostUseCase = new GetHostListUseCase();
        ArrayList<HostOutputDTO> hostList = getHostUseCase.run();
//        AddObserverUseCase addObserverUseCase = new AddObserverUseCase();
//        addObserverUseCase.run(hostList);
//        MonitoringUseCase monitoringUseCase = new MonitoringUseCase();
//        monitoringUseCase.run(hostList);




    }
}

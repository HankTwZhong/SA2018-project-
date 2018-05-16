package ntut.sa2018;

import ntut.sa2018.DTO.HostOutputDTO;
import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import ntut.sa2018.Others.ClientRequireHandler.CilentRequireHandler;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;
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

        MonitoringUseCase monitoringUseCase = new MonitoringUseCase();
        HostManagementUseCase hostManagementUseCase = new HostManagementUseCase();
        monitoringUseCase.run(hostManagementUseCase.GetHostList());

        ArrayList<DataOutputStream> clientOutputStreams = new ArrayList<DataOutputStream>();
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            int port = 5050;
            ServerSocket serverSocket = new ServerSocket(port);//開始監聽port連線請求。
            ThreadGroup threadGroup = new ThreadGroup("clientRequirement");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                if(clientSocket.isConnected()) {
                    try {
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                        clientOutputStreams.add(dos);
                        Thread thread = new Thread(threadGroup,new CilentRequireHandler(clientSocket, clientOutputStreams, hostManagementUseCase));
                        thread.start();
                        System.out.println("目前共:"+threadGroup.activeCount()+"個請求");
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }






    }
}

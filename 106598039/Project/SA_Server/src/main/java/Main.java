import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import Monitoring.MonitoringHost;
import Monitoring.ClientRequireHandler;
import Notify.SendingEmail;
import Notify.SmtpAuthenticator;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {
    public static void main(String[] args) {
        ArrayList<DataOutputStream> clientOutputStreams = new ArrayList<DataOutputStream>();
        /*開始監控host，一秒後開始作，每六秒做一次*/
        MonitoringHost MonitorSchedule = new MonitoringHost();
        MonitorSchedule.addTask(1000, 6000);

        try {
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
                        Thread thread = new Thread(threadGroup,new ClientRequireHandler(clientSocket, clientOutputStreams, MonitorSchedule));
                        thread.start();
                        System.out.println("目前共:"+threadGroup.activeCount()+"個請求");
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
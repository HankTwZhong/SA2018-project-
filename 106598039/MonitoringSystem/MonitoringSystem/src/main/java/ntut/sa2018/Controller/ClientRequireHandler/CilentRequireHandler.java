package ntut.sa2018.Controller.ClientRequireHandler;

import com.google.gson.Gson;
import ntut.sa2018.UseCase.HostManagementUseCase;
import ntut.sa2018.UseCase.MonitoringUseCase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CilentRequireHandler implements Runnable{
    DataInputStream dis;
    ArrayList<DataOutputStream> clientOutputStreams = new ArrayList<DataOutputStream>();
    Gson gson = new Gson();
    protected Socket clientSocket;
    public CilentRequireHandler(Socket clientSocket, ArrayList<DataOutputStream> clientOutputSteams){
        this.clientOutputStreams=clientOutputStreams;
        this.clientSocket=clientSocket;
        try{
            dis = new DataInputStream(clientSocket.getInputStream());
        }catch(Exception e) {
            System.out.println("ClientRequireHandler : "+e.toString());
        }

    }
    @Override
    public void run() {
        try {
            while (true) {
                if(this.clientSocket.isClosed()) return;
                String msg = dis.readUTF();  //接收cilent的要求(json)
                Map<String,String> map = new HashMap<String,String>();
                Map<String,String> clientMsg = (Map<String,String>) gson.fromJson(msg, map.getClass());
                String response ="";
                System.out.println("clientMsg : " + clientMsg);
                String action = clientMsg.get("action");

                /*socket get action monitor, return all the host*/
                if(action.equals("monitor")){
                    response =  "{ \"result\" : "+ new ntut.sa2018.Others.ClientRequireHandler.Presenter().getHostListJson(HostManagementUseCase.Use().GetHostListDTO())+" }";
                }

                /*socket get action create, create a host*/
                else if(action.equals("create")){
                    if(HostManagementUseCase.Use().AddHost(new ntut.sa2018.Others.ClientRequireHandler.Presenter().setHostInputDTO(clientMsg)))
                        response = new ntut.sa2018.Others.ClientRequireHandler.Presenter().getHostListJson(HostManagementUseCase.Use().GetHostListDTO());
                    else
                        response = "{ \"result\" : false }" ;
                    MonitoringUseCase.Use().ReStart();
                }

                /*socket get action delete, delete a host*/
                else if(action.equals("delete")){
                    String ip = clientMsg.get("hostIp");
                    System.out.println(ip);
                    if(HostManagementUseCase.Use().DeleteHost(ip))
                        response = new ntut.sa2018.Others.ClientRequireHandler.Presenter().getHostListJson(HostManagementUseCase.Use().GetHostListDTO());
                    else
                        response = "{ \"result\" : false }" ;
                    MonitoringUseCase.Use().ReStart();
                }

                /*return msg to client*/
                sendMsgToClient(response);
                System.out.println("response to client : " + response);
            }
        } catch (IOException e) {
            System.out.println("client disconnect, error message : "+e.toString());
        }
    }

    public void sendMsgToClient(String message) {
        DataOutputStream writer = null;
        try {
            writer = new DataOutputStream(clientSocket.getOutputStream());
            writer.writeUTF(message);
            writer.flush();
        } catch (Exception e) {
            System.out.println("broadCast : "+e.toString());
            clientOutputStreams.remove(writer);
        }
    }
}

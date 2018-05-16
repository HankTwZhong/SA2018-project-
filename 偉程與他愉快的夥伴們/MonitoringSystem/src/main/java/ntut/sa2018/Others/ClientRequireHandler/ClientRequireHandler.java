package ntut.sa2018.Others.ClientRequireHandler;

import com.google.gson.Gson;
import ntut.sa2018.DTO.HostInputDTO;
import ntut.sa2018.UseCase.GetHostListUseCase;
import ntut.sa2018.UseCase.MonitoringUseCase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientRequireHandler implements Runnable{
    private DataInputStream dis;
    private ArrayList<DataOutputStream> clientOutputStreams = new ArrayList<DataOutputStream>();
    private Gson gson = new Gson();
    private MonitoringUseCase monitoringUseCase;
    protected Socket clientSocket;

    public ClientRequireHandler(Socket clientSocket, ArrayList<DataOutputStream> clientOutputSteams, MonitoringUseCase monitoringUseCase){
        this.clientOutputStreams=clientOutputStreams;
        this.clientSocket=clientSocket;
        this.monitoringUseCase = monitoringUseCase;
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
                    GetHostListUseCase getHostListUseCase=new GetHostListUseCase();
                    response =  "{ \"result\" : "+monitoringUseCase.getHostListJson()+" }";
                }
                /*socket get action create, create a host*/
                else if(action.equals("create")){
                    HostInputDTO hostInputDTO =new HostInputDTO();
                    hostInputDTO.hostIp=clientMsg.get("hostIp");
                    hostInputDTO.hostName=clientMsg.get("hostName");
                    /*WARNING!!!! check method and interval wait jsp inplement
                    hostInputDTO.checkMethod=clientMsg.get("checkMethod");
                    hostInputDTO.checkInterval=Integer.parseInt(clientMsg.get("checkInterval"));*/
                    hostInputDTO.checkMethod="Reachable";
                    hostInputDTO.checkInterval=5;
                    response=monitoringUseCase.addHost(hostInputDTO);
                }
                /*socket get action delete, delete a host*/
                else if(action.equals("delete")){
                    String hostIp = clientMsg.get("hostIp");
                    response=monitoringUseCase.deleteHost(hostIp);
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

package ntut.sa2018.Others.ClientRequireHandler;

import ntut.sa2018.DTO.HostInputDTO;
import com.google.gson.Gson;
import ntut.sa2018.DTO.HostOutputDTO;
import ntut.sa2018.UseCase.HostManagementUseCase;

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
    HostManagementUseCase hostManagementUseCase;
    Gson gson = new Gson();
    protected Socket clientSocket;
    public CilentRequireHandler(Socket clientSocket, ArrayList<DataOutputStream> clientOutputSteams, HostManagementUseCase hostManagementUseCase){
        this.clientOutputStreams=clientOutputStreams;
        this.clientSocket=clientSocket;
        this.hostManagementUseCase = hostManagementUseCase;
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

                if(action.equals("monitor")){
                    response =  "{ \"result\" : "+getHostListJson(this.hostManagementUseCase.GetHostListDTO())+" }";
                }else if(action.equals("create")){
                    HostInputDTO hostInputDTO=setHostInputDTO(clientMsg);
                    response=this.hostManagementUseCase.AddHost(hostInputDTO);

                }else if(action.equals("delete")){
                    String ip = clientMsg.get("hostIp");
                    System.out.println(ip);
                    response=this.hostManagementUseCase.DeleteHost(ip);
                }
                sendMsgToClient(response);  //回傳message給cilent
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

    public String getHostListJson(ArrayList<HostOutputDTO> hostList){
        Gson gson = new Gson();
        String json = gson.toJson(hostList);
        return json;
    }

    public HostInputDTO setHostInputDTO(Map<String,String> clientMsg){
        HostInputDTO hostInputDTO =new HostInputDTO();
        hostInputDTO.hostIp=clientMsg.get("hostIp");
        hostInputDTO.hostName=clientMsg.get("hostName");
        return  hostInputDTO;
    }


}

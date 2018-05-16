package ntut.sa2018.UseCase;

import com.google.gson.Gson;
import ntut.sa2018.DTO.HostInputDTO;
import ntut.sa2018.DTO.HostOutputDTO;
import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

import java.util.ArrayList;

public class HostManagementUseCase {
    StorageInterface hostRepository = StorageDirector.StorageBuild();
    ArrayList<Host> hostList = hostRepository.getHost();

    public ArrayList<HostOutputDTO> GetHostListDTO(){
        //use the storage way to get the host list
        System.out.print(hostList.get(0).getHostStatus());
        System.out.print(hostList.get(0).getLastCheck());
        ArrayList<HostOutputDTO> hostOutputDTOArrayList =new ArrayList<HostOutputDTO>();
        for(int i=0;i<hostList.size();i++){
            HostOutputDTO hostOutputDTO =new HostOutputDTO();
            hostOutputDTO.hostName=hostList.get(i).getHostName();
            hostOutputDTO.hostIp=hostList.get(i).getHostIP();
            hostOutputDTO.status=hostList.get(i).getHostStatus();
            hostOutputDTO.lastCheck=hostList.get(i).getLastCheck();
            hostOutputDTOArrayList.add(hostOutputDTO);
        }

        /*check host list
        for(Integer i=0 ; i<hostList.size(); i++){
            hostList.get(i).printHost();
        }*/
        return hostOutputDTOArrayList;
    }

    public ArrayList<Host> GetHostList(){
        return hostList;
    }

    public String AddHost(HostInputDTO hostInputDTO){
        String result = "{ \"result\" : true }" ;
        Contact contact=new ContactBuilder.newInstance().
                contactName("賴偉程").
                email("t106598064@ntut.edu.tw").
                line(null).
                facebook(null).
                skype(null).
                otheraddress(null).
                otheraddress2(null).
                build();
        try{
            Host host = new HostBuilder.newInstance().
                    name(hostInputDTO.hostName).
                    address(hostInputDTO.hostIp).
                    checkCommand("Console").
                    checkInterval(5).
                    contact(contact).
                    build();
            this.hostRepository.addHost(host);
            this.hostList = hostRepository.getHost();
        }
        catch (Exception ex){
            System.out.println("add host fails, result = " + ex.toString());
            result = "{ \"result\" : false }" ;
        }
        return result;
    }

    public String DeleteHost(String hostIp){
        String result = "{ \"result\" : true }" ;
        try {
            this.hostRepository.deleteHost(hostIp);
            this.hostList = hostRepository.getHost();
        }catch(Exception ex){
            System.out.println("delete host fails, result = " + ex.toString());
            result = "{ \"result\" : false }" ;
        }
        return result;
    }
}

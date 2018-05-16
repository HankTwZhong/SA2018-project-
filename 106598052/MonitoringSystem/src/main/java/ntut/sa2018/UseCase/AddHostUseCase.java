package ntut.sa2018.UseCase;


import ntut.sa2018.DTO.HostInputDTO;
import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

import java.util.ArrayList;

public class AddHostUseCase {
    private StorageInterface hostRepository = StorageDirector.StorageBuild();

    public boolean run(String hostName, String hostIP, String checkMethod, int checkInterval, ArrayList<Contact> contact){
        boolean result = true;
        try{
            Host host = new HostBuilder.newInstance().
                    name(hostName).
                    address(hostIP).
                    checkCommand(checkMethod).
                    checkInterval(checkInterval).
                    contact(contact).
                    build();
            hostRepository.addHost(host);
        }
        catch (Exception ex){
            System.out.println("add host fails, result = " + ex.toString());
            result = false;
        }
        return result;
    }

    public String create(HostInputDTO hostInputDTO){
        String result = "{ \"result\" : true }" ;
        Contact contact=new ContactBuilder.newInstance().
                contactName("賴偉程").
                email("t106598064@ntut.edu.tw").
                build();
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(contact);

        try{
            Host host = new HostBuilder.newInstance().
                    name(hostInputDTO.hostName).
                    address(hostInputDTO.hostIp).
                    checkCommand(hostInputDTO.checkMethod).
                    checkInterval(hostInputDTO.checkInterval).
                    contact(contactArrayList).
                    build();
            hostRepository.addHost(host);
        }
        catch (Exception ex){
            System.out.println("add host fails, result = " + ex.toString());
            result = "{ \"result\" : true }" ;
        }
        return result;
    }
}

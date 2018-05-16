package ntut.sa2018.UseCase;


import ntut.sa2018.DTO.HostInputDTO;
import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

public class AddHostUseCase {
    public String run(HostInputDTO hostInputDTO){
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

        StorageInterface hostRepository = StorageDirector.StorageBuild();
        try{
            Host host = new HostBuilder.newInstance().
                    name(hostInputDTO.hostName).
                    address(hostInputDTO.hostIp).
                    checkCommand(hostInputDTO.checkMethod).
                    checkInterval(hostInputDTO.checkInterval).
                    contact(contact).
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

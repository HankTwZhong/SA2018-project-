package ntut.sa2018.UseCase;


import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

public class AddHostUseCase {
    public boolean run(String hostName, String hostIP, String checkMethod, int checkInterval, Contact contact){
        boolean result = true ;

        StorageInterface hostRepository = StorageDirector.StorageBuild();
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
}

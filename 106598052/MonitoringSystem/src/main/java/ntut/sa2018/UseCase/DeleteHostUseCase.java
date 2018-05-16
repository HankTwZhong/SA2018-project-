package ntut.sa2018.UseCase;

import ntut.sa2018.DTO.HostInputDTO;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

public class DeleteHostUseCase {
    private StorageInterface hostRepository = StorageDirector.StorageBuild();

    public boolean run(String hostIP){
        boolean result = true;
        try {
            hostRepository.deleteHost(hostIP);
        }
        catch (Exception ex){
            result = false;
            System.out.println("delete host fails = " + ex.toString());
        }
        return result;
    }

    public String delete(String hostIp){
        String result = "{ \"result\" : true }" ;
        StorageInterface hostRepository = StorageDirector.StorageBuild();
        try {
            hostRepository.deleteHost(hostIp);
        }catch(Exception ex){
            System.out.println("delete host fails, result = " + ex.toString());
            result = "{ \"result\" : false }" ;
        }
        return result;
    }
}

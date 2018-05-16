package ntut.sa2018.UseCase;

import ntut.sa2018.DTO.HostInputDTO;
import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

public class DeleteHostUseCase {
    public String run(HostInputDTO hostInputDTO){
        String result = "{ \"result\" : true }" ;
        StorageInterface hostRepository = StorageDirector.StorageBuild();
        try {
            hostRepository.deleteHost(hostInputDTO.hostIp);
        }catch(Exception ex){
            System.out.println("delete host fails, result = " + ex.toString());
            result = "{ \"result\" : false }" ;
        }
        return result;
    }
}

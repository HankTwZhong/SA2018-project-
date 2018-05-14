package ntut.sa2018.UseCase;

import ntut.sa2018.Others.Interface.StorageInterface;
import ntut.sa2018.Others.Storage.StorageDirector;

public class DeleteHostUseCase {
    public void run(String hostIP){
        StorageInterface hostRepository = StorageDirector.StorageBuild();
        hostRepository.deleteHost(hostIP);
    }
}

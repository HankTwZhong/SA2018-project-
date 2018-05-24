package ntut.sa2018.Controller.HostManagement;

import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.UseCase.Interface.HostManagementRepository;

import java.util.ArrayList;

public class HostManagementMongoDB implements HostManagementRepository {

    @Override
    public ArrayList<Host> getHost() {
        return null;
    }

    @Override
    public void addHost(Host host) {

    }

    @Override
    public void deleteHost(String hostIP) {

    }
}

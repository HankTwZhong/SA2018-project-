package ntut.sa2018.Others.Storage;

import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Others.Interface.StorageInterface;

import java.util.ArrayList;

public class StorageMongoDB implements StorageInterface{

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

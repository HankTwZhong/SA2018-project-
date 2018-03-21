package DB;

import model.Host;

import java.util.List;

public interface StoragePort {
    public void addHost(Host host);
    public List<Host> getHost();
    public void deleteHost(String hostIp);
}

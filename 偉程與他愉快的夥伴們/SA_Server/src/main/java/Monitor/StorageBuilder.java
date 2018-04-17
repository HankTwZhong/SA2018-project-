package Monitor;

import java.util.ArrayList;
import model.Host;

public interface StorageBuilder {
	public void addHost(Host host);
    public ArrayList<Host> getHost();
    public void deleteHost(String hostIp);
}

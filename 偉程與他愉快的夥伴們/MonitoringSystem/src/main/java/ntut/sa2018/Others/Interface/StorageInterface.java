package ntut.sa2018.Others.Interface;


import ntut.sa2018.Domain.Host.Host;

import java.util.ArrayList;

public interface StorageInterface {
	public ArrayList<Host> getHost();

	public void addHost(Host host);

	public void deleteHost(String hostIP);
}

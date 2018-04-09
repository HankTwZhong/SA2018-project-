package Monitoring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import Repository.StorageRepository;
import config.StorageConfig;
import model.Host;
import Command.GetHostStatusCommand;

public class MonitoringHost {
	private ScheduledExecutorService scheduler;
	private StorageRepository hostRepository = StorageConfig.Build();
	private ArrayList<Host> hostList = hostRepository.getHost();
	
	public MonitoringHost() {
		    scheduler = Executors.newSingleThreadScheduledExecutor();
	}
	public void addTask(long delay, long breakTime) {
		scheduler.scheduleWithFixedDelay(new Shot(), delay, breakTime, TimeUnit.MILLISECONDS);
	}
	private class Shot implements Runnable {
		public Shot() {}

		public void run() {
			monitorHost();
		}
	}
	public String getHostListJson(){
		Gson gson = new Gson();
		String json = gson.toJson(hostList);
		return json;
	}

	public void updateHostList(){
		hostList = hostRepository.getHost();
	}

	public void monitorHost(){
		for(int i=0 ; i<hostList.size() ; i++) {
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(Calendar.getInstance().getTime());
			hostList.get(i).setLastCheck(timeStamp);
            String status = GetHostStatusCommand.execute(hostList.get(i).getHostIp(), hostList.get(i).getCheckMethod());

            if(status.equals(hostList.get(i).getStatus())) {
                //狀態發生改變
            }
            hostList.get(i).setStatus(status);
		}
		Gson gson = new Gson();
		String json = gson.toJson(hostList);
		System.out.println("Server monitorHost result : ");
		System.out.println(json);

	}
}

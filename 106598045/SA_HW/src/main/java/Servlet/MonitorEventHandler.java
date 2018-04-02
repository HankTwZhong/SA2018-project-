package Servlet;

import NotifyManager.NotifyManager;
import MonitorEvent.*;

import java.util.List;

public class MonitorEventHandler {
    NotifyManager notifyManager;
    List<MonitorEvent> eventList;

    public MonitorEventHandler() {
        notifyManager = NotifyMnagerBuilder.Build();
    }

    public void AddEvent(MonitorEvent event){
        eventList.add(event);
    }
}

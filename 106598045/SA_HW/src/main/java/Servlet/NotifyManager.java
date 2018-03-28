package Servlet;
import model.*;
import java.util.*;

public class NotifyManager {
    private Map<Host, Contact> notifyPair = new Hashtable<Host, Contact>();

    public void Register(Host host, Contact contact) {
        notifyPair.put(host, contact);
    }
}

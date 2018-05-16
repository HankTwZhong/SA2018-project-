package ntut.sa2018.Others;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Publisher {
    private List<Subscriber> subscriberList = new ArrayList<Subscriber>();
    public void addSubscriber(Subscriber s) { subscriberList.add(s);}
    public void delSubscriber(Subscriber s) { subscriberList.remove(s);}
    public void notifySubscribers() {
        Iterator<Subscriber> subscriberIterator = subscriberList.iterator();
        Subscriber subscriber;
        while ( subscriberIterator.hasNext() ) {
            subscriber = subscriberIterator.next();
            subscriber.update(this);
        }
    }
    abstract public String getInfo();
}
package DB;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import model.Host;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class StorageDB implements StoragePort {

    public MongoCollection<Document> getDB(){
        MongoClient mongoClient = new MongoClient("140.124.181.15");
        MongoDatabase database = mongoClient.getDatabase( "MonitorSystem" );
        MongoCollection<Document> collection = database.getCollection("host");
        return collection;
    }
    public MongoCollection<Document> getMlab(){
        try{
            MongoClientURI uri  = new MongoClientURI("mongodb://Islab:islab1221@ds121189.mlab.com:21189/monitorsystem");
            MongoClient client = new MongoClient(uri);
            MongoDatabase database = client.getDatabase(uri.getDatabase());
            MongoCollection<Document> collection = database.getCollection("host");
            return collection;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
    public void addHost(Host host){
        MongoCollection<Document> collection = getMlab();
        Document doc =new Document("hostName", host.getHostName())
                .append("hostIp", host.getHostIp());
        collection.insertOne(doc);
    }
    public List<Host> getHost(){
        List<Host> hostList = new ArrayList<Host>();
        MongoCollection<Document> collection = getMlab();

        FindIterable<Document> myDoc = collection.find();
        for (Document document : myDoc) {
            Host host = new Host();
            host.setHostName(document.get("hostName").toString());
            host.setHostIp(document.get("hostIp").toString());
            hostList.add(host);
        }
        return hostList;
    }
    public void deleteHost(String hostIp){
        MongoCollection<Document> collection = getMlab();
        Bson deleteHost = Filters.eq("hostIp", hostIp);
        collection.deleteOne(deleteHost);
    }
}

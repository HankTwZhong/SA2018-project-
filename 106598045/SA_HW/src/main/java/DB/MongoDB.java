package DB;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import model.Host;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MongoDB {
    public static MongoCollection<Document> getDB(){
        MongoClient mongoClient = new MongoClient("127.0.0.1");
        MongoDatabase database = mongoClient.getDatabase( "MonitorSystem" );
        MongoCollection<Document> collection = database.getCollection("host");
        return collection;
    }
    public static void addHost(Host host){
        MongoCollection<Document> collection = getDB();
        Document doc =new Document("hostName", host.getHostName())
                .append("hostIp", host.getHostIp());
        collection.insertOne(doc);
    }
    public static List<Host> getHost(){
        List<Host> hostList = new ArrayList<Host>();
        MongoCollection<Document> collection = getDB();

        FindIterable<Document> myDoc = collection.find();
        for (Document document : myDoc) {
            Host host = new Host();
            host.setHostName(document.get("hostName").toString());
            host.setHostIp(document.get("hostIp").toString());
            hostList.add(host);
        }
        return hostList;
    }
    public static void deleteHost(String hostIp){
        MongoCollection<Document> collection = getDB();
        Bson deleteHost = Filters.eq("hostIp", hostIp);
        collection.deleteOne(deleteHost);
    }
}

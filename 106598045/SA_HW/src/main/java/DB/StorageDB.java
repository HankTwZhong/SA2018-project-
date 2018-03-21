package DB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class StorageDB implements StoragePort {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> collection;

    public MongoCollection<Document> getDB(){
        mongoClient = new MongoClient("140.124.181.15");
        mongoDatabase = mongoClient.getDatabase( "MonitorSystem" );
        collection = mongoDatabase.getCollection("host");
        return collection;
    }
    public MongoCollection<Document> getMlab(){
        System.out.println(getDateTime());
        try{
            MongoClientURI uri  = new MongoClientURI("mongodb://Islab:islab1221@ds121189.mlab.com:21189/monitorsystem");
            mongoClient = new MongoClient(uri);
            mongoDatabase = mongoClient.getDatabase(uri.getDatabase());
            collection = mongoDatabase.getCollection("host");
            System.out.println(getDateTime());
            return collection;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
    public void addHost(Host host){
        System.out.println("addHost start " + getDateTime());
        collection = getMlab();
        Document doc =new Document("hostName", host.getHostName())
                .append("hostIp", host.getHostIp());
        collection.insertOne(doc);
        mongoClient.close();
        System.out.println("addHost finish " + getDateTime());
    }
    public List<Host> getHost(){
        System.out.println("getHost start " + getDateTime());
        List<Host> hostList = new ArrayList<Host>();
        collection = getMlab();

        FindIterable<Document> myDoc = collection.find();
        for (Document document : myDoc) {
            Host host = new Host();
            host.setHostName(document.get("hostName").toString());
            host.setHostIp(document.get("hostIp").toString());
            hostList.add(host);
        }
        mongoClient.close();
        System.out.println("getHost finish " + getDateTime());
        return hostList;
    }
    public void deleteHost(String hostIp){
        System.out.println("deleteHost start " + getDateTime());
        collection = getMlab();
        collection.deleteOne(eq("hostIp", hostIp));
        mongoClient.close();
        System.out.println("deleteHost finish " + getDateTime());
    }
    public String getDateTime(){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        String strDate = sdFormat.format(date);
        return strDate;
    }
}

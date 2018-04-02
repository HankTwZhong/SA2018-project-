package Storage;

import Repository.StorageRepository;
import model.Host;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

public class StorageMongoDB implements StorageRepository {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> collection;

    public MongoCollection<Document> getDB(){
        mongoClient = new MongoClient("127.0.0.1");
        mongoDatabase = mongoClient.getDatabase( "MonitorSystem" );
        collection = mongoDatabase.getCollection("host");
        return collection;
    }
    public MongoCollection<Document> getMlab(){
        try{
            MongoClientURI uri  = new MongoClientURI("mongodb://Islab:islab1221@ds121189.mlab.com:21189/monitorsystem");
            mongoClient = new MongoClient(uri);
            mongoDatabase = mongoClient.getDatabase(uri.getDatabase());
            collection = mongoDatabase.getCollection("host");
            return collection;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
    public void addHost(Host host){
        collection = getDB();
        Document doc =new Document("hostName", host.getHostName())
                .append("hostIp", host.getHostIp());
        collection.insertOne(doc);
        mongoClient.close();
    }
    public ArrayList<Host> getHost(){
    	ArrayList<Host> hostList = new ArrayList<Host>();
        collection = getDB();

        FindIterable<Document> myDoc = collection.find();
        for (Document document : myDoc) {
            Host host = new Host();
            host.setHostName(document.get("hostName").toString());
            host.setHostIp(document.get("hostIp").toString());
            hostList.add(host);
        }
        mongoClient.close();
        return hostList;
    }

    public void deleteHost(String hostIp){
        collection = getDB();
        collection.deleteOne(eq("hostIp", hostIp));
        mongoClient.close();
    }

    private String getDateTime(){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        String strDate = sdFormat.format(date);
        return strDate;
    }
}

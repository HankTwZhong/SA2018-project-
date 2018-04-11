package Storage;

import Repository.StorageInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.Contact;
import model.Host;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

public class StorageMongoDB implements StorageInterface {
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
        } catch (Exception e){
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
        collection = getMlab();

        FindIterable<Document> myDoc = collection.find();
        for (Document document : myDoc) {
            Host host = new Host();
            host.setHostName(document.get("hostName").toString());
            host.setHostIp(document.get("hostIp").toString());
            host.setCheckMethod(Integer.parseInt(document.get("checkMethod").toString()));
            host.setContactList(getContactByString(document.get("contact").toString()));
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

    public ArrayList<Contact> getContactByString(String str){
        Gson gson = new Gson();
        ArrayList<Contact> list = new ArrayList<Contact>();
        Map<String,String> map = new HashMap<String,String>();
        Map<String,ArrayList<String>> contactListMap = (Map<String,ArrayList<String>>) gson.fromJson(str,map.getClass());
        ArrayList<String> arrayList = contactListMap.get("contact");
        JsonParser parser = new JsonParser();
        JsonElement elem   = parser.parse(arrayList.toString());
        JsonArray elemArr = elem.getAsJsonArray();
        for(int i = 0; i< elemArr.size();i++){
            Contact contact = new Gson().fromJson(elemArr.get(i), Contact.class);
            list.add(contact);
        }
        return list;
    }
}
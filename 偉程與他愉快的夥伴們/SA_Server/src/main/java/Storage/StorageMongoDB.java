package Storage;

import Monitor.StorageBuilder;
import com.google.gson.*;
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

public class StorageMongoDB implements StorageBuilder {
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
        collection = getMlab();
        Document doc =new Document("hostName", host.getHostName())
                .append("hostIp", host.getHostIp())
                .append("checkMethod",host.getCheckMethod())
                .append("contact","{\"contact\":[{\"name\":\"林翰隆\",\"email\":\"gunchana0713@gmail.com\",\"addressList\":[\"advrrf1548\"]},{\"name\":\"賴偉程\",\"email\":\"online1201@gmail.com\",\"addressList\":[\"online12345\"]}]}");
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
        collection = getMlab();
        collection.deleteOne(eq("hostIp", hostIp));
        mongoClient.close();
    }

    private String getDateTime(){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        String strDate = sdFormat.format(date);
        return strDate;
    }

    private ArrayList<Contact> getContactByString(String str){
        Gson gson = new Gson();
        ArrayList<Contact> list = new ArrayList<Contact>();
        Map<String,String> map = new HashMap<String,String>();
        Map<String,ArrayList<String>> contactListMap = (Map<String,ArrayList<String>>) gson.fromJson(str,map.getClass());
        ArrayList<String> arrayList = contactListMap.get("contact");
        JsonArray array = new JsonParser().parse(arrayList.toString()).getAsJsonArray();
        for (JsonElement jsonElement : array) {
            JsonObject jsonObject = new JsonParser().parse(jsonElement.toString()).getAsJsonObject();
            String name = jsonObject.get("name").toString();
            String email = jsonObject.get("email").toString();
            JsonArray jsonArray = jsonObject.get("addressList").getAsJsonArray();
            ArrayList<String> contactList = (ArrayList<String>) gson.fromJson(jsonArray,arrayList.getClass());
            Contact contact = new Contact(name,email,contactList);
            list.add(contact);
        }
        return list;
    }
}
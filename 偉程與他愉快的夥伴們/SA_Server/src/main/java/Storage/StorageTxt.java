package Storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Monitor.StorageBuilder;
import com.google.gson.*;
import model.Contact;
import model.Host;

public class StorageTxt implements StorageBuilder {
    String path;
    String csvFile;

    public StorageTxt(){
        this.path = ""+ StorageTxt.class.getClassLoader().getResource("");
        this.csvFile = this.path.substring(6)+"hostList.txt";
    }

    public ArrayList<Host> getHost(){
        Gson gson = new Gson();
    	ArrayList<Host> list = new ArrayList<Host>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(this.csvFile));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",,");
                Host host = new Host();
                host.setHostName(row[0]);
                host.setHostIp(row[1]);
                host.setCheckMethod(Integer.parseInt(row[2]));
                host.setLastCheck(row[3]);
                host.setContactList(getContactByString(row[4]));
                list.add(host);
            }
            br.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addHost(Host host){
        if(existHost(host)) return;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.csvFile,true));
            StringBuilder sb = new StringBuilder();
            sb.append(host.getHostName()).append(",,");
            sb.append(host.getHostIp()).append(",,");
            sb.append(host.getCheckMethod()).append(",,");
            sb.append("null,,");
            sb.append("{\"contact\":[{\"name\":\"林翰隆\",\"email\":\"gunchana0713@gmail.com\",\"addressList\":[\"advrrf1548\"]},{\"name\":\"賴偉程\",\"email\":\"online1201@gmail.com\",\"addressList\":[\"online12345\"]}]}");
            sb.append('\n');
            out.write(sb.toString());
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deleteHost(String ip) {
        ArrayList<Host> list = getHost();
        clearHostList();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getHostIp().equals(ip)) continue;
            addHost(list.get(i));
        }
    }

    private void clearHostList() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.csvFile));
            out.flush();
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean existHost(Host host){
        boolean res = false;
        ArrayList<Host> list = getHost();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getHostIp().equals(host.getHostIp())) res = true;
        }
        return res;
    }

    //將json格式之String轉為arrayList
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

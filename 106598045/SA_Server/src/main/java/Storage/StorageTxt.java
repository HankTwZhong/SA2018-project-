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

import Repository.StorageInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.Contact;
import model.Host;
import com.google.gson.Gson;

public class StorageTxt implements StorageInterface {
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
            sb.append(host.getHostName()).append(',').append(host.getHostIp()).append(',').append(host.getCheckMethod()).append(",null");
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
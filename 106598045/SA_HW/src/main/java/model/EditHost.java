package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EditHost {

    public List<Host> getHostList(){
        List<Host> list = new ArrayList<Host>();
        BufferedReader br;
        String path = ""+EditHost.class.getClassLoader().getResource("");
        String csvFile =path.substring(6)+"hostList.txt";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                Host host = new Host();
                host.setHostName(row[0]);
                host.setHostIp(row[1]);
                host.setLastCheck(row[2]);
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

    public void addHostList(Host host){
        String path = ""+EditHost.class.getClassLoader().getResource("");
        String csvFile =path.substring(6)+"hostList.txt";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(csvFile,true));
            StringBuilder sb = new StringBuilder();
            sb.append(host.getHostName());
            sb.append(',');
            sb.append(host.getHostIp());
            sb.append(',');
            sb.append(host.getLastCheck());
            sb.append('\n');
            out.write(sb.toString());
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deleteHostList(String ip) {
        List<Host> list = getHostList();
        clearHostList();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getHostIp().equals(ip)) continue;
            addHostList(list.get(i));
        }
    }

    public void clearHostList() {
        String path = ""+EditHost.class.getClassLoader().getResource("");
        String csvFile =path.substring(6)+"hostList.txt";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(csvFile));
            out.flush();
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean existHost(Host host){
        boolean res = false;
        List<Host> list = getHostList();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getHostIp().equals(host.getHostIp())) res = true;
        }
        return res;
    }
}

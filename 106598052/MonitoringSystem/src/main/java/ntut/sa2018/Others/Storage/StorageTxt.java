package ntut.sa2018.Others.Storage;


import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import ntut.sa2018.Others.Interface.StorageInterface;

import java.io.*;
import java.util.ArrayList;

public class StorageTxt implements StorageInterface {
    String path;
    String csvFile;

    public StorageTxt(){
        this.path = ""+ StorageTxt.class.getClassLoader().getResource("");
        this.csvFile = this.path.substring(6)+"hostList.txt";
    }

    @Override
    public ArrayList<Host> getHost() {
        ArrayList<Host> list = new ArrayList<Host>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(this.csvFile));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] row = line.split(" ");
                Contact contact = new ContactBuilder.newInstance().
                        contactName(row[4]).
                        email(row[5]).
                        line(row[6]).
                        facebook(row[7]).
                        skype(row[8]).
                        otheraddress(row[9]).
                        otheraddress2(row[10]).
                        build();
                Host host = new HostBuilder.newInstance().
                        name(row[0]).
                        address(row[1]).
                        checkCommand(row[2]).
                        checkInterval(Integer.parseInt(row[3])).
                        contact(contact).
                        build();
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

    @Override
    public void addHost(Host host) {
        if(existHost(host)) return;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.csvFile,true));
            StringBuilder sb = new StringBuilder();
            sb.append(host.getHostName()).append(" ");
            sb.append(host.getHostIP()).append(" ");
            sb.append(host.getCheckMethod()).append(" ");
            sb.append(host.getCheckInterval()).append(" ");
            sb.append(host.getContact().getContactName()).append(" ");
            sb.append(host.getContact().getEmail()).append(" ");
            sb.append(host.getContact().getLineaddress()).append(" ");
            sb.append(host.getContact().getFacebookaddress()).append(" ");
            sb.append(host.getContact().getSkypeaddress()).append(" ");
            sb.append(host.getContact().getOtheraddress()).append(" ");
            sb.append(host.getContact().getOtheraddress2());
            sb.append('\n');
            out.write(sb.toString());
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //use to check the new adding host is exist in the TXT or not
    private boolean existHost(Host host){
        boolean res = false;
        ArrayList<Host> list = getHost();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getHostIP().equals(host.getHostIP())) res = true;
        }
        return res;
    }

    @Override
    public void deleteHost(String hostIP) {
        ArrayList<Host> list = getHost();
        clearHostList();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getHostIP().equals(hostIP)) continue;
            addHost(list.get(i));
        }
    }

    /*clear the TXT*/
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
}

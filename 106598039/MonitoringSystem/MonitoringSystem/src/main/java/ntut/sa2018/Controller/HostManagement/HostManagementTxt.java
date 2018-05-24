package ntut.sa2018.Controller.HostManagement;


import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import ntut.sa2018.UseCase.Interface.HostManagementRepository;

import java.io.*;
import java.util.ArrayList;

public class HostManagementTxt implements HostManagementRepository {
    String path;
    String csvFile;

    public HostManagementTxt(){
        this.path = ""+ HostManagementTxt.class.getClassLoader().getResource("");
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
                int count = row.length;
                int i = 4 ;
                ArrayList<Contact> contactArrayList = new ArrayList<>();
                while(count>1){
                    Contact contact = new ContactBuilder.newInstance().
                            contactName(row[i]).
                            email(row[i+1]).
                            line(row[i+2]).
                            facebook(row[i+3]).
                            skype(row[i+4]).
                            otheraddress(row[i+5]).
                            otheraddress2(row[i+6]).
                            build();
                    contactArrayList.add(contact);
                    count = count - 10;
                    i = i + 7;
                }
                if(row.length > 0) {
                    Host host = new HostBuilder.newInstance().
                            name(row[0]).
                            address(row[1]).
                            checkCommand(row[2]).
                            checkInterval(Integer.parseInt(row[3])).
                            contact(contactArrayList).
                            build();
                    list.add(host);
                }
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
            for(Integer i=0 ; i<host.getContact().size() ; i++){
                sb.append(host.getContact().get(i).getContactName()).append(" ");
                sb.append(host.getContact().get(i).getEmail()).append(" ");
                sb.append(host.getContact().get(i).getLineaddress()).append(" ");
                sb.append(host.getContact().get(i).getFacebookaddress()).append(" ");
                sb.append(host.getContact().get(i).getSkypeaddress()).append(" ");
                sb.append(host.getContact().get(i).getOtheraddress()).append(" ");
                sb.append(host.getContact().get(i).getOtheraddress2()).append(" ");
            }
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

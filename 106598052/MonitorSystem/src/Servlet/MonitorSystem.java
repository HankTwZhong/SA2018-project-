package Servlet;

import Host.host;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static sun.rmi.transport.TransportConstants.Ping;

@WebServlet(name = "MonitorSystem")
public class MonitorSystem extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hostname = request.getParameter("hostname");
        String ipaddress = request.getParameter("ipaddress");
        try {
            ipaddress = InetAddress.getByName(ipaddress).getHostAddress();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream("D:/專案/MonitorSystem/input.txt", true));
            outputStream.println(hostname + " " + ipaddress + " null");
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
        response.sendRedirect("MonitorSystem");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        FileReader fr = new FileReader("D:/專案/MonitorSystem/input.txt");
        BufferedReader br = new BufferedReader(fr);
        ArrayList<host> hostArrayList = new ArrayList<host>();
        while (br.ready()) {
            String[] s = br.readLine().split("\\s+");
            host host = new host(s[0], s[1], s[2]);
            hostArrayList.add(host);
        }
        fr.close();

        System.out.println(getDateTime());
        for(int i=0 ; i<hostArrayList.size() ; i++){
            try {
                if(isAddressAvailable(hostArrayList.get(i).getIP())){
                    hostArrayList.get(i).setStatus("Up");
                    System.out.println(i+ " " + getDateTime());
                }
                else{
                    hostArrayList.get(i).setStatus("Down");
                    System.out.println(i+ " " + getDateTime());
                }
            }
            catch (Exception e){
                hostArrayList.get(i).setStatus("Down");
                System.out.println(getDateTime());
            }
        }

        session.setAttribute("hostarraylist", hostArrayList);
        request.getRequestDispatcher("MonitorSystem.jsp").forward(request, response);
    }

    public boolean isAddressAvailable(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            if (address.isReachable(1000)) {
                return true;
            } else {
                return false;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return false;
    }

    public String getDateTime(){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        String strDate = sdFormat.format(date);
        return strDate;
    }
}

package Servlet;

import Host.host;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;

@WebServlet(name = "deleteHost")
public class deleteHost extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String hostIp = request.getParameter("id");
        FileReader fr = new FileReader("D:/專案/MonitorSystem/input.txt");
        BufferedReader br = new BufferedReader(fr);
        ArrayList<host> hostArrayList = new ArrayList<host>();
        while (br.ready()) {
            String[] s = br.readLine().split("\\s+");
            host host = new host(s[0], s[1], s[2]);
            hostArrayList.add(host);
        }
        fr.close();

        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream("D:/專案/MonitorSystem/input.txt"));
            for(int i=0 ;i<hostArrayList.size() ; i++){
                System.out.println(hostIp+" " +hostArrayList.get(i).getIP());
                if(!hostArrayList.get(i).getIP().equals(hostIp)){
                    outputStream.println(hostArrayList.get(i).getHostName() + " " + hostArrayList.get(i).getIP() + " null");
                }
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
        System.out.println("delete finished");
        response.sendRedirect("MonitorSystem");
    }
}

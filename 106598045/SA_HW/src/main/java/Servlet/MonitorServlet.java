package Servlet;

import Repository.HostDB;
import Repository.HostRepository;
import com.google.gson.Gson;
import Repository.HostTxt;
import config.StorageConf;
import model.Host;
import model.Monitor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "/MonitorServlet",urlPatterns = {"/MonitorServlet"})
public class MonitorServlet extends HttpServlet {
    private List<Host> list;
    private HostRepository hostRepository;

    MonitorServlet() {
        hostRepository = HostRepositoryBuilder.Build();
        list = hostRepository.getHost();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        Gson gson = new Gson();

        Monitor m = new Monitor();
        for(int i=0;i<list.size();i++){
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(Calendar.getInstance().getTime());
            list.get(i).setLastCheck(timeStamp);
            if(m.ping(list.get(i).getHostIp())){
                list.get(i).setStatus("OK");
            }else{
                list.get(i).setStatus("ERROR");
            }
        }
        String resultJSON = "{ \"result\" : "+gson.toJson(list)+" }";
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        try {
            response.getWriter().write(resultJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

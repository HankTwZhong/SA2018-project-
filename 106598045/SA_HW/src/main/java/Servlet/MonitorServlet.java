package Servlet;

import DB.StorageDB;
import DB.StoragePort;
import com.google.gson.Gson;
import DB.StorageTxt;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        Gson gson = new Gson();
        StorageConf storageConf = new StorageConf();
        String storageMethod = storageConf.getStorageMethod();
        StoragePort storage = (storageMethod == "DB")?new StorageDB():new StorageTxt();

        List<Host> list = storage.getHost();
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

package Servlet;

import DB.StorageDB;
import DB.StoragePort;
import DB.StorageTxt;
import model.Host;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditHostServlet",urlPatterns = {"/EditHostServlet"})
public class EditHostServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String storageMethod = "TXT";
        StoragePort storage = (storageMethod == "DB")?new StorageDB():new StorageTxt();

        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String ip = request.getParameter("ip");
        if(action.equals("create")){
            Host host = new Host();
            host.setHostIp(ip);
            host.setHostName(name);
            host.setLastCheck("null");
            storage.addHost(host);
        }else if(action.equals("delete")){
            storage.deleteHost(ip);
        }
        String resultJSON = "{ \"result\" : true }";
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(resultJSON);
    }
}

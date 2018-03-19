package Servlet;

import model.EditHost;
import model.Host;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static model.EditHost.*;

@WebServlet(name = "EditHostServlet",urlPatterns = {"/EditHostServlet"})
public class EditHostServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EditHost editHost = new EditHost();
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String ip = request.getParameter("ip");
        if(action.equals("create")){
            Host host = new Host();
            host.setHostIp(ip);
            host.setHostName(name);
            host.setLastCheck("null");
            if(!editHost.existHost(host)) {
                editHost.addHostList(host);
            }
        }else if(action.equals("delete")){
            editHost.deleteHostList(ip);
        }
        String resultJSON = "{ \"result\" : true }";
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(resultJSON);
    }
}

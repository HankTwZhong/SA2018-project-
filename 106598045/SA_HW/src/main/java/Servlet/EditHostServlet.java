package Servlet;

import Repository.HostRepository;
import model.Host;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "EditHostServlet",urlPatterns = {"/EditHostServlet"})
public class EditHostServlet extends HttpServlet {
    private HostRepository hostRepository = HostRepositoryBuilder.Build();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String ip = request.getParameter("ip");
        System.out.println(action);
        if(action.equals("create")){
            Host host = new Host();
            host.setHostIp(ip);
            host.setHostName(name);
            host.setLastCheck("null");
            hostRepository.addHost(host);
        }else if(action.equals("delete")){
            hostRepository.deleteHost(ip);
        }
        String resultJSON = "{ \"result\" : true }";
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(resultJSON);
    }
}

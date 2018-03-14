<%--
  Created by IntelliJ IDEA.
  User: Chang
  Date: 2018/3/8
  Time: 下午 06:06
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Chang
  Date: 2017/12/7
  Time: 下午 05:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Host.host" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<% ArrayList<host> hostArrayList = (ArrayList<host>) session.getAttribute("hostarraylist"); %>
<% SimpleDateFormat setDateFormat  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); %>
<% ArrayList<host> deadHostArrayList = new ArrayList<host>(); %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="ISLab@NTUT 2016 Project">
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="shortcut icon" href="/resources/favicon.ico" rel="icon" type="image/x-icon">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <title>MonitorSystem</title>
</head>
<body>
<div class="row">
    <div class="col-md-6 col-md-offset-3"></div>
    <div class="clearfix visible-xs-block"></div>
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-success">
            <div class="panel-heading" align="center">MonitorSystem</div>
            <form method=POST action=MonitorSystem>
                <table class="table table-bordered">
                    <thead>
                    <th style="width: 8%">Host name</th>
                    <th style="width: 16%">IP</th>
                    <th style="width: 4%">Status</th>
                    <th style="width: 16%">Update time</th>
                    <th style="width: 4%">Delete</th>
                    </thead>
                    <tbody>
                    <% response.setIntHeader("Refresh", 10); %>
                    <%for (int i=0 ;i<hostArrayList.size() ; i++){ %>
                    <tr>
                        <td style="word-break: break-all"><%=hostArrayList.get(i).getHostName()%></td>
                        <td style="word-break: break-all"><%=hostArrayList.get(i).getIP()%></td>
                        <% if(hostArrayList.get(i).getStatus()=="Up"){%>
                        <td style="word-break: break-all" bgcolor="#adff2f">Up</td>
                        <% }else{ %>
                        <td style="word-break: break-all" bgcolor="red">Down</td>
                        <% deadHostArrayList.add(hostArrayList.get(i)); %>
                        <% } %>
                        <td style="word-break: break-all"><%=setDateFormat.format(Calendar.getInstance().getTime())%></td>
                        <td style="word-break: break-all">
                            <div align="center">
                                <input type="button" class="btn btn-warning" onclick="if (confirm('Are you sure you want to delete?') == true)
                                        {chk('<%=hostArrayList.get(i).getIP()%>')}" value="delete">
                            </div>
                        </td>
                    </tr>
                    <% } %>
                    <tr>
                        <td>
                            <input type="text" name="hostname" class="form-control" id="hostname" required>
                        </td>
                        <td>
                            <input type="text" name="ipaddress" class="form-control" id="ipaddress" required>
                        </td>
                        <td></td>
                        <td></td>
                        <td>
                            <div align="center">
                                <button type="submit" class="btn btn-success">add host</button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<% if(deadHostArrayList.size()>0){%>
<%String hostList = deadHostArrayList.get(0).getIP();%>
<%for(int i=1 ; i<deadHostArrayList.size() ; i++) {%>
<%hostList = hostList + " and " + deadHostArrayList.get(i).getIP();%>
<%}%>
<%String result = hostList + " is dead";%>
<script language="JavaScript">
    window.alert("<%=result%>");
</script>
<%}%>

<footer class="footer">
    <div class="container">
        <div class="pull-right">
            <p class="text-muted">106598052 build:20180308</p>
        </div>
    </div>
</footer>
<script src="/resources/javascript/jquery-3.1.1.min.js"></script>
<script src="/resources/javascript/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $('li.active').removeClass('active');
        $('a[href="' + location.pathname + '"]').closest('li').addClass('active');
    });
    function chk(num){
        window.location.href="deleteHost?id="+num;
    }
</script>

</body>
</html>
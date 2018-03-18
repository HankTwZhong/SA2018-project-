var row = ["0"];
var httpHost = [];
var hostUrl = [];

function getHostName(url) {
  var match = url.match(/:\/\/(www[0-9]?\.)?(.[^/:]+)/i);
  if (match != null && match.length > 2 && typeof match[2] === 'string' && match[2].length > 0) {
    return match[2];
  }
  else {
    return url;
  }
}

function getCurrentTime(row){
  var date = new Date();
  var hour = date.getHours();
  var minute = date.getMinutes();
  var second = date.getSeconds();

  if (hour < 10){
    hour = "0" + hour;
  }
  if (minute < 10){
    minute = "0" + minute;
  }
  if (second < 10){
    second = "0" + second;
  }
  document.getElementById("monitorTime" + row).innerHTML = hour + ":"
                            + minute + ":" 
                            + second;
}

function getIPAddress(hostName) {
  IPPing = new XMLHttpRequest();
  IPPing.onload = function () {
    if (this.responseText != hostName){
      document.getElementById("ipContent" + row[row.length - 1]).innerHTML = this.responseText;
    }
  }  
  IPPing.open("GET", "http://api.konvert.me/forward-dns/" + hostName);
  IPPing.send();
}

function addHost(){
  if (document.getElementById("statusContent" + row[row.length - 1]).textContent != ""){
    var table = document.getElementById("monitorContentTable");
    row.push(table.getElementsByTagName("tr").length - 1);
    var newRow = table.insertRow(row[row.length]);
    var cell1 = newRow.insertCell(0);
    cell1.id = "hostContent" + row[row.length - 1];
    cell1.align = "center";
    var cell2 = newRow.insertCell(1);
    cell2.id = "ipContent" + row[row.length - 1];
    cell2.align = "center";
    var cell3 = newRow.insertCell(2);
    cell3.id = "statusContent" + row[row.length - 1];
    cell3.align = "center";
    var cell4 = newRow.insertCell(3);
    cell4.id = "monitorTime" + row[row.length - 1];
    cell4.align = "center";
    cell1.innerHTML = "<input type='text' id = url" + row[row.length - 1] + ">";
  }
  else{
    alert("Please input the host url.");
  }
}

function pingContent(){
  if (document.getElementById("url" + row[row.length - 1]).value != ""){
    url = document.getElementById("url" + row[row.length - 1]).value;
    hostUrl.push(url);
    hostName = getHostName(url);
    ping = new XMLHttpRequest();
    httpHost.push(ping);
    document.getElementById("hostContent" + row[row.length - 1]).innerHTML = hostName;
    getIPAddress(hostName);
    pingRequest(ping, url, row[row.length - 1]);
  }
  else{
    alert("Please input the host url.");
  }
}

function pingRequest(ping, url, row){
console.log("in request");
getCurrentTime(row);
ping.onreadystatechange = function(){
  if (ping.readyState == 4){
    if (ping.status == 200){
      status = "UP";
    }
    else{
      status = "DOWN";
    }
  }
  document.getElementById("statusContent" + row).innerHTML = status;
}
ping.open("GET", url, true);    
ping.send();
}
setInterval(function(){
  for (let i = 0; i < httpHost.length; i++){
  pingRequest(httpHost[i], hostUrl[i], row[i]);
  }
},10000);
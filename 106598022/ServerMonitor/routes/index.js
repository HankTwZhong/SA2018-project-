var express = require('express');
var router = express.Router();
var nmap = require('node-nmap');
nmap.nmapLocation = "nmap";

router.get('/', function(req, res, next) {
  let hostArray = [];
  const readline = require('readline').createInterface({
    input:require('fs').createReadStream('./ServerMonitor/database.txt')
  });
  readline.on('line',function(line){
    hostArray.push(line);
  }).on('close',function(){
    let hosts = [];
    for(let i =0; i < hostArray.length; i++){
      var quickscan = new nmap.QuickScan(hostArray[i]);
      quickscan.on('complete', function(data){
        console.log(data[0]);
        if(data.length != 0) {
          if (data[0].hostname == null)
            hosts.push({hostname: '未知', ip: data[0].ip, status: 'UP'});
          else
            hosts.push({hostname: data[0].hostname, ip: data[0].ip, status: 'UP'});
        } else
            hosts.push({hostname: hostArray[i], ip: hostArray[i], status: 'Down'});
        if(hosts.length == hostArray.length){
          res.render("index", {hosts:hosts});
        }
      });
    
      quickscan.on('error', function(error){
        console.log(error);
        hosts.push({hostname:hostArray[i],hostIp:hostArray[i], status:"Down"});
        if(hosts.length == hostArray.length){
          res.render("index", {hosts:hosts});
        }
      });
    }
  });
});

/* GET home page. */
router.post('/', function(req, res, next) {
  let hostArray = [];
  let newHost = JSON.parse(JSON.stringify(req.body));

  const fs = require('fs');
    fs.appendFile('./ServerMonitor/database.txt', "\n" + newHost.hostName, (err) => {  
      if (err) throw err;
      console.log('The database were updated!');
      res.send("success");
    });
  
});

module.exports = router;

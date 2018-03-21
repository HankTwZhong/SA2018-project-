var express = require('express');
var router = express.Router();

var nmap = require('node-nmap');
nmap.nmapLocation = "nmap"; //default 

var hostList = [];

/* GET home page. */
router.all('/', function(req, res) {
  res.render('index', { hostList: hostList, warning: ''});
});

router.post('/search', function(req, res) {
  if(req.body.ipName == '') 
    res.render('index', { hostList: hostList, warning: 'Input cannot be blank!!'});
  else {
    var quickscan = new nmap.QuickScan(req.body.ipName);

    quickscan.on('complete', function(data) {
      console.log(data);
      if(data.length != 0) {
        if (data[0].hostname == null)
        hostList.push({hostname: '未知', ip: data[0].ip, status: 'OK'});
        else
          hostList.push({hostname: data[0].hostname, ip: data[0].ip, status: 'OK'});
      } else
        hostList.push({hostname: req.body.ipName, ip: req.body.ipName, status: 'Down'});
      res.redirect('/');
    });
    quickscan.on('error', function(error) {
      console.log(error);
      hostList.push({hostname: req.body.ipName, ip: req.body.ipName, status: 'Down'});
      res.redirect('/');
    });

    quickscan.startScan();
  }

});

module.exports = router;

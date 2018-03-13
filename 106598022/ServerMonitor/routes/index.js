var express = require('express');
var router = express.Router();
var nmap = require('node-nmap');
nmap.nmapLocation = "nmap";
 

/* GET home page. */
router.get('/', function(req, res, next) {
  var count = 0;
  var quickscan = new nmap.QuickScan('127.0.0.1 google.com');

  quickscan.on('complete', function(data){
    console.log(data[0]);
    count++;
    if(count == 2)
      res.render('index', { hostname: data[0]}); 
  });

  quickscan.on('error', function(error){
    console.log(error);
  });
});

module.exports = router;

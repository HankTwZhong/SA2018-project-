var express = require('express');
var router = express.Router();
var nmap = require('node-nmap');
nmap.nmapLocation = "nmap";
 

/* GET home page. */
router.get('/', function(req, res, next) {
  var quickscan = new nmap.QuickScan('google.com');

  quickscan.on('complete', function(data){
    console.log(data[0].hostname);
  });

  quickscan.on('error', function(error){
    console.log(error);
  });
  res.render('index', { title: 'Express'}); 
});

module.exports = router;

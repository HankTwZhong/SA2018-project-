var dns = require('dns');
var ping = require('ping');
var async = require('async');
var express = require('express');
var router = express.Router();
 
/* GET home page. */
router.get('/', function(req, res, next) {

  var resList = [];
  if(req.session.hostList == null){
    req.session.hostList = ["192.168.1.1", "google.com"];
  }
  
  req.session.hostList.forEach(function(host){
    async.parallel({
      ip : function(callback){
        dns.lookup(host, function(err, address, family){
          callback(null, address);
        });
      },
      statusType : function(callback){
        ping.sys.probe(host, function(isAlive){
          var statusMsg = isAlive ? 'up' : 'down';
          callback(null, statusMsg);
        }); 
      }
    }, function(err, results){
      resList.push({ name : host, ip : results.ip, status : results.statusType });
      if(resList.length == req.session.hostList.length){
        res.render('index', {hostStatusList : resList});
      }
    });
  });
});

router.get('/AddHostList', function(req, res, next){
  req.session.hostList.push(req.query.newHostName);
  res.redirect('/');
});

router.post('/DelHostList', function(req, res, next){
  req.session.hostList.splice(req.session.hostList.indexOf(req.body.name), 1);
  res.redirect('/');
});

module.exports = router;
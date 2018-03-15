var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  console.log('進入監控首頁');
  res.render('index', { title: '連線成功' });
});

module.exports = router;

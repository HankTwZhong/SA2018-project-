var express = require('express');
var app = express();
var nagiosServer = require('./src/nagiosServer')
const cors = require('cors')

app.use(cors())
app.options('*', cors())

app.get('/getStatusByIp', nagiosServer.getStatusByIp)

app.listen(5433, function () {
  console.log('listening on port 5433!');
});
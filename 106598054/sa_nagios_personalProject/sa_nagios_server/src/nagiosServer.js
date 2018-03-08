var ping = require('ping')

exports.getStatusByIp = function (req, res) {
  var host = req.query.ipAddress
  ping.sys.probe(host, function (isAlive) {
    if (isAlive) {
      res.send('UP')
    } else {
      res.send('DOWN')
    }
  })
}

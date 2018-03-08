const axios = require('axios')
const serverDomain = 'http://localhost:5433'

module.exports.getStatus = function (ipAddress) {
  return axios.get(serverDomain + '/getStatusByIp', { params: { ipAddress: ipAddress } })
}

const axios = require('axios')
const serverDomain = 'http://localhost:3000'

module.exports.getStatus = function () {
  return axios.get(serverDomain + '/hosts')
}

module.exports.changeCommand = function (id, command) {
  return axios.put(serverDomain + '/hosts/' + id, { command: command })
}
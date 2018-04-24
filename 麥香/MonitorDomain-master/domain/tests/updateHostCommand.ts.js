var hostService = require('../services/hostService');
const util = require('util');

var host = {
    id: '5ac1bd0a623d624d1c5b89f6',
    command:'ping'
};

hostService.updateHostCommand(host).then((newHost) => {
    console.log(util.inspect(newHost, false, null));
}, (err) => {
    console.log(err);
});
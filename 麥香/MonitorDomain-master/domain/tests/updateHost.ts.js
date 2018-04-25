var hostService = require('../services/hostService');
const util = require('util');

var host = {
    id: '5ac0a299fdd49919e0c9c371',
    ip: '127.0.0.1',
    status:'Unknown'
};

hostService.updateHostStatus(host).then((newHost) => {
    console.log(util.inspect(newHost, false, null));
}, (err) => {
    console.log(err);
});
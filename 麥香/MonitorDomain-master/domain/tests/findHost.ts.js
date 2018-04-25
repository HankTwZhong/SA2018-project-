var hostService = require('../services/hostService');
const util = require('util');

hostService.findHost('5ac0a299fdd49919e0c9c371').then((host) => {
    console.log(util.inspect(host, false, null));
}, (err) => {
    console.log(err);
});
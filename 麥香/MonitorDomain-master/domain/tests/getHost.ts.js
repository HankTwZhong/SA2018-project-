var hostService = require('../services/hostService');
const util = require('util');

hostService.getHosts().then((hsots) => {
    console.log(util.inspect(hsots, false, null));
}, (err) => {
    console.log(err);
});
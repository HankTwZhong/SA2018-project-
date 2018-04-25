var hostService = require('../services/hostService');
const util = require('util');

var host = {
    id: '5ac1bd0a623d624d1c5b89f6'
};

var contact = {
    id: '5ac1bd89d48f9347a0d3abc5'
};

hostService.attachContact(host, contact).then((newHost) => {
    console.log(util.inspect(newHost, false, null));
}, (err) => {
    console.log(err);
});
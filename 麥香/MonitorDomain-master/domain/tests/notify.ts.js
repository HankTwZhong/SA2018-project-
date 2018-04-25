var notifyService = require('../services/notifyService');

let contact = {
    address: {
        Email: "Obama@gmail.com",
        Line: "ObamaLineid",
        FB: "ObamaFB",
        Phone: "0806449"
    },
    name: "Obama"
}

let message = {
    timestamp: '19700101',
    msg: 'damm'
};

notifyService.notify(contact, message);
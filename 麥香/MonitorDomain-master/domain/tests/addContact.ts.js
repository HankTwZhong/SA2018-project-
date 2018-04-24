var contactService = require('../services/contactService');

contactService.addContact({
    name: 'Trump',
    address: {
        Email:'Trump@gmail.com',
        Line: 'TrumpLineid',
        FB: 'TrumpFB',
        Phone: '0806449'
    }
}).then((result) => {
    console.log(result);
}, (err) => {
    console.log(err);
});

contactService.addContact({
    name: 'Obama',
    address: {
        Email: 'Obama@gmail.com',
        Line: 'ObamaLineid',
        FB: 'ObamaFB',
        Phone: '0806449'
    }
}).then((result) => {
    console.log(result);
}, (err) => {
    console.log(err);
});
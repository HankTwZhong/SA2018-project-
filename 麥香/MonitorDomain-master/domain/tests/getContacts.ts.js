var contactService = require('../services/contactService');


contactService.getContacts().then((result) => {
    console.log(result);
}, (err) => {
    console.log(err);
});
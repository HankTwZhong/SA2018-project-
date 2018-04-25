var mongoose = require('mongoose');
var connection = mongoose.connect('mongodb://Sasuke:9487@ds033126.mlab.com:33126/narutomongo');

var ContactSchema = require('../Schemas/contactSchema');
var Contact = require('../contact');

var self = {};

self.addContact = async (contact) => {
    try {
        return new Contact(await ContactSchema.create({
            name: contact.name,
            address:contact.address
        }));
    } catch (err) {
        throw err;
    }
};

self.getContacts = async () => {
    try {
        let contacts = await ContactSchema.find().exec();
        return contacts.map(e => new Contact(e));
    } catch (err) {
        throw err;
    }
};

module.exports = self;
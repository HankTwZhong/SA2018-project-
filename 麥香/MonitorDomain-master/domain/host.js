var Contacts = require('./contact');
module.exports = class {
    constructor(host) {
        this.id = host.id;
        this.name = host.name;
        this.command = host.command;
        this.ip = host.ip || '';
        this.status = host.status || 'Unknown';
        this.contacts = (host.contacts)?host.contacts.map(e=>new Contacts(e)):[];
    }
};
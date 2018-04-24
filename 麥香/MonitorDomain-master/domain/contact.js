module.exports = class {
    constructor(contact) {
        this.id = contact.id;
        this.name = contact.name;
        this.address = {
            Email:contact.address.Email,
            Line:contact.address.Line,
            FB:contact.address.FB,
            Phone:contact.address.Phone,
        };
    }
};
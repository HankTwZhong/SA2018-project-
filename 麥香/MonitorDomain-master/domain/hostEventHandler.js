var messageBus = require('./messageBus');
var notifyService = require('./services/notifyService');

messageBus.subscribe((event) => {
    if (event.eventType == 'onStatusChangeEvent') {
        let host = event.argument;
        host.contacts.forEach(element => {
            notifyService.notify(element, {
                name: host.name,
                status: host.status
            });
        });
    }
});

module.exports = this;
var hostService = require('./services/hostService');
var monitor = require('./monitor');
var messageBus = require('./messageBus');
var hostEventHandler = require('./hostEventHandler');
var timer = require('./timer');

messageBus.subscribe((event) => {
    if (event.eventType == 'onTimeoutEvent') {
        console.log(event);
        monitor.inspectHosts();
    }
});

class Domain {
    async getHosts() {
        try {
            return await hostService.getHosts();
        } catch (err) {
            throw err;
        }
    }

    async findHost(key) {
        try {
            return await hostService.findHost(key);
        } catch (err) {
            throw err;
        }
    }

    async updateHostCommand(host) {
        try {
            return await hostService.updateHostCommand(host);
        } catch (err) {
            throw err;
        }
    }
}

module.exports = new Domain();
'use strict';

var servie = {};
servie['nmap'] = require('./nmapService');
servie['ping'] = require('./pingService');

class MonitorService {
    async queryHost(host) {
        try {
            if (host.command && servie[host.command]) {
                return servie[host.command].queryHost(host);
            }
            return 'unvalid command';
        } catch (err) {
            throw err;
        }
    }
}

module.exports = new MonitorService();
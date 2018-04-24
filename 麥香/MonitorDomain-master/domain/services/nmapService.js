'use strict';

var nmap = require('node-nmap');

class NmapService {
    queryHost(host) {
        return new Promise((resolve, reject) => {
            let address = host.name;
            if (typeof address != 'string') {
                reject('address is not a string type');
            }

            //    Accepts array or comma separated string of NMAP acceptable hosts 
            let quickscan = new nmap.QuickScan(address);

            quickscan.on('complete', function (data) {
                resolve({
                    name: (data.length == 0) ? "" : data[0].hostname,
                    ip: (data.length == 0) ? "" : data[0].ip,
                    status: (data.length == 1) ? "UP" : "DOWN"
                });
            });

            quickscan.on('error', function (error) {
                resolve({
                    status: 'UNKNOWN'
                });
            });
            quickscan.startScan();
        });
    };
}

module.exports = new NmapService();
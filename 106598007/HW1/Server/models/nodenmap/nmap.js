const nmap = require('node-nmap');
nmap.nmapLocation = "nmap"; //default 
var quickscan

/*******************************************/
// var nmapscan = new nmap.NmapScan('127.0.0.1 google.com', '-sn');
 
// nmapscan.on('complete',function(data){
//   console.log(data);
// });
// nmapscan.on('error', function(error){
//   console.log(error);
// });
 
// nmapscan.startScan();

module.exports = {

    getScanResults: (hostName) => {
        quickscan = new nmap.QuickScan(hostName);
        quickscan.startScan();

        return new Promise((resolve, fail) => {
            quickscan.on('complete', function(data) {
                console.log(data);
                return resolve(data);
            });
            quickscan.on('error', function(error){
                console.log(error);
                return fail(error);
            });
        })        
    }
};
var hostService = require('../services/hostService');

hostService.addHost({
    name: 'youtube.com',
    command: 'nmap'
}).then((result) => {
    console.log(result);
}, (err) => {
    console.log(err);
});
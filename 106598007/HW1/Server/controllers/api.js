const nmap = require('../models/nodenmap/nmap');

module.exports = {
    'GET /api/:hostName': async (ctx, next) => {
        ctx.rest({
            scanResults: await nmap.getScanResults(ctx.params.hostName)
        });
        // nmap.getScanResults(ctx.params.hostName).then( (data) => {
        //     console.log(data)
        //     return data
        //  }, (error) => {
        //     console.log(error)
        //     return error
        //  })

        
        // ctx.rest({
        //     hostNames: nmap.getScanResults(ctx.params.hostName)
        // });
    }
};

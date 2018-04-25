var pingService = require('../services/pingService');

pingService.queryHost({
    name: 'google.com'
}).then((result) => {
    console.log(result);
}, (err) => {
    console.log(err);
});
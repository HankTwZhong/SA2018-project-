var messageBus = require('./messageBus');
var OnTimeoutEvent = require('./events/onTimeoutEvent')

setInterval(_ => {
    messageBus.brocast(new OnTimeoutEvent());
}, 3000);

module.exports = this;
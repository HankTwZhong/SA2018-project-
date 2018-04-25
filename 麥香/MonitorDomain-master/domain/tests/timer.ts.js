var timer = require('../timer');
var messageBus = require('../messageBus');

messageBus.subscribe((event) => {
    if (event.eventType == 'onTimeoutEvent') {
        console.log(event);
    }
});
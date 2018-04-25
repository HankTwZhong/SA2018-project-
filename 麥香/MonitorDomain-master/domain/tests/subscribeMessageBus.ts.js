var messageBus = require('../messageBus');
var OnStatusChangeEvent = require('../events/onStatusChangeEvent');
var util = require('util');

messageBus.subscribe((event) => {
    console.log(util.inspect(event, false, null));
});

messageBus.subscribe((event) => {
    console.log(event.eventType);
    console.log(event.argument);
});

var host = {
    id:0,
    name:'trump server',
    status:'unknown'
};

messageBus.brocast(new OnStatusChangeEvent(host));
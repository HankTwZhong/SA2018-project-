var timestamp = require('time-stamp');

module.exports = class {
    constructor() {
        this.eventType = 'onTimeoutEvent';
        this.argument = timestamp('YYYY/MM/DD HH:mm:ss');
    }
};
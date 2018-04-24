var subscribers = [];

class MessageBus {

    subscribe(callback) {
        subscribers.push(callback);
    }

    brocast(event) {
        subscribers.forEach(callback => callback(event));
    }
}

module.exports = new MessageBus();
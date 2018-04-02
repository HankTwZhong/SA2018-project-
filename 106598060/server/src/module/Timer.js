export default class Timer{
    constructor(frequency){
        this.frequency = frequency
    }
    pingInterval(host){
       let setIntervalId =  setInterval(function() {
            host.pingHost()
        }, this.frequency)
        return setIntervalId
    }
}
import Command from "../Entity/Command"

var ping = require('ping')
export default class PingCommand extends Command{
    constructor(){
        super()
        this.name = "Ping"
    }

    monitor(ipAddress, callback){
        ping.sys.probe(ipAddress, function(active){
            callback(active)
        })
    }
}
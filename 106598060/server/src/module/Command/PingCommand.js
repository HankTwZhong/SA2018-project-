import Command from "./Command"

var ping = require('ping')
export default class PingCommand extends Command{
    constructor(){
        super()
        this.name = "Ping"
    }

    monitor(host, hostManager, callback){
        ping.sys.probe(host.ipAddress, function(active){
            hostManager.setResponseData(host, active, function(hostInfo){
                if(callback)callback(hostInfo)
                }) 
        })
    }
}
import Command from "./Command"
const isAllReachable = require('is-all-reachable')

export default class IsReachableCommand extends Command{
    constructor(){
        super()
        this.name = "isReachable"
    }

    monitor(host, hostManager, callback){
        isAllReachable([
            host.ipAddress
          ], (err, reachable) => {
            hostManager.setResponseData(host, reachable, function(hostInfo){
                if(callback)callback(hostInfo)
                }) 
          });
    }
}
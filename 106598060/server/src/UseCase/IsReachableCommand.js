import Command from "../Entity/Command"
const isAllReachable = require('is-all-reachable')

export default class IsReachableCommand extends Command{
    constructor(){
        super()
        this.name = "isReachable"
    }

    monitor(ipAddress, callback){
        isAllReachable([
            ipAddress
          ], (err, reachable) => {
            callback(reachable)
          });
    }
}
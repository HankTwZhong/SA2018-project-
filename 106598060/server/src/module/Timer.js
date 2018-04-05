import Host from './Host'
export default class Timer{
    constructor(frequency){
        this.frequency = frequency
    }
    pingInterval(hostManage){
       let self  = this
       let setIntervalId =  setInterval(function() {
            self.checkHostStatus(hostManage)
        }, this.frequency)
        return setIntervalId
    }
    checkHostStatus(hostManage,callback){
        let responseList= hostManage.getAllHost()
        let hostList = hostManage.getHostList()
        var self = this
        hostList.forEach(function(host){
            hostManage.pingHost(host,function(hostInfo){
            for(var i = 0 ; i <responseList.length; i++)
            {
              if(responseList[i].hostName === hostInfo.hostName)
                {
                    if(responseList[i].active !==  hostInfo.active)
                        Host.notifyAll(responseList[i].hostName)
                    responseList[i].active =  hostInfo.active
                    responseList[i].date  = hostInfo.date
                }
            }
          })
        })
        hostManage.setResponseH(responseList)
    }
}
import Host from './Host'
import PingCommand from './Command/PingCommand'
import IsReachableCommand from './Command/IsReachableCommand';
import moment from 'moment'

export default class CheckHostUseCase{
    checkHostStatus(hostManage,callback){
        let responseList= hostManage.getAllHost()
        let hostList = hostManage.getHostList()
        var self = this
        hostList.forEach(function(host){
            self.selectCommand(host,function(hostInfo){
            for(var i = 0 ; i <responseList.length; i++)
            {
              if(responseList[i].hostName === hostInfo.hostName)
                {
                    if(responseList[i].active !==  hostInfo.active)
                    {
                        let host = new Host(responseList[i].hostName,responseList[i].ipAddress, responseList[i].selected)
                        host.notifyAll()
                    }
                    responseList[i].active =  hostInfo.active
                    responseList[i].date  = hostInfo.date
                }
            }
          })
        })
        hostManage.setResponseH(responseList)
    }
    selectCommand(host,callback){
        let self = this
        if (host.selected == 'Ping'){
            let pingCommand = new PingCommand()
            pingCommand.monitor(host.ipAddress, ((active)=>{
                self.setResponseData(host,active,((hostInfo)=>{
                    callback(hostInfo)
                }))
            }))
        }
        else if(host.selected == 'isReachable'){
            let isReachableCommand = new IsReachableCommand()
            isReachableCommand.monitor(host.ipAddress, ((active)=>{
                self.setResponseData(host,active,((hostInfo)=>{
                    callback(hostInfo)
                }))
            }))
        }
    }
    setResponseData(host,active,callback){
        let hostInfo = {}
        hostInfo.contact = []
        hostInfo.hostName = host.hostName
        hostInfo.ipAddress = host.ipAddress
        hostInfo.active =  active ? 'Up' :  'Down'
        hostInfo.date = moment().format('YYYY/MM/DD  HH:mm:ss')
        hostInfo.selected = host.selected
        hostInfo.contact=host.contact
        callback(hostInfo)
    }
}
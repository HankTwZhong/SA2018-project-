
import moment from 'moment'
import PingCommand from './PingCommand'
import IsReachableCommand from './IsReachableCommand';

export default class CheckHostUseCase{
    constructor(){
        this.pingCommand = new PingCommand()
        this.isReachableCommand = new IsReachableCommand()  
    }
    checkHostStatus(applicationContext,callback){
        let responseList= applicationContext.getResponseList()
        let hostList = applicationContext.getAllHostList()
        var self = this
        hostList.forEach(function(host){
            self.selectCommand(host,function(hostInfo){
            for(var i = 0 ; i <responseList.length; i++){
              if(responseList[i].hostName === hostInfo.hostName){
                    if(responseList[i].active !==  hostInfo.active){
                        hostList[i].notifyAll(hostInfo.active)
                    }
                    responseList[i].active =  hostInfo.active
                    responseList[i].date  = hostInfo.date
                }
            }
          })
        })
        applicationContext.setResponseList(responseList)
    }
    selectCommand(host,callback){
        let self = this
        if (host.selected === 'Ping'){
            self.pingCommand.monitor(host.ipAddress, ((active)=>{
                self.setResponseData(host,active,((hostInfo)=>{
                    callback(hostInfo)
                }))
            }))
        }
        else if(host.selected === 'isReachable'){
            self.isReachableCommand.monitor(host.ipAddress, ((active)=>{
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
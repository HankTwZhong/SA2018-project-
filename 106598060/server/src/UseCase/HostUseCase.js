import Host from '../Entity/Host'
import ApplicationContext from './ApplicationContext'
import CheckHostUseCase from './CheckHostUseCase'

export default class HostUseCase{
    constructor(applicationContext){
        this.applicationContext= applicationContext
        this.responseL = this.applicationContext.getResponseList()
        if (this.responseL === undefined)
            this.responseL = []
    }
    
    getAllHost(){
        return this.responseL
    }
    
    deleteHost(hostName,callback){
        let self = this
        this.applicationContext.deleteHost(hostName,((restHost,deletedHostName)=>{
            this.responseL = this.responseL.filter((hostData)=>{
                return hostData.hostName !== hostName
            })
            callback(deletedHostName)
        }))
    }

    addHost(hostObject,callback){
        var self =this
        let host = new Host(hostObject.hostName, hostObject.ipAddress, hostObject.selected,[],[])
        this.applicationContext.addHost(host,(()=>{

        }))
        let checkHostUseCase = new CheckHostUseCase()
          checkHostUseCase.selectCommand(hostObject,function(hostInfo){   
            self.responseL.push(hostInfo)
            self.applicationContext.setResponseList(self.responseL)
            callback()
          })
    }

}

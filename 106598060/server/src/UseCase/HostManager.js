import Host from '../Entity/Host'
import Timer from '../UseCase/Timer'
import CheckHostUseCase from '../UseCase/CheckHostUseCase'
import ApplicationContext from './ApplicationContext'

export default class HostManager{
    constructor(applicationContext){
        this.responseL = []
        this.applicationContext= applicationContext
    }
    updateAllHostInterval(){
        const frequency = 5000
        console.log('IntervalMonitor every ' + frequency/1000 +' sec')
        let timer = new Timer(frequency)
        let setIntervalId  =  timer.pingInterval(this)
        return setIntervalId
    }
    setEachResponeHost(callback){
        let count = 0
        let self = this
        let checkHostUseCase = new CheckHostUseCase
        if(this.applicationContext.getAllHostList().length === 0)
        callback()
        this.applicationContext.getAllHostList().forEach(function(host){
            checkHostUseCase.selectCommand(host,function(hostInfo){
                count++
                self.responseL.push(hostInfo)
                if(count === self.applicationContext.getAllHostList().length)
                callback()
            })
        })
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
    setResponseH(responseList){
        this.responseL = responseList
    }
    addHost(hostObject,callback){
        var self =this
        let host = new Host(hostObject.hostName, hostObject.ipAddress, hostObject.selected,[],[])
        this.applicationContext.addHost(host,(()=>{

        }))
        let checkHostUseCase = new CheckHostUseCase
          checkHostUseCase.selectCommand(hostObject,function(hostInfo){   
            self.responseL.push(hostInfo)
            callback()
          })
    }

}

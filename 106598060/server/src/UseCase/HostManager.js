import TxtOperator from '../Adapter/TxtOperator'
import Host from '../Entity/Host'
import Timer from '../UseCase/Timer'
import Observer from '../Entity/Observer'
import CheckHostUseCase from '../UseCase/CheckHostUseCase'
import ApplicationContext from './ApplicationContext'


export default class HostManager{
    constructor(){
        this.hostL = []
        this.responseL = []
        this.fileOperator = new TxtOperator
        this.applicationContext= undefined
    }
    startMonitorHost(callback){
        var self  =  this
        this.applicationContext = new ApplicationContext(()=>{
            self.hostL = this.applicationContext.getAllHostList()
            callback()
        })
    }

    updateAllHostInterval(){
        const frequency = 5000
        console.log('only once')
        let timer = new Timer(frequency)
        let setIntervalId  =  timer.pingInterval(this)
        return setIntervalId
    }
    setEachResponeHost(callback){
        let count = 0
        let self = this
        this.hostL.forEach(function(host){
          count++
          let checkHostUseCase = new CheckHostUseCase
          checkHostUseCase.selectCommand(host,function(hostInfo){
            self.responseL.push(hostInfo)
          })
          if(count === self.hostL.length)
            callback(self.responseL)
        })
      
    }
    getAllHost(){
          return this.responseL
    }
    getHostList(){
            return this.hostL
    }
    deleteHost(hostName,callback){
        let self = this
        this.applicationContext.deleteHost(hostName,((restHost,deletedHostName)=>{
            this.hostL =  restHost
            this.responseL = this.responseL.filter((hostData)=>{
                return hostData.hostName !== hostName
            })
            callback(deletedHostName)
        }))
    }
    setHostList(hostList){
        this.hostL = hostList
    }
    setResponseH(responseList){
        this.responseL = responseList
    }

    addHost(req,callback){
        var self =this
        let host = new Host(req.body.hostName, req.body.ipAddress, req.body.selected)
        this.applicationContext.addHost(host,((hostList)=>{
            self.hostL = hostList
        }))
        let checkHostUseCase = new CheckHostUseCase
          checkHostUseCase.selectCommand(req.body,function(hostInfo){   
            self.responseL.push(hostInfo)
            callback()
          })
    }
}

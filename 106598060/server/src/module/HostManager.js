import FileOperator from './FileOperator'
import Host from './Host'
import Timer from './Timer'
import Observer from './Observer/Observer'
import CheckHostUseCase from './CheckHostUseCase'


export default class HostManager{
    constructor(){
        this.hostL = []
        this.responseL = []
        this.fileOperator = new FileOperator
    }
    startMonitorHost(callback){
        var self = this
        this.fileOperator.readData('hostList',function(data){
            self.hostL  = data
            callback()
        })
    }

    // setResponseData(host,active,callback){
    //     let hostInfo = {}
    //     hostInfo.contact = []
    //     hostInfo.hostName = host.hostName
    //     hostInfo.ipAddress = host.ipAddress
    //     hostInfo.active =  active ? 'Up' :  'Down'
    //     hostInfo.date = moment().format('YYYY/MM/DD  HH:mm:ss')
    //     hostInfo.selected = host.selected
    //     hostInfo.contact=host.contact
    //     callback(hostInfo)
    // }
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
    deleteHost(req,callback){
        let self = this
        this.fileOperator.readData('observerList',((data)=>{
            let allObserverList = data
            allObserverList = allObserverList.filter((eachList)=>{
                return eachList.hostName !==  req.body.hostName
            })
            this.fileOperator.saveData('observerList',allObserverList)
        }))
        this.fileOperator.readData('contactList',((data)=>{
            let allContactList = data
            allContactList = allContactList.filter((eachList)=>{
                return eachList.hostName !==  req.body.hostName
            })
            this.fileOperator.saveData('contactList',allContactList)
        }))
        this.hostL = this.hostL.filter(function(hostData){
            return hostData.hostName !== req.body.hostName
        })
        this.responseL = this.responseL.filter((hostData)=>{
            return hostData.hostName !== req.body.hostName
        })
    
        this.fileOperator.saveData('hostList',this.hostL)
        callback(this.hostL)
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
        host.contact = []
        this.hostL.push(host)
        this.fileOperator.saveData('hostList',this.hostL)
        let checkHostUseCase = new CheckHostUseCase
          checkHostUseCase.selectCommand(req.body,function(hostInfo){   
            self.responseL.push(hostInfo)
            callback()
          })
    }
}

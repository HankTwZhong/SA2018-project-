import FileOperater from './FileOperater'
import Contact from './Contact'
import Host from './Host'
import Timer from './Timer'
import Observer from './Observer/Observer';

var ping = require('ping')
var moment = require('moment')
export default class HostManager{
    constructor(){
        this.hostL = []
        this.responseL = []
        this.fileOperater = new FileOperater
    }
    startMonitorHost(callback){
        var self = this
        this.fileOperater.readData(function(data){
            self.hostL  = data
            callback(self.hostL)
        })

    }
    pingHost(host,callback){
        let self = this
        ping.sys.probe(host.ipAddress, function(active){
            self.setResponseData(host,active,function(hostInfo){
            if(callback)callback(hostInfo)
            }) 
        })
      }
    setResponseData(host,active,callback){
        let hostInfo = {}
        hostInfo.contact = []
        hostInfo.hostName = host.hostName
        hostInfo.ipAddress = host.ipAddress
        hostInfo.active =  active ? 'Up' :  'Down'
        hostInfo.date = moment().format('YYYY/MM/DD  HH:mm:ss')
        hostInfo.contact=host.contact
        callback(hostInfo)
    }
    updateAllHostInterval(){
        const frequency = 5000
        console.log('only once')
        let timer = new Timer(frequency)
        let setIntervalId  =  timer.pingInterval(this)
        return setIntervalId
    }
    checkHostStatus(callback){
        var self = this
        this.hostL.forEach(function(host){
            self.pingHost(host,function(hostInfo){
            for(var i = 0 ; i <self.responseL.length; i++)
            {
              if(self.responseL[i].hostName === host.hostName)
                {
                    var s = self
                    if(self.responseL[i].active !==  hostInfo.active)
                       { 
                        //    let host = new Host(self.responseL[i].hostName,self.responseL[i].ipAddress)
                        //    host.notifyAll(self.hostL[i].observerList)
                           self.notifyAll(self.hostL[i])
                       }
                    self.responseL[i].active =  hostInfo.active
                    self.responseL[i].date  = hostInfo.date
                }
            }
          })
        })
        return this.responseL
    }
    setEachResponeHost(callback){
        var count = 0
        var self = this
        this.hostL.forEach(function(host){
          self.pingHost(host,function(hostInfo){
            self.responseL.push(hostInfo)
                callback(self.responseL)
          })
        })
      
    }
    getAllHost(){
          return this.responseL
    }
    getHostList(){
            return this.hostL
    }
    deleteHost(req,callback){
        var self = this
        this.hostL = this.hostL.filter(function(hostData){
            return hostData.hostName !== req.body.hostName
        })
        this.responseL = this.responseL.filter((hostData)=>{
            return hostData.hostName !== req.body.hostName
        })
        this.fileOperater.saveData(this.hostL)
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
        let host = new Host(req.body.hostName , req.body.ipAddress)
        host.contact = []
        this.hostL.push(host)
          this.fileOperater.saveData(this.hostL)
          this.pingHost(req.body,function(hostInfo){   
            self.responseL.push(hostInfo)
            callback()
          })
    }
    attach(hostName,observer = new Observer){
        var self = this
        for(var i = 0 ;i <this.hostL.length ; i++){
            if(self.hostL[i].hostName === hostName)
            {
              if(self.hostL[i].observerList === undefined)    
                    self.hostL[i].observerList = [observer]
              else if(self.hostL[i].observerList.map(function(e) { return e.name}).indexOf(observer.name) === -1 )
                    self.hostL[i].observerList.push(observer)
              self.responseL[i].observerList = self.hostL[i].observerList
              console.log(self.responseL[i].observerList[0])
            }
            if(i === self.hostL.length-1)
                self.fileOperater.saveData(self.hostL)
        }
    }
    notifyAll(host){
        if(host.observerList === undefined)
            console.log('no contact!!')
        else{
            host.observerList.forEach((observer)=>{
                if(observer.name ==='facebookObserber'){
                    let fb =  new FacebookObserver
                    fb.notify()
                }
            })
        }
    }
    addContact(req,callback){
        var self = this
        let contact  = new Contact(req.body.contactName,req.body.communicate,req.body.hostName)
        for(var i = 0 ;i <this.hostL.length ; i++){
            if(self.hostL[i].hostName === req.body.hostName)
            {
              if(self.hostL[i].contact === undefined)    
                self.hostL[i].contact = [contact]
              else
                self.hostL[i].contact.push(contact)    
              self.responseL[i].contact = self.hostL[i].contact
            }
            if(i === self.hostL.length-1){
                self.fileOperater.saveData(self.hostL)
                callback()
            }
        }
    }
}

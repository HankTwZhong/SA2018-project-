import FileOperater from './FileOperater'
import Contact from './Contact'

var ping = require('ping')
var moment = require('moment')
export default class Host{
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
    setResponseHost(host,callback){
        var hostInfo = {}
        ping.sys.probe(host.ipAddress, function(active){
          hostInfo.contact = []
          hostInfo.hostName = host.hostName
          hostInfo.ipAddress = host.ipAddress
          hostInfo.active =  active ? 'Up' :  'Down'
          hostInfo.date = moment().format('YYYY/MM/DD  HH:mm:ss')
          hostInfo.contact=host.contact
          if(callback)callback(hostInfo)
        })  
      }
    updateAllHostInterval(){
        var frequency = 2000
        var self = this
        var setIntervalId  = setInterval(function() {
            self.pingHost()
        }, frequency)
        return setIntervalId
    }
    pingHost(callback){
        var self = this
        this.hostL.forEach(function(host){
            self.setResponseHost(host,function(hostInfo){
            for(var i = 0 ; i <self.responseL.length; i++)
            {
              if(self.responseL[i].hostName === host.hostName)
                {
                    var s = self
                    if(self.responseL[i].active !==  hostInfo.active)
                    {
                        var contact = new Contact()
                        contact.emergencyContact(s.responseL[i].contact)
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
        count++ 
          self.setResponseHost(host,function(hostInfo){
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
        this.hostL.push(
            {
            hostName : req.body.hostName,
            ipAddress : req.body.ipAddress,
            contact:[]
          })
          this.fileOperater.saveData(this.hostL)
          this.setResponseHost(req.body,function(hostInfo){   
            self.responseL.push(hostInfo)
            callback()
          })
    }
}

import Host from './module/Host'
import Contact from './module/Contact'

let hostManage
var express = require('express')
var app = express()
const cors = require('cors')
let bodyPaser = require('body-parser')
let setIntervalId

app.use(bodyPaser.urlencoded({extended:true}))
app.use(bodyPaser.json())
app.use(cors())
app.options('*',cors())

export default class Restful{

startServer(){
  app.listen(3000, function () {
    var self = this
    hostManage = new Host()
    hostManage.startMonitorHost(function(){
      hostManage.setEachResponeHost(function(res){
        setIntervalId=hostManage.updateAllHostInterval()
      })
    })
    console.log('Example app listening on port 3000!');
  })
}
addHost(){
  app.post('/addHost',function(req,res){
    var hostList = hostManage.getHostList()
    // var responseList = hostManage.getAllHost()
    if(req.body.hostName === undefined || req.body.ipAddress === undefined)
    res.send('')
    else if(hostList.map(function(e) { return e.hostName}).indexOf(req.body.hostName)> 0 )
    res.send('There has same host')
    else{
      hostManage.addHost(req,function(){
        res.send('Host: "'+ req.body.hostName + '" was added success')
      })
    }
  })
}
deleteHost(){
  app.post('/deleteHost',function(req,res){
    var hostList = hostManage.getHostList()  
    clearInterval(setIntervalId)
    hostManage.deleteHost(req,function(hostList){
      setIntervalId=hostManage.updateAllHostInterval()
          
          res.send('host'+ hostList.hostName +'has been delete')
      })
  })
}
getHostData(){
  app.post('/getHostsData', function (req, res) {
      var responseList = hostManage.getAllHost() 
      console.log(responseList)
      let page = req.query.page
      let per_page = req.query.per_page
      let current_page = 1
      let last_page = 1
      let prev_page_url = null
      let domain = "http://localhost:3000/getHostsData"
      let vuetableFormat = {}
      if(page){
        current_page = page * 1
      }
        if(responseList.length % 10 === 0 && responseList.length !== 0){
          last_page = responseList.length / 10
        }
        else{
            last_page = Math.round(responseList.length / 10) + 1
        }                               
        if(current_page > 1){
            prev_page_url = domain + '&sort=&page=' + (current_page - 1) +'&per_page=' + per_page
        }             
        vuetableFormat.total = responseList.length
        vuetableFormat.per_page = per_page
        vuetableFormat.current_page = current_page
        vuetableFormat.last_page = last_page
        vuetableFormat.next_page_url = domain + '&sort=&page=' + (current_page + 1) +'&per_page=' + per_page
        vuetableFormat.prev_page_url = prev_page_url
        vuetableFormat.from = 1 + 10 * (current_page - 1)
        vuetableFormat.to = 10 * current_page
        vuetableFormat.data = responseList.slice(vuetableFormat.from - 1 , vuetableFormat.to)
        res.json(vuetableFormat)
  
  })
}
getContact(){
  app.post('/getContact',function(req,res){
    var responseList = hostManage.getAllHost()
    var specificHost = responseList.filter((eachHost)=>{
      return eachHost.hostName === req.query.hostName
    })
    var contactList = specificHost[0].contact
    if(contactList !== undefined){
    for(var i=0 ; i <contactList.length;i++)
    {
      for(var j=0 ; j<contactList[i].communicate.length;j++){
          if(contactList[i].communicate[j].type==='Facebook')
            contactList[i].facebookAddress =  contactList[i].communicate[j].address
          if(contactList[i].communicate[j].type==='Telephone')
            contactList[i].telephoneAddress =  contactList[i].communicate[j].address
          if(contactList[i].communicate[j].type==='Email')
            contactList[i].emailAddress =  contactList[i].communicate[j].address
          if(contactList[i].communicate[j].type==='Skype')
            contactList[i].skypeAddress =  contactList[i].communicate[j].address
          if(contactList[i].communicate[j].type==='LineID')
            contactList[i].lineIDAddress =  contactList[i].communicate[j].address
        }
      
    }
    }
    let page = req.query.page
    let per_page = req.query.per_page
    let current_page = 1
    let last_page = 1
    let prev_page_url = null
    let domain = "http://localhost:3000/todo"
    let vuetableFormat = {}
    if(contactList === undefined)
    contactList = []
    if(page){
      current_page = page * 1
    }
      if(contactList.length % 10 === 0 &&contactList.length !== 0){
        last_page =contactList.length / 10
      }
      else{
          last_page = Math.round(contactList.length / 10) + 1
      }                               
      if(current_page > 1){
          prev_page_url = domain + '&sort=&page=' + (current_page - 1) +'&per_page=' + per_page
      }             
      vuetableFormat.total =contactList.length
      vuetableFormat.per_page = per_page
      vuetableFormat.current_page = current_page
      vuetableFormat.last_page = last_page
      vuetableFormat.next_page_url = domain + '&sort=&page=' + (current_page + 1) +'&per_page=' + per_page
      vuetableFormat.prev_page_url = prev_page_url
      vuetableFormat.from = 1 + 10 * (current_page - 1)
      vuetableFormat.to = 10 * current_page
      vuetableFormat.data =contactList.slice(vuetableFormat.from - 1 , vuetableFormat.to)
      console.log(vuetableFormat)
      res.json(vuetableFormat)
    
  })
}
addContact(){
    app.post('/addContact',function(req,res){
    let contact = new Contact
    contact.addContact(req,hostManage,function(){
      res.send('add success')
    })
    }
  )
}

}
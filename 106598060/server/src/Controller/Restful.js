import HostManager from '../UseCase/HostManager'
import FacebookObserver from '../UseCase/FacebookObserver';
import LineObserver from '../UseCase/LineObserver';
import EmailObserver from '../UseCase/EmailObserver';
import SkypeObserver from '../UseCase/SkypeObserver';
import TelephoneObserver from '../UseCase/TelephoneObserver';
import Host from '../Entity/Host'
import ApplicationContext from '../UseCase/ApplicationContext' 
import { callbackify } from 'util';


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
  constructor(callback){
    this.hostManager = undefined
    let self = this
    this.applicationContext = new ApplicationContext(()=>{
      self.hostManager = new HostManager(this.applicationContext)
      callback()
    })
  }
startServer(){
  var self = this
  app.listen(3000, function () {
      self.hostManager.setEachResponeHost(function(){
        setIntervalId=self.hostManager.updateAllHostInterval()
      })
    console.log('App listening on port 3000!');
  })
}
addHost(){
  let self = this
  app.post('/addHost',function(req,res){
    let hostList = self.applicationContext.getHostList()
    if(req.body.hostName === undefined || req.body.ipAddress === undefined || req.body.selected === undefined)
    res.send('')
    else if(hostList.map(function(e) { return e.hostName}).indexOf(req.body.hostName)>=0 )
    res.send('There has same host')
    else{
      self.hostManager.addHost(req.body,function(){
        res.send('Host: "'+ req.body.hostName + '" was added success')
      })
    }
  })
}
deleteHost(){
  let self = this
  app.post('/deleteHost',function(req,res){
    clearInterval(setIntervalId)
    self.hostManager.deleteHost(req.body.hostName,function(hostName){
      console.log('hostName' + hostName )
      setIntervalId=self.hostManager.updateAllHostInterval()
      res.send('host "'+ hostName +' "has been delete')
      })
  })
}
getHostData(){
  let self = this
  app.post('/getHostsData', function (req, res) {
      var responseList = self.hostManager.getAllHost() 
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
  let self = this
  app.post('/getContact',function(req,res){
    let contactList  
      let specificContactList = self.applicationContext.getContactList(req.query.hostName)
      if(specificContactList.length>0)
      contactList = specificContactList[0].contactList
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
            if(j===contactList[i].communicate.length - 1)
              contactList[i].communicate =[]
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
        res.json(vuetableFormat)
  })
}
addContact(){
  let self = this
    app.post('/addContact',function(req,res){
     let hostList = self.applicationContext.getAllHostList()
     let host
     hostList.forEach((eachHost)=>{
        if(eachHost.hostName  === req.body.hostName)
        host = eachHost
     })
      host.addContact(req.body,self.applicationContext,function(){
        let observerList =  []
        for(let i =0 ; i < req.body.communicate.length ; i++){
          if(req.body.communicate[i].type === 'Facebook')
            observerList.push(new FacebookObserver())
          if(req.body.communicate[i].type === 'Telephone')
            observerList.push(new TelephoneObserver())
          if(req.body.communicate[i].type === 'Email')
            observerList.push(new EmailObserver())
          if(req.body.communicate[i].type === 'Skype')
            observerList.push(new SkypeObserver())
          if(req.body.communicate[i].type === 'LineID')
            observerList.push(new LineObserver())
        }
        host.attach(req.body.hostName,self.applicationContext,observerList)
        res.send('Attach Observer Success')
      })
  
    }
  )
}

}
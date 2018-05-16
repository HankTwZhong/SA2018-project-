import HostUseCase from '../UseCase/HostUseCase'
import Host from '../Entity/Host'
import ApplicationContext from '../UseCase/ApplicationContext'
import InitialUseCase from '../UseCase/InitialUseCase'; 
import HostInputDTO from '../UseCase/HostInputDTO'
import HostViewModel from '../Controller/HostViewModel'
import ContactViewModel from '../Controller/ContactViewModel'
import ContactUseCase from '../UseCase/ContactUseCase'
import ContactInputDTO from '../UseCase/ContactInputDTO';

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
    this.hostUseCase = undefined
    this.initialUseCase = undefined
    this.contactUseCase = undefined
    let self = this
    this.applicationContext = new ApplicationContext(()=>{
      self.initialUseCase = new InitialUseCase(this.applicationContext)
      callback()
    })
  }
startServer(){
  var self = this
  app.listen(3000, function () {
      self.initialUseCase.initialResponseList(function(){
      self.hostUseCase = new HostUseCase(self.applicationContext)    
      self.contactUseCase = new ContactUseCase(self.applicationContext)          
      setIntervalId=self.initialUseCase.initialTimerFrequency()
      })
    console.log('App listening on port 3000!');
  })
}
addHost(){
  let self = this
  app.post('/addHost',function(req,res){
    let hostList = self.applicationContext.getAllHostList()
    if(req.body.hostName === undefined || req.body.ipAddress === undefined || req.body.selected === undefined)
    res.send('')
    else if(hostList.map(function(e) { return e.hostName}).indexOf(req.body.hostName)>=0 )
    res.send('There has same host')
    else{
      let hostInputDTO = new HostInputDTO(req.body.hostName,req.body.ipAddress, req.body.selected)
      self.hostUseCase.addHost(hostInputDTO,function(){
        res.send('Host: "'+ req.body.hostName + '" was added success')
      })
    }
  })
}
deleteHost(){
  let self = this
  app.post('/deleteHost',function(req,res){
    clearInterval(setIntervalId)
    self.hostUseCase.deleteHost(req.body.hostName,function(hostName){
      setIntervalId=self.initialUseCase.initialTimerFrequency()
      res.send('host "'+ hostName +' "has been delete')
      })
  })
}
getHostData(){
  let self = this
  app.post('/getHostsData', function (req, res) {
      let hostOutputDTO = self.hostUseCase.getAllHost()
      let hostViewModel = new HostViewModel(hostOutputDTO.getResponseList())
      let vuetableFormat = hostViewModel.hostDataForVueTable(req)
      res.json(vuetableFormat)
  })
}
getContact(){
  let self = this
  app.post('/getContact',function(req,res){
    let contactList  
      let contactOutputDTO = self.contactUseCase.getContactList(req.query.hostName)
      let contactViewModel = new ContactViewModel(contactOutputDTO.getContactList())
      contactViewModel.contactDataForVueTable(req,function(vuetableFormat){
        res.json(vuetableFormat)
      })
  })
}
addContact(){
  let self = this
    app.post('/addContact',function(req,res){
     for(var i=0 ; i <req.body.communicate.length;i++){
      if(req.body.communicate[i].type==='Facebook')
        req.body.facebookAddress =  req.body.communicate[i].address
      if(req.body.communicate[i].type==='Telephone')
        req.body.telephoneAddress =  req.body.communicate[i].address
      if(req.body.communicate[i].type==='Email')
        req.body.emailAddress =  req.body.communicate[i].address
      if(req.body.communicate[i].type==='Skype')
        req.body.skypeAddress =  req.body.communicate[i].address
      if(req.body.communicate[i].type==='LineID')
        req.body.lineIDAddress =  req.body.communicate[i].address
    }
    let contactInputDTO  = new ContactInputDTO(req.body.contactName,req.body.facebookAddress,req.body.lineIDAddress,req.body.skypeAddress,req.body.telephoneAddress,req.body.emailAddress)
    self.contactUseCase.addContact(req.body.hostName, contactInputDTO, function(){
      res.send('Attach Observer Success')
    })
  })
}

}
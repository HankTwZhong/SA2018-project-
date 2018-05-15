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
      var hostOutputDTO = self.hostUseCase.getAllHost()
      let hostViewModel = new HostViewModel(hostOutputDTO.getResponseList())
      let vuetableFormat = hostViewModel.hostDataForVueTable(req)
      res.json(vuetableFormat)
  })
}
getContact(){
  let self = this
  app.post('/getContact',function(req,res){
    // let contactViewModel = new ContactViewModel()
    let contactList  
      let specificContactList = self.applicationContext.getContactList(req.query.hostName)
      if(specificContactList.length === 0)
      contactList = []
      else
      contactList = specificContactList[0].contactList
      let page = req.query.page
      let per_page = req.query.per_page
      let current_page = 1
      let last_page = 1
      let prev_page_url = null
      let domain = "http://localhost:3000/todo"
      let vuetableFormat = {}
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
     let contactUseCase = new ContactUseCase(self.applicationContext)
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
    contactUseCase.addContact(req.body.hostName, contactInputDTO, function(){
      res.send('Attach Observer Success')
    })
  })
}

}
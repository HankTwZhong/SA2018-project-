import model from './src/model'
var express = require('express')
var app = express()
var ping = require('ping')
var moment = require('moment')
let oldHostList = []
let count =  0
let info = {}
let bodyPaser = require('body-parser')
let host2 = []

const cors = require('cors')

app.use(bodyPaser.urlencoded({extended:true}))
app.use(bodyPaser.json())
app.use(cors())
app.options('*',cors())
app.listen(3000, function () {
  model.readData(function(data){
    host2 = data
    pingHost()
  })
  intervalGetHostStatus()
  console.log('Example app listening on port 3000!');
})


function pingHost(callback){
  if(host2.length === 0)
  oldHostList = []
  getEachStatus()
  function getEachStatus(callback){
    var count=0
      if(host2.length===0);
        host2.forEach(function(host){
          count++
          ping.sys.probe(host.ipAddress, function(active){
            info.hostName = host.hostName
            info.ipAddress = host.ipAddress
            info.active =  active ? 'Up' :  'Down'
            info.date = moment().format('YYYY/MM/DD  HH:mm:ss')
            for(var i = 0 ; i <oldHostList.length; i++)
            {
              if(oldHostList[i].hostName === host.hostName)
                {
                  oldHostList[i].active =  info.active
                  oldHostList[i].date  = info.date
                }
            }
            if(oldHostList.map(function(e) { return e.hostName}).indexOf(host.hostName) === -1)
              oldHostList.push(info)
            info={}
          })
          if(count== host2.length)
          if(callback) callback()
    })
  }
}

function intervalGetHostStatus(){
  var frequency = 500
      setInterval(function() {
        pingHost()
      }, frequency)
}
app.post('/addHost',function(req,res){
  if(req.body.newHost === undefined || req.body.ipAddress === undefined)
  res.send('')
  else if(host2.map(function(e) { return e.hostName}).indexOf(req.body.newHost)> 0 )
   res.send('There has same host')
  else{
    host2.push(
      {
      hostName:req.body.newHost,
      ipAddress : req.body.ipAddress
    })
    model.saveData(host2)
    ping.sys.probe(req.body.ipAddress, function(active){
      info.hostName = req.body.newHost
      info.ipAddress = req.body.ipAddress
      info.active =  active ? 'Up' :  'Down'
      info.date = moment().format('YYYY/MM/DD  HH:mm:ss')
      oldHostList.push(info)
      info ={}
      res.send('Host: "'+ req.body.newHost+ '" was added success')
    })  
  }
})


app.post('/deleteHost',function(req,res){
  deleteHost(function(host2){
    model.saveData(host2)
    res.send('host'+ host2.hostName +'has been delete')
    })
  function deleteHost(callback){
    host2 = host2.filter(function(hostData){
      return hostData.hostName !== req.body.hostName
    })
    oldHostList = oldHostList.filter((hostData)=>{
      return hostData.hostName !== req.body.hostName
    })
    callback(host2)
  }
})

app.get('/todo', function (req, res) {
  console.log(oldHostList)
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
    if(oldHostList.length % 10 === 0 && oldHostList.length !== 0){
      last_page = oldHostList.length / 10
    }
    else{
        last_page = Math.round(oldHostList.length / 10) + 1
    }                               
    if(current_page > 1){
        prev_page_url = domain + '&sort=&page=' + (current_page - 1) +'&per_page=' + per_page
    }             
    vuetableFormat.total = oldHostList.length
    vuetableFormat.per_page = per_page
    vuetableFormat.current_page = current_page
    vuetableFormat.last_page = last_page
    vuetableFormat.next_page_url = domain + '&sort=&page=' + (current_page + 1) +'&per_page=' + per_page
    vuetableFormat.prev_page_url = prev_page_url
    vuetableFormat.from = 1 + 10 * (current_page - 1)
    vuetableFormat.to = 10 * current_page
    vuetableFormat.data = oldHostList.slice(vuetableFormat.from - 1 , vuetableFormat.to)
    res.json(vuetableFormat)
})

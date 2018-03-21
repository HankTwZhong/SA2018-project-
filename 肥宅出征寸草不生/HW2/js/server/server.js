import FileOperater from './src/FileOperater'
let fileOperater = new FileOperater
var express = require('express')
var app = express()
var ping = require('ping')
var moment = require('moment')
const cors = require('cors')
let bodyPaser = require('body-parser')
let responseList = []
let hostList = []
let setIntervalId

app.use(bodyPaser.urlencoded({extended:true}))
app.use(bodyPaser.json())
app.use(cors())
app.options('*',cors())


app.listen(3000, function () {
  fileOperater.readData(function(data){
    hostList = data
    pingHost()
    setEachResponeHost(function(){
      updateAllHostInterval()
      console.log('All Host Data were added ')
    })
  })
  
  
  console.log('Example app listening on port 3000!');
})

function setEachResponeHost(callback){
  var count = 0
  hostList.forEach(function(host){
    count++
    setResponseHost(host,function(hostInfo){
      responseList.push(hostInfo)
    })
  })
  if(count === hostList.length)
  callback()
}

function pingHost(callback){
        hostList.forEach(function(host){
          setResponseHost(host,function(hostInfo){
            for(var i = 0 ; i <responseList.length; i++)
            {
              if(responseList[i].hostName === host.hostName)
                {
                  responseList[i].active =  hostInfo.active
                  responseList[i].date  = hostInfo.date
                }
            }
          })
    })
}

function updateAllHostInterval(){
  var frequency = 3000
    setIntervalId  = setInterval(function() {
        pingHost()
      }, frequency)
}
function setResponseHost(host,callback){
  var hostInfo = {}
  ping.sys.probe(host.ipAddress, function(active){
    hostInfo.hostName = host.hostName
    hostInfo.ipAddress = host.ipAddress
    hostInfo.active =  active ? 'Up' :  'Down'
    hostInfo.date = moment().format('YYYY/MM/DD  HH:mm:ss')
    if(callback)callback(hostInfo)
  })  
}

app.post('/addHost',function(req,res){
  if(req.body.hostName === undefined || req.body.ipAddress === undefined)
  res.send('')
  else if(hostList.map(function(e) { return e.hostName}).indexOf(req.body.hostName)> 0 )
   res.send('There has same host')
  else{
    hostList.push(
      {
      hostName : req.body.hostName,
      ipAddress : req.body.ipAddress
    })
    fileOperater.saveData(hostList)
    setResponseHost(req.body,function(hostInfo){
      res.send('Host: "'+ req.body.hostName + '" was added success')
      console.log(hostInfo)
      responseList.push(hostInfo)
    })
  }
})
app.post('/deleteHost',function(req,res){
  clearInterval(setIntervalId)
  deleteHost(function(hostList){
    updateAllHostInterval()
        fileOperater.saveData(hostList)
        res.send('host'+ hostList.hostName +'has been delete')
    })
  function deleteHost(callback){
    hostList = hostList.filter(function(hostData){
      return hostData.hostName !== req.body.hostName
    })
    responseList = responseList.filter((hostData)=>{
      return hostData.hostName !== req.body.hostName
    })
    callback(hostList)
  }
})

app.get('/todo', function (req, res) {
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

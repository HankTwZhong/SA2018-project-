var express = require('express')
var app = express()
var ping = require('ping')
var moment = require('moment')
var model = require('./model')
let hostList = []
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
  getHostStatus()
  console.log('Example app listening on port 3000!');
})
function getHostStatus(){
  var frequency = 60000 
      setInterval(function() {
        host2.forEach(function(host){
          ping.sys.probe(host.ipAddress, function(active){
            info.hostName = host.hostName
            info.ipAddress = host.ipAddress
            info.active =  active ? 'Up' :  'Down'
            info.date = moment().format('YYYY/MM/DD  HH:mm:ss')
            hostList.push(info)
            info ={}
          })
        })
        if(oldHostList !== hostList)
          oldHostList = hostList
        hostList = []
      }, frequency)
}
app.post('/addHost',function(req,res){
  if(req.body.newHost === undefined)
  res.send('you send a empty host')
  else if(host2.map(function(e) { return e.hostName}).indexOf(req.body.newHost)> 0 )
   res.send('There has same host')
  else{
    host2.push(
      {
      hostName:req.body.newHost,
      ipAddress : req.body.ipAddress
    })
    ping.sys.probe(req.body.ipAddress,function(active){
      hostList.push({
        'hostName' : req.body.newHost,
        'ipAddress': req.body.ipAddress,
        'active' :   active ? 'Up' :  'Down',
        'date' : moment().format('YYYY/MM/DD  HH:mm:ss')
      })
    })
    if(oldHostList !== hostList)
    oldHostList = hostList
    res.send('Host: "'+ req.body.newHost+ '" was added success')
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
    console.log(vuetableFormat)
    res.json(vuetableFormat)
})
export default { getHostStatus }
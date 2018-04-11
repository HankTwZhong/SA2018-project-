import TxtOperator from '../src/module/FileOperator/TxtOperator'
let fileOperator = new  TxtOperator
let assert = require('assert');
let hostList=[]
describe('Save Data', function() {
  describe('prepare an array ', function() {
    it('prepare data', function() {
       hostList = [
         {
           hostName:'Johnsons Host',
           'ipAddress':'192.168.0.1'
          },  
          {
            hostName:'Jokers Host',
            ipAddress:'192.168.0.12'
          }
        ]
    })
    it('read and write data should same',function(){
      fileOperator.saveData('hostList',hostList)
      fileOperator.readData('hostList',function(data){
         console.log(JSON.stringify(data))
       })
      // fileOperator.readData('hostList','utf8')
    })
  })
})
import TxtOperator from '../src/module/FileOperator/TxtOperator'
let fileOperator = new  TxtOperator
let assert = require('assert');
let hostList=[]
let hostList2 = []
describe.only('Save Data', function() {
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
        hostList2 = [
          {
            hostName:'GGGG Host',
            'ipAddress':'192.168.0.1'
           },  
           {
             hostName:'GGGGG Host',
             ipAddress:'192.168.0.12'
           }
         ]
    })
    it('read and write data should same',function(){
      fileOperator.saveData('testList',hostList)
      fileOperator.saveData('testList',hostList2)
      // fileOperator.readData('hostList','utf8')
    })
  })
})
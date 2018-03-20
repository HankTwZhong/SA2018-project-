import FileOperater from '../src/FileOperater'
let fileOperater = new  FileOperater
var assert = require('assert');
let hostList=[]
describe('Save Data', function() {
  describe('prepare an array ', function() {
    it('should return -1 when the value is not present', function() {
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
    it('test',function(){
       fileOperater.saveData(hostList)
       fileOperater.readData(function(data){
          console.log(data)
       })
    })
  })
})
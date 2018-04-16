import TxtOperator from '../src/module/FileOperator/TxtOperator'
let fileOperator = new TxtOperator

let fakeHostList=[]
let fakeObject={}
function fakeData(callback){
    var fakeDataCount = 7
    
    for(var i =  0 ; i < fakeDataCount ; i ++)
    {
        fakeObject.hostName = 'host' +i 
        fakeObject.ipAddress =  '140.124.181.'+ i
        fakeObject.selected = 'Ping'
        fakeHostList.push(fakeObject)
        fakeObject={}
        if(i=== fakeDataCount -1)
        {
            if(callback)
            callback()
        }
    }
}

function startFakeData(){
    fakeData(function(){
        fileOperator.saveData('hostList',fakeHostList)
    })
}
startFakeData()
export default {startFakeData}

import FileOperator from '../src/module/FileOperator'
let fileOperator = new FileOperator

let fakeHostList=[]
let fakeObject={}
function fakeData(callback){
    var fakeDataCount = 7
    
    for(var i =  0 ; i < fakeDataCount ; i ++)
    {
        fakeObject.hostName = 'host' +i 
        fakeObject.ipAddress =  '140.124.181.'+ i
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

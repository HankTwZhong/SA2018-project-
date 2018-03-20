import module from './src/model'


let fakeHostList=[]
let fakeObject={}
function fakeData(callback){
    var fakeDataCount = 50
    
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
        module.saveData(fakeHostList)
    })
}
startFakeData()
export default {startFakeData}

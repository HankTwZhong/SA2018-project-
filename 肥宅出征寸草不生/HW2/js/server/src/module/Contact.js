import FileOperater from './FileOperater'
export default class Contact{
    addContact(req,hostManage,callback){
        var hostList = hostManage.getHostList()
        var responseList = hostManage.getAllHost()
        var fileOperater = new FileOperater
        var self = this
        for(var i = 0 ;i <hostList.length ; i++){
            if(hostList[i].hostName === req.body.hostName)
            {
              if(hostList[i].contact === undefined)    
              hostList[i].contact = [req.body]
              else{
                  console.log(hostList[i].contact)
                    hostList[i].contact.push(req.body)
                    responseList[i].contact = hostList[i].contact
                  console.log(hostList[i].contact)
                  console.log(hostList[i])                 
                }
            }
            if(i === hostList.length-1)
            { 
              hostManage.setHostList(hostList)  
              hostManage.setResponseH(responseList)                           
              fileOperater.saveData(hostList)
            }
        }
        callback(responseList , hostList)
    }
    emergencyContact(emergencyContact){
        console.log('emergecy'+JSON.stringify(emergencyContact))
    }
}
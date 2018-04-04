import FileOperator from './FileOperator'
import Contact from './Contact'
export default class Host{
    constructor(hostName,ipAddress){
        this.hostName  =  hostName
        this.ipAddress = ipAddress
        this.contact = undefined
        this.observerList =  undefined
    }
    getHostName(){
        return this.hostName
    }
    getIPAddress(){
        return this.ipAddress
    }
    getObserverList(){
        return this.observerList
    }
    static attach(hostName,observer = new Observer){
        let fileoperator = new FileOperator
        let self = this
        fileoperator.readData('observerList',function(data){
            let allObserverList = data
            let foundList = allObserverList.filter((eachList)=>{
                return eachList.hostName === hostName
            })
            if(foundList.length === 0){
                let info = {
                    hostName:hostName,
                    observerList : [observer]
                }              
                allObserverList.push(info)
                fileoperator.saveData('observerList',allObserverList)
            }
            else{
                for(let i = 0 ; i < allObserverList.length ;i++){
                    if(allObserverList[i].hostName === hostName){
                        console.log(allObserverList[i].observerList.map(function(e) { return e.name}))
                        if(allObserverList[i].observerList.map(function(e) { return e.name}).indexOf(observer.name) === -1)
                            allObserverList[i].observerList.push(observer)
                    }
                    if(i === allObserverList.length -1)
                        fileoperator.saveData('observerList',allObserverList)
                }
            }
        })
        
    }
    static notifyAll(hostName){
        let fileoperator = new FileOperator
        let self = this
        fileoperator.readData('observerList', ((data)=>{
            let allObserverList = data
            let notifyHost = allObserverList.filter((eachList)=>{
                return eachList.hostName === hostName
            })
            notifyHost[0].observerList.forEach((observer)=>{
                console.log(observer.name)
            })
        }))
    }
    // static removeAttach(observer){

    // }
    static addContact(req,callback){
        let fileoperator = new FileOperator
        let contact  = new Contact(req.body.contactName,req.body.communicate)
        var self = this
        fileoperator.readData('contactList',function(data){
            let allContactList = data
            let foundList = allContactList.filter((eachList)=>{
                return eachList.hostName === req.body.hostName
            })
            if(foundList.length === 0){
                let info = {
                    hostName:req.body.hostName,
                    contactList : [contact]
                }
                allContactList.push(info)
                fileoperator.saveData('contactList',allContactList)
                callback()
            }
            else{
                for(let i = 0 ; i < allContactList.length ;i++){
                    if(allContactList[i].hostName === req.body.hostName){
                        if(allContactList[i].contactList.map(function(e) { return e.contactName}).indexOf(req.body.contactName) === -1)
                            allContactList[i].contactList.push(contact)
                    }
                    if(i === allContactList.length -1){

                        fileoperator.saveData('contactList',allContactList)
                        callback()
                    }
                }
            }
        })
    }
    static getContactList(hostName,callback){
        let fileoperator = new FileOperator
        fileoperator.readData('contactList',function(data){
            let allContactList = data
            let findContactList = allContactList.filter((eachList)=>{
                return eachList.hostName === hostName
            })
            console.log(findContactList)
            callback(findContactList)
        })

    }
}
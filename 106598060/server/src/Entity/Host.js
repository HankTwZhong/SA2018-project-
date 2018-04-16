import TxtOperator from '../Adapter/TxtOperator'
import Contact from '../Entity/Contact'
export default class Host{
    constructor(hostName,ipAddress,selected,callback){
        this.fileoperator  =  new TxtOperator
        let self = this
        this.fileoperator.readData('observerList',function(data){
            self.allObserverList = data
            if(callback)callback()
        })    
        this.observerList = undefined
        this.hostName  =  hostName
        this.ipAddress = ipAddress
        this.selected = selected
        this.contact = undefined 
    }
    getObserverList(){
        return this.observerList
    }
    attach(hostName,observer = new Observer){
        this.observerList =  this.allObserverList.filter((eachList)=>{
            return eachList.hostName === hostName
        })
        let fileoperator = new TxtOperator
        let self = this
        if(this.observerList.length === 0){
            let info = {
                hostName:hostName,
                observerList : [observer]
            }              
            self.allObserverList.push(info)
        }
        else{
            for(let i = 0 ; i < self.allObserverList.length ;i++){
                if(self.allObserverList[i].hostName === hostName){
                    if(self.allObserverList[i].observerList.map(function(e) { return e.name}).indexOf(observer.name) === -1)
                        self.allObserverList[i].observerList.push(observer)
                }
            }
        }
        
    }
    observerToTxt(){
        this.fileoperator.saveData('observerList',this.allObserverList)
    }
    notifyAll(){
        let self = this
        this.fileoperator.readData('observerList',function(data){
            self.allObserverList = data
            let notifyHost = self.allObserverList.filter((eachList)=>{
                return eachList.hostName === self.hostName
            })
            if(notifyHost.length === 0)
                console.log(self.hostName + ' does not have any observer')
            else{
                console.log(notifyHost[0].hostName)
                notifyHost[0].observerList.forEach((observer)=>{
                    console.log(observer.name)
                })
            }
        })  
     
    }
    static addContact(req,callback){
        let fileoperator = new TxtOperator
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
                        // if(allContactList[i].contactList.map(function(e) { return e.contactName}).indexOf(req.body.contactName) === -1)
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
        let fileoperator = new TxtOperator
        fileoperator.readData('contactList',function(data){
            let allContactList = data
            let findContactList = allContactList.filter((eachList)=>{
                return eachList.hostName === hostName
            })
            callback(findContactList)
        })

    }
}
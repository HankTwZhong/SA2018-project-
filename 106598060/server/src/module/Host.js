import FileOperator from './FileOperator'
import Contact from './Contact'
export default class Host{
    constructor(hostName,ipAddress){
        this.fileoperator  =  new FileOperator
        let self = this
        this.fileoperator.readData('observerList',function(data){
            self.allObserverList = data
        })     
        this.observerList = undefined
        this.hostName  =  hostName
        this.ipAddress = ipAddress
        this.contact = undefined 
    }
    getObserverList(){
        return this.observerList
    }
    attach(hostName,observer = new Observer){
        this.observerList =  this.allObserverList.filter((eachList)=>{
            return eachList.hostName === hostName
        })
        let fileoperator = new FileOperator
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
        let fileoperator = new FileOperator
        fileoperator.readData('contactList',function(data){
            let allContactList = data
            let findContactList = allContactList.filter((eachList)=>{
                return eachList.hostName === hostName
            })
            callback(findContactList)
        })

    }
}
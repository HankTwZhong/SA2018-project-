import TxtOperator from '../TxtOperator'
import Host from '../Entity/Host'
import Contact from '../Entity/Contact'
import FacebookObserver from '../UseCase/Observer/FacebookObserver';
import LineObserver from '../UseCase/Observer/LineObserver';
import EmailObserver from '../UseCase/Observer/EmailObserver';
import SkypeObserver from '../UseCase/Observer/SkypeObserver';
import TelephoneObserver from '../UseCase/Observer/TelephoneObserver';


let txtOperator = new TxtOperator

export default class ApplicationContext{
    constructor(callback){
        this.responseList = undefined
        let self = this
        this.readAllObserverList((allObserverList)=>{
            self.allObserverList = allObserverList
            self.readAllContactList((allContactList)=>{
                self.allContactList = allContactList
                self.readAllhostList(allObserverList,allContactList,(allHostList)=>{
                    self.allHostList = allHostList
                    callback()
                })
            })
        })
    }

    setResponseList(responseList){
        this.responseList = responseList
    }

    getResponseList(){
        return this.responseList
    }

    readAllhostList(allObserverList,allContactList,callback){
        let hostList = []
        txtOperator.readData('hostList',((allHostList)=>{
            allHostList.forEach((host)=>{
                let observerList =[]
                let contactList = []
                for(let i = 0;i<allObserverList.length;i++){
                    if(host.hostName === allObserverList[i].hostName)
                        observerList = allObserverList[i].observerList
                }
                for(let i = 0;i<allContactList.length;i++){
                    if(host.hostName === allContactList[i].hostName)
                    contactList = allContactList[i].contactList
                }
                hostList.push(new Host(host.hostName,host.ipAddress,host.selected,contactList,observerList))

            })
            callback(hostList)
        }))
    }
    readAllContactList(callback){
        txtOperator.readData('contactList',((allContactList)=>{
            callback(allContactList)            
        }))
    }
    readAllObserverList(callback){
        let list = []
        txtOperator.readData('observerList',((allObserverList)=>{
            allObserverList.forEach((observerList)=>{
                let list2 = []
                observerList.observerList.forEach((observer)=>{
                    let currentObserver
                    if(observer.name === 'facebookObserver')
                        currentObserver = new FacebookObserver()
                    else if(observer.name === 'telephoneObserver')
                        currentObserver = new TelephoneObserver()
                    else if(observer.name === 'emailObserver')
                        currentObserver = new EmailObserver()
                    else if(observer.name === 'skpyeObserver')
                        currentObserver = new SkypeObserver()
                    else if(observer.name === 'lineObserver')
                        currentObserver = new LineObserver()
                        list2.push(currentObserver)
                })
                list.push({
                    hostName:observerList.hostName,
                    observerList:list2
                })
            })
            callback(list)
        }))
    }
    getAllHostList(){
        return this.allHostList
    }
    getAllContactList(){
        return this.allContactList
    }
    getContactList(hostName){
        return this.allContactList.filter((contactList)=>{
            return contactList.hostName === hostName
        })
        
    }
    getObserverList(hostName,callback){
        return this.allObserverList.filter((observerList)=>{
            return observerList.hostName === hostName
        })
    }
    deleteObserver(hostName){
        this.allObserverList = this.allObserverList.filter((observerList)=>{
            return observerList.hostName != hostName
        })
        txtOperator.saveData('observerList',this.allObserverList) 
    }
    deleteContact(hostName){
        this.allContactList = this.allContactList.filter((contactList)=>{
            return contactList.hostName != hostName
        })
        txtOperator.saveData('contactList',this.allContactList)         
    }
    deleteHost(hostName,callback){
        this.allHostList = this.allHostList.filter((hostList)=>{
            return hostList.hostName != hostName
        })
        this.deleteObserver(hostName)
        this.deleteContact(hostName)
        txtOperator.saveData('hostList',this.allHostList)  
        callback(this.allHostList,hostName)
    }
    addHost(hostObject,callback){
        this.allHostList.push(hostObject)
        txtOperator.saveData('hostList',this.allHostList)          
        callback(this.allHostList)
    }
    addContact(hostName,contactInputDTO){
        let index =this.allContactList.map(function(contactList) { return contactList.hostName}).indexOf(hostName)
        if(index === -1)
        this.allContactList.push({
            hostName:hostName,
            contactList:[contactInputDTO]
        })
        else
        this.allContactList[index].contactList.push(contactInputDTO)
        txtOperator.saveData('contactList',this.allContactList) 
    }
    addObserver(hostName,observerList){
        let index =this.allObserverList.map(function(observerList) { return observerList.hostName}).indexOf(hostName)
        let self = this
        if(index === -1)
        self.allObserverList.push({
            hostName:hostName,
            observerList:observerList
        })
        else{
            observerList.forEach((eachObserver)=>{
                if(self.allObserverList[index].observerList.map((observer)=>{return observer.name}).indexOf(eachObserver.name) === -1)
                self.allObserverList[index].observerList.push(eachObserver)
            })
        }
        txtOperator.saveData('observerList',this.allObserverList)
    }
}

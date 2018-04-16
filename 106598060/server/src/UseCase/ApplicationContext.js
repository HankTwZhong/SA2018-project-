import TxtOperator from '../Adapter/TxtOperator'
import Host from '../Entity/Host'
import Contact from '../Entity/Contact'
import FacebookObserver from '../UseCase/FacebookObserver';
import LineObserver from '../UseCase/LineObserver';
import EmailObserver from '../UseCase/EmailObserver';
import SkypeObserver from '../UseCase/SkypeObserver';
import TelephoneObserver from '../UseCase/TelephoneObserver';

let txtOperator = new TxtOperator

export default class ApplicationContext{
    constructor(callback){
            this.allHostList = []
            let self = this
            txtOperator.readData('hostList',((allHostList)=>{
                allHostList.forEach((host)=>{
                    self.allHostList.push(new Host(host.hostName,host.ipAddress,host.selected))
                })
                if(callback)callback()
            }))
            txtOperator.readData('contactList',((allContactList)=>{
                self.allContactList = allContactList            
            }))
            txtOperator.readData('observerList',((allObserverList)=>{
                self.allObserverList = []
                allObserverList.forEach((observerList)=>{
                    self.observerList = []
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
                        self.observerList.push(currentObserver)
                    })
                    self.allObserverList.push({
                        hostName:observerList.hostName,
                        observerList:self.observerList
                    })
                })
                // console.log(self.allObserverList)
            }))
        }
    getAllHostList(){
        return this.allHostList
    }
    getHostList(hostName,callback){
        return this.allHostList.filter((host)=>{
            return hostName === host.hostName
        })
        if(callback)callback()
    }
    getContactList(hostName,callback){
        return this.allContactList.filter((contactList)=>{
            return contactList.hostName === hostName
        })
        if(callback)callback()        
    }
    getObserverList(hostName,callback){
        return this.allObserverList.filter((observerList)=>{
            return observerList.hostName === hostName
        })
        if(callback)callback()
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
}

import Contact from '../Entity/Contact'
export default class Host{
    constructor(hostName,ipAddress,selected,callback){
        this.hostName  =  hostName
        this.ipAddress = ipAddress
        this.selected = selected
    }
    attach(hostName,applicationContext,observerList){
        applicationContext.addObserver(hostName,observerList)            
    }
    notifyAll(hostName,applicationContext){
        let observerList = applicationContext.getObserverList(hostName)[0].observerList
        observerList.forEach((observer)=>{
            console.log(observer.name)
        })
    }
    addContact(contactList,applicationContext,callback){
        let contact  = new Contact(contactList.contactName,contactList.communicate)
        applicationContext.addContact(contactList.hostName,contact,(()=>{
            callback()
        }))
    }
}
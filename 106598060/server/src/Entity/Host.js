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
    notifyAll(hostName,active,applicationContext){
        console.log(hostName +' status: '+active)
        let observerList = applicationContext.getObserverList(hostName)[0].observerList
        let contactList = applicationContext.getContactList(hostName)
        contactList[0].contactList.forEach((contact)=>{
           console.log('----------------------------------------')
           console.log('聯絡人名稱 :' + contact.contactName)
           console.log('聯絡方式')
            if(contact.facebookAddress !== undefined) 
                console.log('FB : '+ contact.facebookAddress)
            if(contact.lineIDAddress !== undefined) 
                console.log('Line : '+ contact.lineIDAddress)
            if(contact.skypeAddress !== undefined) 
                console.log('Skype : '+ contact.skypeAddress)
            if(contact.telephoneAddress !== undefined) 
                console.log('Telephone : '+ contact.telephoneAddress)
            if(contact.emailAddress !== undefined) 
                console.log('Email : '+ contact.emailAddress)
        })
        // observerList.forEach((observer)=>{
        //     console.log(observer.name)
        // })
    }
    addContact(contactList,applicationContext,callback){
        let contact  = new Contact(contactList.contactName,contactList.communicate)
        applicationContext.addContact(contactList.hostName,contact,(()=>{
            callback()
        }))
    }
}
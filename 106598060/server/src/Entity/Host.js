export default class Host{
    constructor(hostName,ipAddress,selected,contactList,observerList){
        this.hostName  =  hostName
        this.ipAddress = ipAddress
        this.selected = selected
        this.contactList = contactList
        this.observerList = observerList
    }
    attach(observer){
        if(this.observerList.map((observer)=>{return observer.name}).indexOf(observer. name)===-1)
            this.observerList.push(observer)
    }
    notifyAll(active){
        console.log('-----####-----')
        console.log(this.hostName +' status: '+active)
        if(this.contactList.length === 0)
        console.log(this.hostName +' 沒有任何緊急聯絡人')
        this.contactList.forEach((contact)=>{
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
    }
    addContact(contactObject, callback){
        this.contactList.push(contactObject)
        callback()
    }
}
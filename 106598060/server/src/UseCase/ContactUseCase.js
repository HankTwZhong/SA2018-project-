import FacebookObserver from '../UseCase/Observer/FacebookObserver';
import LineObserver from '../UseCase/Observer/LineObserver';
import EmailObserver from '../UseCase/Observer/EmailObserver';
import SkypeObserver from '../UseCase/Observer/SkypeObserver';
import TelephoneObserver from '../UseCase/Observer/TelephoneObserver';
import Contact from '../Entity/Contact'
import ContactOutputDTO from '../UseCase/ContactOutputDTO'

export default class ContactUseCase{
    constructor(applicationContext){
        this.applicationContext = applicationContext
    }
    addContact(hostName, contactInputDTO, callback){
        let self = this
        let hostList = this.applicationContext.getAllHostList()
        let host
        hostList.forEach((eachHost)=>{
            if(eachHost.hostName  === hostName)
                host = eachHost
        })
        let contactObject  = new Contact(contactInputDTO.getContactName()
                    ,contactInputDTO.getFacebookAddress()
                    ,contactInputDTO.getLineIDAddress()
                    ,contactInputDTO.getSkypeAddress()
                    ,contactInputDTO.getTelephoneAddress()
                    ,contactInputDTO.getEmailAddress())
        host.addContact(contactObject,function(){
        let observerList =  []
        let observer

        if(contactInputDTO.getFacebookAddress() !== undefined){
              observer = new FacebookObserver()
              observerList.push(observer)
              host.attach(observer)
            }
          if(contactInputDTO.getTelephoneAddress() !== undefined){
            observer = new TelephoneObserver()            
            observerList.push(observer)
            host.attach(observer)
          }
          if(contactInputDTO.getEmailAddress() !== undefined){
            observer = new EmailObserver()                        
            observerList.push(observer)     
            host.attach(observer)       
          }
          if(contactInputDTO.getSkypeAddress() !== undefined){
            observer = new SkypeObserver()                                    
            observerList.push(observer)
            host.attach(observer)
          }
          if(contactInputDTO.getLineIDAddress() !== undefined){
            observer = new LineObserver()                                                
            observerList.push(observer)     
            host.attach(observer)       
          } 
        self.applicationContext.addObserver(hostName,observerList)  
        self.applicationContext.addContact(hostName,contactObject)          
        callback()          
      })
    }

    getContactList(hostName){
        return new ContactOutputDTO(this.applicationContext.getContactList(hostName))
    }
}
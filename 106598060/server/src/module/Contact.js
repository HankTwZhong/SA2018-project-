export default class Contact{
    constructor(contactName,communicate,hostName){
        this.contactName = contactName
        this.communicate = communicate
        this.hostName = hostName
    }
    emergencyContact(emergencyContact){
        console.log('emergecy'+JSON.stringify(emergencyContact))
    }
}
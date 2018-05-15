export default class ContactInputDTO{
    constructor(contactName,facebookAddress,lineIDAddress,skypeAddress,telephoneAddress,emailAddress){
        this.contactName = contactName
        this.facebookAddress = facebookAddress
        this.lineIDAddress = lineIDAddress
        this.skypeAddress = skypeAddress
        this.telephoneAddress = telephoneAddress
        this.emailAddress = emailAddress
    }

    getContactName(){
        return this.contactName
    }

    getFacebookAddress(){
        return this.facebookAddress
    }

    getLineIDAddress(){
        return this.lineIDAddress
    }

    getSkypeAddress(){
        return this.skypeAddress
    }

    getTelephoneAddress(){
        return this.telephoneAddress
    }

    getEmailAddress(){
        return this.emailAddress
    }
}
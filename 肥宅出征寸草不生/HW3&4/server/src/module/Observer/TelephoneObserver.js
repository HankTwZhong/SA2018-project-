import Observer from './Observer'


export default class TelephoneObserver extends Observer{
    constructor(){
        super()
        this.name = 'telephoneObserver'
    }
    name(){
        return this.name
    }
    notify(){
        return 'this is telephone notify'
    }
}
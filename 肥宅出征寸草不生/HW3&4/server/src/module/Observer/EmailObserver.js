import Observer from './Observer'


export default class EmailObserver extends Observer{
    constructor(){
        super()
        this.name = 'emailObserver'
    }
    name(){
        return this.name
    }
    notify(){
        return 'this is email notify'
    }
}
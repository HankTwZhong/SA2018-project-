import Observer from './Observer'


export default class SkypeObserver extends Observer{
    constructor(){
        super()
        this.name = 'skpyeObserver'
    }
    name(){
        return this.name
    }
    notify(){
        return 'this is skype notify'
    }
}
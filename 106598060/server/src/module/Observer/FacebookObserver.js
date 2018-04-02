import Observer from './Observer'


export default class FacebookObserver extends Observer{
    constructor(){
        super()
        this.name = 'facebookObserver'
        this.notify = function(){
            return 'this is facebook notify'
        }
    }
    name(){
        return this.name
    }
    notify(){
        return 'this is facebook notify'
    }
}
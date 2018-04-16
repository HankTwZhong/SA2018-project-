import Observer from '../Entity/Observer'

export default class LineObserver extends Observer{
    constructor(){
        super()
        this.name = 'lineObserver'
    }
    name(){
        return this.name
    }
    notify(){
        return 'this is line notify'
    }
}
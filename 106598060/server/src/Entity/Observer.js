export default class Observer{
    constructor(){
        this.name = undefined
    }
    notify(){
        throw new Error('You have to implement the method notify!');
    }
}
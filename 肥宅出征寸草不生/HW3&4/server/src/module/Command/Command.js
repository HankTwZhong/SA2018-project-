export default class Command{
    constructor(){
        this.name = undefined
    }
    monitor(){
        throw new Error('You have to implement the monitor method!');
    }
}

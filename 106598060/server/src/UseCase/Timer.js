import CheckHostUseCase from './CheckHostUseCase'


export default class Timer{
    constructor(frequency){
        this.frequency = frequency
    }
    pingInterval(applicationContext){
       let self  = this
       let checkHostUseCase = new CheckHostUseCase()
       let setIntervalId =  setInterval(function() {
           checkHostUseCase.checkHostStatus(applicationContext)
        }, this.frequency)
        return setIntervalId
    }
}
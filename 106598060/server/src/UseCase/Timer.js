import CheckHostUseCase from './CheckHostUseCase'

export default class Timer{
    constructor(frequency){
        this.frequency = frequency
    }
    pingInterval(hostManage){
       let self  = this
       let setIntervalId =  setInterval(function() {
           let checkHostUseCase = new CheckHostUseCase
           checkHostUseCase.checkHostStatus(hostManage)
        }, this.frequency)
        return setIntervalId
    }
}
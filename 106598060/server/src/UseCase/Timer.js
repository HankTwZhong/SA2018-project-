import CheckHostUseCase from './CheckHostUseCase'


export default class Timer{
    constructor(frequency){
        this.frequency = frequency
    }
    pingInterval(hostManage){
       let self  = this
       let checkHostUseCase = new CheckHostUseCase()
       let setIntervalId =  setInterval(function() {
           checkHostUseCase.checkHostStatus(hostManage)
        }, this.frequency)
        return setIntervalId
    }
}
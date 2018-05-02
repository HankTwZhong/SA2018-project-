import Timer from '../UseCase/Timer'
import CheckHostUseCase from '../UseCase/CheckHostUseCase'

export default class InitialUseCase{
    constructor(applicationContext){
        this.applicationContext= applicationContext
        this.responseL = []
    }
    initialTimerFrequency(){
        const frequency = 5000
        console.log('IntervalMonitor every ' + frequency/1000 +' sec')
        let timer = new Timer(frequency)
        let setIntervalId  =  timer.pingInterval(this.applicationContext)
        return setIntervalId
    }
    initialResponseList(callback){
        let count = 0
        let self = this
        let checkHostUseCase = new CheckHostUseCase
        if(this.applicationContext.getAllHostList().length === 0)
        callback()
        this.applicationContext.getAllHostList().forEach(function(host){
            checkHostUseCase.selectCommand(host,function(hostInfo){
                count++
                self.responseL.push(hostInfo)
                if(count === self.applicationContext.getAllHostList().length){
                    self.applicationContext.setResponseList(self.responseL)
                    callback()
                }
            })
        })
    }
}


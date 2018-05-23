import Host from '../Entity/Host'
import CheckHostUseCase from './CheckHostUseCase'
import HostOutputDTO from './HostOutputDTO'

export default class HostUseCase{
    constructor(applicationContext){
        this.applicationContext= applicationContext
        this.responseL = this.applicationContext.getResponseList()
        if (this.responseL === undefined)
            this.responseL = []
    }
    
    getAllHost(){
        let hostOutputDTO = new HostOutputDTO(this.responseL) 
        return hostOutputDTO
    }
    
    deleteHost(hostName,hostRepository,hostRecallback){
        let self = this
        this.applicationContext.deleteHost(hostName,((restHost,deletedHostName)=>{
            this.responseL = this.responseL.filter((hostData)=>{
                return hostData.hostName !== hostName
            })
            hostRepository.deleteHost(restHost)
            callback(deletedHostName)
        }))
    }

    addHost(hostInputDTO,hostRepository,callback){
        var self =this
        let host = new Host(hostInputDTO.getHostName(), hostInputDTO.getIpAddress(), hostInputDTO.getSelected(),[],[])
        this.applicationContext.addHost(host,((hostList)=>{
            hostRepository.saveHost(hostList)
        }))
        let checkHostUseCase = new CheckHostUseCase()
        checkHostUseCase.selectCommand(host,function(hostInfo){   
            self.responseL.push(hostInfo)
            self.applicationContext.setResponseList(self.responseL)
            callback()
          })
    }

}

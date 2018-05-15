export default class HostInputDTO{
   constructor(hostName,ipAddress,selected){
        this.hostName = hostName
        this.ipAddress = ipAddress
        this.selected = selected
   }
   getHostName(){
       return this.hostName
   }
   getIpAddress(){
       return this.ipAddress
   }
   getSelected(){
       return this.selected
   }
  }


export default class HostData{
    constructor(hostName,ipAddress){
        this.hostName  =  hostName
        this.ipAddress = ipAddress
    }
    getHostName(){
        return this.hostName
    }
    getIPAddress(){
        return this.ipAddress
    }
}
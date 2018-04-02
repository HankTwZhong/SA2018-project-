export default class Host{
    constructor(hostName,ipAddress){
        this.hostName  =  hostName
        this.ipAddress = ipAddress
        this.contact = undefined
        this.observerList =  undefined
        
    }
    getHostName(){
        return this.hostName
    }
    getIPAddress(){
        return this.ipAddress
    }
    getObserverList(){
        return this.observerList
    }
    // attach(hostName,observer = new Observer){
    //     var self = this
    //     for(var i = 0 ;i <this.hostL.length ; i++){
    //         if(self.hostL[i].hostName === hostName)
    //         {
    //           if(self.hostL[i].observerList === undefined)    
    //                 self.hostL[i].observerList = [observer]
    //           else if(self.hostL[i].observerList.map(function(e) { return e.name}).indexOf(observer.name) === -1 )
    //                 self.hostL[i].observerList.push(observer)
    //           self.responseL[i].observerList = self.hostL[i].observerList
    //         }
    //         if(i === self.hostL.length-1)
    //             self.fileOperater.saveData(self.hostL)
    //     }
    // }
    // notifyAll(host){
    //     if(host.observerList === undefined)
    //         console.log('no contact!!')
    //     else{
    //         host.observerList.forEach((observer)=>{
    //             if(observer.name ==='facebookObserber'){
    //                 let fb =  new FacebookObserver
    //                 fb.notify()
    //             }
    //         })
    //     }
    // }
}
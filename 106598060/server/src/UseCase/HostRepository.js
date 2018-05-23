import TxtOperator from '../TxtOperator'
let txtOperator = new TxtOperator()
export default class HostRepository{
    static saveHost(hostList){
        txtOperator.saveData('hostList',hostList) 
    }
    static deleteHost(hostList){
        txtOperator.saveData('hostList',hostList)  
    }
}
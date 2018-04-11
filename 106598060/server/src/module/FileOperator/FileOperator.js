import fs from 'fs'
export default class FileOperator{
    saveData(fileName,data){
        throw new Error('You have to implement the saveData method!');        
      }
    readData(fileName,callback){
        throw new Error('You have to implement the readData method!');        
      }
  }

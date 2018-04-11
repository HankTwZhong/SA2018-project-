import fs from 'fs'
export default class FileOperator{
    saveData(){
        // let content = JSON.stringify(hostList)
        // var file = fs.createWriteStream('./'+fileName+'.txt')
        // file.on('error', function(err) { /* error handling */ })
        //    file.write(content)
        // file.end()
        throw new Error('You have to implement the saveData method!');        
      }
    readData(){
        throw new Error('You have to implement the readData method!');        
        // fs.readFile('./'+fileName+'.txt', 'utf8', function(err, data){
        //   if(data!== undefined && data !==[])
        //   callback(JSON.parse(data))
        // })
      }
  }


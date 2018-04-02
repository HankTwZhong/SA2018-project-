import fs from 'fs'
export default class FileOperater{
    saveData(hostList){
        let content = JSON.stringify(hostList)
        var file = fs.createWriteStream('./src/module/hostList.txt')
        file.on('error', function(err) { /* error handling */ })
           file.write(content)
        file.end()
      }
    readData(fileName,callback){
        fs.readFile('./'+fileName+'.txt', 'utf8', function(err, data){
          if(data!== undefined || data !==[])
          callback(JSON.parse(data))
        })
      }
  }


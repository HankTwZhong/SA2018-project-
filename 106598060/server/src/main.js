import Restful from './Controller/Restful'

let restful = new Restful

restful.startServer()
restful.addContact()
restful.addHost()
restful.deleteHost()
restful.getContact()
restful.getHostData()
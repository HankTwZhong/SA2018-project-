import assert from 'assert'
import ApplicationContext from '../src/UseCase/ApplicationContext'
import HostUseCase from '../src/UseCase/HostUseCase'
import FakeHostRepository from './utils/FakeHostRepository'
import HostInputDTO from '../src/UseCase/HostInputDTO'

describe.only('Add Host', function() {
    let applicationContext
    let hostUseCase
      before(()=>{
        return new Promise((resolve)=>{
            applicationContext = new ApplicationContext(()=>{
                hostUseCase = new HostUseCase(applicationContext)
                resolve()
            })
        })
      })
      it('Correctly add host', function() {

        let expectHost = {
          hostName:'testHost',
          ipAddress:'1.2.3.4',
          selected:'ping'
        }
        let hostListLengthBeforeAddHost = applicationContext.getAllHostList().length
        let hostInputDTO = new HostInputDTO(expectHost.hostName,expectHost.ipAddress,expectHost.selected)

        hostUseCase.addHost(hostInputDTO,FakeHostRepository)

        let hostListLengthAfterAddHost = applicationContext.getAllHostList().length
        let lastHost = applicationContext.getAllHostList()[hostListLengthAfterAddHost-1]

        assert.equal(hostListLengthBeforeAddHost+1,hostListLengthAfterAddHost)
        assert.equal(expectHost.hostName,lastHost.hostName)
        assert.equal(expectHost.ipAddress,lastHost.ipAddress)
        assert.equal(expectHost.selected,lastHost.selected)
        
      })
  })
import FacebookObserver from '../src/module/Observer/FacebookObserver'
import EmailObserver from '../src/module/Observer/EmailObserver'
import Host from '../src/module/Host'
import SkypeObserver from '../src/module/Observer/SkypeObserver';

let host = new Host('johnson','werw')
let facebookObserver
let emailObserver
describe.only('The subjectObserver should correctly use it own method by override root method', function() {
    describe('create a object  ', function() {
      it('should return -1 when the value is not present', function() {
            facebookObserver = new FacebookObserver
            let facebookObserver2  = new FacebookObserver
      })
      it('should',function(){
        let skpeObserver = new SkypeObserver
        emailObserver = new EmailObserver
        host.attach('johnson',facebookObserver)
        console.log(host.getObserverList())
        // host.attach('yamin',facebookObserver)
        host.attach('johnson',skpeObserver)
        host.attach('johnson',emailObserver)
        
        host.ObserverToTxt()
      })
    })
  })
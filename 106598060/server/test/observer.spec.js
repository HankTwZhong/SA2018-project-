import FacebookObserver from '../src/module/Observer/FacebookObserver'
import EmailObserver from '../src/module/Observer/EmailObserver'
import Host from '../src/module/Host'


let facebookObserver
let emailObserver
describe('The subjectObserver should correctly use it own method by override root method', function() {
    describe('create a object  ', function() {
      it('should return -1 when the value is not present', function() {
            facebookObserver = new FacebookObserver
            let facebookObserver2  = new FacebookObserver
      })
      it('should',function(){
        emailObserver = new EmailObserver
        Host.attach('johnson',facebookObserver)
        Host.attach('yamin',facebookObserver)
        // Host.attach('johnson',facebookObserver)
      })
    })
  })
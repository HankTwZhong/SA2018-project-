package ntut.sa2018.Domain.Host;

import ntut.sa2018.Domain.Contact.Contact;

public class HostBuilder {
    public static class newInstance{
        public String hostName;
        public String hostIP;
        public String checkMethod;
        public int checkInterval;
        public Contact contact;

        public newInstance name(String hostName){
            this.hostName = hostName;
            return(this);
        }

        public newInstance address(String hostIP){
            this.hostIP = hostIP;
            return(this);
        }

        public newInstance checkCommand(String checkMethod){
            this.checkMethod = checkMethod;
            return(this);
        }

        public newInstance checkInterval(int checkInterval){
            this.checkInterval = checkInterval;
            return(this);
        }

        public newInstance contact(Contact con){
            this.contact = con;
            return(this);
        }

        public Host build(){
            return new Host(this);
        }
    }
}

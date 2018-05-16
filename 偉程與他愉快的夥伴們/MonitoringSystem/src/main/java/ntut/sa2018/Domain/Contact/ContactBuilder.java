package ntut.sa2018.Domain.Contact;

public class ContactBuilder {
    public static class newInstance{
        public String contactName;
        public String email;
        public String lineaddress;
        public String facebookaddress;
        public String skypeaddress;
        public String address;
        public String address2;
        
        public newInstance contactName(String contactName){
            this.contactName = contactName;
            return(this);
        }

        public newInstance email(String email){
            this.email = email;
            return(this);
        }

        public newInstance line(String lineaddress){
            this.lineaddress = lineaddress;
            return(this);
        }

        public newInstance facebook(String facebookaddress){
            this.facebookaddress = facebookaddress;
            return(this);
        }

        public newInstance skype(String skypeaddress){
            this.skypeaddress = skypeaddress;
            return(this);
        }

        public newInstance otheraddress(String otheraddress){
            this.address = otheraddress;
            return(this);
        }

        public newInstance otheraddress2(String otheraddress2){
            this.address2 = otheraddress2;
            return(this);
        }

        public Contact build(){
            return new Contact(this);
        }
    }
}

package ntut.sa2018.Domain;

import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class ContactBuilderTest {
    @Test
    public void BuildContactTest() {
        Contact contact = new ContactBuilder.newInstance().
                    contactName("賴偉程").
                    email("test@ntut.edu.tw").
                    build();

        assertEquals("賴偉程", contact.getContactName());
        assertEquals("test@ntut.edu.tw", contact.getEmail());
        assertEquals(null, contact.getFacebookaddress());
        assertEquals(null, contact.getLineaddress());
        assertEquals(null, contact.getSkypeaddress());
        assertEquals(null, contact.getOtheraddress());
        assertEquals(null, contact.getOtheraddress2());
    }
}
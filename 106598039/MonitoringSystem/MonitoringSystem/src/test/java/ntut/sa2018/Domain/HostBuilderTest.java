package ntut.sa2018.Domain;

import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class HostBuilderTest {

    @Test
    public  void BuildHostTest(){
        Host host = new HostBuilder.newInstance().
                name("tom").
                address("184.122.21.3").
                checkCommand("Reachable").
                checkInterval(5).
                build();

        assertEquals("tom", host.getHostName());
        assertEquals("184.122.21.3", host.getHostIP());
        assertEquals("Reachable", host.getCheckMethod());
        assertEquals(5, host.getCheckInterval());
    }

    @Test
    public  void BuildHostWithoutAddressTest(){
        Host host = new HostBuilder.newInstance().
                name("tom").
                checkCommand("Reachable").
                checkInterval(5).
                build();

        assertEquals("tom", host.getHostName());
        assertEquals("Reachable", host.getCheckMethod());
        assertEquals(5, host.getCheckInterval());
    }

    /*
    @Test
    public void BuildHostExistContactTest() {
        Host host = new HostBuilder.newInstance().
                name("tom").
                address("184.122.21.3").
                checkCommand("Reachable").
                checkInterval(5).
                contact(
                        new ContactBuilder.newInstance().
                                contactName("賴偉程").
                                email("test@ntut.edu.tw").
                                build()
                ).
                build();

        assertEquals("tom", host.getHostName());
        assertEquals("184.122.21.3", host.getHostIP());
        assertEquals("Reachable", host.getCheckMethod());
        assertEquals(5, host.getCheckInterval());

        assertEquals("賴偉程", host.getContact().getContactName());
        assertEquals("test@ntut.edu.tw", host.getContact().getEmail());
        assertEquals(null, host.getContact().getFacebookaddress());
        assertEquals(null, host.getContact().getLineaddress());
        assertEquals(null, host.getContact().getSkypeaddress());
        assertEquals(null, host.getContact().getOtheraddress());
        assertEquals(null, host.getContact().getOtheraddress2());
    }*/

}
package ntut.sa2018.Domain;

import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by gunch on 2018/5/17.
 */
public class Notify {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    /*
    @Test
    public void NotifyTest() {
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

        host.setHostStatus("ERROR");
        assertEquals("Email = test@ntut.edu.tw hostName = tom, hostIP = 184.122.21.3 the host has change status",
                outContent.toString());
        host.setHostStatus("OK");
        assertEquals("Email = test@ntut.edu.tw hostName = tom, hostIP = 184.122.21.3 the host has change status",
                outContent.toString());
    }

    @Test
    public void Notify_Contact_Has_Line_Test() {
        Host host = new HostBuilder.newInstance().
                name("tom").
                address("184.122.21.3").
                checkCommand("Reachable").
                checkInterval(5).
                contact(
                        new ContactBuilder.newInstance().
                                contactName("賴偉程").
                                email("test@ntut.edu.tw").
                                line("kooko").
                                build()
                ).
                build();

        host.setHostStatus("ERROR");
        assertEquals("Email = test@ntut.edu.tw hostName = tom, hostIP = 184.122.21.3 the host has change status\n" +
                        "Line address = kooko hostName = tom, hostIP = 184.122.21.3 the host has change status\n",
                outContent.toString());
    }*/
}
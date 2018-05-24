package ntut.sa2018.UseCase;

import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Domain.Contact.ContactBuilder;
import ntut.sa2018.Domain.Host.Host;
import ntut.sa2018.Domain.Host.HostBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by gunch on 2018/5/24.
 */
public class GetHostTest {
    @Test
    public void MonitoringUseCaseTestOneHost() throws InterruptedException {
        ArrayList<Host> HostList;
        ArrayList<Contact> contactArrayList = new ArrayList<Contact>();
        Contact contact = new ContactBuilder.newInstance().
                contactName("賴偉程").
                email("test@ntut.edu.tw").
                build();

        contactArrayList.add(contact);

        Host hostA = new HostBuilder.newInstance().
                name("tom").
                address("127.0.0.1").
                checkCommand("Reachable").
                checkInterval(5).
                contact(contactArrayList).
                build();

        HostManagementUseCase.Use().AddHost(hostA);
        HostList = HostManagementUseCase.Use().GetHostList();

        assertEquals(1, HostList.size());
        assertEquals("127.0.0.1", HostList.get(0).getHostIP());
        HostManagementUseCase.Use().DeleteHost(HostList.get(0).getHostIP());
    }

    @Test
    public void MonitoringUseCaseTestTwoHost() throws InterruptedException {
        ArrayList<Host> HostList;
        ArrayList<Contact> contactArrayList = new ArrayList<Contact>();
        Contact contact = new ContactBuilder.newInstance().
                contactName("賴偉程").
                email("test@ntut.edu.tw").
                build();

        contactArrayList.add(contact);

        Host hostA = new HostBuilder.newInstance().
                name("tom").
                address("127.0.0.1").
                checkCommand("Reachable").
                checkInterval(5).
                contact(contactArrayList).
                build();

        HostManagementUseCase.Use().AddHost(hostA);

        Host hostB = new HostBuilder.newInstance().
                name("jay").
                address("192.168.1.2").
                contact(contactArrayList).
                checkCommand("Reachable").
                checkInterval(10).
                build();
        HostManagementUseCase.Use().AddHost(hostB);
        HostList = HostManagementUseCase.Use().GetHostList();
        assertEquals(2, HostList.size());
        assertEquals("127.0.0.1", HostList.get(0).getHostIP());
        assertEquals("192.168.1.2", HostList.get(1).getHostIP());

        HostManagementUseCase.Use().DeleteHost(HostList.get(0).getHostIP());
        HostManagementUseCase.Use().DeleteHost(HostList.get(1).getHostIP());
    }

}

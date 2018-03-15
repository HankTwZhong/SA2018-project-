using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Net;
using System.Net.NetworkInformation;
using System.Net.Sockets;

using System.Collections;

namespace Model
{
    class MonitorSystem
    {
        private ArrayList _hostCollection = new ArrayList();

        public MonitorSystem()
        { 
            initHostData();
        }

        public void initHostData()
        {
            addHost(new Host("www.youtubehyrtd.com"));
            addHost(new Host("www.google.com"));
            addHost(new Host("www.facebook.com"));
        }

        private bool isInternetConnected()
        {
            Ping p = new Ping();
            PingReply reply;

            try
            {
                IPAddress hostIP = Dns.GetHostAddresses("www.google.com")[0];
                reply = p.Send(hostIP);
                if (reply.Status == IPStatus.Success)
                    return true;
                else
                    return false;
            }
            catch
            {
                return false;
            }
        }

        private void setMonitorHostsStatusDown()
        {
            foreach (Host host in _hostCollection)
                host.setStatus(false);
        }

        public void pingAll()
        {
            if (isInternetConnected())
                foreach (Host host in _hostCollection)
                    ping(host);
            else
                setMonitorHostsStatusDown();
        }

        private void ping(Host host)
        {
            Ping p = new Ping();
            PingReply reply;

            try
            {
                IPAddress hostIP = Dns.GetHostAddresses(host.name())[0];
                reply = p.Send(hostIP);
                if (reply.Status == IPStatus.Success)
                {
                    //host.setName(Dns.GetHostEntry(hostIP).HostName);
                    host.setIp(hostIP.ToString());
                    host.setStatus(true);
                    addHost(host);
                }
                else
                {
                    host.setStatus(false);
                    addHost(host);
                }
            }
            catch
            {
                host.setStatus(false);
                addHost(host);
            }
        }

        public ArrayList currentHosts()
        {
            return _hostCollection;
        }

        public bool addHost(Host host)
        {
            if (!isHostAlreadyInMonitoring(host))
            {
                _hostCollection.Add(host);
                return true;
            }
            return false;
        }

        public void removeHost(Host host)
        {
            if (isHostAlreadyInMonitoring(host))
            {
                host = getHostFromHostCollection(host);
                _hostCollection.Remove(host);
            }
        }

        public Host getHostFromHostCollection(Host host)
        {
            foreach (Host hostFromHostCollection in _hostCollection)
            {
                if (host.name().Equals(hostFromHostCollection.name()))
                    return hostFromHostCollection;
            }
            return null;
        }

        public bool isHostAlreadyInMonitoring(Host host)
        {
            foreach(Host hostFromHostCollection in _hostCollection)
            {
                if(host.name().Equals(hostFromHostCollection.name()))
                    return true;
                else if(host.name().Equals(hostFromHostCollection.ip()))
                    return true;
            }
            return false;
        }
    }
}

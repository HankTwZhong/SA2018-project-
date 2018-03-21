using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.NetworkInformation;
using System.Text;
using System.Threading.Tasks;

namespace pingHost_Project
{
    class ConnectHostWithPing
    {
        private string[] _hostList = new string[0] ;
        private string[] _ipList = new string [0];
        private int _currentIndex = 0;
        public void initIndex()
        {
            _currentIndex = 0;
        }
        public void next()
        {
            _currentIndex++;
        }
        public int getIpListLength()
        {
            return _ipList.Length;
        }
        public string makeHostList()
        {
            return string.Format("{0}\t\t{1}\t\t", _hostList[_currentIndex], _ipList[_currentIndex]);
        }
        public string connectInfo()
        {
            return string.Format("{0}\t\t{1}\t\t{2}", _hostList[_currentIndex], _ipList[_currentIndex], pingStatus());
        }
        public string getIp()
        {
            return _ipList[_currentIndex];

        }
        public string getHostName()
        {
            return _hostList[_currentIndex];
        }
        public void inesertHostAndIp(string hostName, string ipAddress)
        {
            Array.Resize(ref _hostList, _hostList.Length + 1);
            _hostList[_hostList.Length - 1] = hostName;
            Array.Resize(ref _ipList, _ipList.Length + 1);
            _ipList[_ipList.Length - 1] = ipAddress;
        }
        public bool pingStatus()
        {
            try
            {
                IPAddress tIP = IPAddress.Parse(_ipList[_currentIndex]);
                Ping tPingControl = new Ping();
                PingReply tReply = tPingControl.Send(tIP, 2);
                tPingControl.Dispose();
                if (tReply.Status != IPStatus.Success)
                    return false;
                else
                    return true;
            }
            catch 
            {
                return false;
            }
        }

        public void RemoveByIp(string rmIP)
        {
            ArrayList alToIp = new ArrayList(_ipList);
            ArrayList alToHost = new ArrayList(_hostList);
            int rmIndex = alToIp.IndexOf(rmIP);
            alToIp.RemoveAt(rmIndex);
            string rmHost = alToHost[rmIndex].ToString();
            alToHost.RemoveAt(rmIndex);
            string removeInfo = string.Format("刪除項目\nHost:\t{0}\tIP{1}\t為第{2}項目", rmHost, rmIP, rmIndex + 1);
            _ipList = (string[])alToIp.ToArray(typeof(string));
            _hostList = (string[])alToHost.ToArray(typeof(string));
            Console.WriteLine(removeInfo);
        }
    }
}

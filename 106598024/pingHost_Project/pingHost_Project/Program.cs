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
    class Program
    {
        static void Main(string[] args)
        {
            string header = string.Format("{0}\t\t{1}\t\t\t{2}","Host名稱","Ip位址","狀態");
            string Column ="" ;
            Console.WriteLine(header);
            ConnectHostWithPing Conn = new ConnectHostWithPing();
            for (int i = 0; i <= Conn.getIpListLength() - 1; i++)
            {
                Column += Conn.connectInfo() + "\n";
                Conn.next();
            }
            Console.WriteLine(Column);            
            string host = "";
            string ip = "";
            string command ="" ;
            while (true)
            {
                Column = "";
                Conn.init();
                Console.WriteLine("\n Add Or Remove Or Ping \n");
                command = Console.ReadLine();
                if(command == "Add")
                {
                    Console.Write("Host:\t");
                    host = Console.ReadLine();
                    Console.Write("IP:\t");
                    ip = Console.ReadLine();
                    Conn.inesertHostAndIp(host, ip);
                }
                else if (command == "Remove")
                {
                    Console.WriteLine("輸入欲刪除的IP:");
                    string removeIp = Console.ReadLine();
                    Conn.RemoveByIp(removeIp);
                }
                else if (command == "Ping")
                {
                    for (int i = 0; i <= Conn.getIpListLength() - 1; i++)
                    {
                        Column += Conn.connectInfo() + "\n";
                        Conn.next();
                    }
                    Console.WriteLine(Column);
                }                
            }

        }

    }
    class ConnectHostWithPing
    {
        private string[] _hostList = new string[] { "google", "NTUT" };
        private string[] _ipList = new string[] { "216.58.200.36", "140.124.181.101"};
        private int _currentIndex =0;
        public void init() 
        {
            _currentIndex = 0;
        }
        public void next ()
        {
            _currentIndex++;
        }
        public int getIpListLength()
        {
            return _ipList.Length;
        }
        public string connectInfo()
        {
            return string.Format("{0}\t\t{1}\t\t\t{2}", _hostList[_currentIndex], _ipList[_currentIndex], pingStatus());
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
            _hostList[_hostList.Length-1] = hostName;
            Array.Resize(ref _ipList, _ipList.Length + 1);
            _ipList[_ipList.Length-1] = ipAddress;
        }
        public  bool pingStatus()
        {
            IPAddress tIP = IPAddress.Parse(_ipList[_currentIndex]);
            Ping tPingControl = new Ping();
            PingReply tReply = tPingControl.Send(tIP);
            tPingControl.Dispose();
            if (tReply.Status != IPStatus.Success)
                return false;
            else
                return true;
        }

        public void RemoveByIp(string rmIP)
        {
            ArrayList alToIp = new ArrayList(_ipList);
            ArrayList alToHost  = new ArrayList(_hostList);
            int rmIndex = alToIp.IndexOf(rmIP);
            alToIp.RemoveAt(rmIndex);
            string rmHost = alToHost[rmIndex].ToString();
            alToHost.RemoveAt(rmIndex);
            string removeInfo = string.Format("刪除項目\nHost:\t{0}\tIP{1}\t為第{2}項目",rmHost, rmIP, rmIndex+1);
            Console.WriteLine(removeInfo);
        }
    }
}

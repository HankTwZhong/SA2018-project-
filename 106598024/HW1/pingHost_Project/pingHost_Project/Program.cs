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
            FileOperation file = new FileOperation();
            ConnectHostWithPing Conn = file.generateHostList();
            string header = string.Format("{0}\t{1}\t\t\t{2}", "Host名稱", "IPv4位址", "狀態");
            string Column = ""; string host = "";  string ip = "";  string command = "";

            Console.WriteLine(header);
            for (int i = 0; i <= Conn.getIpListLength() - 1; i++)
            {
                Column += Conn.connectInfo() + "\n";
                Conn.next();
            }
            Console.WriteLine(Column);

            while (true)
            {
                Column = "";
                Conn.initIndex();
                Console.WriteLine("\n Add Or Remove Or Ping \n");
                command = Console.ReadLine();
                if (command == "Add")
                {
                    Console.Write("Host:\t"); host = Console.ReadLine();
                    Console.Write("IP:\t");   ip = Console.ReadLine();
                    Conn.inesertHostAndIp(host, ip);
                    FileOperation csv = new FileOperation();
                    csv.saveFile(Conn);
                }
                else if (command == "Remove")
                {
                    Console.Write("輸入欲刪除的IP:");
                    string removeIp = Console.ReadLine();
                    Conn.RemoveByIp(removeIp);
                    FileOperation csv = new FileOperation();
                    csv.saveFile(Conn);
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
}

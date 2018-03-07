using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pingHost_Project
{
    class FileOperation
    {
        string appath = System.IO.Directory.GetCurrentDirectory() + @"\..\..\hostList.txt";
        public void saveFile(ConnectHostWithPing data)
        {
            string hostList = "";
            for (int i = 0; i <= data.getIpListLength() - 1; i++)
            {
                hostList += data.makeHostList() + "\n";
                data.next();
            }
            try
            {
                FileStream FileStream = new FileStream(appath, FileMode.Create);
                StreamWriter sw = new StreamWriter(FileStream, System.Text.Encoding.UTF8);
                sw.WriteLine(hostList);
                sw.Flush();
                sw.Close();
                FileStream.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        public string fetchFileContent()
        {           
            string currentFileLine = "";
            string totalLine ="";
            StreamReader sr = new StreamReader(appath);
            ConnectHostWithPing Conn = new ConnectHostWithPing();
            
            while ((currentFileLine = sr.ReadLine()) != null)
            {
                totalLine += currentFileLine;
            }
            sr.Close();
            return totalLine;
        }
        public  ConnectHostWithPing generateHostList()
        {
            ConnectHostWithPing conn = new ConnectHostWithPing();
            conn.initIndex();
            string fileContent = fetchFileContent();
            string[] HostAndIPGroup = fileContent.Split(new string[] { "\t\t" }, StringSplitOptions.RemoveEmptyEntries);
            for (int i = 0; i <= HostAndIPGroup.Length - 1; i += 2)
            {
                conn.inesertHostAndIp(HostAndIPGroup[i], HostAndIPGroup[i + 1]);
            }
            return conn;
        }
    }
}

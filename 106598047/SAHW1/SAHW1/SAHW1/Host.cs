using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model
{
    public class Host
    {
        private string _name;
        private string _ip;
        private bool _status;

        public Host(string name = "")
        {
            _name = name;
            _ip = "";
            _status = false;
        }

        public String name()
        {
            return _name;
        }

        public void setName(String name)
        {
            _name = name;
        }

        public String ip()
        {
            return _ip;
        }

        public void setIp(string ip)
        {
            _ip = ip;
        }

        public String status()
        {
            return _status ? "Up" : "Down";
        }

        public void setStatus(bool status)
        {
            _status = status;
        }
    }
}

using System;
using System.Net.NetworkInformation;
using System.Windows.Forms;

namespace Host
{
    public partial class FormMonitor : Form
    {
        public FormMonitor()
        {
            InitializeComponent();
            dataGridViewTable.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
            for(int i=0; i<3; i++)
            {
                this.dataGridViewTable.Rows.Add();
            }
            labelTime.Text = "檢測中...請稍後!";
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            checkHost("http://140.124.181.108/ezScrum", 0);
            checkHost("https://www.google.com.tw/", 1);
            checkHost("http://www.ntut.edu.tw/bin/home.php", 2);
            checkHost("http://facebook.com", 3);
            labelTime.Text = "檢測時間:" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
        }

        private void checkHost(String address, int r)
        {
            Ping ping = new Ping();
            Uri uri = null;
            try
            {
                uri = new Uri(address);
            }
            catch (UriFormatException e)
            {
                this.dataGridViewTable.Rows[r].Cells[0].Value = address;
                this.dataGridViewTable.Rows[r].Cells[1].Value = e.Message;
                this.dataGridViewTable.Rows[r].Cells[2].Value = "Down";
                return;
            }
            string host = uri.Host;
            PingReply reply = ping.Send(host);
            this.dataGridViewTable.Rows[r].Cells[0].Value = host;
            if (reply.Status == IPStatus.Success)
            {
                string ipAddress = reply.Address.ToString();
                this.dataGridViewTable.Rows[r].Cells[1].Value = ipAddress;
                this.dataGridViewTable.Rows[r].Cells[2].Value = "Up";
            }
            else
            {
                this.dataGridViewTable.Rows[r].Cells[1].Value = "Null";
                this.dataGridViewTable.Rows[r].Cells[2].Value = "Down";
            }
        }
    }
}

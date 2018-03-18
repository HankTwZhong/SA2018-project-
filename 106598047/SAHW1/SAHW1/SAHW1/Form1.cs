using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

using System.Net;
using System.Net.NetworkInformation;
using System.Net.Sockets;

using System.Collections;

using Model;

namespace SAHW1
{
    public partial class Form1 : Form
    {
        private DataGridViewRowCollection rows;
        private MonitorSystem monitorSystem = new MonitorSystem();

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            rows = dataGridView1.Rows;
            label1.Text = DateTime.Now.ToString();

            refreshDataGridView();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            label1.Text = DateTime.Now.ToString();
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            refreshMonitorSystem();
        }

        private void refreshMonitorSystem()
        {
            monitorSystem.pingAll();
            refreshDataGridView();
        }

        private void refreshDataGridView()
        {
            rows.Clear();
            int rowIndex = 0;
            ArrayList hostCollection = monitorSystem.currentHosts();
            foreach (Host host in hostCollection)
            {
                rows.Add(new Object[] { host.name(), host.ip(), host.status() });
                Color statusColor = host.status() == "Up" ? Color.Green : Color.Red;
                dataGridView1.Rows[rowIndex].Cells[2].Style.BackColor = statusColor;
                rowIndex++;
            }
        }

        private void buttonNewHost_Click(object sender, EventArgs e)
        {
            try
            {
                string hostName = textBoxNewHost.Text.ToString();
                if (string.IsNullOrEmpty(hostName))
                    throw new Exception("New Host欄位不可為空!");
                else
                {
                    bool addHostSuccess = monitorSystem.addHost(new Host(hostName));
                    if (addHostSuccess)
                        refreshDataGridView();
                    else
                        throw new Exception("Host已存在!");
                }
            }
            catch(Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            int rowIndex = dataGridView1.CurrentCell.RowIndex;
            if(rows[rowIndex].Cells[3].Selected)
            {
                string hostName = dataGridView1.Rows[e.RowIndex].Cells[0].Value.ToString();
                monitorSystem.removeHost(new Host(hostName));
                dataGridView1.Rows.RemoveAt(rowIndex);
            }
        }
    }
}

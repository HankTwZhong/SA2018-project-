<template>
  <div class="row">
    <div class="col">
      Host name: <input type="text" v-model="host"><br>
      <br>
      IP address: <input type="text" v-model="ip"><br>
      <br>
      <button type="button" @click="addHost()" :disabled="host===''||ip===''">Add</button>
    </div>
    <div class="col">
      <br>
      <table>
        <tr>
          <th>Host Name</th>
          <th>IP Address</th>
          <th>Status</th>
        </tr>
        <tr v-for="(item, index) in hostPool" v-bind:key="index">
          <td>{{item.host}}</td>
          <td>{{item.ip}}</td>
          <td>{{item.status}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
const serverAPI = require('../main/serverAPI')
let template
export default {
  name: 'MainPage',
  data () {
    return {
      hostPool: [],
      host: '',
      ip: ''
    }
  },
  methods: {
    addHost: function () {
      let checkHostName = template.hostPool
        .map(function (e) {
          return e.host
        })
        .indexOf(template.host)
      let checkIpAddress = template.hostPool
        .map(function (e) {
          return e.ip
        })
        .indexOf(template.ip)
      if (checkHostName > -1 && checkIpAddress > -1) {
        alert('Duplicate Host Name and IP Address.')
      } else {
        if (checkIpAddress > -1) {
          alert('Duplicate IP Address.')
        } else if (checkHostName > -1) {
          alert('Duplicate Host Name.')
        } else {
          template.hostPool.push({
            host: template.host,
            ip: template.ip,
            status: undefined
          })
          template.host = undefined
          template.ip = undefined
        }
      }
    },
    updateStatus: function () {
      template.hostPool.forEach(function (eachHost) {
        serverAPI
          .getStatus(eachHost.ip)
          .then(result => {
            eachHost.status = result.data
          })
          .catch(err => {
            console.log(`[err] ${err}`)
          })
      })
    },
    setInterval: function () {
      setInterval(() => {
        template.updateStatus()
      }, 1000)
    }
  },
  mounted () {
    template = this
    template.setInterval()
  }
}
</script>

<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}
td,
th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}
tr:nth-child(even) {
  background-color: #dddddd;
}
</style>

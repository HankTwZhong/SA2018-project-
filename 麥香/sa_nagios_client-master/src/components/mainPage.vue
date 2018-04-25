<template>
  <div class="row">
    <div class="col">
      <br>
      <table>
        <tr>
          <th>Host Name</th>
          <th>IP Address</th>
          <th>Status</th>
          <th>Check Type</th>
          <th>Last Check Time</th>
          <th>Change Check Type</th>
          <th>Contacts</th>
        </tr>
        <tr v-for="(item, index) in hostPool" v-bind:key="index">
          <td>{{item.name}}</td>
          <td>{{item.ip}}</td>
          <td>{{item.status}}</td>
          <td>{{item.command}}</td>
          <td><span>{{ new Date() | moment("h:mm:ss a") }}</span></td>
          <td>
            <select v-model="selected" de>
              <option id="namp">namp</option>
              <option id="ping">ping</option>
              
            </select>
          </td>
          <td>
            <button type="button" @click="showContacts(index)">Contacts</button>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
const serverAPI = require("../serverAPI");
let template;
export default {
  name: "mainPage",
  data() {
    return {
      hostPool: [],
      contactsPool: [],
      selected: null
    };
  },
  methods: {
    getHost: function() {
      serverAPI
        .getStatus()
        .then(result => {
          template.hostPool = result.data;
        })
        .catch(err => {
          // template.hostPool = template.hostPool.map(
          //   each => (each.status = "DOWN")
          // );
          console.log(err);
        });
    },
    changeCheckMethod: function(index, command) {
      if (command === "nmap") {
        var targetMethod = "ping";
        serverAPI.changeCommand(template.hostPool[index].id, targetMethod);
      } else {
        targetMethod = "nmap";
        serverAPI.changeCommand(template.hostPool[index].id, targetMethod);
      }
    },
    showContacts: function(index) {
      template.hostPool[index].contacts.map(each =>
        alert(
          "Name: " +
            each.name +
            "\n" +
            "Email: " +
            each.address.Email +
            "\n" +
            "Line: " +
            each.address.Line +
            "\n" +
            "FB: " +
            each.address.FB +
            "\n" +
            "Phone: " +
            each.address.Phone
        )
      );
    },
    setInterval: function() {
      template.getHost();
      setInterval(() => {
        template.getHost();
      }, 3000);
    }
  },
  mounted() {
    template = this;
    template.setInterval();
  }
};
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


<template>
<div>
    <!-- <contact-top-bar></contact-top-bar> -->
    <ul class="nav navbar-nav navbar-left">
       <a class="navbar-brand">{{this.hostName}}</a>&nbsp;&nbsp;&nbsp;&nbsp;
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <add-contact :hostName="this.hostName" v-on:sendContact="sendContact()"></add-contact>
            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">
            <span class="glyphicon glyphicon-user"></span> 新增聯絡人</button>
            <button class="btn btn-primary btn-sm" @click="backList()">
            <span class="glyphicon glyphicon-log-out"></span> HostList</button>
    </ul>
    <div class="row">      
      <div class="col-md-12">
           <contact-table ref="ContactTable" :contactUrl="this.contactUrl" :hostName="this.hostName" :hostIP="this.hostIP"></contact-table>
      </div>
    </div>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
import ContactTable from './ContactTable'
import AddContact from './AddContact'
import swal from 'sweetalert2'

export default {
  data(){
      return{
          contactName:undefined,
          email:undefined,
          email2:undefined,
          lineID:undefined,
          facebook:undefined,
          skype:undefined
      }
  },
  components: {
      AddContact,
    ContactTable
  },
  computed: {
    ...mapGetters({contactUrl: 'getContactUrl', hostIP: 'getIP',hostName:'getHostName'})
  },
  mounted(){
      console.log('weijfw')
  },
  methods:{
      sendContact(){
          this.$refs.ContactTable.refresh()
      },
      backList(){
          this.$router.push({
                path: '/nagios-hw3'
          })
      }
  }
}
</script>
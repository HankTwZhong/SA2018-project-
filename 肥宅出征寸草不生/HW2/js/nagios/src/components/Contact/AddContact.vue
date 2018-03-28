<template>
  <div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">新增聯絡人</h4>
        </div>
        <div class="modal-body">
          <form role="form">
            <div class="form-group">
              <label for="contactName">姓名</label>
              <input class="form-control" id="contactName" placeholder="輸入姓名" v-model="contactName" />
            </div>
            <div class="pull-left">
              <label for="contactName">選擇通知方式</label>
              <select v-model="selected">
                <option>Facebook</option>
                <option>Telephone</option>
                <option>Email</option>
                <option>Skype</option>
                <option>LineID</option>              
              </select>
              <input  class="input-sm" id="information" placeholder="輸入帳號" v-model="information"><button type="button" class="btn btn-default" @click="addAddressData"><span class="glyphicon glyphicon-plus"></span></button>
            </div>
            <div>
              <div class="col-xs-12" v-for="(data, index) in contactData" v-bind:key="data.id" v-bind:type="data.type" v-bind:address="data.address">  <div  class ="form-group pull-left" style="margin-top: 10px;margin-bottom:0px;" >
                {{data.type}} :{{ data.address }}
                <button type="button" class="btn btn-default" v-on:click="contactData.splice(index, 1)"><span class="glyphicon glyphicon-minus"></span></button>
                </div>
                </div>
            </div>
          </form>
          <br>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-default" @click="addContact()" data-dismiss="modal">確認</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const axios= require('axios')
  export default {
    data() {
      return {
        contactData: [],
        newId: 1,
        contactName: undefined,
        selected: undefined,
        information: undefined
      }
    },
    props: ['hostName'],
    methods: {
      addContact:function() {
        axios.post('http://localhost:3000/addContact', {
            hostName:this.hostName,
            contactName: this.contactName,
            communicate: this.contactData
          })
          .then((res) => {
            console.log(res)
            this.$refs.vuetable.refresh()
            this.contactData =[]
            this.contactName =[]
            this.newId = 1
            this.selected = ''
            this.information = ''
          }).catch((err) => {
            console.log(err)
          })
      },
      addAddressData: function() {
        var data={
          id: this.newId++,
          type: this.selected,
          address: this.information
        }
        this.contactData.push(data)
        data={}
        this.selected = ''
        this.information = ''
      }
    }
  }
</script>

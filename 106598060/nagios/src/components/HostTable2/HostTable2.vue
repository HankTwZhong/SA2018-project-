<template>
  <div>
    <div class="row">
      <div class="col-sm-3" style="padding-left:0px;">
        <label>頁面刷新速度{{intervalTime/1000}}秒</label>
        <div class="input-group">
          <input class="form-control width100" v-model="intervalTime">
          <span class="input-group-btn">
          <button class="btn btn-success" v-if="autofresh === '自動刷新'" @click="startInterval()">{{autofresh}}</button>
          <button :class="refreshBtnClass" v-else @click="stopInterval()">{{autofresh}}</button>
          </span>
        </div>
      </div>
      <form @submit.prevent="addHost()" method="post">
        <div class="col-sm-3" style="padding-left:0px;">
          <label for="newHostName">HostName</label>
          <input type="text" class="form-control" id="newHostName" v-model="newHostName">
        </div>
        <div class="col-sm-3" style="padding-left:0px;">
          <label for="ipAddress">IP or Domain Name</label>
          <input id="ipAddress" class="form-control" v-model="ipAddress">
        </div>
        <label>&nbsp;</label>
        <span class="col-xs-3" style="padding-right:0px;">
            <button type="submit" class="btn btn-success pull-left"  :disabled="buttonDisable">增加Host&nbsp;</button>
            <button class="btn btn-info pull-right" type="button" @click="refreshed()" :disabled="buttonDisable">重新整理</button>
          </span>
      </form>
    </div>
    <br>
    <div class="row">
      <vuetable ref="vuetable" pagination-path="" :css="css.table" :sort-order="sortOrder" @vuetable:pagination-data="onPaginationData" @vuetable:loading="onLoading" @vuetable:loaded="onLoaded" api-url="http://localhost:3000/todo" :fields="fields">
        <template slot="actions" slot-scope="props">
                        <button class="btn btn-danger btn-sm " :disabled="buttonDisable" @click="deleteHost(props.rowData)">
                          <span class="glyphicon glyphicon-trash"></span>刪除主機</button>&nbsp;&nbsp;
</template>
      </vuetable>
      <vuetable-pagination ref="pagination" :css="css.pagination" @vuetable-pagination:change-page="onChangePage"></vuetable-pagination>
    </div>
  </div>
</template>

<script>
  const axios = require('axios')
  export default {
    components: {},
    data() {
      return {
        newHostName: undefined,
        ipAddress: undefined,
        buttonDisable: false,
        btnDisableTime: 200,
        setIntervalId: undefined,
        intervalTime: 5000,
        autofresh:'自動刷新',
        refreshBtnClass:'btn btn-success',
        css: {
          table: {
            tableClass: 'ui blue selectable celled stackable attached table',
            loadingClass: 'loading',
            handleIcon: 'glyphicon glyphicon-menu-hamburger',
          },
          pagination: {
            infoClass: 'pull-left',
            wrapperClass: 'vuetable-pagination pull-right',
            activeClass: 'btn-primary',
            disabledClass: 'disabled',
            pageClass: 'btn btn-border',
            linkClass: 'btn btn-border',
            icons: {
              first: '',
              prev: '',
              next: '',
              last: ''
            }
          }
        },
        sortOrder: [{
          sortField: 'hostName',
          field: 'hostName',
          direction: 'des'
        }],
        fields: [{
            name: 'hostName',
            title: '<span class="orange glyphicon glyphicon-book"></span> 主機名稱',
            titleClass: 'center aligned',
            dataClass: 'center aligned'
          },
          {
            name: 'ipAddress',
            title: 'IP',
            titleClass: 'center aligned',
            dataClass: 'center aligned'
          },
          {
            name: 'active',
            title: '狀態',
            titleClass: 'center aligned',
            dataClass: 'center aligned',
          },
          {
            name: 'date',
            title: '檢查時間',
            titleClass: 'center aligned',
            dataClass: 'center aligned'
          },
          '__slot:actions'
        ],
      }
    },
    mounted() {},
    methods: {
      startInterval() {
        let scope = this
        this.autofresh = '停止自動刷新'
        this.refreshBtnClass ='btn btn-danger'
        this.setIntervalId = setInterval(function() {
            scope.refreshed()
        }, this.intervalTime)
      },
      stopInterval() {
        this.autofresh = '自動刷新'
        this.refreshBtnClass ='btn btn-success'
        clearInterval(this.setIntervalId)
      },
      onPaginationData(paginationData) {
        this.$refs.pagination.setPaginationData(paginationData)
      },
      onChangePage(page) {
        this.$refs.vuetable.changePage(page)
      },
      onLoading() {
        console.log('loading... show your spinner here')
      },
      onLoaded() {
        console.log('loaded! .. hide your spinner here')
      },
      addHost() {
        this.buttonDisable = true
        axios.post('http://localhost:3000/addHost', {
            newHost: this.newHostName,
            ipAddress: this.ipAddress
          })
          .then((res) => {
            console.log(res)
            var scope = this
            setTimeout(function() {
              console.log('after ' + scope.btnDisableTime / 1000 + ' sec buttom will enable')
              scope.refreshed()
              scope.buttonDisable = false
            }, scope.btnDisableTime)
            this.initForm()
          }).catch((err) => {
            console.log(err)
          })
      },
      initForm() {
        this.newHostName = undefined
        this.ipAddress = undefined
      },
      refreshed() {
        this.$refs.vuetable.refresh()
      },
      deleteHost(hostData) {
        this.buttonDisable = true
        axios.post('http://localhost:3000/deleteHost', {
            hostName: hostData.hostName
          })
          .then((res) => {
            var scope = this
            setTimeout(function() {
              scope.buttonDisable = false
              scope.refreshed()
            }, scope.btnDisableTime)
          })
      }
    }
  }
</script>


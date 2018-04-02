const state = {
    hostName: undefined,
    ip:undefined,
    contactUrl: undefined
  }
  
  const mutations = {
    CONTACT (state, hostData) { 
      state.contactUrl = 'http://localhost:3000/getContact?hostName='+hostData.hostName
      state.ip = hostData.ipAddress
      state.hostName = hostData.hostName
    }
  }
  
  const actions = {
    actionContact ({ commit }, hostData) {
      commit('CONTACT', hostData)
    }
  }
  
  const getters = {
    getIP: state => state.ip,
    getHostName: state => state.hostName,
    getContactUrl: state => state.contactUrl
  }
  
  export default {
    state,
    mutations,
    actions,
    getters
  }
export default class HostViewModel{
    constructor(responseList){
        this.responseList = responseList
    }
    hostDataForVueTable(req){
      if (this.responseList === undefined)
      this.responseList = [] 
      let page = req.query.page
      let per_page = req.query.per_page
      let current_page = 1
      let last_page = 1
      let prev_page_url = null
      let domain = "http://localhost:3000/getHostsData"
      let vuetableFormat = {}
      if(page){
        current_page = page * 1
      }
        if(this.responseList.length % 10 === 0 && this.responseList.length !== 0){
          last_page = this.responseList.length / 10
        }
        else{
            last_page = Math.round(this.responseList.length / 10) + 1
        }                               
        if(current_page > 1){
            prev_page_url = domain + '&sort=&page=' + (current_page - 1) +'&per_page=' + per_page
        }             
        vuetableFormat.total = this.responseList.length
        vuetableFormat.per_page = per_page
        vuetableFormat.current_page = current_page
        vuetableFormat.last_page = last_page
        vuetableFormat.next_page_url = domain + '&sort=&page=' + (current_page + 1) +'&per_page=' + per_page
        vuetableFormat.prev_page_url = prev_page_url
        vuetableFormat.from = 1 + 10 * (current_page - 1)
        vuetableFormat.to = 10 * current_page
        vuetableFormat.data = this.responseList.slice(vuetableFormat.from - 1 , vuetableFormat.to)
    
        return vuetableFormat
    }
}
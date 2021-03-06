var Host = require('../../entity/Host');

class GetHostsUseCase {
    constructor(hostRepository) {
        this._hostRepository = hostRepository;
    }

    async execute() {
        let foundHostsFromDB = await this._hostRepository.getHosts();
        let hostsInstancesList = [];
        let hostsObjectList = []

        foundHostsFromDB.forEach(host => {
            hostsInstancesList.push(new Host(host._id, host.displayName, host.host, host.status, host.statusStartTime, host.lastCheckTime, host.checkServiceOption));
        });

        hostsInstancesList.forEach(host => {
            hostsObjectList.push({"id": host._id, "displayName": host._displayName, "host": host._host, "status": host._status, "statusStartTime": host._statusStartTime, "lastCheckTime": host._lastCheckTime, "checkServiceOption": host._checkServiceOption});
        });

        return hostsObjectList;
    }
}

module.exports = GetHostsUseCase;
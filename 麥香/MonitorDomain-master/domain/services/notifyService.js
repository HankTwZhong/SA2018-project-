var send = {};

send['Email'] = (Email, message) => {
    console.log(`Send Email ${Email} ->`, message);
};

send['Line'] = (Line, message) => {
    console.log(`Send Line ${Line} ->`, message);
};

send['FB'] = (FB, message) => {
    console.log(`Send FB ${FB} ->`, message);
};

send['Phone'] = (Phone, message) => {
    console.log(`Send Phone ${Phone} ->`, message);
};

class NotifyService {
    notify(receiver, message) {
        let address = receiver.address;;
        for (let key in address) {
            send[key](address[key], message);
        }
    }
}

module.exports = new NotifyService();
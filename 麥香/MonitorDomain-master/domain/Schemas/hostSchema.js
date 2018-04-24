var mongoose = require('mongoose'),
    uniqueValidator = require('mongoose-unique-validator'),
    Schema = mongoose.Schema;

var hostSchema = new Schema({
    name: {
        type: String,
        required: true,
        unique: true
    },
    command:{
        type: String,
        required: true,
    },
    ip: {
        type: String
    },
    status: {
        type: String
    },
    contacts: [{
        type: String,
        ref: 'Contact'
    }]
});

hostSchema.plugin(uniqueValidator);

module.exports = mongoose.model('Host', hostSchema);
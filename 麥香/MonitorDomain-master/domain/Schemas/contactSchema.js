var mongoose = require('mongoose'),
    uniqueValidator = require('mongoose-unique-validator'),
    Schema = mongoose.Schema;

var contactSchema = new Schema({
    name: {
        type: String,
        required: true,
        unique: true
    },
    address: {
        Email: {
            type: String
        },
        Line: {
            type: String
        },
        FB: {
            type: String
        },
        Phone: {
            type: String
        }
    }
});

contactSchema.plugin(uniqueValidator);

module.exports = mongoose.model('Contact', contactSchema);
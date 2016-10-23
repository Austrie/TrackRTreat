// app/models/users.js

var mongoose        = require('mongoose');
var Schema          = mongoose.Schema;

var UsersSchema = new Schema({
    userhash: String, points: Number, submitted: Number}, { versionKey: 'version'});

module.exports = mongoose.model('Users', UsersSchema);

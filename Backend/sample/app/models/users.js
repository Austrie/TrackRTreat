// app/models/bear.js

var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var BearSchema   = new Schema({
	username: String, friendsList: Array, displayName: String, avatarUrl: String, deviceId: String, mobileNumber: String, payPalEmail: String, commonLocations: String, accountBalance: String, offersCompleted: Array, offersReceived: Array, password: String, resetPass: String, stripeId: String}, { versionKey: 'version' });

module.exports = mongoose.model('Users', BearSchema);
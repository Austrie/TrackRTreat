// app/models/bears.js

var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var BearSchema   = new Schema({
	imageUrl: String, date: String}, { versionKey: 'version' });

module.exports = mongoose.model('Bears', BearSchema);

// app/models/locs.js

var mongoose    = require('mongoose');
var Schema      = mongoose.Schema;

var LocSchema  = new Schema({
    submitter: String, score: Number, count: Number, x: String, y: String}, { versionKey: 'version' });

module.exports = mongoose.model('Locs',LocSchema);

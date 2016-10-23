// server.js

// BASE SETUP
// =============================================================================

// call the packages we need
var schedule = require('node-schedule');
var Bears     = require('./app/models/bears');

function randomString(length, chars) {
    var result = '';
    for (var i = length; i > 0; --i) result += chars[Math.round(Math.random() * (chars.length - 1))];
    return result;
}

var childProc = require('child_process');

var mongoose   = require('mongoose');

var ObjectId = mongoose.Schema.Types.ObjectId;
var Entry = new mongoose.Schema({
  name: String,
  comment: String,
  postId: String
});



mongoose.connect('mongodb://localhost:27017/'); // connect to our database
var db = mongoose.connection;
var express    = require('express'); 		// call express
var app        = express(); 				// define our app using express
var bodyParser = require('body-parser');
var ObjectID = require('mongoose').ObjectID;

// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(bodyParser({uploadDir:'./uploads'}));

var port = process.env.PORT || 8070; 		// set our port

// ROUTES FOR OUR API
// =============================================================================
var router = express.Router(); 				// get an instance of the express Router

function stripAlphaChars(source) {
    var out = source.replace(/[^0-9]/g, '');

    return out;
}

router.route('/offers')

	// create a bear (accessed at POST http://localhost:8080/api/bears)
	.post(function(req, res) {
    console.log("made it through, capn");
		var bear = new Bears();
		//console.log(req.body);
		bear.imageUrl = req.param("imageUrl");
		bear.date = req.param("date");
		// save the bear and check for errors
		bear.save(function(err) {
			if (err) {
				res.send(err);
      }
      console.log("Saved: ", bear);
			res.json({ message: 'Bear created!' });
		});
	})
		.get(function(req, res) {
		    var query = Bears.findOne({'date' : 'date'});
        // console.log(query)
        query.select('imageUrl');
        query.exec(function(err, bear){
          if (err){
              console.log("Couldn't find the bear");
              res.send(err);
          }
          else if (bear === null) {
            res.json({message: "no bear found :-("});
          }
          else
            res.json({message: bear.imageUrl})
        })
	});

  router.route('/testcase')
      .get(function(req, res) {
      res.json( { message : "This is a test of the early warning system"});
});
// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', router);

app.use(bodyParser.urlencoded({
    extended: true
}));

// app.get("*", function (req, res, next) {
//     res.redirect("https://" + req.headers.host + "/" + req.path);
// });


router.use(function(req, res, next) {
	// do logging
	console.log('Something is happening.');
	next(); // make sure we go to the next routes and don't stop here
});

// test route to make sure everything is working (accessed at GET http://localhost:8080/api)
router.get('/', function(req, res) {
	res.json({ message: 'There is nothing here!' });
});
router.post('/', function(req,res) {
    res.json({ message: "This is a post!"});
});
router.use('/', function(req, res) {
	res.json({ message: 'You have no business here. Go away.' });
});

process.on('uncaughtException', function (err) {
    console.log(err.stack);
    process.exit();
});


// more routes for our API will happen here

// START THE SERVER
// =============================================================================
app.listen(port);

console.log('Engaging Pedel on port ' + port +', muhlady');

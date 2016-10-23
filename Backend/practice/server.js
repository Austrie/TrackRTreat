var Users = require('./app/models/users');
var Locs = require('./app/models/locs');
var mongoose = require('mongoose');

mongoose.connect('mongodb://localhost:27017/'); //Connect to db
var db = mongoose.connection;
var express = require('express');               //Call express
var app = express();                            //Define app using express
var bodyParser = require('body-parser');

//Configure app to use bodyParser()
// to get data from a POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(bodyParser({uploadDir: './uploads'}));

var port = process.env.PORT || 8080; //Setting port

//Routes for API
//We're doing a thing?
var router = express.Router();      // Get instance of express Router

router.route('/newloc')
    //create a loc
    .post(function(req, res) {
        //console.log("Did we make it?");
          var loc = new Locs();
          loc.submitter = req.param("userhash");
          loc.score = req.param("score");
          loc.count = 1;
          loc.x = req.param("x");
          loc.y = req.param("y");

          loc.save(function(err) {
            if (err) {
                res.send(err);
            }
            console.log("Saved: ", loc);
            res.json({message:'Location created'});
          });
    })
    // Return all locs
    .get(function(req,res) {
          var query = Locs.find();
          query.select();
          query.exec(function(err, loc){
            if (err){
              console.log("Err:Couldn't find the location");
              res.send(err);
            }
            else if (loc === null) {
              res.json({message: "No such location found"});
            }
            else
              res.json({message: loc})
            })
    });
    //Compute new score and increment count upon checkin review
router.route('/checkin')
    .post(function(req,res) {
          var query = Locs.findOne(req.param("ObjectID"));
          query.select();
          query.exec(function(err, loc){
            if (err){
              console.log("Err: Could not find loc for checkin");
              res.send(err);
            }
            else if (loc === null) {
              res.json({message: "No such loc found"});
            }
            else {
              var tmpscore = Number(req.param("score"));
              loc.score = tmpscore + loc.score;
              loc.count = loc.count + 1;
              loc.save(function(err) {
                if (err) {
                    res.send(err);
                }
                console.log("Updated: ", loc);
                res.json({message:'Location updated'});
              });
            }
          })
  });
//Register routes, all routes are prefixed with /api
app.use('/api',router);

app.use(bodyParser.urlencoded({
    extended:true
}));

router.use(function(req, res, next) {
    console.log('Avast!');
    next();
});

router.get('/', function(req,res) {
    res.json({message:'There is nothing here'});
});

process.on('uncaughtException', function (err) {
    console.log(err.stack);
    process.exit();
});

app.listen(port);
console.log('We be running!');

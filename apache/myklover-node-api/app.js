var CONSTANTS = require('./helpers/constants.js');

var userCtrl = require('./controllers/user/ctrl.js');


var express = require("express"),
    app = express(),
    bodyParser  = require("body-parser"),
    methodOverride = require("method-override");

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(methodOverride());



var router = express.Router();

router.route('/user/registerUser')
        .post(userCtrl.registerUser);

router.route('/user/loginUser')
    .post(userCtrl.registerUser);

app.use('/api/',router);

app.listen(CONSTANTS.CONFIG.LISTEN_PORT, function() {
    console.log("Node server runnning on:" + CONSTANTS.CONFIG.LISTEN_PORT);
});
var CONSTANTS = require('./helpers/constants.js');

var userCtrl = require('./controllers/user/ctrl.js');
var resourceCtrl = require('./controllers/resource/ctrl.js');


var express = require("express"),
    app = express(),
    bodyParser  = require("body-parser"),
    methodOverride = require("method-override");


app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(methodOverride());

app.use(function (req, res, next) {

    // Website you wish to allow to connect
    res.setHeader('Access-Control-Allow-Origin', "http://"+CONSTANTS.CONFIG.APACHE_HOST+':'+CONSTANTS.CONFIG.APACHE_PORT);

    // Request methods you wish to allow
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

    // Request headers you wish to allow
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

    // Set to true if you need the website to include cookies in the requests sent
    // to the API (e.g. in case you use sessions)
    res.setHeader('Access-Control-Allow-Credentials', true);

    // Pass to next layer of middleware
    next();
});

var router = express.Router();

router.route('/user/registerUser')
        .post(userCtrl.registerUser);

router.route('/user/loginUser')
    .post(userCtrl.loginUser);

router.route('/user/loginUserExternalProvider')
    .post(userCtrl.loginUserExternalProvider);

router.route('/user/recoveryPassword')
    .post(userCtrl.recoveryPassword);

router.route('/user/validateCode')
    .post(userCtrl.validateCode);

router.route('/user/changePassword')
    .post(userCtrl.changePassword);

router.route('/resource/getResource')
    .get(resourceCtrl.getResource);

router.route('/resource/getResourceText')
    .get(resourceCtrl.getResourceText)




app.use('/api/',router);

app.listen(CONSTANTS.CONFIG.LISTEN_PORT, function() {
    console.log("Node server runnning on:" + CONSTANTS.CONFIG.LISTEN_PORT);
});
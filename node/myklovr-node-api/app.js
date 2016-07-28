var CONSTANTS = require('./helpers/constants.js');

var userCtrl = require('./controllers/user/ctrl.js');
var userProfileCtrl = require('./controllers/userProfile/ctrl.js');
var dictionariesCtrl = require('./controllers/dictionaries/ctrl.js');
var resourceCtrl = require('./controllers/resource/ctrl.js');
var cookieParser = require('cookie-parser')


var express = require("express"),
    app = express(),
    bodyParser  = require("body-parser"),
    methodOverride = require("method-override");


app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(methodOverride());
app.use(cookieParser());

app.use(function (req, res, next) {

    var allowedOrigins = ['http://localhost:61116', 'http://104.197.29.46'];
    var origin = req.headers.origin;
    if(allowedOrigins.indexOf(origin) > -1){
        res.setHeader('Access-Control-Allow-Origin', origin);
    }
    // Website you wish to allow to connect
    /*res.setHeader('Access-Control-Allow-Origin', "http://"+CONSTANTS.CONFIG.APACHE_HOST+':'+CONSTANTS.CONFIG.APACHE_PORT);*/

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

/*** resources ***/
router.route('/resource/getResource')
    .get(resourceCtrl.getResource);

router.route('/resource/getResourceText')
    .get(resourceCtrl.getResourceText)

/*** USER LOGIN-REGISTRATION METHODS***/

router.route('/user/registerUser')
        .post(userCtrl.registerUser);
router.route('/user/loginUser')
    .post(userCtrl.loginUser);
router.route('/user/loginUserExternalProvider')
    .post(userCtrl.loginUserExternalProvider);
router.route('/user/RecoveryMethods')
    .get(userCtrl.getRecoveryMethods)
router.route('/user/recoveryPassword')
    .post(userCtrl.recoveryPassword);
router.route('/user/validateCode')
    .post(userCtrl.validateCode);
router.route('/user/changePasswordRecovery')
    .post(userCtrl.changePassword);

/*** USER PROFILE***/

router.route('/userProfile/getSectionUserKind')
    .get(userProfileCtrl.getSectionUserKind)
router.route('/userProfile/getUserSectionBasicInfo')
    .get(userProfileCtrl.getUserSectionBasicInfo)
router.route('/userProfile/getUserSectionPurpose')
    .get(userProfileCtrl.getUserSectionPurpose)
router.route('/userProfile/setSectionUserKind')
    .post(userProfileCtrl.setSectionUserKind)
router.route('/userProfile/setUserSectionBasicInfo')
    .post(userProfileCtrl.setUserSectionBasicInfo)
router.route('/userProfile/setUserSectionPurpose')
    .post(userProfileCtrl.setUserSectionPurpose)


/*** DICTIONARIES **/

router.route('/dictionaries/getCountries')
    .get(dictionariesCtrl.getCountries)
router.route('/dictionaries/getStates')
    .get(dictionariesCtrl.getStates)
router.route('/dictionaries/getSchoolTypes')
    .get(dictionariesCtrl.getSchoolTypes)
router.route('/dictionaries/getSubjects')
    .get(dictionariesCtrl.getSubjects)
router.route('/dictionaries/getIndustries')
    .get(dictionariesCtrl.getIndustries)



/*** UTILS ***/

router.route('/userProfile/resetWizard')
    .get(userProfileCtrl.resetWizard);

app.use('/api/',router);

app.listen(CONSTANTS.CONFIG.LISTEN_PORT, function() {
    console.log("Node server runnning on:" + CONSTANTS.CONFIG.LISTEN_PORT);
});
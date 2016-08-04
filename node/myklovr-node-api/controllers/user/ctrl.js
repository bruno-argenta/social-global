var CONSTANTS = require('../../helpers/constants.js');
var service = require('../../services/requestRest.js');
var uuid = require('node-uuid');


var conectionsPool = [];

exports.registerUser = function(req, res) {
    console.log('POST registerUser');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var connUUID = uuid.v1();
    conectionsPool[connUUID] = {request: req,response:res,createCookie:true};
    var requestModel = {
        username: req.body.Email,
        provider: "myklovr",
        password: req.body.Password
    }
    service.requestPost(requestModel,CONSTANTS.SERVICES.USER.REGISTER_USER,response,connUUID);
};

exports.loginUser = function(req, res) {
    console.log('POST loginUser');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var connUUID = uuid.v1();
    conectionsPool[connUUID] = {request: req,response:res,createCookie:true};
    var requestModel = {
        username: req.body.Email,
        provider: "myklovr",
        password: req.body.Password
    }
    service.requestPost(requestModel,CONSTANTS.SERVICES.USER.LOGIN_USER,response,connUUID);
};

exports.loginUserExternalProvider = function(req, res) {
    console.log('POST loginUserExternalProvider');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var requestModel = {
        username: req.body.code,
        provider: req.body.provider,
        password: ''
    }
    var connUUID = uuid.v1();
    conectionsPool[connUUID] = {request: req,response:res,createCookie:true};
    service.requestPost(requestModel,CONSTANTS.SERVICES.USER.LOGIN_EXTERNAL,response,connUUID);
};

exports.recoveryPassword = function(req, res) {
    console.log('POST recoveryPassword');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var connUUID = uuid.v1();
    var requestModel = {
        username: req.body.Email,
        method: req.body.Mode,
    }
    conectionsPool[connUUID] = {request: req,response:res};
    service.requestPost(requestModel,CONSTANTS.SERVICES.USER.RECOVERY_PASSWORD,response,connUUID);
};


exports.getRecoveryMethods = function(req, res) {
    var responseModel = {
        OperationStatus: -1,
        Message :
        {
            Text:"Email not found",
            Level: "Error"
        },
        OperationData: null
    }
    var sessionToken = req.cookies.sessionToken;

    console.log('SessionToken: '+sessionToken);
    var email = req.param("Email");

    if (email != undefined){
        email =  email.slice(email.lastIndexOf('@'));
        responseModel.OperationStatus =0;
        responseModel.Message.Text = "Success";
        responseModel.Message.Level = "Success";
        responseModel.OperationData = [{
            Contact:"*********"+email,
            Method:"EMAIL"
        }]
    }
    res.status(200).jsonp(responseModel);
};

exports.validateCode = function(req, res) {
    console.log('POST validateCode');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var connUUID = uuid.v1();
    conectionsPool[connUUID] = {request: req,response:res};
    service.requestPost(req.body,CONSTANTS.SERVICES.USER.VALIDATE_CODE,response,connUUID);
};

exports.changePassword = function(req, res) {
    console.log('POST changePassword');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var connUUID = uuid.v1();
    conectionsPool[connUUID] = {request: req,response:res};
    var requestModel = {
        username: req.body.Email,
        newPassword: req.body.Password,
        code: req.body.ValidationCode,
        provider: '',
    }
    console.log(JSON.stringify(requestModel));
    service.requestPost(requestModel,CONSTANTS.SERVICES.USER.CHANGE_PASSWORD,response,connUUID);
};



function response(statusCode,model, connUUID){
    var connection = conectionsPool[connUUID];
    delete conectionsPool[connUUID];
    var res = connection.response;
    if ((connection.createCookie) && (statusCode == 200)){
        res.cookie('sessionToken',model.OperationData.sessionToken, { maxAge: 900000, httpOnly: true });
        console.log('Save cookie: '+model.OperationData);
    }
    res.status(statusCode).jsonp(model);
}
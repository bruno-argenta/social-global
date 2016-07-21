var CONSTANTS = require('../../helpers/constants.js');
var service = require('../../services/requestRest.js');
var uuid = require('node-uuid');


var conectionsPool = [];

exports.registerUser = function(req, res) {
    console.log('POST registerUser');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var connUUID = uuid.v1();
    conectionsPool[connUUID] = {request: req,response:res};
    service.requestPost(req.body,CONSTANTS.SERVICES.USER.REGISTER_USER,response,connUUID);
};

exports.loginUser = function(req, res) {
    console.log('POST loginUser');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var connUUID = uuid.v1();
    conectionsPool[connUUID] = {request: req,response:res};
    service.requestPost(req.body,CONSTANTS.SERVICES.USER.LOGIN_USER,response,connUUID);
};

exports.recoveryPassword = function(req, res) {
    console.log('POST recoveryPassword');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var connUUID = uuid.v1();
    conectionsPool[connUUID] = {request: req,response:res};
    service.requestPost(req.body,CONSTANTS.SERVICES.USER.RECOVERY_PASSWORD,response,connUUID);
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
    service.requestPost(req.body,CONSTANTS.SERVICES.USER.CHANGE_PASSWORD,response,connUUID);
};


function response(model, connUUID){
    var conection = conectionsPool[connUUID];
    delete conectionsPool[connUUID];
    var res = conection.response;
    res.status(200).jsonp(JSON.stringify(model));
}
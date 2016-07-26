var CONSTANTS = require('../../helpers/constants.js');
var service = require('../../services/requestRest.js');
var uuid = require('node-uuid');


var conectionsPool = [];

exports.getBasicUserInfoProfile = function(req, res) {
    console.log('GET BasicUserInfoProfile');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var connUUID = uuid.v1();
    conectionsPool[connUUID] = {request: req,response:res};
    var requestModel = {
        username: req.body.Email,
        provider: "myklovr",
        password: req.body.Password
    }
    service.requestPost(requestModel,CONSTANTS.SERVICES.USER.REGISTER_USER,response,connUUID);
};


function response(statusCode,model, connUUID){
    var conection = conectionsPool[connUUID];
    delete conectionsPool[connUUID];
    var res = conection.response;
    res.status(statusCode).jsonp(model);
}
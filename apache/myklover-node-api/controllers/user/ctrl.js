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




function response(model, connUUID){
    console.log("before after: " + conectionsPool);
    var conection = conectionsPool[connUUID];
    delete conectionsPool[connUUID];
    var res = conection.response;
    console.log("array after: " + conectionsPool);
    res.status(200).jsonp(JSON.stringify(model));
}
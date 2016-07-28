var CONSTANTS = require('../helpers/constants.js');
//var http = require('http');
var async = require('async');
var http = require('request');


exports.requestPost = function(model,service,callback,connUUID){
    var options = setOptions(model, 'POST', service)
    console.log("REQUEST OPTIONS: " + JSON.stringify(options));
    request(options,callback,connUUID);
}

exports.requestGet = function(model,service){
    var options = setOptions(model, 'GET', service)
    console.log("REQUEST OPTIONS: " + JSON.stringify(options));
    request(options,callback,connUUID);
}



function request(options, callback, connUUID){
    http(options, function (error, response, body) {
        console.log("RESPONSE STATUSCODE JBOSS: " + response.statusCode + " RESPONSE BODY JBOSS: " + JSON.stringify(body));
        var statusCode = response.statusCode;
        if (statusCode === undefined){
            statusCode = 500;
        }
        if (!error && response.statusCode == 200) {
            var modelResponse = createResponse(body);
            callback(response.statusCode,modelResponse,connUUID);
        }else{
            var responseModel = {
                OperationStatus: -1,
                Message: {
                    Text:'An internal error occurss, try in a few minutes or contact to the administrator',
                    Level:'Error',
                },
                OperationData: ''
            }
            callback(statusCode,responseModel,connUUID);
        }
    });
}


function setOptions(model, method, service){
    var options = {
        url: 'http://'+CONSTANTS.CONFIG.JBOSS_HOST+':'+ CONSTANTS.CONFIG.JBOSS_PORT + service,
        body: model,
        json: true,
        method: method
    };
    return options;
}

function createResponse(data){
    var responseModel = {
        OperationStatus:'',
        Message: {
            Text:'',
            Level:'',
        },
        OperationData: ''
    }
    responseModel.Message.Text = data.message;
    responseModel.OperationData = data.object
    if (data.code < 0){
        responseModel.OperationStatus = -1;
        responseModel.Message.Level = 'ERROR';
    }else if (data.code > 0){
        responseModel.OperationStatus = 1;
        responseModel.Message.Level = 'WARNING';
    }else{
        responseModel.OperationStatus = 0;
        responseModel.Message.Level = 'SUCCESS';
    }
    return responseModel;
}


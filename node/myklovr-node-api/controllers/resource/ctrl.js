var CONSTANTS = require('../../helpers/constants.js');
var service = require('../../services/requestRest.js');
var uuid = require('node-uuid');


var conectionsPool = [];
var resourcesText = [];
resourcesText["login.scroller1.text"] = "text1";
resourcesText["login.scroller2.text"] ="text2";
resourcesText["login.scroller3.text"] ="text3";
resourcesText["login.scroller4.text"] = "text4";

var resources = [];
resources["login.scroller1.image"] = "img/LogIn/image-1.jpg";
resources["login.scroller2.image"] = "img/LogIn/image-2.jpg";
resources["login.scroller3.image"] = "img/LogIn/image-3.jpg";
resources["login.scroller4.image"] = "img/LogIn/image-4.jpg";
resources["passwordrecovery.background.image"] = "img/PasswordRecovery/background.png";

exports.getResource = function(req, res) {

    var responseModel = {
            OperationStatus: -1,
            Message :
            {
                Text:"Resource not found",
                Level: "Error"
            },
            OperationData: null
        }

    var resourceId = req.param("resourceId");
    var resource = resources[resourceId];
    if (resource != undefined){
        responseModel.OperationStatus =0;
        responseModel.Message.Text = "Success";
        responseModel.Message.Level = "Success";
        responseModel.OperationData = { ResourceUrl:"http://"+CONSTANTS.CONFIG.APACHE_HOST + ":" + CONSTANTS.CONFIG.APACHE_PORT+"/"+resource};
    }
    res.status(200).jsonp(responseModel);
};

exports.getResourceText = function(req, res) {
    var responseModel = {
        OperationStatus: -1,
        Message :
        {
            Text:"Resource not found",
            Level: "Error"
        },
        OperationData: null
    }

    var resourceId = req.param("resourceId");
    var resource = resourcesText[resourceId];
    if (resource != undefined){
        responseModel.OperationStatus =0;
        responseModel.Message.Text = "Success";
        responseModel.Message.Level = "Success";
        responseModel.OperationData = { ResourceText: resource }
    }
    res.status(200).jsonp(responseModel);
};

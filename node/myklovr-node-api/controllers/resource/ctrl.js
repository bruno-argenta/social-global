var CONSTANTS = require('../../helpers/constants.js');
var service = require('../../services/requestRest.js');
var uuid = require('node-uuid');
var multiparty = require('multiparty');
var fs = require('fs');
var format = require('util').format;

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

exports.uploadUserProfileImage = function(req,res){
// create a form to begin parsing
    var form = new multiparty.Form();
    var image;

    var responseModel = {
        OperationStatus: -1,
        Message :
        {
            Text:"Internal Error",
            Level: "Error"
        },
        OperationData: null
    }

    form.on('error', function(){
        res.status(200).jsonp(responseModel)
    });
    form.on('close', function(){
        var imageName = uuid.v1() + image.filename;
        var path = CONSTANTS.CONFIG.IMAGE_PATH + imageName;
        fs.writeFile(path,image.data,'binary', function(err){
            if (err){
                console.log('error: '+err);
                responseModel.Message.Text="Can't save the file";
                res.status(200).jsonp(responseModel);
            }else{
                responseModel.Message = null;
                responseModel.OperationStatus =0;
                responseModel.OperationData = { Path: path};
                res.status(200).jsonp(responseModel);
            }
        });

    });

    // listen on field event for title
    form.on('field', function(name, val){
        if (name !== 'title') return;
        title = val;
    });

    // listen on part event for image file
    form.on('part', function(part){
        if (!part.filename) return;
        if (part.name !== 'image') return part.resume();
        image = {};
        image.filename = part.filename;
        image.size = 0;
        image.data = undefined;
        part.setEncoding('binary');
        part.on('data', function(buf){
            if (image.data === undefined){
                image.data = buf;
            }else{
                image.data = image.data + buf;
            }
            image.size += buf.length;
        });
    });


    // parse the form
    form.parse(req);
}

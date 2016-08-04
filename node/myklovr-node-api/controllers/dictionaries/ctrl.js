var CONSTANTS = require('../../helpers/constants.js');
var service = require('../../services/requestRest.js');
var uuid = require('node-uuid');


var connectionsPool = [];

var invalidArgumentResponse = {
    OperationStatus:3,
    Message: {
        Text:"Invalid arguments",
        Level:'ERROR',
    },
    OperationData: ''
}

var countries =[
    {id:'US',value:'United States'},
    {id:'FR',value:'France'},
    {id:'GB',value:'England'},
    {id:'CA',value:'Canada'},
    {id:'MX',value:'Mexico'},
    {id:'DE',value:'Germany'}]

var states = [
    {id:'NY',value:'New York'},
    {id:'FL',value:'Florida'},
    {id:'MN',value:'Minnesota'},
    {id:'VA',value:'Virginia'},
    {id:'WA',value:'Washington'},
    {id:'MO',value:'Missouri'},
]

var subjects = [
    {id:'1',value:'Economics'},
    {id:'2',value:'Mathematics'},
    {id:'3',value:'Mathscinet'}
]

var industries =[
    {id:'1',value:'Accounting and Finance'},
    {id:'2',value:'Art and Entretaiment'},
    {id:'3',value:'Architecture, Interior Design'},
    {id:'4',value:'Business'},
    {id:'5',value:'Business Executive'}
]

var schoolType =[
    {id:'1',value:'Private High School'},
    {id:'2',value:'Public High School'},
    {id:'3',value:'Charter High School'},
    {id:'4',value:'Magnet High School'},
    {id:'5',value:'Public Collage/University'},
    {id:'6',value:'Private Collage/University'}
]


var connectionsPool = [];

exports.getCountries = function(req, res) {
    getDictionary(req.body.Language,'COUNTRY',req,res,'');
};

exports.getStates = function(req, res) {
    if (req.body.Country != undefined){
        getDictionary(req.body.Language,'STATE',req,res,req.body.Country);
    }else{
        req.status(200).jsonp(invalidArgumentResponse);
    }
};
exports.getIndustries = function(req, res) {
    getDictionary(req.body.Language,'INDUSTRY_TYPE',req,res,'');
};
exports.getSchoolTypes = function(req, res) {
    getDictionary(req.body.Language,'SCHOOL_TYPE',req,res,'');
};

exports.getSubjects = function(req, res) {
    getDictionary(req.body.Language,'SUBJECTS',req,res,'');
};

function getDictionary(language,dictionary,req,res,filter){
    if (language != undefined){
        var connUUID = uuid.v1();
        connectionsPool[connUUID] = {request: req,response:res};
        var requestModel = {
            dictionary:dictionary,
            language: language,
            filter:filter
        }
        service.requestPost(requestModel,CONSTANTS.SERVICES.DICTIONARY.GET_DICTIONARY,response,connUUID);
    }else{
        res.status(200).jsonp(invalidArgumentResponse);
    }
}

function response(statusCode,model, connUUID){
    var connection = connectionsPool[connUUID];
    delete connectionsPool[connUUID];
    var res = connection.response;
    res.status(statusCode).jsonp(model);
}
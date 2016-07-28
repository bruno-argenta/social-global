var CONSTANTS = require('../../helpers/constants.js');
var service = require('../../services/requestRest.js');
var uuid = require('node-uuid');


var conectionsPool = [];
var resourcesText = [];

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


exports.getCountries = function(req, res) {

    var responseModel = {
            OperationStatus: 0,
            Message :null,
            OperationData: {
                Countries: countries
            }
        }
    res.status(200).jsonp(responseModel);
};

exports.getStates = function(req, res) {

    var responseModel = {
        OperationStatus: 0,
        Message :null,
        OperationData: {
            States: states
        }
    }
    res.status(200).jsonp(responseModel);
};
exports.getIndustries = function(req, res) {

    var responseModel = {
        OperationStatus: 0,
        Message :null,
        OperationData: {
            Industries: industries
        }
    }
    res.status(200).jsonp(responseModel);
};
exports.getSchoolTypes = function(req, res) {

    var responseModel = {
        OperationStatus: 0,
        Message :null,
        OperationData: {
            SchoolTypes: schoolType
        }
    }
    res.status(200).jsonp(responseModel);
};

exports.getSubjects = function(req, res) {

    var responseModel = {
        OperationStatus: 0,
        Message :null,
        OperationData: {
            Subjects: subjects
        }
    }
    res.status(200).jsonp(responseModel);
};
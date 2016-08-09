var CONSTANTS = require('../../helpers/constants.js');
var cassandra = require('../../services/cassandraService.js');
var uuid = require('node-uuid');

var connectionPool =[];

exports.verifySession = function(req,res,next){

    var connId = uuid.v1();
    connectionPool[connId] = {res:res,req:req,next:next};
    var query = 'select * from myklovr.session;';
    cassandra.executeQuery(query,verifySessionCallback,connId);

}


function verifySessionCallback(connId,result,err){
    var res = connectionPool[connId].res;
    if (err != null){
        res.status(403).jsonp({});
    }else{
        console.log('back to the controller');
        var req = connectionPool[connId].req;
        var next = connectionPool[connId].next;
        next(req,res);
    }
}
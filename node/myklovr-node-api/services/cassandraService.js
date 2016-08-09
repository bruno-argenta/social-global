const cassandra = require('cassandra-driver');
const authProvider = new cassandra.auth.PlainTextAuthProvider('myklovr', 'myklovr');
const client = new cassandra.Client({ contactPoints: ['8.34.214.12'],authProvider: authProvider });
client.connect(function (err) {
    console.log(err);
});

exports.executeQuery = function(query,callback,connId,params){
    console.log('executing query');
    client.execute(query, params, function (err, result) {
        callback(connId,result,err);
    })
}
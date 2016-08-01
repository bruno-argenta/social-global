const cassandra = require('cassandra-driver');
const authProvider = new cassandra.auth.PlainTextAuthProvider('myklover', 'myklover');
const client = new cassandra.Client({ contactPoints: ['host1'],authProvider: authProvider });
client.connect(function (err) {
    assert.ifError(err);
});
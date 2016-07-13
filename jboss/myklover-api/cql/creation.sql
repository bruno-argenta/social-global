CREATE KEYSPACE myklover
WITH durable_writes = true
AND replication = {
	'class' : 'NetworkTopologyStrategy',
	'Cassandra' : 1
};


CREATE TABLE myklover."Login" (
	"userId" uuid,
	"userName" text,
	provider text,
	password text,
	"userCreationTimestamp" timestamp,
	"wrongPasswordCounter" int,
	"accountBlocked" boolean,
	PRIMARY KEY ("userId", "userName", provider)
);
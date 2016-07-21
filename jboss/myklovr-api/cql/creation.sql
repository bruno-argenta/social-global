CREATE KEYSPACE myklover
WITH durable_writes = true
AND replication = {
	'class' : 'NetworkTopologyStrategy',
	'Cassandra' : 1
};


CREATE TABLE myklover."Login" (
	userId uuid,
	userName text,
	provider text,
	password text,
	userCreationTimestamp timestamp,
	wrongPasswordCounter int,
	accountBlocked boolean,
	PRIMARY KEY (provider,userName)
)

CREATE TABLE myklover.session (
	sessiontoken text,
	userid uuid,
	expirationtimestamp timestamp,
	PRIMARY KEY (sessiontoken)
);

CREATE TABLE myklover.passwordrecovery (
	username text,
	verificationtoken text,
	expirationtimestamp timestamp,
	PRIMARY KEY (verificationtoken)
);


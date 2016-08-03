CREATE KEYSPACE myklovr
WITH durable_writes = true
AND replication = {
	'class' : 'NetworkTopologyStrategy',
	'Cassandra' : 1
};


CREATE TABLE myklovr.login (
	userId uuid,
	userName text,
	provider text,
	password text,
	userCreationTimestamp timestamp,
	wrongPasswordCounter int,
	accountBlocked boolean,
	PRIMARY KEY (provider,userName)
)

CREATE TABLE myklovr.session (
	sessiontoken text,
	userid uuid,
	expirationtimestamp timestamp,
	PRIMARY KEY (sessiontoken)
);

CREATE TABLE myklovr.passwordrecovery (
	username text,
	verificationtoken text,
	expirationtimestamp timestamp,
	PRIMARY KEY (verificationtoken)
);


CREATE TABLE myklovr.country (
	lenguage text,
	code text,
	name text,
	PRIMARY KEY (lenguage)
);

CREATE TABLE myklovr.state (
	lenguage text,
	country_code text,
	code text,
	name text,
	PRIMARY KEY (lenguage,country_code)
);

CREATE TABLE myklovr.industry_type (
	lenguage text,
	code uuid,
	name text,
	PRIMARY KEY (lenguage)
);

CREATE TABLE myklovr.subjects (
	lenguage text,
	code uuid,
	name text,
	PRIMARY KEY (lenguage)
);

CREATE TABLE myklovr.school_type (
	lenguage text,
	code uuid,
	name text,
	PRIMARY KEY (lenguage)
);


CREATE TABLE myklovr.user_profile (
	userid uuid,
	user_kind text,
	section text,
	value MAP<text, text>,
	PRIMARY KEY (userid, section)
)


ALTER TABLE myklovr.login
add name text;

ALTER TABLE myklovr.login
add urlimage text;

ALTER TABLE myklovr.login
add nextpage text;

ALTER TABLE myklovr.login
add kind text;

ALTER TABLE myklovr.session
add username text;

ALTER TABLE myklovr.session
add provider text;

ALTER TABLE myklovr.session
add kind text;


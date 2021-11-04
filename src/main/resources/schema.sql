drop table words if exists;

create table words (
 id BIGINT NOT NULL,
 name varchar(128) NOT NULL,
 meaning varchar(256) NOT NULL,
 PRIMARY KEY (id)
);
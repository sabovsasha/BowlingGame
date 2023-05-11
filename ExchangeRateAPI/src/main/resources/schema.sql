CREATE TABLE IF NOT EXISTS monocurrencies (id  serial not null, cc varchar(255), buy numeric(19, 2), sell numeric(19, 2), rate numeric(19, 2), date timestamp, primary key (id))

CREATE TABLE IF NOT EXISTS bankgovcurrencies (id serial not null, cc varchar(255), rate numeric(19, 2), date timestamp, primary key (id))

CREATE TABLE IF NOT EXISTS privatcurrencies (id serial not null, cc varchar(255), buy numeric(19, 2), sale numeric(19, 2), date timestamp, primary key (id))














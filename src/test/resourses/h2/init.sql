CREATE TABLE status (
    id serial PRIMARY KEY,
    name varchar
);

CREATE TABLE task (
    id serial PRIMARY KEY ,
    name varchar,
    description varchar,
    status_id integer REFERENCES status (id)
);

CREATE TABLE epic(
    id          serial PRIMARY KEY,
    name        varchar,
    description varchar,
    status_id integer REFERENCES status (id)
);

CREATE TABLE subtask (
    id          serial PRIMARY KEY,
    name        varchar,
    description varchar,
    epic_id integer REFERENCES epic (id) ON DELETE CASCADE,
    status_id integer REFERENCES status (id)
);

INSERT INTO status (name) VALUES
('NEW'),
('IN_PROGRESS'),
('DONE');

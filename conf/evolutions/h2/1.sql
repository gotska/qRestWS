# sql_query schema
 
# --- !Ups

CREATE TABLE sql_query (
    name       text NOT NULL,
    query      text NOT NULL
);

# --- !Downs

DROP TABLE sql_query;


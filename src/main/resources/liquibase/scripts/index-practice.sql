-- liquibase formatted sql

-- changeset salex:1
CREATE INDEX students_name_index ON student (name);

-- changeset salex:2
CREATE INDEX faculties_nc_index ON faculty (name,color);
-- liquibase formatted sql

-- changeset wladw:1737233454726-5
ALTER TABLE users
    ADD CONSTRAINT users_pk_2 UNIQUE (login);
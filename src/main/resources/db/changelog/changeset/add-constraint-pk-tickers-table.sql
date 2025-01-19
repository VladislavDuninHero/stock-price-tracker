-- liquibase formatted sql

-- changeset wladw:1737233454726-6
ALTER TABLE tickers
    ADD CONSTRAINT tickers_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;
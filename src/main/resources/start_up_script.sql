DROP TABLE IF EXISTS lf_user;

CREATE TABLE IF NOT EXISTS lf_user (
    id BIGINT primary key GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(1024) NOT NULL UNIQUE,
    first_name VARCHAR(128),
    last_name VARCHAR(128)
);


INSERT INTO lf_user (username, first_name, last_name) VALUES ('john.doe@domain.com', 'John', 'Doe');

--Login table

DROP TABLE IF EXISTS lf_login;

CREATE TABLE IF NOT EXISTS lf_login (
    id BIGINT primary key GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(1024) NOT NULL,
    password_hash VARCHAR(1024) UNIQUE,
    user_id BIGINT NOT NULL,
    login_type SMALLINT NOT NULL
);

ALTER TABLE lf_login ADD FOREIGN KEY (user_id) REFERENCES lf_user(id);

INSERT INTO lf_login (username, password_hash, user_id, login_type) VALUES ('john.doe@domain.com', '$2a$10$/7PScdp9I0tisbJckn3leunI58GZ1n.w5irYpG1ae/Vd4ljtXXCSe', 0, 1);
INSERT INTO lf_login (username, password_hash, user_id, login_type) VALUES ('john.doe@domain.com', 'UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q', 0, 2);


DROP TABLE IF EXISTS lf_job;

CREATE TABLE IF NOT EXISTS lf_job (
    id BIGINT primary key GENERATED ALWAYS AS IDENTITY,
    created_at TIMESTAMP(2) WITH TIME ZONE NOT NULL,
    processing_at TIMESTAMP(2) WITH TIME ZONE,
    completed_at TIMESTAMP(2) WITH TIME ZONE,
    sender_username VARCHAR(128) NOT NULL,
    message_type SMALLINT NOT NULL,
    message_title VARCHAR(4096),
    message_body VARCHAR(8192),
    message_metadata VARCHAR(8192),
    send_to_type SMALLINT NOT NULL,
    devices LONGVARCHAR
);

DROP TABLE IF EXISTS lf_device;

CREATE TABLE IF NOT EXISTS lf_device (
    id BIGINT primary key GENERATED ALWAYS AS IDENTITY,
    created_at TIMESTAMP(2) WITH TIME ZONE NOT NULL,
    notification_id VARCHAR(1024) NOT NULL UNIQUE,
    status SMALLINT NOT NULL,
    device_os VARCHAR(128),
    device_name VARCHAR(128),
    device_os_version VARCHAR(128),
    device_model VARCHAR(128)
);

DROP TABLE IF EXISTS lf_message;

CREATE TABLE IF NOT EXISTS lf_message (
    id BIGINT primary key GENERATED ALWAYS AS IDENTITY,
    job_id BIGINT NOT NULL,
    device_id BIGINT NOT NULL,
    notification_id VARCHAR(1024) NOT NULL,
    message_type SMALLINT NOT NULL,
    message_title VARCHAR(4096),
    message_body VARCHAR(8192),
    message_metadata VARCHAR(8192)
);

ALTER TABLE lf_message ADD FOREIGN KEY (job_id) REFERENCES lf_job(id);
ALTER TABLE lf_message ADD FOREIGN KEY (device_id) REFERENCES lf_device(id);
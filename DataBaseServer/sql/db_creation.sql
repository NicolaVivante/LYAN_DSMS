DROP DATABASE lyan_dsms IF EXISTS;
CREATE DATABASE lyan_dsms IF NOT EXISTS;

CREATE TABlE users(
    username VARCHAR(50) PRIMARY KEY,
    password_hash VARCHAR(512),
    public_key VARCHAR(512)
);
DROP DATABASE lyan_dsms IF EXISTS;
CREATE DATABASE lyan_dsms IF NOT EXISTS;
CREATE TABlE users(
    userName VARCHAR(50) PRIMARY KEY,
    passwordHash VARCHAR(512),
    publicKey VARCHAR(512)
);
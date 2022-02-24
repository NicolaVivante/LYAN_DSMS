CREATE TABlE users(
    username VARCHAR(50) PRIMARY KEY,
    password_hash VARCHAR(512),
    public_key VARCHAR(512)
);

CREATE TABLE symmetric_keys(
    username VARCHAR(50),
    file_hash VARCHAR(512),
    symmestric_key VARCHAR(512)
    PRIMARY KEY(username, file_hash),
    FOREIGN KEY(username) REFERENCES users(username)
);
CREATE TABlE users(
    username VARCHAR(50) PRIMARY KEY,
    password_hash VARCHAR(512),
    public_key VARCHAR(512)
);
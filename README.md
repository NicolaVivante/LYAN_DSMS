# LYAN_DSMS
The best Digital Signature Management System

## DB structure

### Table Users:
- UserName varchar(50) PK
- PasswordHash varchar(512)
- PublicKey varchar(512)

### Table SymmetricKeys
- UserName varchar(50) PK FK
- FileHash varchar(512) PK
- SymmetricKey varchar(512)

## DB interaction methods
- bool userExists(UserName)
- void register(UserName, PasswordHash)
- bool verifyUser(UserName, PasswordHash)
- void addPublicKey(UserName, PublicKey)
- String getPublicKey(UserName)
- void addSymmetricKey(UserName, FileHash, SymmetricKey)
- String getSymmetricKey(UserName, FileHash, SymmetricKey)
- String[] getAllUsers()
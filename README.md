# LYAN_DSMS
The best Digital Signature Management System

## DB structure

### Table Users:
- UserName varchar(50) PK
- PasswordHash varchar(512)
- PublicKey varchar(512)

### Table SymmetricKeys
- UserName varchar(50) PK FK
- FileHash varchar(512) PK (hash of the encrypted file)
- SymmetricKey varchar(512)

## DB interaction methods
- bool userExists(UserName)
- void register(UserName, PasswordHash)
- bool verifyUser(UserName, PasswordHash)
- void addPublicKey(UserName, PublicKey)
- String getPublicKey(UserName)
- void addSymmetricKey(UserName, FileHash, SymmetricKey)
- String getSymmetricKey(UserName, FileHash)
- String[] getAllUsers()

## General mechanics
public and private keys are stored in a file with ".lyan" extension, on client side.

.lyan file structure:

- fileName: String, the name of the file
- fileContent: String, bytes of the file, could be encrypted and encoded in Base64
- isEncrypted: boolean, whether the fileContent is encrypted
- signature: String, bytes of encrypted hash encoded in Base64



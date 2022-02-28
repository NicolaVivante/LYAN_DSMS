# LYAN_DSMS
The best Digital Signature Management System

## DB structure

### Table Users:
- UserName varchar(50) PK
- PasswordDigest varchar(512)
- PublicKey varchar(512)

## DB interaction methods
- bool userExists(UserName)
- void register(UserName, PasswordDigest)
- bool verifyUser(UserName, PasswordDigest)
- void addPublicKey(UserName, PublicKey)
- String getPublicKey(UserName)
- String[] getAllUsers()

## General system

a signed file is saved with extension ".sig.lyan"

.sig.lyan file structure:

- fileName: String, the name of the file
- fileContent: String, bytes of the file, could be encrypted
- isEncrypted: boolean, whether the fileContent is encrypted
- signature: String, bytes of encrypted digest (of plaintext fileContent)
- list of key-value pairs containing:
  - usernameDigest: String, bytes of digest of user name
  - symmetricKey: String, bytes of the symmetric key (to decrypt the fileContent) encrypted with user public key  

public and private keys, as well as user name are stored in a file with ".keys.lyan" extension, on client side.

.keys.lyan file structure

- passwordDigest: String, bytes of user password digest
- userName: String, user name (encrypted with user password as key)
- publicKey: String, bytes of user public key (encrypted with user password as key)
- privateKey: String, bytes of private key (encrypted with user password as key)



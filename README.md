# LYAN_DSMS
The best Digital Signature Management System

## DB structure

### Table Users:
- UserName varchar(50) PK
- PasswordDigest varchar(512)
- PublicKey varchar(512)

## DB interaction methods
- bool userExists(UserName)
- void register(UserName, PasswordDigest, PublicKey)
- bool verifyUser(UserName, PasswordDigest)
- void addPublicKey(UserName, PublicKey)
- String getUser(UserName)
- String[] getAllUsers()

## General system

### File formats

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

.user.lyan file structure

- password: String, user password
- userName: String, user name (encrypted with user password as key)
- publicKey: String, bytes of user public key (encrypted with user password as key)
- privateKey: String, bytes of private key (encrypted with user password as key)

### Functioning

a new user can either create a new account or load an existing .keys.lyan file.
After entering the password the user can:
- sign a file: the signature process is done by generating the file digest and optionally encrypting the content. If so, the symmetric key used is encrypted with a public key for each user allowed to decrypt the file.
- verify a signed file: after importing the file, the system checks if the file content is encrypted (if so, the user must be allowed to read the file and therefore inside the .sig.lyan there is a symmetric key encrypted with the user public key) and then a digest is calculated, the digest contained in the .sig.lyan is decrypted and the 2 digest are compared

## Notes

- to convert byte[] into String and back Converter class should always be used

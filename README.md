# LYAN_DSMS
The best Digital Signature Management System

## DB structure

### Table Users:
- UserName varchar(50) PK
- PublicKey varchar(512)

## DB interaction methods
- bool userExists(UserName)
- void register(UserName, PublicKey)
- PublicUser getUser(UserName)
- PublicUser[] getAllUsers()

## General system

### File formats

Public and private keys, as well as user name are stored in a file with ".user.lyan" extension, on client side.

Public key and username are also associated by means of a certificate, signed by a certification authority. This certificate is saved on client side with ".cert.lyan" extension

A signed file is saved with extension ".sig.lyan"

Certification authority public and private keys are saved with ".key.public.lyan" and ".key.private.lyan" extensions


### Functioning

When executing the program, the first 2 available options are:
- create new user: allow to create a new user from username and password, add it to the DB and save it on client side
- import user: allow to import an existing user file

After importing a user it is possible to:
- sign a file: after selecting a file and a certificate, the file can be signed and also encrypted for a list of recipients selected from the DB
- create a certificate: create a certificate and save it on client side
- verificate a signed file: after selecting a file, if the user has access to it and the signed is verfied, the original content can be saved

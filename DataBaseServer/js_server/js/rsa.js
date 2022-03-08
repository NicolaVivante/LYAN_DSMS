const NodeRSA = require("node-rsa");
const fileManager = require("./fileManager");
let rsa = {};

let publicKey = new NodeRSA();
let publicKeyData =
    publicKey.importKey({
    });

rsa.encrypt = () => key.encrypt("sus");

rsa.decrypt = () => {

};

module.exports = rsa;
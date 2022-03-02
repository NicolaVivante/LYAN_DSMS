/*
 * MySQL DB storing all the users (DDL in DataBaseServer\sql\db_creation.sql)
*/
const db = require("./db.js");
const DBDataManager = {};
DBDataManager.getUsers = () => {
    try {
        return await db.selectUsers();
    } catch (error) {
        console.error(error);
    }
};
DBDataManager.getUserByName = (userName) => {
    try {
        return (await db.selectUserByName(userName))[0];
    } catch (error) {
        console.error(error);
    }
};
DBDataManager.addUser = (user) => {
    try { return db.insertUser(user); }
    catch (error) { console.error(error); };
};
DBDataManager.setKey = (key, userName) => {
    // TODO
};

module.exports = DBDataManager;
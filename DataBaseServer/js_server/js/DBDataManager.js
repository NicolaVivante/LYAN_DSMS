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
    try { return await db.insertUser(user); }
    catch (error) { console.error(error); };
};
DBDataManager.setKey = (key, userName) => {
    try { return await db.setKey(key, userName); }
    catch (error) { console.error(error); }
};

module.exports = DBDataManager;
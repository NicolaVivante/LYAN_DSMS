/*
 * JSON management
 * All the information is stored in a simple JSON file
 * Quicker and easier to set up, but slower and less capable of handling too much users.
 * Use this if MySQL is not configured on the server
 */

const JSONDataManager = {};
const fileManager = require("./fileManager");

const filePath = "./users.json";
JSONDataManager.getUsers = async function () {
  return JSON.parse(await fileManager.readfile(filePath));
};
JSONDataManager.getUserByName = async (userName) => {
  return await JSON.parse(await fileManager.readfile(filePath)).filter(
    (u) => u.userName == userName
  )[0];
};
JSONDataManager.addUser = async (user) => {
  const users = JSON.parse(await fileManager.readfile(filePath));
  users.push(user);
  return fileManager.writeFile(filePath, JSON.stringify(users));
};

module.exports = JSONDataManager;

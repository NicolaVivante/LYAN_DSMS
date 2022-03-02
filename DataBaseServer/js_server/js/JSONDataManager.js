/*
 * JSON management
 * All the information is stored in a simple JSON file
 * Quicker and easier to set up, but slower and less capable of handling too much users.
 * Use this if MySQL is not configured on the server
 */
const fs = require("fs").promises;
async function readfile(path) {
  try {
    return await fs.readFile(path);
  } catch (error) {
    console.log(error);
    return false;
  }
}

async function writeFile(path, content) {
  try {
    await fs.writeFile(path, content);
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
}

const JSONDataManager = {};

const filePath = "./users.json";
JSONDataManager.getUsers = async function () {
  return JSON.parse(await readfile(filePath));
};
JSONDataManager.getUserByName = async (userName) => {
  return await JSON.parse(await readfile(filePath)).filter(
    (u) => u.userName == userName
  )[0];
};
JSONDataManager.addUser = async (user) => {
  const users = JSON.parse(await readfile(filePath));
  users.push(user);
  return writeFile(filePath, JSON.stringify(users));
};
JSONDataManager.setKey = async (key, userName) => {
  let users = JSON.parse(await readfile(filePath));
  users.forEach((element) => {
    if (element.userName == userName) element.key = key;
  });
  return writeFile(filePath, JSON.stringify(users));
};

module.exports = JSONDataManager;

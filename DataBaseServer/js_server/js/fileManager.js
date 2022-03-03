let file = {};
const fs = require("fs").promises;

file.readfile = async (path) => {
    try {
        return await fs.readFile(path);
    } catch (error) {
        console.log(error);
        return false;
    }
};

file.writeFile = async (path, content) => {
    try {
        await fs.writeFile(path, content);
        return true;
    } catch (error) {
        console.log(error);
        return false;
    }
};

module.exports = file;
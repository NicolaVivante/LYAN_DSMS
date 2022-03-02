const fs = require("fs").promises;
const express = require("express");
const app = express();
app.use(express.json());
const PORT = 5555;

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

const filePath = "./users.json";
const JSONDataManager = {
  getUsers: async function () {
    return JSON.parse(await readfile(filePath));
  },
  getUserByName: async (userName) => {
    return await JSON.parse(await readfile(filePath)).filter(
      (u) => u.userName == userName
    )[0];
  },
  addUser: async (user) => {
    const users = JSON.parse(await readfile(filePath));
    users.push(user);
    writeFile(filePath, JSON.stringify(users));
  },
  setKey: async (key, userName) => {
    let users = JSON.parse(await readfile(filePath));
    users.forEach(element => {
      if (element.userName == userName) element.key = key;
    });
    writeFile(filePath, JSON.stringify(users));
  },
};

async function test() {
  console.log(await JSONDataManager.getUserByName("luca"));
  await JSONDataManager.setKey("test", "luca");
}
test();

const DBDataManager = {
  getUsers: () => {
    // TODO
  },
  getUserByName: (userName) => {
    // TODO
  },
  addUser: (user) => {
    // TODO
  },
  setKey: (key, userName) => {
    // TODO
  },
};

const DataManager = JSONDataManager;

app.get("/users", async (req, res) => {
  // TODO: filters
  res.json(await DataManager.getUsers());
});

app.get("/verify", async (req, res) => {
  res.json({
    verified:
      (await DataManager.getUserByName(req.body.username).passwordDigest) ==
      req.body.passwordDigest,
  });
});

app.post("/users", async (req, res) => {
  // TODO: check JSON consistency
  await DataManager.addUser(req.body);
  // TODO: send outcome
});

app.post("/key", async (req, res) => {
  // TODO: check JSON consistency
  await DataManager.setKey(req.body.key, req.body.userName);
  // TODO: send outcome
});

app.listen(PORT, async () => {
  console.log("LYAN server listening on port " + PORT);
});

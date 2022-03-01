const express = require("express");
const app = express();
app.use(express.json());
const PORT = 5555;

const JSONDataManager = {
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

app.get("/users", (req, res) => {
  // TODO: filters
  res.json(DataManager.getUsers());
});

app.get("/verify", (req, res) => {
  res.json({
    verified:
      DataManager.getUserByName(req.body.username).passwordDigest ==
      req.body.passwordDigest,
  });
});

app.post("/users", (req, res) => {
  DataManager.addUser(req.body);
  // TODO: send outcome
});

app.post("/key", (req, res) => {
  // TODO
  DataManager.setKey(req.body.key, req.body.userName);
  // TODO: send outcome
});

app.listen(PORT, () => {
  console.log("LYAN server listening on port " + PORT);
});

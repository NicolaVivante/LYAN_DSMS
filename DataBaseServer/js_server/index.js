require("dotenv").config();
const express = require("express");
const app = express();
app.use(express.json());
const PORT = process.env.PORT;

// Change the value of this variable to JSONDataManager or DBDataManager to swich between the two systems
const DataManager = require("./js/JSONDataManager");

// API URLs
app.get("/users", async (req, res) => {
  if (req.query.userName)
    res.status(200).json(await DataManager.getUserByName(req.query.userName));
  else res.status(200).json(await DataManager.getUsers());
});

app.post("/users", async (req, res) => {
  // TODO: check JSON consistency
  res.status((await DataManager.addUser(req.body)) ? 200 : 500).end();
});

app.listen(PORT, async () => {
  console.log("LYAN server listening on port " + PORT);
});

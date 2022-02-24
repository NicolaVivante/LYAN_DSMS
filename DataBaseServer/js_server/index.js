let app = require("express")();
const port = 5555;

const JSONDataManager = {

};

const DBDataManager = {

};

const DataManager = JSONDataManager;

// CALLS:
/*
/users (add, remove, get)
/verify/user
/user/key (add, get)
/symmetrickeys (add, remove, get)
*/

app.get("/users", (req, res) => {
    // TODO
});

app.get("/verify/:username", (req, res) => {
    // TODO
});

app.get("/:user/key", (req, res) => {
    // TODO
});

app.get("/symmetrickeys", (req, res) => {
    // TODO
});

app.post("/users", (req, res) => {
    // TODO
});

app.post("/:user/key", (req, res) => {
    // TODO
});

app.post("/symmetrickeys", (req, res) => {
    // TODO
});

app.listen(port, () => {
    console.log("LYAN server listening on port " + port);
});
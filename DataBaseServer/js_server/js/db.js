const mysql = require("mysql");
const dbConn = mysql.createPool({
  connectionLimit: process.env.DB_CONNECTION_LIMIT,
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  user: process.env.DB_USER,
  database: process.env.DB_NAME,
});
dbConn.connect(function (err) {
  if (err) console.error(err);
  else console.log("Connected to MySQL!");
});

let db = {};

db.selectUsers = () => {
  return new Promise((resolve, reject) => {
    dbConn.query("SELECT * FROM users", (error, results) => {
      return error ? reject(error) : resolve(results);
    });
  });
};
db.selectUserByName = (userName) => {
  return new Promise((resolve, reject) => {
    dbConn.query(
      "SELECT * FROM users WHERE username = ?",
      [userName],
      (error, results) => {
        return error ? reject(error) : resolve(results);
      }
    );
  });
};
db.insertUser = (user) => {
  return new Promise((resolve, reject) => {
    dbConn.query(
      "INSERT INTO users(username, password_hash, public_key) VALUES(?, ?, ?)",
      [user.userName, user.passwordDigest, user.key],
      (error) => {
        return error ? reject(error) : resolve();
      }
    );
  });
};

module.exports = db;

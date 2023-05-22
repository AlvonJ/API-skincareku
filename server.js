const express = require('express');
const app = express();

const admin = require('firebase-admin');
const credentials = require('./serviceAccountKey.json');

admin.initializeApp({
  credential: admin.credential.cert(credentials),
});

app.use(express.json());

app.use(express.urlencoded({ extended: true }));

app.post('/signup', async (req, res) => {
  const userResponse = await admin.auth().createUser({
    email: req.body.email,
    password: req.body.password,
    emailVerified: false,
    disabled: false,
  });

  res.json(userResponse);
});

const port = process.env.PORT || 8080;

const server = app.listen(port, () => {
  console.log(`Server running on port ${port}...`);
});

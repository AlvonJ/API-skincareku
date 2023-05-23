const express = require('express');
const admin = require('firebase-admin');
const credentials = require('./serviceAccountKey.json');
const userRouter = require('./routes/userRoutes');

const app = express();

admin.initializeApp({
  credential: admin.credential.cert(credentials),
});

app.use(express.json());

app.use(express.urlencoded({ extended: true }));

// ROUTES
app.use('/', userRouter);

module.exports = app;

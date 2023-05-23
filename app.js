const express = require('express');
const admin = require('firebase-admin');
const credentials = require('./serviceAccountKey.json');
const userRouter = require('./routes/userRoutes');
const globalErrorHandler = require('./controllers/errorController');
const AppError = require('./utils/appError');

const app = express();

admin.initializeApp({
  credential: admin.credential.cert(credentials),
});

app.use(express.json());

app.use(express.urlencoded({ extended: true }));

// ROUTES
app.use('/users', userRouter);

app.all('*', (req, res, next) => {
  next(new AppError(`Can't find ${req.originalUrl} on this server!`, 404));
});

app.use(globalErrorHandler);

module.exports = app;

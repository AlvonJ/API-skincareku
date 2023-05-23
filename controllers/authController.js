const admin = require('firebase-admin');
const catchAsync = require('../utils/catchAsync');
const AppError = require('../utils/appError');

exports.register = catchAsync(async (req, res, next) => {
  // Error handling
  if (!req.body.email || !req.body.password)
    return next(new AppError('Please provide email and password!', 400));

  const newUser = await admin.auth().createUser({
    email: req.body.email,
    password: req.body.password,
    emailVerified: false,
    disabled: false,
  });

  res.status(201).json({
    status: 'success',
    data: {
      user: newUser,
    },
  });
});

exports.deleteByEmail = catchAsync(async (req, res, next) => {
  // Error handling
  if (!req.body.email) return next(new AppError('Please provide email!', 400));

  const user = await admin.auth().getUserByEmail(req.body.email);

  await admin.auth().deleteUser(user.uid);

  res.status(204).json({
    status: 'success',
    data: null,
  });
});

exports.deleteByUid = catchAsync(async (req, res, next) => {
  // Error handling
  if (!req.body.uid) return next(new AppError('Please provide uid!', 400));

  await admin.auth().deleteUser(req.body.uid);

  res.status(204).json({
    status: 'success',
    data: null,
  });
});

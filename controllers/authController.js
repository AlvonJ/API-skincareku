const admin = require('firebase-admin');
const catchAsync = require('../utils/catchAsync');
const AppError = require('../utils/appError');

exports.register = catchAsync(async (req, res, next) => {
  // Error handling
  if (!req.body.email || !req.body.password || !req.body.name)
    return next(new AppError('Please provide name, email, and password!', 400));

  //firebase
  const newUser = await admin.auth().createUser({
    email: req.body.email,
    password: req.body.password,
    displayName: req.body.name,
    emailVerified: false,
    disabled: false,
  });

  // Token
  const uid = newUser.uid;
  const additionalClaims = {
    rules: {
      exp: 3600,
    },
  }; // kalo butuh tambahaan syarat biar filtering gampang
  const token = await admin.auth().createCustomToken(uid, additionalClaims);
  console.log('uid :', uid, 'tokenya :', token);

  //firestore
  const db = admin.firestore();
  const userJson = {
    email: newUser.email,
    name: newUser.displayName,
    uid,
    token,
  };
  await db.collection('users').doc(uid).set(userJson);

  //success
  res.status(201).json({
    status: 'success',
    data: {
      user: newUser,
      token,
    },
  });
});

// exports.login = catchAsync(async (req, res, next) => {
//   if (!req.body.email || !req.body.password)
//     return next(new AppError('Please provide email and password!', 400))
//   initializeApp()
//   await admin.auth().signIn
// })

exports.deleteByEmail = catchAsync(async (req, res, next) => {
  // Error handling
  if (!req.body.email) return next(new AppError('Please provide email!', 400));

  const user = await admin.auth().getUserByEmail(req.body.email);

  await admin.auth().deleteUser(user.uid);

  //firestore
  const db = admin.firestore();
  await db.collection('users').doc(user.uid).delete();

  res.status(204).json({
    status: 'success',
    data: null,
  });
});

exports.deleteByUid = catchAsync(async (req, res, next) => {
  // Error handling
  if (!req.body.uid) return next(new AppError('Please provide uid!', 400));

  await admin.auth().deleteUser(req.body.uid);

  //firestore
  const db = admin.firestore();
  await db.collection('users').doc(req.body.uid).delete();

  res.status(204).json({
    status: 'success',
    data: null,
  });
});

exports.updateUser = catchAsync(async (req, res, next) => {
  // error handling
  if (!req.body.email) return next(new AppError('Please provide email!', 400));
  if (
    req.body.gender &&
    req.body.gender.toLowerCase() !== 'male' &&
    req.body.gender.toLowerCase() !== 'female'
  )
    return next(new AppError('Gender value must be male or female!', 400));

  const user = await admin.auth().getUserByEmail(req.body.email);

  // firestore
  const db = admin.firestore();
  const userFirestore = (
    await db.collection('users').doc(user.uid).get()
  ).data();

  const { skinProblem, allergy, gender, birthDate } = userFirestore;

  await db
    .collection('users')
    .doc(user.uid)
    .update({
      skinProblem: req.body.skinProblem ?? skinProblem ?? '',
      allergy: req.body.allergy ?? allergy ?? '',
      gender: req.body.gender ?? gender ?? '',
      birthDate: req.body.birthDate ?? birthDate ?? '',
    });

  const updatedUser = (await db.collection('users').doc(user.uid).get()).data();

  //success
  res.status(200).json({
    status: 'success',
    data: {
      user: updatedUser,
    },
  });
});

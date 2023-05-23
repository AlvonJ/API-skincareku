const admin = require('firebase-admin')
const catchAsync = require('../utils/catchAsync')
const AppError = require('../utils/appError')

exports.register = catchAsync(async (req, res, next) => {
  // Error handling
  if (!req.body.email || !req.body.password)
    return next(new AppError('Please provide email and password!', 400))

  //firebase
  const newUser = await admin.auth().createUser({
    email: req.body.email,
    password: req.body.password,
    emailVerified: false,
    disabled: false
  })

  console.log(newUser.uid)
  //firestore
  const db = admin.firestore()
  const uid = newUser.uid
  const userJson = {
    email: newUser.email,
    uid: uid
  }
  await db.collection('users').doc(uid).set(userJson)

  //success
  res.status(201).json({
    status: 'success',
    data: {
      user: newUser
    }
  })
})

exports.deleteByEmail = catchAsync(async (req, res, next) => {
  // Error handling
  if (!req.body.email) return next(new AppError('Please provide email!', 400))

  const user = await admin.auth().getUserByEmail(req.body.email)

  await admin.auth().deleteUser(user.uid)

  //firestore
  const db = admin.firestore()
  await db.collection('users').doc(user.uid).delete()

  res.status(204).json({
    status: 'success',
    data: null
  })
})

exports.deleteByUid = catchAsync(async (req, res, next) => {
  // Error handling
  if (!req.body.uid) return next(new AppError('Please provide uid!', 400))

  await admin.auth().deleteUser(req.body.uid)

  //firestore
  const db = admin.firestore()
  await db.collection('users').doc(req.body.uid).delete()

  res.status(204).json({
    status: 'success',
    data: null
  })
})

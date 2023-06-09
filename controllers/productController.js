const admin = require('firebase-admin')
const catchAsync = require('../utils/catchAsync')
const AppError = require('../utils/appError')

exports.getAll = catchAsync(async (req, res, next) => {
  const db = admin.firestore()
  const productRef = db.collection('skincare')
  const product = await productRef.get()
  let productArr = []
  product.forEach(doc => {
    productArr.push(doc.data())
  })
  res.status(200).json({
    status: 'Success',
    data: productArr
  })
})

exports.getSingle = catchAsync(async (req, res, next) => {
  const id = req.body.id
  const db = admin.firestore()
  const productRef = db.collection('skincare').doc(id)
  const product = await productRef.get()

  if (!product.exists) return next(new AppError('Product Not Found!', 404))

  res.status(200).json({
    status: 'Success',
    data: product.data()
  })
})

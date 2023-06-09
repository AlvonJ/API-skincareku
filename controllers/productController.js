const admin = require('firebase-admin')
const catchAsync = require('../utils/catchAsync')
const AppError = require('../utils/appError')

exports.getAll = catchAsync(async (req, res, next) => {
  const db = admin.firestore()
  const productRef = db.collection('skincare')
  const product = await productRef.get()
  let productArr = []
  product.forEach(doc => {
    const data = doc.data()
    const ingredientsArr = data.ingredients
      .split(', ')
      .map(item => item.toLowerCase())
    productArr.push({
      id: doc.id,
      data: { ...data, ingredients: ingredientsArr }
    })
  })
  res.status(200).json({
    item: productArr.length,
    status: 'Success',
    data: productArr
  })
})

exports.getAllFiltered = catchAsync(async (req, res, next) => {
  const { field, value } = req.body
  const db = admin.firestore()
  const productRef = db.collection('skincare')
  const product = await productRef.where(field, '==', value).get()
  let productArr = []
  product.forEach(doc => {
    const data = doc.data()
    const ingredientsArr = data.ingredients
      .split(', ')
      .map(item => item.toLowerCase())
    productArr.push({
      id: doc.id,
      data: { ...data, ingredients: ingredientsArr }
    })
  })

  if (productArr.length === 0)
    return next(new AppError('Products Not Found!', 404))

  res.status(200).json({
    item: productArr.length,
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

exports.getFilteredIngredients = catchAsync(async (req, res, next) => {
  const db = admin.firestore()
  const productRef = db.collection('skincare')
  const product = await productRef.get()
  let productArr = []
  product.forEach(doc => {
    const data = doc.data()
    const ingredientsArr = data.ingredients
      .split(', ')
      .map(item => item.toLowerCase())
    productArr.push({
      id: doc.id,
      data: { ...data, ingredients: ingredientsArr }
    })
  })

  const { filter, method } = req.body
  if (!filter || !method)
    return next(new AppError('input filter and method!', 500))

  let filtered = []
  if (method === 'and') {
    filtered = productArr.filter(product =>
      filter.every(ingredient => product.data.ingredients.includes(ingredient))
    )
  } else if (method === 'or') {
    filtered = productArr.filter(product =>
      filter.some(ingredient => product.data.ingredients.includes(ingredient))
    )
  }

  if (productArr.length === 0 || filtered.length === 0)
    return next(new AppError('Products Not Found!', 404))

  res.status(200).json({
    item: filtered.length,
    status: 'Success',
    data: filtered
  })
})

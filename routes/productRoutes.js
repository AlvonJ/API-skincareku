/**
 * @swagger
 * components:
 *   schemas:
 *     Products:
 *       type: object
 *       properties:
 *         id:
 *           type: string
 *           description: The auto-generated id of the product
 *         product_url:
 *           type: string
 *           description: The link of product to online shop
 *         image_url:
 *           type: string
 *           description: The link of image of the product
 *         rating:
 *           type: integer
 *           description: The rating of product
 *         ingredients:
 *           type: array
 *           items:
 *            type: string
 *           description: The ingredients of product
 *         description:
 *           type: string
 *           description: The description of product
 *         bad_reviews:
 *           type: integer
 *           description: The total number of bad reviews of product
 *         category:
 *           type: string
 *           description: The category of product
 *         product_name:
 *           type: string
 *           description: The name of product
 *         brand:
 *           type: string
 *           description: The brand of product
 *         good_reviews:
 *           type: integer
 *           description: The total number of good reviews of product
 *     getProduct:
 *       type: object
 *       properties:
 *         id:
 *           type: string
 *           description: The auto-generated id of the product
 *     filterProduct:
 *       type: object
 *       properties:
 *         field:
 *           type: string
 *           description: field that want to filtered(look at Products's Schema)
 *         value:
 *           type: string
 *           description: the value that want to be shown
 *     filterProductIngredients:
 *       type: object
 *       properties:
 *         filter:
 *           type: array
 *           items:
 *            type: string
 *           description: field that want to filtered by Ingredients(filter must be in Array)
 *         method:
 *           type: string
 *           description: set method with and / or
 */

const express = require('express')
const productController = require('../controllers/productController')

const router = express.Router()

/**
 * @swagger
 *  tags:
 *   name: Products
 *   description: The Products managing API
 * /products/getAll:
 *   get:
 *     summary: get all data of products
 *     tags: [Products]
 *
 *     responses:
 *       200:
 *         description: get all products data
 *       404:
 *         description: The products was not found
 */
router.get('/getAll', productController.getAll)

/**
 * @swagger
 * /products/getAllFiltered:
 *   post:
 *     summary: get all data of products already filtered(field,value required)
 *     tags: [Products]
 *
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/filterProduct'
 *
 *     responses:
 *       200:
 *         description: get products data already filtered
 *       404:
 *         description: The products was not found
 */
router.post('/getAllFiltered', productController.getAllFiltered)

/**
 * @swagger
 * /products/getAllFilteredIngredients:
 *   post:
 *     summary: get all data of products already filtered by ingredients(filter required in Array)
 *     tags: [Products]
 *
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/filterProductIngredients'
 *
 *     responses:
 *       200:
 *         description: get products data already filtered
 *       404:
 *         description: The products was not found
 */
router.post(
  '/getAllFilteredIngredients',
  productController.getFilteredIngredients
)

/**
 * @swagger
 * /products/getSingle:
 *   post:
 *     summary: get single data of product(id required)
 *     tags: [Products]
 *
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/getProduct'
 *
 *     responses:
 *       200:
 *         description: get product data
 *       404:
 *         description: The product was not found
 */
router.post('/getSingle', productController.getSingle)

module.exports = router

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
 *         skin type:
 *           type: string
 *           description: The skin type of product
 *         product:
 *           type: string
 *           description: The type of product
 *         link:
 *           type: string
 *           description: The link of product to online shop
 *         title:
 *           type: string
 *           description: The title of product
 *         category:
 *           type: string
 *           description: The category of product
 *         brand:
 *           type: string
 *           description: The brand of product
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
 */

const express = require('express')
const productController = require('../controllers/productController')

const router = express.Router()

/**
 * @swagger
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

/**
 * @swagger
 * components:
 *   schemas:
 *     Users:
 *       type: object
 *       properties:
 *         uid:
 *           type: string
 *           description: The auto-generated id of the user
 *         email:
 *           type: string
 *           description: The email of user
 *         token:
 *           type: string
 *           description: The created token for user
 *         name:
 *           type: string
 *           description : Name of created user
 *     UserCreate:
 *       type: object
 *       properties:
 *         email:
 *           type: string
 *           description: The email of user
 *         password:
 *           type : string
 *           description : user password
 *         name:
 *           type: string
 *           description : Name of created user
 *     UserDeleteByEmail:
 *       type: object
 *       properties:
 *         email:
 *           type: string
 *           description: The email of user
 *     UserDeleteByUid:
 *       type: object
 *       properties:
 *         uid:
 *           type: string
 *           description: The auto-generated id of the user
 *     updateUser:
 *       type: object
 *       properties:
 *         email:
 *           type: string
 *           description: The email of user
 *         skinProblem:
 *           type: string
 *           description: The skin problem of user
 *         gender:
 *           type: string
 *           description: The gender of user
 *         allergy:
 *           type: string
 *           description: The allergy of user
 *         birthDate:
 *           type: string
 *           description: The birthDate of user
 *     getUserData:
 *       type: object
 *       properties:
 *         email:
 *           type: string
 *           description: The email of user
 */

const express = require('express')
const productController = require('../controllers/productController')

const router = express.Router()

/**
 * @swagger
 * /users/getUserData:
 *   post:
 *     summary: post user data by user email
 *     tags: [Users]
 *
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/getUserData'
 *
 *     responses:
 *       200:
 *         description: get user data
 *       404:
 *         description: The user was not found
 */
router.get('/getAll', productController.getAll)
router.post('/getSingle', productController.getSingle)

module.exports = router

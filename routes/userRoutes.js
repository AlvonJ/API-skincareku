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
const authController = require('./../controllers/authController')

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
router.post('/getUserData', authController.getUserData)

/**
 * @swagger
 *  tags:
 *   name: Users
 *   description: The Users managing API
 * /users/register:
 *  post:
 *     summary: Register new user (required email & password)
 *     tags: [Users]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/UserCreate'
 *
 *     responses:
 *       201:
 *         description: The user was created

 *       500:
 *         description: Some server error
 */
router.post('/register', authController.register)

/**
 * @swagger
 * /users/deleteByEmail:
 *   delete:
 *     summary: delete user by email address (required email)
 *     tags: [Users]
 *
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/UserDeleteByEmail'
 *
 *     responses:
 *       204:
 *         description: The user was deleted
 *       404:
 *         description: The user was not found
 */
router.delete('/deleteByEmail', authController.deleteByEmail)

/**
 * @swagger
 * /users/deleteByUid:
 *   delete:
 *     summary: delete user by Uid (Required Uid)
 *     tags: [Users]
 *
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/UserDeleteByUid'
 *
 *     responses:
 *       204:
 *         description: The user was deleted
 *       404:
 *         description: The user was not found
 */
router.delete('/deleteByUid', authController.deleteByUid)

/**
 * @swagger
 * /users/updateUser:
 *   patch:
 *     summary: update user (required email)
 *     tags: [Users]
 *
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/updateUser'
 *
 *     responses:
 *       200:
 *         description: The user was updated
 *       404:
 *         description: The user was not found
 */
router.patch('/updateUser', authController.updateUser)

module.exports = router

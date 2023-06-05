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
 */

const express = require('express');
const authController = require('./../controllers/authController');

const router = express.Router();

/**
 * @swagger
 *  tags:
 *   name: Users
 *   description: The Users managing API
 * /users/register:
 *  post:
 *     summary: Create a new book
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
 *         description: The created book.

 *       500:
 *         description: Some server error
 */
router.post('/register', authController.register);

/**
 * @swagger
 * /users/deleteByEmail:
 *   delete:
 *     summary: delete user by email address
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
router.delete('/deleteByEmail', authController.deleteByEmail);

/**
 * @swagger
 * /users/deleteByUid:
 *   delete:
 *     summary: delete user by Uid
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
router.delete('/deleteByUid', authController.deleteByUid);

router.patch('/updateUser', authController.updateUser);

module.exports = router;

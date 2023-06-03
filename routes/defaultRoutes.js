/**
 * @swagger
 * tags:
 *   name: Default
 *   description: The Default managing API
 * /:
 *   get:
 *     summary: Lists all the Default
 *     tags: [Default]
 *     responses:
 *       200:
 *         description: Home of the API
 */

const express = require('express')
const defaultController = require('./../controllers/defaultController')

const router = express.Router()

router.get('/', defaultController)

module.exports = router

const express = require('express');
const authController = require('./../controllers/authController');

const router = express.Router();

router.post('/register', authController.register);
router.post('/deleteByEmail', authController.deleteByEmail);
router.post('/deleteByUid', authController.deleteByUid);

module.exports = router;

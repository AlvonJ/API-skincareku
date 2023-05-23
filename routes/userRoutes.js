const express = require('express');
const authController = require('./../controllers/authController');

const router = express.Router();

router.post('/register', authController.register);
router.delete('/deleteByEmail', authController.deleteByEmail);
router.delete('/deleteByUid', authController.deleteByUid);

module.exports = router;

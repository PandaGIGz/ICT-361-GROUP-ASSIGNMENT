const express = require('express');
const router = express.Router();
const studentController = require('../controllers/student.controller');
const authMiddleware = require('../middleware/auth');

router.get('/', authMiddleware, studentController.getStudents);

module.exports = router;

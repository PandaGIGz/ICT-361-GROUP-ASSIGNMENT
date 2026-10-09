const express = require('express');
const router = express.Router();
const groupController = require('../controllers/group.controller');

router.get('/my-groups', groupController.getMyGroups);
router.post('/create', groupController.createGroup);
router.post('/:id/join', groupController.joinGroup);

module.exports = router;

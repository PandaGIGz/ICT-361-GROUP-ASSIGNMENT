const db = require('../db');

exports.getStudents = async (req, res) => {
  try {
    const [rows] = await db.query("SELECT id, identity, name, email, role, created_at FROM users WHERE role = 'student'");
    res.json({ success: true, students: rows });
  } catch (err) {
    res.status(500).json({ success: false, message: err.message });
  }
};

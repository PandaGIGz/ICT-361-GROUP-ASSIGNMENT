const db = require('../db');

// Ensure lab groups tables exist
async function ensureGroupTables() {
  try {
    await db.query(`
      CREATE TABLE IF NOT EXISTS lab_groups (
        id INT AUTO_INCREMENT PRIMARY KEY,
        group_name VARCHAR(100) NOT NULL,
        course_code VARCHAR(20) NOT NULL,
        created_by INT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
      )
    `);

    await db.query(`
      CREATE TABLE IF NOT EXISTS group_members (
        id INT AUTO_INCREMENT PRIMARY KEY,
        group_id INT NOT NULL,
        user_id INT NOT NULL,
        joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (group_id) REFERENCES lab_groups(id) ON DELETE CASCADE,
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
      )
    `);
  } catch (err) {
    console.error("Group table check error:", err.message);
  }
}
ensureGroupTables();

// List my groups
exports.getMyGroups = async (req, res) => {
  try {
    const userId = req.user.id;
    const [rows] = await db.query(`
      SELECT g.* FROM lab_groups g
      JOIN group_members m ON g.id = m.group_id
      WHERE m.user_id = ?
    `, [userId]);

    res.json({ success: true, groups: rows });
  } catch (err) {
    res.status(500).json({ success: false, message: err.message });
  }
};

// Create Group
exports.createGroup = async (req, res) => {
  try {
    const { group_name, course_code } = req.body;
    const userId = req.user.id;

    if (!group_name || !course_code) {
      return res.status(400).json({ success: false, message: 'Group name and course code are required.' });
    }

    const [result] = await db.query(
      "INSERT INTO lab_groups (group_name, course_code, created_by) VALUES (?, ?, ?)",
      [group_name, course_code, userId]
    );

    const groupId = result.insertId;

    // Automatically add creator as member
    await db.query(
      "INSERT INTO group_members (group_id, user_id) VALUES (?, ?)",
      [groupId, userId]
    );

    res.json({
      success: true,
      message: 'Lab group created successfully!',
      group: { id: groupId, group_name, course_code, created_by: userId }
    });
  } catch (err) {
    res.status(500).json({ success: false, message: err.message });
  }
};

// Join Group
exports.joinGroup = async (req, res) => {
  try {
    const groupId = req.params.id;
    const userId = req.user.id;

    // Check if group exists
    const [groups] = await db.query("SELECT * FROM lab_groups WHERE id = ?", [groupId]);
    if (groups.length === 0) {
      return res.status(404).json({ success: false, message: 'Lab group not found.' });
    }

    // Check if already member
    const [existing] = await db.query("SELECT * FROM group_members WHERE group_id = ? AND user_id = ?", [groupId, userId]);
    if (existing.length > 0) {
      return res.status(400).json({ success: false, message: 'Already a member of this group.' });
    }

    await db.query(
      "INSERT INTO group_members (group_id, user_id) VALUES (?, ?)",
      [groupId, userId]
    );

    res.json({ success: true, message: 'Successfully joined lab group!' });
  } catch (err) {
    res.status(500).json({ success: false, message: err.message });
  }
};

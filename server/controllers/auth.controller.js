const db = require('../db');
const jwt = require('jsonwebtoken');
require('dotenv').config();

const SECRET_KEY = process.env.JWT_SECRET || 'gaidonsichone_ict361_secret_key';

// Ensure tables exist
async function ensureTables() {
  try {
    await db.query(`
      CREATE TABLE IF NOT EXISTS users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        identity VARCHAR(255) UNIQUE NOT NULL,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) UNIQUE NOT NULL,
        role ENUM('student', 'lecturer', 'admin') DEFAULT 'student',
        password VARCHAR(255) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
      )
    `);
  } catch (err) {
    console.error("Auth table check error:", err.message);
  }
}
ensureTables();

exports.login = async (req, res) => {
  try {
    const { identity, password } = req.body;
    if (!identity || !password) {
      return res.status(400).json({ success: false, message: 'Identity and password are required.' });
    }

    const [rows] = await db.query(
      "SELECT * FROM users WHERE identity = ? OR email = ?",
      [identity, identity]
    );

    if (rows.length === 0) {
      return res.status(401).json({ success: false, message: 'Invalid credentials.' });
    }

    const user = rows[0];
    if (user.password !== password) {
      return res.status(401).json({ success: false, message: 'Invalid credentials.' });
    }

    const token = jwt.sign(
      { id: user.id, identity: user.identity, email: user.email, role: user.role },
      SECRET_KEY,
      { expiresIn: '7d' }
    );

    res.json({
      success: true,
      message: 'Login successful!',
      token,
      user: {
        id: user.id,
        name: user.name,
        email: user.email,
        identity: user.identity,
        role: user.role
      }
    });
  } catch (err) {
    res.status(500).json({ success: false, message: err.message });
  }
};

exports.register = async (req, res) => {
  try {
    const { identity, name, email, password, role } = req.body;
    if (!identity || !name || !email || !password) {
      return res.status(400).json({ success: false, message: 'All registration fields are required.' });
    }

    const userRole = role || 'student';

    const [existing] = await db.query("SELECT * FROM users WHERE email = ? OR identity = ?", [email, identity]);
    if (existing.length > 0) {
      return res.status(400).json({ success: false, message: 'User with this email or identity already exists.' });
    }

    const [result] = await db.query(
      "INSERT INTO users (identity, name, email, role, password) VALUES (?, ?, ?, ?, ?)",
      [identity, name, email, userRole, password]
    );

    const token = jwt.sign(
      { id: result.insertId, identity, email, role: userRole },
      SECRET_KEY,
      { expiresIn: '7d' }
    );

    res.json({
      success: true,
      message: 'Registration successful!',
      token,
      user: {
        id: result.insertId,
        name,
        email,
        identity,
        role: userRole
      }
    });
  } catch (err) {
    res.status(500).json({ success: false, message: err.message });
  }
};

const express = require('express');
const cors = require('cors');
require('dotenv').config();

const authRoutes = require('./routes/auth.routes'); // SRS auth
const groupRoutes = require('./routes/groups.routes'); // Lab Manager groups
const studentRoutes = require('./routes/students.routes'); // SRS students
const authMiddleware = require('./middleware/auth'); // SRS JWT check

const app = express();
app.use(cors());
app.use(express.json());

app.get('/', (req, res) => {
  res.send('Merged MUConnect Backend (Student Registration + Lab Group Manager) is running!');
});

// Public Auth routes (login / register)
app.use('/api/auth', authRoutes);

// Protected routes (uses same JWT from SRS login)
app.use('/api/students', authMiddleware, studentRoutes);
app.use('/api/groups', authMiddleware, groupRoutes);

const PORT = process.env.PORT || 3000;
app.listen(PORT, '0.0.0.0', () => {
  console.log(`Merged backend running on http://0.0.0.0:${PORT}`);
});

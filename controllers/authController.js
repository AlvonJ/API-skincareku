const admin = require('firebase-admin');

exports.signup = async (req, res) => {
  const newUser = await admin.auth().createUser({
    email: req.body.email,
    password: req.body.password,
    emailVerified: false,
    disabled: false,
  });

  res.status(201).json({
    status: 'success',
    data: {
      user: newUser,
    },
  });
};

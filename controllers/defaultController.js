const catchAsync = require('../utils/catchAsync');

module.exports = catchAsync(async (req, res, next) => {
  res.status(200).json({
    status: 'Connected to SkinCareku API',
    data: null,
  });
});

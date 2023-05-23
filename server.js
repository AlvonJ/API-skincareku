process.on('uncaughtException', err => {
  console.log('UNCAUGHT EXCEPTION!');
  console.log(err.name, err.message);

  process.exit(1);
});

const app = require('./app');

const port = process.env.PORT || 8080;

const server = app.listen(port, () => {
  console.log(`Server running on port ${port}...`);
});

process.on('unhandledRejection', err => {
  console.log('UNHANDLED REJECTION!');
  console.log(err.name, err.message);

  server.close(() => {
    process.exit(1);
  });
});

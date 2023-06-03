const express = require('express')
const admin = require('firebase-admin')
const credentials = require('./serviceAccountKey.json')
const userRouter = require('./routes/userRoutes')
const defaultRouter = require('./routes/defaultRoutes')
const globalErrorHandler = require('./controllers/errorController')
const AppError = require('./utils/appError')
const swaggerJsDoc = require('swagger-jsdoc')
const swaggerUi = require('swagger-ui-express')

const app = express()

admin.initializeApp({
  credential: admin.credential.cert(credentials)
})

app.use(express.json())

app.use(express.urlencoded({ extended: true }))

//swagger
const options = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'SkinCareku API Documentation',
      version: '1.0',
      description: "This is SkinCareku's API Documentation for Capstone Project"
    },
    servers: [
      {
        // development
        url: 'http://localhost:8080/'
      },
      {
        // release
        url: 'https://services-skincareku-5ctldki4wq-et.a.run.app/'
      }
    ]
  },
  apis: ['./routes/*.js']
}
const specs = swaggerJsDoc(options)
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(specs))

// ROUTES
app.use('/users', userRouter)

app.use('/', defaultRouter)

app.all('*', (req, res, next) => {
  next(new AppError(`Can't find ${req.originalUrl} on this server!`, 404))
})

app.use(globalErrorHandler)

module.exports = app

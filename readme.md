# SkinCareku

SkinCareku is an application that can detect facial skin health and provide suitable
treatment or skincare recommendations. This is because many people, not only women but
also men, are becoming more aware of the importance of skin health. The weather
conditions in Indonesia require us to take care of our facial skin to maintain its brightness
and health.

Often there is a mismatch between skincare and skin types, and many people spend a lot
of money trying various skincare products. With our application, we hope to help users
identify their skin issues and obtain suitable skincare recommendations.

## SkinCareku API

SkinCareku API provides an API to create, read, update, and delete (CRUD) the data contained in the Cloud Firestore.

The following are the services contained in the SkinCareku API:

- Manage User Data

&emsp;&emsp;Register - Get one user data - Delete by email - Delete by user ID - Update user

- Manage Product Data

&emsp;&emsp;Get all products - Get filtered products - Get one product

## Tech Stack

- [Node.js](https://nodejs.org/)
- [Express.js](https://expressjs.com/)
- [Swagger UI](https://swagger.io/tools/swagger-ui/)
- [Firebase authentication](https://firebase.google.com/docs/auth?hl=id)
- [Firestore](https://firebase.google.com/docs/firestore?hl=id)
- [Google Cloud Run](https://cloud.google.com/run?hl=id)

## Documentation

The following is the API documentation created using the [Swagger UI](https://swagger.io/tools/swagger-ui/) and deployed using [Google Cloud Run](https://cloud.google.com/run?hl=id).

[https://services-skincareku-5ctldki4wq-et.a.run.app/api-docs/](https://services-skincareku-5ctldki4wq-et.a.run.app/api-docs/)

## Installation

Download and use the package manager [npm](https://www.npmjs.com/).

Clone repository

```bash
git clone -b main https://github.com/AlvonJ/API-skincareku.git
cd API-skincareku
```

Install the dependencies and start the server.

```bash
# install dependencies
npm i
# install nodemon globally
npm i nodemon -g
# run server
npm run dev
```

## Author

C226DSX0853 - Alvon Jovanus Chandra - Universitas Kristen Maranatha

C226DSX0859 - Immanuel Kurniawan David Airlambang - Universitas Kristen Maranatha

> Note: This project is made for Capstone Project at Bangkit Academy 2023.

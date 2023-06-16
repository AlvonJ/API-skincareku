# SkinCareku

SkinCareku is an application that can detect facial skin health and provide suitable
treatment or skincare recommendations. This is because many people, not only women but
also men, are becoming more aware of the importance of skin health. The weather
conditions in Indonesia require us to take care of our facial skin to maintain its brightness
and health.

Often there is a mismatch between skincare and skin types, and many people spend a lot
of money trying various skincare products. With our application, we hope to help users
identify their skin issues and obtain suitable skincare recommendations.

## Model API

Model API provides an API to predict facial problems that users have such as acne, comedo, and clean face.

## Tech Stack

- [Python](https://www.python.org/)
- [Flask](https://flask.palletsprojects.com/)
- [Tensorflow](https://www.tensorflow.org/)
- [Google Compute Engine](https://cloud.google.com/compute)

## Tools

- [Visual Studio Code](https://code.visualstudio.com/)
- [Postman](https://www.postman.com/)

## Documentation

API is deployed using [Google Compute Engine](https://cloud.google.com/compute)

### URL

[http://34.101.209.86:5000/](http://34.101.209.86:5000/)

### Usage

| Method | Endpoint   | Request Body | Description                  |
| ------ | ---------- | ------------ | ---------------------------- |
| `POST` | `/predict` | image: file  | Predict facial image problem |

### Result

- Status code : `200`
- Content :

```json
{
  "prediction": {
    "acne": 0.798438549041748,
    "clean": 0.00010735754040069878,
    "comedo": 0.20145408809185028
  }
}
```

## Installation

Download and use the package manager [pip](https://pypi.org/project/pip/).

Clone repository

```bash
git clone -b model-api https://github.com/AlvonJ/API-skincareku.git
cd API-skincareku
```

Install the dependencies and start the server.

```bash
# install dependencies
pip install numpy==1.22.2 tensorflow==2.12.0 Flask==2.3.2 Pillow==9.5.0
# run server
py app.py
```

## Author

C226DSX0853 - Alvon Jovanus Chandra - Universitas Kristen Maranatha

C226DSX0859 - Immanuel Kurniawan David Airlambang - Universitas Kristen Maranatha

> Note: This project is made for Capstone Project at Bangkit Academy 2023.

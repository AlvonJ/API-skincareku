from flask import Flask, request, jsonify
import numpy as np
import tensorflow as tf
from PIL import Image

app = Flask(__name__)

# Load the TFLite model
interpreter = tf.lite.Interpreter(model_path='./SkincareKu.tflite')
interpreter.allocate_tensors()
input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()


@app.route("/predict", methods=["POST"])
def predict():
    # Get the image from the request payload
    image = request.files['image']
    img = Image.open(image)
    img = img.resize((150, 150))
    img = np.float32(img)
    img_array = np.array(img)
    img_array = img_array / 255.0

    # Run inference
    interpreter.set_tensor(input_details[0]['index'], [img_array])
    interpreter.invoke()
    output = interpreter.get_tensor(output_details[0]['index'])

    outputList = output.tolist()[0]

    outputJson = {
        "acne": outputList[0],
        "clean": outputList[1],
        "comedo": outputList[2]
    }
    
    # Return the prediction result.
    response = {'prediction': outputJson}
    return jsonify(response)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
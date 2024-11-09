from flask import Flask, request, jsonify
import google.generativeai as genai

app = Flask(__name__)

    # Set your Gemini API key
GOOGLE_API_KEY = "AIzaSyDzgz7fEK83KY7FsWDIRhhsKk46yMLBY9k"
genai.configure(api_key=GOOGLE_API_KEY)

@app.route('/')
def home():
        return jsonify(message="Welcome to the Citizen Complaint and Service Request API")

@app.route('/simplify', methods=['POST'])
def simplify_text():    
        data = request.json
        legal_text = data.get('legal_text')

        # Use Gemini API to simplify the text
        model = genai.GenerativeModel('gemini-pro')
        response = model.generate_content(f"Simplify this legal text: {legal_text}")

        simplified_text = response.text.strip()
        return jsonify({'simplified_text': simplified_text})

if __name__ == '__main__':
        app.run(host='0.0.0.0', port=5000)
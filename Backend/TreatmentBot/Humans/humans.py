from flask import Flask, request, jsonify
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score
from marshmallow import Schema, fields, ValidationError

# Define the Flask app
app = Flask(__name__)

# Load the dataset and initialize the model
file_path = 'Disease_symptom_with_treatments.csv'
df = pd.read_csv(file_path)

# Define a dictionary with treatment suggestions based on diseases
treatments = {
    "Influenza": "Rest, fluids, antiviral medications, over-the-counter pain relievers",
    "Common Cold": "Rest, hydration, decongestants, saline nasal spray",
    "Eczema": "Moisturizers, topical steroids, avoiding irritants, antihistamines",
    "Asthma": "Inhalers (bronchodilators), corticosteroids, avoiding triggers",
}

# Map treatments to diseases in a new column
df['Treatment'] = df['Disease'].map(treatments)

# Convert categorical variables to numerical (Yes/No, Gender, Blood Pressure, etc.)
df['Fever'] = df['Fever'].map({'Yes': 1, 'No': 0})
df['Cough'] = df['Cough'].map({'Yes': 1, 'No': 0})
df['Fatigue'] = df['Fatigue'].map({'Yes': 1, 'No': 0})
df['Difficulty Breathing'] = df['Difficulty Breathing'].map({'Yes': 1, 'No': 0})
df['Gender'] = df['Gender'].map({'Male': 1, 'Female': 0})
df['Blood Pressure'] = df['Blood Pressure'].map({'Low': 0, 'Normal': 1})
df['Cholesterol Level'] = df['Cholesterol Level'].map({'Normal': 1})

# Define feature columns and the target variable
X = df[['Fever', 'Cough', 'Fatigue', 'Difficulty Breathing', 'Age', 'Gender', 'Blood Pressure', 'Cholesterol Level']]
y = df['Disease']

# Split the data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Train a Decision Tree Classifier
clf = DecisionTreeClassifier()
clf.fit(X_train, y_train)

# Define Marshmallow schema for input validation
class SymptomInputSchema(Schema):
    fever = fields.Int(required=True)
    cough = fields.Int(required=True)
    fatigue = fields.Int(required=True)
    difficulty_breathing = fields.Int(required=True)
    age = fields.Int(required=True)
    gender = fields.Int(required=True)
    blood_pressure = fields.Int(required=True)
    cholesterol_level = fields.Int(required=True)

symptom_input_schema = SymptomInputSchema()

# Endpoint to predict the disease and return the treatment
@app.route("/predict", methods=["POST"])
def predict_disease():
    try:
        symptoms = symptom_input_schema.load(request.json)
    except ValidationError as err:
        return jsonify(err.messages), 400

    # Convert symptoms to a DataFrame
    input_data = pd.DataFrame([symptoms.values()], columns=X.columns)

    # Predict the disease
    predicted_disease = clf.predict(input_data)[0]

    # Get the corresponding treatment
    treatment = treatments.get(predicted_disease, "No treatment available")

    return jsonify({"predicted_disease": predicted_disease, "treatment": treatment})

# Root endpoint for checking if the API is running
@app.route("/")
def read_root():
    return jsonify({"message": "Disease Prediction API is running."})

if __name__ == "__main__":
    app.run(debug=True)

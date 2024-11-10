import pandas as pd
from flask import Flask, request, jsonify
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.preprocessing import LabelEncoder
from sklearn.pipeline import make_pipeline
from sklearn.compose import ColumnTransformer
from sklearn.preprocessing import OneHotEncoder
import joblib

# Initialize Flask app
app = Flask(__name__)

# Load and preprocess data
data = pd.read_csv('data_with_treatments.csv')
data['CombinedSymptoms'] = data[['symptoms1', 'symptoms2', 'symptoms3', 'symptoms4', 'symptoms5']].apply(
    lambda x: ' '.join(str(i) for i in x), axis=1)

# Encode target variable (SuggestedTreatment) as integers
treatment_encoder = LabelEncoder()
data['TreatmentEncoded'] = treatment_encoder.fit_transform(data['SuggestedTreatment'])

# Define features and target
features = data[['AnimalName', 'CombinedSymptoms']]
target = data['TreatmentEncoded']

# Set up column transformer and pipeline
preprocessor = ColumnTransformer(
    transformers=[
        ('animal', OneHotEncoder(handle_unknown='ignore'), ['AnimalName']),
        ('symptoms', OneHotEncoder(handle_unknown='ignore'), ['CombinedSymptoms'])
    ])

# Split data and create pipeline
X_train, X_test, y_train, y_test = train_test_split(features, target, test_size=0.2, random_state=42)
model = make_pipeline(preprocessor, DecisionTreeClassifier())
model.fit(X_train, y_train)

# Save the model for persistence
joblib.dump(model, 'animal_treatment_model.joblib')
joblib.dump(treatment_encoder, 'treatment_encoder.joblib')

# Load the model and encoder
model = joblib.load('animal_treatment_model.joblib')
treatment_encoder = joblib.load('treatment_encoder.joblib')

# Define prediction endpoint
@app.route("/predict_treatment/", methods=["POST"])
def predict_treatment():
    data = request.get_json()
    animal = data['animal']
    symptoms = data['symptoms']
    combined_symptoms = ' '.join(symptoms)
    input_data = pd.DataFrame({'AnimalName': [animal], 'CombinedSymptoms': [combined_symptoms]})
    treatment_encoded = model.predict(input_data)[0]
    treatment = treatment_encoder.inverse_transform([treatment_encoded])[0]
    return jsonify({"animal": animal, "symptoms": symptoms, "predicted_treatment": treatment})

# Run this code with `flask run`
if __name__ == "__main__":
    app.run(debug=True)

from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///citizen_complaints.db'
db = SQLAlchemy(app)

class ServiceRequest(db.Model):
        id = db.Column(db.Integer, primary_key=True)
        name = db.Column(db.String(100), nullable=False)
        address = db.Column(db.String(200), nullable=False)
        phoneNumber = db.Column(db.String(20), nullable=False)
        email = db.Column(db.String(100), nullable=False)
        writtenDetails = db.Column(db.Text, nullable=False)

class ComplaintRequest(db.Model):
        id = db.Column(db.Integer, primary_key=True)
        name = db.Column(db.String(100), nullable=False)
        address = db.Column(db.String(200), nullable=False)
        phoneNumber = db.Column(db.String(20), nullable=False)
        email = db.Column(db.String(100), nullable=False)
        category = db.Column(db.String(100), nullable=False)
        type = db.Column(db.String(100), nullable=False)
        writtenDetails = db.Column(db.Text, nullable=False)

with app.app_context():
        db.create_all()

@app.route('/request-service', methods=['POST'])
def request_service():
        data = request.json
        new_service = ServiceRequest(
            name=data['name'],
            address=data['address'],
            phoneNumber=data['phoneNumber'],
            email=data['email'],
            writtenDetails=data['writtenDetails']
        )
        db.session.add(new_service)
        db.session.commit()
        return jsonify({"message": "Service request submitted successfully"}), 201

@app.route('/raise-complaint', methods=['POST'])
def raise_complaint():
        data = request.json
        new_complaint = ComplaintRequest(
            name=data['name'],
            address=data['address'],
            phoneNumber=data['phoneNumber'],
            email=data['email'],
            category=data['category'],
            type=data['type'],
            writtenDetails=data['writtenDetails']
        )
        db.session.add(new_complaint)
        db.session.commit()
        return jsonify({"message": "Complaint submitted successfully"}), 201

if __name__ == '__main__':
        app.run(debug=True)
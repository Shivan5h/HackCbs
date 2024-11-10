"""Run the database command for once to create the database and then run the flask api command :)"""

from flask import Flask, request, jsonify
import sqlite3

app = Flask(__name__)

        # Function to get database connection
def get_db_connection():
            conn = sqlite3.connect("polls.db")
            conn.row_factory = sqlite3.Row
            return conn

        # Endpoint to create a new poll
@app.route('/create_poll', methods=['POST'])
def create_poll():
            data = request.get_json()
            question = data.get('question')

            conn = get_db_connection()
            cursor = conn.cursor()
            cursor.execute("INSERT INTO polls (question) VALUES (?)", (question,))
            conn.commit()
            poll_id = cursor.lastrowid
            conn.close()

            return jsonify({"message": "Poll created successfully", "poll_id": poll_id}), 201

        # Endpoint for citizens to participate in a poll
@app.route('/participate', methods=['POST'])
def participate():
            data = request.get_json()
            poll_id = data.get('poll_id')
            response = data.get('response')

            conn = get_db_connection()
            cursor = conn.cursor()
            cursor.execute("INSERT INTO responses (poll_id, response) VALUES (?, ?)", (poll_id, response))
            conn.commit()
            conn.close()

            return jsonify({"message": "Response submitted successfully"}), 200

        # Endpoint to view poll results
@app.route('/poll_results/<int:poll_id>', methods=['GET'])
def poll_results(poll_id):
            conn = get_db_connection()
            cursor = conn.cursor()

            # Get the poll question
            cursor.execute("SELECT question FROM polls WHERE poll_id = ?", (poll_id,))
            question = cursor.fetchone()

            if question is None:
                return jsonify({"message": "Poll not found"}), 404

            # Get all responses for the poll
            cursor.execute("SELECT response FROM responses WHERE poll_id = ?", (poll_id,))
            responses = cursor.fetchall()
            response_list = [response['response'] for response in responses]

            conn.close()

            return jsonify({
                "poll_id": poll_id,
                "question": question["question"],
                "responses": response_list
            }), 200

        # Endpoint to get all polls
@app.route('/get_polls', methods=['GET'])
def get_polls():
            conn = get_db_connection()
            cursor = conn.cursor()
            cursor.execute("SELECT poll_id, question FROM polls")
            polls = cursor.fetchall()
            poll_list = [{"id": poll["poll_id"], "question": poll["question"]} for poll in polls]
            conn.close()

            # Print the poll list to the terminal
            print(poll_list)

            return jsonify(poll_list), 200

if __name__ == "__main__":
            app.run(debug=True)

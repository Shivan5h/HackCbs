import sqlite3

def create_db():
    conn = sqlite3.connect("polls.db")
    cursor = conn.cursor()

    # Create table for storing poll questions
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS polls (
            poll_id INTEGER PRIMARY KEY AUTOINCREMENT,
            question TEXT NOT NULL
        )
    """)

    # Create table for storing responses
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS responses (
            response_id INTEGER PRIMARY KEY AUTOINCREMENT,
            poll_id INTEGER,
            response TEXT,
            FOREIGN KEY (poll_id) REFERENCES polls (poll_id)
        )
    """)

    conn.commit()
    conn.close()

if __name__ == "__main__":
    create_db()

import sqlite3

def deleteSpecificUser(user_id):
	conn = sqlite3.connect("medical_info.db")
	cursor = conn.cursor()
	cursor.execute("DELETE From User where user_id = ?",(user_id,))
	conn.commit()
	conn.close()
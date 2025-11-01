import sqlite3

def changeacceptancestatus(set_status, userid):
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	cursor.execute('''UPDATE User SET is_approved = ? WHERE user_id = ? ''',(set_status,userid))
	conn.commit()
	conn.close()

def update_user(userid, **userinfo):
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()

	for key, value in userinfo.items():
		
		if key == 'name':
			cursor.execute('''Update User set name = ? where user_id = ? ''',(value,userid))
		elif key == 'password':
			cursor.execute('''Update User set password = ? where user_id = ?''',(value,userid))
		elif key == 'block_status':
			cursor.execute('''Update User set block_status = ? where user_id = ?''',(value,userid))
		elif key == 'phone_number':
			cursor.execute('''Update User set phone_number = ? where user_id = ?''',(value,userid))
		elif key == 'email':
			cursor.execute('''Update User set email = ? where user_id = ? ''',(value,userid))
		elif key == 'address':
			cursor.execute('''Update User set address = ? where user_id = ? ''',(value, userid))
		elif key == 'pincode':
			cursor.execute('''Update User set pincode = ? where user_id = ?''',(value, userid))
	conn.commit()
	conn.close()
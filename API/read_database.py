import sqlite3

def authenticate(email, password):
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	cursor.execute('''select * from User where email = ? and password = ?''',(email, password))
	user = cursor.fetchone()
	conn.close()
	return user


def checkuser(user_id):
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	cursor.execute('''SELECT * from User where user_id = ?''',(user_id, ))
	user_details = cursor.fetchone()
	conn.close()
	if(user_details):
		return 1
	else : 
		return 0


def readalldata():
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	datajson = []
	cursor.execute('''SELECT * From User''')
	users = cursor.fetchall()
	for user in users:
		temp = {
			"Id" : user[0],
			"user_id" : user[1],
			"password" : user[2],
			"name" : user[3],
			"date_of_creation" : user[4],
			"is_approved" : user[5],
			"block_status" : user[6],
			"address" : user[7],
			"phone_number" : user[8],
			"email" : user[9],
			"pincode" : user[10]
		}
		datajson.append(temp)

	conn.close()
	return datajson


def readspecificuser(uid):
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	cursor.execute('''SELECT * from User Where user_id = ?''', (uid,))
	user = cursor.fetchone()
	datajson =  {
		"Id" : user[0],
		"user_id" : user[1],
		"password" : user[2],
		"name" : user[3],
		"date_of_creation" : user[4],
		"is_approved" : user[5],
		"block_status" : user[6],
		"address" : user[7],
		"phone_number" : user[8],
		"email" : user[9],
		"pincode" : user[10]
	}
	conn.close()
	return datajson


def getAllProducts():
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()

	cursor.execute('''SELECT * from store''')
	datajson = []
	products = cursor.fetchall()
	for product in products:
		temp = {
			"ID" : product[0],
			"product_id" : product[1],
			"name" : product[2],
			"price" : product[3],
			"category" : product[4],
			"stock" : product[5]		
		}
		datajson.append(temp)
	conn.close()
	return datajson

def getSpecificProduct(pid):
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	cursor.execute('''SELECT * from store Where product_id = ?''',(pid,))
	product = cursor.fetchone()
	datajson = {
		"ID" : product[0],
		"product_id" : product[1],
		"name" : product[2],
		"price" : product[3],
		"category" : product[4],
		"stock" : product[5]
	}
	conn.close()
	return datajson

def getAllorder():
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	cursor.execute('''SELECT * from order_details''')
	datajson = []
	orderdata = cursor.fetchall()
	for data in orderdata :
		temp = {
			"id" : data[0],
			"order_id" : data[1],
			"product_id" : data[2],
			"user_id" : data[3],
			"quantity" : data[4],
			"total_price" : data[5],
			"product_price" : data[6],
			"product_name" : data[7],
			"message" : data[8],
			"category" : data[9],
			"user_name" : data[10],
			"date_of_order_creation" : data[11],
			"isApproved" : data[12]

		}
		datajson.append(temp)
	conn.close()
	return datajson


def getApproveOrders():
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	cursor.execute('''SELECT * from order_details where isApproved = 1''')
	datajson = []
	orderdata = cursor.fetchall()
	for data in orderdata :
		temp = {
			"id" : data[0],
			"order_id" : data[1],
			"product_id" : data[2],
			"user_id" : data[3],
			"quantity" : data[4],
			"total_price" : data[5],
			"product_price" : data[6],
			"product_name" : data[7],
			"message" : data[8],
			"category" : data[9],
			"user_name" : data[10],
			"date_of_order_creation" : data[11],
			"isApproved" : data[12]

		}
		datajson.append(temp)
	conn.close()
	return datajson


def readorderHistory(user_id):
	conn = sqlite3.connect("medical_info.db")
	cursor = conn.cursor()
	cursor.execute('''Select * from sell_history where user_id = ?''',(user_id,))
	orders = cursor.fetchall()
	datajson = []
	for order in orders:
		temp = {
			"ID" : order[0],
			"Sell_ID"  :  order[1],
			"Product_Id" : order[2],
			"Quantity" : order[3],
			"Remaning_Stock" : order[4],
			"Date Of Selling" : order[5],
			"Price" : order[6],
			"Product_name" : order[7],
			"User_ID" : order[8]
		}
		datajson.append(temp)	
	conn.close()
	return datajson

def readuserstock():
	conn = sqlite3.connect("medical_info.db")
	cursor = conn.cursor()
	cursor.execute('''SELECT * from User_Stock''')
	stocks = cursor.fetchall()
	datajson = []
	for data in stocks:
		temp = {
			"Product ID" : data[0],
			"Product Name" : data[1],
			"Stock" : data[2],
			"Price" : data[3],
			"Category" : data[4],
			"User ID" : data[5],
			"User Name" : data[6]
		}
		datajson.append(temp)
	
	conn.close()
	return datajson
import sqlite3, uuid
from datetime import date

def add_user( name, address, phone_number, pincode,	email,	password ):
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	dateofcreation = date.today()
	uid = str(uuid.uuid4())
	cursor.execute(
		'''
		insert into User(user_id, password, name, date_of_creation, is_approved, block_status, address, phone_number, email ,pincode)
		values(?,?,?,?,?,?,?,?,?,?)
		''',(uid, password, name, dateofcreation, 0, 0, address, phone_number, email, pincode)
	)
	conn.commit()
	conn.close()
	return uid
	
def add_product(name,price,category,stock):
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	product_id = str(uuid.uuid4())
	cursor.execute(
		'''
		insert into store(product_id,name,price,category,stock)
		values(?,?,?,?,?)
		''',(product_id,name,price,category,stock)
	)
	conn.commit()
	conn.close()
	return product_id


def add_order_details(product_id, user_id, quantity, total_price, product_price, product_name, message, category, user_name, isApproved):
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()
	oid = str(uuid.uuid4())
	date_of_order = date.today()
	cursor.execute(
		'''
		insert into order_details(order_id, product_id, user_id, quantity, total_price, product_price, product_name, message, category, user_name, date_of_order_creation, isApproved)
		values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
		''',(oid, product_id, user_id, quantity, int(total_price), int(product_price), product_name, message, category, user_name, date_of_order, isApproved)
	)
	conn.commit()
	conn.close()
	return oid


def addSellHistory(product_id, quantity, remaning_stock, price, product_name, user_id):
	conn = sqlite3.connect("medical_info.db")
	cursor = conn.cursor()
	sid = str(uuid.uuid4())
	date_of_selling = date.today()
	cursor.execute(
		'''
		insert into sell_history(sell_id,product_id, quantity, remaning_stock, date_of_selling, price, product_name, user_id)
		values(?, ?, ?, ?, ?, ?, ?, ?)
		''',(sid, product_id,quantity, remaning_stock, date_of_selling, price, product_name, user_id)
	)
	conn.commit()
	conn.close()
	return sid

def addUserStock(pid, pname, stock, price, category, uid, user_name):
	conn = sqlite3.connect("medical_info.db")
	cursor =  conn.cursor()
	cursor.execute('''
		insert into User_stock(product_id, product_name, stock, price, category, user_id, user_name)
		values(?, ?, ?, ?, ?, ?, ? )
		''',(pid, pname, stock, price, category, uid, user_name)
	)
	conn.commit()
	conn.close()
	

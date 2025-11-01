import sqlite3


def createtables():
	conn = sqlite3.connect('medical_info.db')
	cursor = conn.cursor()

	cursor.execute(
		'''CREATE TABLE IF NOT EXISTS User (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		user_id varchar(255),
		password varchar(255),
		name varchar(255),
		date_of_creation date,
		is_approved BOOLEAN,
		block_status BOOLEAN,
		address varchar(255),
		phone_number varchar(255),
		email varchar(255),
		pincode varchar(255))
		'''
	)

	cursor.execute(
		'''CREATE TABLE  IF NOT EXISTS store (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		product_id varchar(255),
		name varchar(255),
		price float,
		category varchar(255),
		stock int(255))
		'''
	)

	cursor.execute(
		'''
		CREATE TABLE IF NOT EXISTS order_details(
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		order_id varchar(255),
		product_id varchar(255),
		user_id varchar(255),
		quantity int,
		total_price int,
		product_price int,
		product_name varchar(255),
		message varchar(255),
		category varchar(255),
		user_name varchar(255),
		date_of_order_creation date,
		isApproved boolean)
		'''
	)

	cursor.execute(
		'''
		Create table if not exists sell_history(
		id integer primary key autoincrement,
		sell_id varchar(255),
		product_id varcher(255),
		quantity int,
		remaning_stock int,
		date_of_selling date,
		price float,
		product_name varchar(255),
		user_id varchar(255))
		'''
	)

	cursor.execute(
		'''
		Create table if not exists User_stock(
		id integer primary key autoincrement,
		product_id varchar(255),
		product_name varchar(255),
		stock int,
		price float,
		category varchar(255),
		user_id varchar(255),
		user_name varchar(255)
		)
		'''
	)

	conn.commit()
	conn.close()

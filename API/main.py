from flask import Flask , jsonify, request
from Databases_creation import createtables
from insert_database import add_user,add_product, add_order_details, addSellHistory, addUserStock
from read_database import authenticate, readalldata, readspecificuser, checkuser, getAllProducts, getSpecificProduct, getAllorder, getApproveOrders, readorderHistory, readuserstock
from update_database import changeacceptancestatus,update_user
from delete import deleteSpecificUser

app = Flask("app")

def str_to_bool(val):
    return str(val).lower() in ("true", "1", "yes", "on")


@app.route("/createuser", methods=["POST"])
def createUser():
	try:
		Password = request.form['password']
		Name = request.form['name']
		Address = request.form['address']
		Phone_number = request.form['phone_number']
		Email = request.form['email']
		Pincode = request.form['pincode']

		user = add_user(password= Password, name = Name, address= Address, phone_number= Phone_number, email= Email, pincode= Pincode)
		return jsonify({"message " : user, "status ":200})

	except Exception as e:
		return jsonify({"message " : str(e), "status " : 400}) 
	

	

@app.route("/login", methods=["POST"])
def login_user():
	try:
		email = request.form['email']
		password = request.form['password']
		user = authenticate(email= email, password= password)
		if(user):
			return jsonify({"UserId" : user[1], "Name " : user[3], "status " : 200})
		else:
			return jsonify({"Error  " : "Invaild Email and Password ", "status " : 400})
	except Exception as e:
		return jsonify({"message " : str(e),"status : " : 400})
	

	
@app.route("/listalldata", methods = ['GET'])
def viewalldata():
	try:
		data = readalldata()
		return jsonify(data)

	except Exception as e :
		return jsonify({"An Error Occurred while fething data : " : str(e), "status" : 400})
	

	
@app.route("/readspecificuser", methods = ['POST'])
def readuserusinguid():
	try : 
		userid = request.form['user_id']
		data = readspecificuser(uid = userid)
	
		if(data):
			return jsonify(data)
		else:
			return jsonify({"No such user with provided userId "})
	except Exception as e:
		return jsonify({"Ann Error occurred while reading the data" : str(e), "status" : 400})
	


	
@app.route("/approveuser", methods = ['PATCH'])
def changeuseracceptance():
	try:
		uid = request.form['user_id']
		Is_approved_str = request.form['is_approved']
		Is_approved_bool = str_to_bool(Is_approved_str)
		userdata = checkuser(uid)
		if(userdata == 1):
			
			changeacceptancestatus(userid = uid, set_status = Is_approved_bool)
			return jsonify({"Message":"Updated Successfully", "status" : 200})
		else:
			return jsonify({"Message":"No Such User Exists ", "status" : 400})
	except Exception as e :
		return jsonify({"Ann Error occurred while updating " : str(e), "status" : 400})
	

	
	
@app.route("/updateuser", methods = ["PATCH"])
def Update_user():
	try:
		userId = request.form['user_id']
		updteuser = {}
		for key, value in request.form.items():
			if key != 'user_id':
				updteuser[key] = value
		update_user(userid = userId, **updteuser)
		return jsonify({"Message":"User Update Successfully","status" : 200})
	except Exception as e:
		return jsonify({"An Error occurred ":str(e),"status" : 400})
	

@app.route("/addproduct", methods = ["POST"])
def addproduct():
	try:
		name = request.form['name']
		price = request.form['price']
		category = request.form['category']
		stock = request.form['stock']
		add_product(name= name,price= price,category= category, stock= stock)
		return jsonify({"Message" : "Product added successfully", "status" : 200})
		
	except Exception as e:
		return jsonify({"An Error Occurred ":str(e), "status" : 400})


@app.route("/getallproducts", methods = ["GET"])
def viewallproducts():
	try:
		data = getAllProducts()
		return jsonify(data)

	except Exception as e:
		return jsonify({"An Error Occurred WHile fetching Products : ":str(e),"status" : 400})
	
	
@app.route("/getspecificproducts", methods = ["POST"])
def getspecificProduct():
	try:
		product_id = request.form['product_id']
		product = getSpecificProduct(pid= product_id)
		return jsonify(product) 

	except Exception as e:
		return jsonify({"An error occurred ":str(e),"status" : 400})
	

@app.route("/addOrderDetails", methods = ["POST"])
def addorderdetails():
	try:
		pid = request.form["product_id"]
		uid = request.form["user_id"]
		quantity = request.form["quantity"]
		total_price = request.form["total_price"]
		product_price = request.form["product_price"]
		product_name = request.form["product_name"]
		msg = request.form["message"]
		category = request.form["category"]
		user_name = request.form["user_name"]
		isApprove_str = request.form['IsApproved']
		is_approve_bool = str_to_bool(isApprove_str)
		oid = add_order_details(product_id = pid, user_id= uid, quantity= quantity,  total_price = total_price, product_price= product_price , product_name = product_name, message= msg, category= category, user_name = user_name, isApproved= is_approve_bool)

		return jsonify({"Message" : "Order created successfully","Order ID : ":oid, "status" : 200})
	except Exception as E :
		return jsonify({"An Error Occurred" : str(E),"status" : 400})
	

@app.route("/showAllOrderDetails", methods = ["GET"])
def showallorderdetails():
	try :
		order_data = getAllorder()
		return jsonify(order_data)
	except Exception as e:
		return jsonify({"An error Occurred : ":str(e), "status : " : 400})
	

@app.route("/deleteuserspecific",methods = ["DELETE"])
def deleteuser():
	try:
		uid = request.form["user_id"]
		deleteSpecificUser(user_id= uid)
		return jsonify({"Message : " : "User Data Deleted Successfully", "Status : " : 200})
		
	except Exception as error :
		return jsonify({"An error Occurred : " : str(error) , "status : " : 400})
	

@app.route("/getapproveorderlist", methods = ["GET"])
def getapproveorders():
	try:
		order = getApproveOrders()
		return jsonify(order)
	except Exception as error :
		return jsonify({"An Error occurred : " : str(error) , "status : " : 400})


@app.route("/addsellhistory", methods = ["POST"])
def Addsellhistory():
	try:
		product_id = request.form["product_id"]
		quantity = request.form["quantity"]
		remaining_stock = request.form["remaining_stock"]
		price = request.form["price"]
		product_name = request.form["product_name"]
		user_id = request.form["user_id"]
		addSellHistory(product_id = product_id, quantity= quantity, remaning_stock= remaining_stock, price= price, product_name= product_name, user_id= user_id)
		return	jsonify({"Message : " : "Order History Successfully created", "status : " : 200})
	except Exception as error :
		jsonify({"An Error occurred : " : str(error), "status : " : 400})


@app.route("/getSellHistory", methods = ["GET"])
def fetchsellhistory():
	try:
		user_id = request.form["User_ID"]
		History = readorderHistory(user_id= user_id)
		return jsonify(History)
	except Exception as error :
		 return jsonify({"An Error Occurred : " : str(error), "Status : " : 400})
	

@app.route("/adduserstock", methods = ["POST"])
def adduserstock():
	try:
		pid = request.form["product_id"]
		product_name = request.form["product_name"]
		stock = request.form["stock"]
		price = request.form["price"]
		category = request.form["category"]
		user_id = request.form["user_id"]
		user_name = request.form["user_name"]
		addUserStock(pid= pid, pname= product_name, stock= stock, price= price, category= category, uid = user_id, user_name= user_name)
		return jsonify({"Message : " : "User Stock Added Successfully" , "staus : ":200})
	except Exception as error : 
		return jsonify({"An Error Occurred : " : str(error) , "Status : " : 400})
	

@app.route("/ReadUserStock", methods = ["GET"])
def getuserstock():
	try :
		data = readuserstock()
		return jsonify(data)
	except Exception as error :
		return jsonify({"An Error Occurred : " : str(error) , " Status  : " : 400})


@app.route("/")
def start():
	return "Hello World"

if __name__ == "__main__":
	createtables()
	app.run(debug=True)
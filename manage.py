import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from firebase_admin import auth
import sys, os
 
# Main definition - constants
menu_actions  = {}  

def get_platform():
    platforms = {
        'linux1' : 'Linux',
        'linux2' : 'Linux',
        'darwin' : 'OS X',
        'win32' : 'Windows'
    }
    if sys.platform not in platforms:
        return sys.platform
    
    return platforms[sys.platform]

def clear():
	if(get_platform()=='Linux'):
		os.system('clear')
	else:
		os.system('cls')

def connect():
	print ("connecting...")
	# Fetch the service account key JSON file contents
	cred = credentials.Certificate('C:\\Users\\mpt\\Documents\\store-f1fdd-firebase-adminsdk-prhug-f50c36443b.json')
	# Initialize the app with a service account, granting admin privileges
	default_app = firebase_admin.initialize_app(cred, {'databaseURL': 'https://store-f1fdd.firebaseio.com/'}) 
	# As an admin, the app has access to read and write all data, regradless of Security Rules
	print("success")
	main_menu()

		
		
def main_menu():
		clear()
		print("Hello,\n")
		print("Please an action:")
		print("1. show users list")
		print("2. delete user")
		print("3. add user")
		print("\n0. Quit")
		choice = input(" >>  ")
		exec_menu(choice)
 
		return

	# Execute menu
def exec_menu(choice):
		clear()
	
		ch = choice.lower()
		if (ch == ''):
			menu_actions['main_menu']()
		else:
			try:
				menu_actions[ch]()
			except KeyError:
				print ("Invalid selection, please try again.\n")
				menu_actions['main_menu']()
		return
		
def showUsers():
# Start listing users from the beginning, 1000 at a time.
	page = auth.list_users()
	while page:
		for user in page.users:
			print ('User: ' , user.uid , 'email: ',user.email)
		# Get next batch of users.
		page = page.get_next_page()
	ref = db.reference('users')
	print(ref.get())
	_ = input("press any key to back to the menu...")
	main_menu()

def moreDetails(email,usrName,uid):
	first = input(" Insert first name:  ")
	last = input(" Insert last name:  ")
	city = input(" Insert city:  ")
	street = input(" Insert street: ")
	adress = input(" Insert address: ")
	ref = db.reference('users')
	isManager = input(" Is Manager?[true/false]<case sensitive> ")
	b = (isManager=='true')
	ref.child(uid).set({
		'address':adress,
		'city':city,
		'email':email,
		'firsrName':first,
		'lastName':last,
		'street':street,
		'userName':usrName,
		'manager':b
		})
	print("details add to table...")
	
def addUser():
	mail = input(" Insert Email:  ")
	passw = input(" Insert Password:  ")
	name = input(" Insert User Name:  ")
	user = auth.create_user(
		email=mail,
		email_verified=False,
		password=passw,
		display_name=name,
			disabled=False)
	print ('Sucessfully create user: {0}'.format(user.uid))
	more = input("Would you like to add more details for this user?[Y/n] ")
	if(more.lower()=="y"):
		moreDetails(mail,name,user.uid)
	_ = input("press any key to back to the menu...")
	main_menu()


	
	
def deleteUser():
	id = input(" Insert Email or Uid for user delete:  ")
	# Start listing users from the beginning, 1000 at a time.
	page = auth.list_users()
	while page:
		for user in page.users:
			if(id == user.uid or id == user.email):
				print ('Delete -->X User: ' , user.uid , 'email: ',user.email)
				auth.delete_user(user.uid )
				_ = input("press any key to back to the menu...")
				main_menu()
		# Get next batch of users.
		page = page.get_next_page()
	_ = input("No found such a user! \n press any key to back to the menu...")
	main_menu()		
		
# Exit program
def exit():
    sys.exit()
	

	
menu_actions = {
    'main_menu': main_menu,
    '1': showUsers,
    '2': deleteUser,
    '3': addUser,
    '0': exit,
}
# =======================
#      MAIN PROGRAM
# =======================
 
# Main Program
if __name__ == "__main__":
	connect()


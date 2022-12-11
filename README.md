# CS180-Project-5
Project 5 CS180 - L28

# Project 5: 
Project 5 consists of PJ-4 (required 4 Components (3 Core and 1 selection) + 1 extra component), but with concurrency, Network IO, and a GUI.

# Instructions
Open the marketServer.java and run the main method.
Open the Main.java class and run the main method, this can be for as many users as you wish.
Make sure all the text files is inside the files folders and all the Java files are inside the src folder.
The program should consist of 11 classes:
- Cart.java
- Customer.java
- Main.java
- marketServer.java
- Products.java
- ProductSales.java
- Sales.java
- Seller.java
- Server.java
- Stores.java

The program also contains 3 test files in the files folder:
- Accounts.txt
- Customer.txt
- Seller.txt

While running the program, make sure there isn't a space character at the end of the input, and that the correct inputs are inserted\
example: [Hello] but not [Hello ] 

# Submission List
The following group member submitted the following items:
Parth - Submitted Vocareum Workspace
Pranav - Submitted Report on Brightspace
Parth - Submitted Presentation on Brightspace

# Detailed Description of each Class

## marketServer.java
The markerServer.java main method starts a ServerSocket and allows multiple clients to run on different threads concurrently.

First, the marketServer.java class creates a ServerSocket

Then, it accepts a socket connection from multiple Clients (Main.java) (through a loop)
Finally, it sends the socket variable as a parameter to a new Server object that is run on a new thread


## Main.java
The Main.java main method is the client that sends JSONObjects to the server based on the users GUI inputs to process their requests.

First, The main method connectes to the server 

Then, it handles the users requests in the gui

Finally, it sends the respective request to the server and proceeds as necessary.


## Server.java
The Server main method is the Server that handles the clients requests.

First, the server get multiple "keys" from the JSONObject that the client sent to find out the reuqest that the client is requesting

Then, the server is getting all the information from the JSONObject that is necessary to continue the action

Next, it is processing the request and sending back any information necessary for the client to have.

Finally, the server is writing down all the serizlizable object data to the database for the other threads to read and implement.


## Customer.java
The Customer class implements serializable

It holds the customers ID, an arraylist of Carts (each cart is a product that the user want to buy), and a arraylist of PurchaseHistory objects (holds the info of the purchases of the customer)


## Cart.java
The Cart class implements serializable, it is a class that holds the info of a product the user wants to buy

It holds the ID of the user thats purchasing the object, the quantity of the product, and the id of that product.


## PurchaseHist.java
The PurchaseHist.java class implements serializable, is is the class that holds the info of the customers purchases

It is a class that holds the info of the customers id, the sellers id, the stores id, the products id, the quantity bought, and the price of each.


## Seller.java
The Seller class implements serializable

It holds the sellers ID, and an arraylist of the users Stores (the users Stores holds all the information of the sales and products)


## Stores.java
The Stores class implements serializable

It holds the storeID, name, sellerID, Arraylist of the storeProducts (the info of every product), and Sales of the product


## Products.java
The Products class implements serializable

It holds the information of the product, the id, name, description, price, quantity, and orderlimit.


## Sales.java
The Sales class implements serializable

It holds the sellerID, totalSales price, and an arraylist of productSales objects (the productSales class holds the information of each specific sale made)


## ProductSales.java
The ProductSales class implements serializable

It holds the productID, quantity, price, customerID, and productName. 

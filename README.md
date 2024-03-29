# Ebay Project


# Instructions
Open the marketServer.java and run the main method.
Open the Main.java class and run the main method, this can be for as many users as you wish.
Make sure all the text files are inside the files folders and all the Java files are inside the src folder.
The program should consist of 11 classes:
- Cart.java
- Customer.java
- Main.java
- MarketServer.java
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

Also, please make sure that the External Libraries openCSV and JSON are imported.

While running the program, make sure there isn't a space character at the end of the input, and that the correct inputs are inserted\
example: [Hello] but not [Hello ].

# Detailed Description of each Class

## MarketServer.java
The MarketServer.java main method starts a ServerSocket and allows multiple clients to run on different threads
concurrently.

First, the MarketServer.java class creates a ServerSocket.

Then, it accepts a socket connection from multiple Clients (Main.java) (through a loop).

Finally, it sends the socket variable as a parameter to a new Server object that is run on a new thread.


## Main.java
The Main.java main method is the client that sends JSONObjects to the server based on the user's GUI inputs to process their requests.

First, the main method connects to the server.

Then, it handles the user's requests in the GUI.

Finally, it sends the respective request to the server and proceeds as necessary.


## Server.java
The Server main method is the Server that handles the client's requests.

First, the server gets multiple "keys" from the JSONObject that the client sent to find out the request that the client is requesting.

Then, the server is getting all the information from the JSONObject that is necessary to continue the action.

Next, it is processing the request and sends back any information necessary for the client to have.

Finally, the server is writing down all the serializable object data to the database for the other threads to read and implement.


## Customer.java
The Customer class implements serializable.

It holds the customer's ID, an ArrayList of Carts (each cart is a product that the user wants to buy), and an ArrayList of PurchaseHistory objects (holds the info of the purchases of the customer).


## Cart.java
The Cart class implements serializable, it is a class that holds the info of a product the user wants to buy.

It holds the ID of the user that purchased the object, the quantity of the product, and the ID of that product.


## PurchaseHist.java
The PurchaseHist.java class implements serializable, it is the class that holds the info on the customer's purchases.

It is a class that holds the info of the customer's ID, the seller's ID, the store's ID, the product's ID, the quantity bought, and the price of each.


## Seller.java
The Seller class implements serializable.

It holds the seller's ID and an ArrayList of the user's Stores (the user's Stores holds all the information on the sales and products).


## Stores.java
The Stores class implements serializable.

It holds the store ID, name, seller ID, Arraylist of the storeProducts (the info of every product), and Sales of the product.


## Products.java
The Products class implements serializable.

It holds the information of the product, the ID, name, description, price, quantity, and order limit.


## Sales.java
The Sales class implements serializable.

It holds the seller ID, total sales price, and an ArrayList of productSales objects (the productSales class holds the information of each specific sale made).


## ProductSales.java
The ProductSales class implements serializable.

It holds the product ID, quantity, price, customer ID, and productName. 

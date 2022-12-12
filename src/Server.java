import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;

import javax.swing.*;

/**
 * Server.java
 * This class description is mentioned in the README.md.
 *
 * @author Parth Shah, Pranav Konda, Aarush Sachdeva
 * @version December 10th, 2022
 */
public class Server extends Thread {
    private static ArrayList<Seller> sellers = new ArrayList<Seller>();
    private static ArrayList<Customer> customers = new ArrayList<Customer>();
    Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
        try {
            File fileMake = new File("files/Accounts.txt");
            fileMake.createNewFile();

            fileMake = new File("files/Seller.txt");
            fileMake.createNewFile();

            fileMake = new File("files/Customer.txt");
            fileMake.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {

            oos = new ObjectOutputStream(socket.getOutputStream());
            //oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        readCustomer();
        readSeller();


        boolean runAgain = true;
        do {
            //serverSocket.accept();
            try {
                String prejsonObject = (String) ois.readObject();
                JSONObject jsonObject = new JSONObject(prejsonObject);
                //System.out.println(jsonObject.toString());
                switch (jsonObject.getString("userKey")) {

                    case "Login": // Login
                        // Check if user exists, send back userID and null if no exist
                        String emailL = jsonObject.getString("email");
                        String passwordL = jsonObject.getString("password");
                        JSONObject signedIn = logIn(emailL, passwordL);
                        if (signedIn != null) {
                            oos.writeObject(signedIn.toString());
                            oos.flush();
                        } else {
                            oos.writeObject(null);
                            oos.flush();
                        }
                        break;

                    case "Signup": // Signup
                        // Create a user, send back userID , should have received a choice for type
                        String emailS = jsonObject.getString("email");
                        //System.out.println(emailS);
                        String passwordS = jsonObject.getString("password");
                        //System.out.println(passwordS);
                        int typeS = jsonObject.getInt("userType");
                        //System.out.println(typeS);
                        JSONObject signedUp = signUp(emailS, passwordS, typeS);
                        if (signedUp != null) {
                            oos.writeObject(signedUp.toString());
                            oos.flush();
                        } else {
                            oos.writeObject(null);
                            oos.flush();
                        }
                        break;


                    case "Seller": // Seller
                        switch (jsonObject.getString("actionKey")) {
                            case "1": // Edit stores
                                switch (jsonObject.getString("1Key")) {
                                    case "3": // The user is now going to do something to the Store
                                        switch (jsonObject.getString("editStoreKey")) {
                                            case "1": // edit products
                                                switch (jsonObject.getString("editProductKey")) {
                                                    case "edit": // Editproduct
                                                        // send back if the product was added or not
                                                        int userID1 = jsonObject.getInt("userID");
                                                        String storeName1 =
                                                                jsonObject.getString("storeName");
                                                        String oldProductName =
                                                                jsonObject.getString("oldProductName");
                                                        String productName1 =
                                                                jsonObject.getString("newName");
                                                        String productDescription =
                                                                jsonObject.getString("productDescription");
                                                        double productPrice =
                                                                jsonObject.getDouble("productPrice");
                                                        int productQuantity =
                                                                jsonObject.getInt("productQuantity");
                                                        int maxQuantity = jsonObject.getInt("maxQuantity");
                                                        Stores stores1 = null;
                                                        Seller seller1 = null;
                                                        for (Seller s : sellers) {
                                                            if (s.getSellerId() == userID1) {
                                                                seller1 = s;
                                                                break;
                                                            }
                                                        }
                                                        for (Stores s : seller1.getStores()) {
                                                            if (s.getName().equals(storeName1)) {
                                                                stores1 = s;
                                                                break;
                                                            }
                                                        }
                                                        for (Products p : stores1.getStoreProducts()) {
                                                            if (p.getName().equals(oldProductName)) {
                                                                p.editProduct(productName1,
                                                                        productDescription,
                                                                        productQuantity,
                                                                        productPrice, maxQuantity);

                                                                break;
                                                            }
                                                        }
                                                        break;
                                                    case "info": // send back an JSONobject of the product's
                                                        // information
                                                        int userID = jsonObject.getInt("userID");
                                                        String storeName = jsonObject
                                                                .getString("storeName");
                                                        String productName = jsonObject
                                                                .getString("productName");
                                                        Stores stores = null;
                                                        Seller seller = null;
                                                        for (Seller s : sellers) {
                                                            if (s.getSellerId() == userID) {
                                                                seller = s;
                                                                break;
                                                            }
                                                        }
                                                        for (Stores s : seller.getStores()) {
                                                            if (s.getName().equals(storeName)) {
                                                                stores = s;
                                                                break;
                                                            }
                                                        }
                                                        for (Products p : stores.getStoreProducts()) {
                                                            if (p.getName().equals(productName)) {
                                                                JSONObject productInfo = new JSONObject();
                                                                productInfo.put("name", p.getName());
                                                                productInfo.put("price", p.getPrice());
                                                                productInfo.put("quantity", p.getQuantity());
                                                                productInfo.put("descrip" +
                                                                        "tion", p.getDescription());
                                                                productInfo.put("maxQuan" +
                                                                        "tity", p.getOrderLimit());
                                                                oos.writeObject(productInfo.toString());
                                                                oos.flush();
                                                                break;
                                                            }
                                                        }
                                                        break;
                                                }
                                                break;
                                            case "2": // add products
                                                int userID =
                                                        jsonObject.getInt("userID");
                                                String storeName =
                                                        jsonObject.getString("storeName");
                                                String productName =
                                                        jsonObject.getString("productName");
                                                String productDescription =
                                                        jsonObject.getString("productDescription");
                                                double productPrice =
                                                        jsonObject.getDouble("productPrice");
                                                int productQuantity =
                                                        jsonObject.getInt("productQuantity");
                                                int maxQuantity =
                                                        jsonObject.getInt("maxQuantity");
                                                Stores stores = null;
                                                Seller seller = null;
                                                for (Seller s : sellers) {
                                                    if (s.getSellerId() == userID) {
                                                        seller = s;
                                                        break;
                                                    }
                                                }
                                                for (Stores s : seller.getStores()) {
                                                    if (s.getName().equals(storeName)) {
                                                        stores = s;
                                                        break;
                                                    }
                                                }
                                                int pId = 0;
                                                for (Seller s : sellers) {
                                                    for (Stores s2 : s.getStores()) {
                                                        for (Products p : s2.getStoreProducts()) {
                                                            if (p.getProductID() > pId) {
                                                                pId = p.getProductID();
                                                            }
                                                        }
                                                    }
                                                }
                                                pId++;
                                                System.out.println("ID: " + pId + "," + productName +
                                                        productDescription +
                                                        productQuantity + productPrice + maxQuantity);
                                                stores.addStoreProduct(pId, productName, productDescription,
                                                        productQuantity, productPrice, maxQuantity);
                                                break;
                                            case "3": // remove products
                                                int userID2 = jsonObject.getInt("userID");
                                                String storeName2 = jsonObject.getString("storeName");
                                                String productName2 = jsonObject.getString("productName");
                                                Stores stores2 = null;
                                                Seller seller2 = null;
                                                for (Seller s : sellers) {
                                                    if (s.getSellerId() == userID2) {
                                                        seller2 = s;
                                                        break;
                                                    }
                                                }
                                                for (Stores s : seller2.getStores()) {
                                                    if (s.getName().equals(storeName2)) {
                                                        stores2 = s;
                                                        break;
                                                    }
                                                }
                                                for (Products p : stores2.getStoreProducts()) {
                                                    if (p.getName().equals(productName2)) {
                                                        stores2.removeStoreProduct(p);
                                                        break;
                                                    }
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case "2": // Make new store
                                int userID = jsonObject.getInt("userID");
                                String newStore = jsonObject.getString("storeName");
                                Seller userS = getSeller(userID);
                                int storeId = 0;
                                for (Seller s : sellers) {
                                    for (Stores s2 : s.getStores()) {
                                        if (s2.getStoreID() > storeId) {
                                            storeId = s2.getStoreID();
                                        }
                                    }
                                }
                                storeId++;
                                System.out.println("IDStore: " + storeId);
                                userS.addStore(storeId, newStore);
                                // Create the store, send back nothing.
                                break;
                            case "3": // Delete store
                                switch (jsonObject.getString("3Key")) {
                                    case "1": // send back list of stores
                                        int userID3 = jsonObject.getInt("userID");
                                        Seller userS3 = getSeller(userID3);
                                        ArrayList<Stores> stores = null;
                                        if (userS3 != null) {
                                            stores = userS3.getStores();

                                            String[] storeNames = new String[stores.size()];
                                            for (int i = 0; i < stores.size(); i++) {
                                                storeNames[i] = stores.get(i).getName();
                                            }
                                            oos.writeObject(storeNames);
                                            oos.flush();
                                        } else {
                                            oos.writeObject(null);
                                            oos.flush();
                                        }
                                        break;
                                    case "2": // Delete store
                                        int userID2 = jsonObject.getInt("userID");
                                        String storeName = jsonObject.getString("storeName");
                                        Seller userS2 = getSeller(userID2);
                                        ArrayList<Stores> tempDelete = userS2.getStores();
                                        for (int i = 0; i < tempDelete.size(); i++) {
                                            if (tempDelete.get(i).getName().equals(storeName)) {
                                                tempDelete.remove(i);
                                                oos.writeObject((Boolean) true);
                                                oos.flush();
                                                break;
                                            }
                                        }
                                        break;
                                }
                                break;
                            case "4": // View sales
                                int userID4 = jsonObject.getInt("userID");
                                String storeName = jsonObject.getString("storeName");
                                Seller userS4 = getSeller(userID4);
                                ArrayList<Stores> stores4 = userS4.getStores();
                                Stores store4 = null;
                                for (Stores s : stores4) {
                                    if (s.getName().equals(storeName)) {
                                        store4 = s;
                                        break;
                                    }
                                }
                                Sales sales = store4.getSales();
                                ArrayList<ProductSales> saleList = sales.getProductSales();
                                ArrayList<String> saleStrings = new ArrayList<>();
                                saleStrings.add("Revenue: $" + sales.getTotalSales());
                                for (ProductSales productSales : saleList) {
                                    saleStrings.add("Customer ID: " + productSales.getCustomerId() +
                                            " Product Name:" +
                                            " " +
                                            productSales.getProductName() + " Quantity: " +
                                            productSales.getQuantity() +
                                            " Price: " +
                                            productSales.getPrice());
                                }
                                oos.writeObject(saleStrings);
                                oos.flush();
                                break;
                            case "5": // Import CSV for stores
                                int userID5 = jsonObject.getInt("userID");
                                int objectSize = jsonObject.getInt("objectSize");
                                ArrayList<JSONObject> jsonObjects = new ArrayList<>();
                                for (int i = 0; i < objectSize; i++) {
                                    String temp = (String) ois.readObject();
                                    JSONObject tempObject = new JSONObject(temp);
                                    jsonObjects.add(tempObject);
                                }
                                Seller userS5 = getSeller(userID5);
                                try {
                                    for (JSONObject storeJson : jsonObjects) {
                                        String newStoreName = storeJson.getString("storeName");
                                        String productName = storeJson.getString("productName");
                                        String productDescription = storeJson.getString("productDescription");
                                        int productQuantity = storeJson.getInt("productQuantity");
                                        double productPrice = storeJson.getDouble("productPrice");
                                        int maxQuantity = storeJson.getInt("productMaxQuantity");
                                        ArrayList<Stores> stores = userS5.getStores();
                                        boolean unique = true;
                                        for (Stores s : stores) {
                                            if (s.getName().equalsIgnoreCase(newStoreName)) {
                                                unique = false;
                                            }
                                        }
                                        if (unique) {
                                            //create new store and add it to the list
                                            int storeId1 = 0;
                                            for (Seller s : sellers) {
                                                for (Stores s2 : s.getStores()) {
                                                    if (s2.getStoreID() > storeId1) {
                                                        storeId1 = s2.getStoreID();
                                                    }
                                                }
                                            }
                                            storeId1++;
                                            userS5.addStore(storeId1, newStoreName);
                                            System.out.println("CSVADD");
                                        }
                                        Stores store = userS5.selectStore(newStoreName);
                                        int productId = 0;
                                        for (Seller s : sellers) {
                                            for (Stores s2 : s.getStores()) {
                                                for (Products p : s2.getStoreProducts()) {
                                                    if (p.getProductID() > productId) {
                                                        productId = p.getProductID();
                                                    }
                                                }
                                            }
                                        }
                                        productId++;
                                        System.out.println("ID: " + productId + "," + productName +
                                                productDescription +
                                                productQuantity + productPrice + maxQuantity);

                                        store.addStoreProduct(productId, productName, productDescription,
                                                productQuantity, productPrice,
                                                maxQuantity);

                                    }
                                    oos.writeObject((Boolean) true);
                                    oos.flush();
                                } catch (Exception e) {
                                    oos.writeObject((Boolean) false);
                                    oos.flush();
                                }

                                break;
                            case "6": // Export CSV for stores
                                int userID6 = jsonObject.getInt("userID");
                                Seller userS6 = getSeller(userID6);
                                ArrayList<String> storeExport = new ArrayList<>();
                                ArrayList<Stores> stores = userS6.getStores();
                                storeExport.add("Store name," +
                                        " Product name," +
                                        " Product description," +
                                        " Product quantity," +
                                        " Product price," +
                                        " Product max quantity," +
                                        " Product ID");
                                for (Stores s : stores) {
                                    ArrayList<Products> products = s.getStoreProducts();
                                    for (Products p : products) {
                                        storeExport.add(s.getName() +
                                                "," + p.getName() + "," +
                                                p.getDescription() + "," +
                                                p.getQuantity() + "," + p.getPrice() +
                                                "," + p.getOrderLimit() + ", " + p.getProductID());
                                    }
                                }
                                oos.writeObject(storeExport);
                                oos.flush();
                                break;
                            case "7": // Export CSV for Sales
                                int userID41 = jsonObject.getInt("userID");
                                String storeName1 = jsonObject.getString("storeName");
                                Seller userS41 = getSeller(userID41);
                                ArrayList<Stores> stores41 = userS41.getStores();
                                Stores store41 = null;
                                for (Stores s : stores41) {
                                    if (s.getName().equals(storeName1)) {
                                        store41 = s;
                                        break;
                                    }
                                }
                                Sales sales1 = store41.getSales();
                                ArrayList<ProductSales> saleList1 = sales1.getProductSales();
                                ArrayList<String> saleStrings1 = new ArrayList<>();
                                saleStrings1.add("Customer ID, Product name, Quantity, Price");
                                for (ProductSales productSales : saleList1) {
                                    saleStrings1.add(productSales.getCustomerId() + "," +
                                            productSales.getProductName() + "," + productSales.getQuantity() + "," +
                                            productSales.getPrice());
                                }
                                oos.writeObject(saleStrings1);
                                oos.flush();
                                break;
                        }
                        break;


                    case "Customer": // Customer
                        switch (jsonObject.getString("actionKey")) {
                            case "getStoresCust":
                                ArrayList<String> storeNames = new ArrayList<>();
                                for (Seller s : sellers) {
                                    for (Stores store : s.getStores()) {
                                        storeNames.add(store.getName());
                                    }
                                }
                                System.out.println(sellers.size());
                                oos.writeObject(storeNames);
                                oos.flush();
                                break;
                            case "getProductsCust":
                                ArrayList<String> productNames = new ArrayList<>();
                                for (Seller s : sellers) {
                                    for (Stores store : s.getStores()) {
                                        for (Products p : store.getStoreProducts()) {
                                            productNames.add(p.getName());
                                        }
                                    }
                                }
                                break;
                            case "getProductsStoreCust":
                                String storeName = jsonObject.getString("storeName");
                                ArrayList<String> productNamesStore = new ArrayList<>();
                                for (Seller s : sellers) {
                                    for (Stores store : s.getStores()) {
                                        if (store.getName().equals(storeName)) {
                                            for (Products p : store.getStoreProducts()) {
                                                productNamesStore.add(p.getName());
                                            }
                                        }
                                    }
                                }
                                oos.writeObject(productNamesStore);
                                oos.flush();
                                break;
                            case "specificProductCust":
                                String storeName1 = jsonObject.getString("storeName");
                                String productName1 = jsonObject.getString("productName");
                                System.out.println("StoreName: " + storeName1 + "Product Name: " + productName1);
                                Products product1 = null;
                                for (Seller s : sellers) {
                                    for (Stores store : s.getStores()) {
                                        if (store.getName().equals(storeName1)) {
                                            for (Products p : store.getStoreProducts()) {
                                                if (p.getName().equalsIgnoreCase(productName1)) {
                                                    product1 = p;
                                                    System.out.println("Product found");
                                                    System.out.println("Product name: " + p.getName());
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                JSONObject productJSON = new JSONObject();
                                productJSON.put("name", product1.getName());
                                productJSON.put("productID", product1.getProductID());
                                productJSON.put("price", Double.toString(product1.getPrice()));
                                productJSON.put("quantity", product1.getQuantity());
                                productJSON.put("description", product1.getDescription());
                                oos.writeObject(productJSON.toString());
                                oos.flush();
                                break;
                            case "addToCartCust":
                                //String storeName2 = jsonObject.getString("storeName");
                                int productID = jsonObject.getInt("productID");
                                int quantity2 = jsonObject.getInt("quantity");
                                int customerID = jsonObject.getInt("customerID");
                                Customer customer = getCust(customerID);
                                Products product2 = null;
                                for (Seller s : sellers) {
                                    for (Stores store : s.getStores()) {
                                        for (Products p : store.getStoreProducts()) {
                                            if (p.getProductID() == productID) {
                                                product2 = p;
                                                System.out.println("Product found to add to cart " + p.getName());
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (product2.getQuantity() >= quantity2) {
                                    customer.addToCart(quantity2, product2.getProductID());
                                    System.out.println("Product added to cart" + "ID:" + product2.getProductID());
                                    oos.writeObject(true);
                                    oos.flush();
                                } else {
                                    oos.writeObject(false);
                                    oos.flush();
                                }
                                break;
                            case "buyCust":
                                String storeName3 = jsonObject.getString("storeName");
                                int productID3 = jsonObject.getInt("productID");
                                int quantity3 = jsonObject.getInt("quantity");
                                int customerID3 = jsonObject.getInt("customerID");

                                Customer customer3 = getCust(customerID3);
                                Stores store3 = null;
                                Products product3 = null;
                                for (Seller s : sellers) {
                                    for (Stores store : s.getStores()) {
                                        if (store.getName().equals(storeName3)) {
                                            store3 = store;
                                            for (Products p : store.getStoreProducts()) {
                                                if (p.getProductID() == productID3) {
                                                    product3 = p;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (product3 != null) {
                                    int sellerID = store3.getSellerID();
                                    if (product3.getQuantity() >= quantity3) {
                                        if (product3.getOrderLimit() >= quantity3 ||
                                                product3.getOrderLimit() == -1) {
                                            customer3.addPurchase(sellerID, store3.getStoreID(),
                                                    productID3, quantity3,
                                                    product3.getPrice());
                                            store3.addSale(productID3, quantity3, product3.getPrice(),
                                                    customer3.getCustId(),
                                                    product3.getName());
                                            oos.writeObject("true");
                                            oos.flush();
                                        } else {
                                            oos.writeObject("falseMQ");
                                            oos.flush();
                                            oos.writeObject(product3.getOrderLimit());
                                            oos.flush();
                                        }
                                    } else {
                                        oos.writeObject("false");
                                        oos.flush();
                                    }
                                } else {
                                    oos.writeObject("false");
                                    oos.flush();
                                }
                                break;
                            case "searchProduct":
                                String productName0 = jsonObject.getString("productName");
                                ArrayList<String> products = new ArrayList<>();
                                for (Seller s : sellers) {
                                    for (Stores s1 : s.getStores()) {
                                        for (Products p : s1.getStoreProducts()) {
                                            if (p.getName().toLowerCase().contains(productName0.toLowerCase())) {
                                                products.add(p.getName());
                                            }
                                        }
                                    }
                                }
                                if (products.size() > 0) {
                                    oos.writeObject(true);
                                    oos.writeObject(products);
                                    oos.flush();
                                } else {
                                    oos.writeObject(false);
                                    oos.flush();
                                }
                                break;
                            case "getStorefromProduct":
                                String productName = jsonObject.getString("productName");
                                Stores stores = null;
                                for (Seller s : sellers) {
                                    for (Stores s1 : s.getStores()) {
                                        for (Products p : s1.getStoreProducts()) {
                                            if (p.getName().toLowerCase().contains(productName.toLowerCase())) {
                                                stores = s1;
                                                break;
                                            }
                                        }
                                    }
                                }
                                oos.writeObject(stores.getName());
                                oos.flush();
                                break;
                            case "viewPurchaseHistory":
                                System.out.println("In view purchase history");
                                int customerID1 = jsonObject.getInt("customerID");
                                Customer customer1 = getCust(customerID1);
                                ArrayList<PurchaseHist> purchaseHist = customer1.getPurchaseHists();
                                ArrayList<String[]> returner = new ArrayList<>();
                                System.out.println("preparing to send purchase history");
                                if (purchaseHist.size() > 0) {
                                    for (PurchaseHist purchaseHist1 : purchaseHist) {
                                        Products purchased = null;
                                        for (Seller s : sellers) {
                                            for (Stores s1 : s.getStores()) {
                                                for (Products p : s1.getStoreProducts()) {
                                                    if (p.getProductID() == purchaseHist1.getProductId()) {
                                                        purchased = p;
                                                    }
                                                }
                                            }
                                        }
                                        String productName2 = purchased.getName();
                                        int quantity = purchaseHist1.getQuantity();
                                        double price = purchaseHist1.getPrice();
                                        double total = quantity * price;
                                        String priceString = String.format("%.2f", price);
                                        String totalString = String.format("%.2f", total);
                                        String[] returner1 = {productName2, Integer.toString(quantity),
                                                priceString, totalString};
                                        returner.add(returner1);
                                    }

                                    oos.writeObject(returner);
                                } else {
                                    oos.writeObject(returner);
                                }
                                oos.flush();
                                System.out.println("Purchase history sent");
                                break;
                            case "testCart":
                                int customerID2 = jsonObject.getInt("customerID");
                                Customer customer2 = getCust(customerID2);
                                ArrayList<Cart> cart = customer2.getCarts();
                                ArrayList<String[]> returnerCartArray = new ArrayList<>();
                                if (cart.size() > 0) {
                                    for (Cart c : cart) {
                                        System.out.println(c.getProductID());
                                        Products product = null;
                                        for (Seller s : sellers) {
                                            for (Stores s1 : s.getStores()) {
                                                for (Products p : s1.getStoreProducts()) {
                                                    if (p.getProductID() == c.getProductID()) {
                                                        System.out.println("found product" + p.getProductID());
                                                        product = p;
                                                    }
                                                }
                                            }
                                        }
                                        if (product == null) {
                                            customer2.getCarts().remove(c);
                                        }
                                        String productInCartName = product.getName();
                                        int quantityInCart = c.getQuantity();
                                        double priceInCart = product.getPrice();
                                        String priceInCartString = String.format("%.2f", priceInCart);
                                        String quantityInCartString = Integer.toString(quantityInCart);
                                        String[] returnerArray = {productInCartName, quantityInCartString,
                                                priceInCartString};
                                        returnerCartArray.add(returnerArray);
                                    }
                                    oos.writeObject(returnerCartArray);
                                } else {
                                    oos.writeObject(returnerCartArray);
                                }
                                oos.flush();
                                break;
                            case "removeItem":
                                int customerID5 = jsonObject.getInt("customerID");
                                String productName5 = jsonObject.getString("productName");
                                int quantity5 = jsonObject.getInt("quantityToRemove");
                                int productID5 = 0;
                                for (Seller s : sellers) {
                                    for (Stores s1 : s.getStores()) {
                                        for (Products p : s1.getStoreProducts()) {
                                            if (p.getName().equalsIgnoreCase(productName5)) {
                                                productID5 = p.getProductID();
                                            }
                                        }
                                    }
                                }
                                Customer customer5 = getCust(customerID5);
                                boolean removed = false;
                                for (Cart c : customer5.getCarts()) {
                                    if (c.getProductID() == productID5) {
                                        if (quantity5 > 1) {
                                            c.setQuantity(quantity5);
                                        } else {
                                            customer5.removeCart(c);
                                        }
                                        removed = true;
                                        break;
                                    }
                                }
                                oos.writeObject(removed);
                                oos.flush();
                                break;
                            case "buyCart":
                                int customerID6 = jsonObject.getInt("customerID");
                                Customer customer6 = getCust(customerID6);
                                oos.writeObject(purchaseCart(customer6));
                                oos.flush();
                                break;
                        }
                        break;
                    case "getStores":
                        //send back the stores
                        int userID = jsonObject.getInt("userID");
                        Seller userS = getSeller(userID);
                        ArrayList<Stores> stores = null;
                        ArrayList<String> sendStores = new ArrayList<>();
                        if (userS != null) {
                            stores = userS.getStores();
                            for (int i = 0; i < stores.size(); i++) {
                                sendStores.add(stores.get(i).getName());
                            }
                            oos.writeObject(sendStores);
                            oos.flush();
                        } else {
                            oos.writeObject(sendStores);
                            oos.flush();
                        }
                        break;
                    case "getProducts":
                        //send back the products
                        int userID2 = jsonObject.getInt("userID");
                        String storeName = jsonObject.getString("storeName");
                        Seller userS2 = getSeller(userID2);
                        ArrayList<Stores> stores2 = userS2.getStores();
                        Stores stores1 = null;
                        for (int i = 0; i < stores2.size(); i++) {
                            if (stores2.get(i).getName().equals(storeName)) {
                                stores1 = stores2.get(i);
                            }
                        }
                        ArrayList<Products> products = null;
                        ArrayList<String> sendProducts = new ArrayList<>();
                        if (stores1 != null) {
                            products = stores1.getStoreProducts();

                            for (int i = 0; i < products.size(); i++) {
                                sendProducts.add(products.get(i).getName());
                            }
                            oos.writeObject(sendProducts);
                            oos.flush();
                        } else {
                            oos.writeObject(sendProducts);
                            oos.flush();
                        }
                        break;


                    default:
                        break;
                }
                writeCustomer();
                writeSeller();
                readCustomer();
                readSeller();
            } catch (Exception e) {
                e.printStackTrace();
                writeCustomer();
                writeSeller();
                readCustomer();
                readSeller();
                //socket.close();
                runAgain = false;
            }
        } while (runAgain);
        //Write into database and read database EVERYWHERE.
        writeCustomer();
        writeSeller();
        try {
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static JSONObject logIn(String email, String password) {
        try {
            ArrayList<String> lines = new ArrayList<String>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("files/Accounts.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            for (String s : lines) {
                String[] split = s.split(";");
                if (split[2].equals(email) && split[3].equals(password)) {
                    int userID = Integer.parseInt(split[0]);
                    int userType = Integer.parseInt(split[1]);
                    return new JSONObject().put("userID", userID).put("userType", userType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject signUp(String email, String password, int userTyper) { //FORMAT: ID;TYPE;EMAIL;PASSWORD
        try {
            ArrayList<String> lines = new ArrayList<String>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("files/Accounts.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(
                    new FileWriter("files/Accounts.txt")));
            for (String s : lines) {
                printWriter.println(s);
            }
            int userInfo = lines.size();
            printWriter.println(userInfo + ";" + userTyper + ";" + email + ";" + password);
            int userID = userInfo;
            int userType = userTyper;
            printWriter.flush();
            printWriter.close();
            System.out.println("User created, ID: " + userID + " Type: " + userType);
            if (userType == 1) { // Seller
                Seller userS = new Seller(userID);
                sellers.add(userS);
            } else if (userType == 2) { // Customer
                Customer userC = new Customer(userID);
                customers.add(userC);
            }
            return new JSONObject().put("userID", userID).put("userType", userType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Error");
        return null;
    }


    public static void readSeller() {
        try {
            sellers = new ArrayList<>();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("files/Seller.txt"));
            Seller obj = null;
            while ((obj = (Seller) in.readObject()) != null) {
                sellers.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readCustomer() {
        try {
            customers = new ArrayList<>();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("files/Customer.txt"));
            Customer obj = null;
            while ((obj = (Customer) in.readObject()) != null) {
                customers.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeSeller() {
        File fileMake = new File("files/Seller.txt");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files/Seller.txt"));
            for (Seller seller : sellers) {
                out.writeObject(seller);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    public static void writeCustomer() {
        File fileMake = new File("files/Customer.txt");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files/Customer.txt"));
            for (Customer customer : customers) {
                out.writeObject(customer);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    public static Seller getSeller(int userID) {
        for (Seller seller : sellers) {
            if (seller.getSellerId() == userID) {
                return seller;
            }
        }
        return null;
    }

    public static Customer getCust(int userID) {
        for (Customer customer : customers) {
            if (customer.getCustId() == userID) {
                return customer;
            }
        }
        return null;
    }

    public static String purchaseCart(Customer userC) {
        Boolean purchase = true;
        ArrayList<Cart> cartToRemove = new ArrayList<>();
        for (Cart cart : userC.getCarts()) {
            //we have the productID, quantity and price
            Products products = null;
            Stores productStore = null;
            for (Seller s : sellers) {
                for (Stores s1 : s.getStores()) {
                    for (Products p : s1.getStoreProducts()) {
                        if (p.getProductID() == cart.getProductID()) {
                            products = p;
                            productStore = s1;
                            break;
                        }
                    }
                }
            }
            double productPrice = products.getPrice();
            int productID = products.getProductID();
            int storeID = productStore.getStoreID();
            int sellerID = productStore.getSellerID();
            int quantity = cart.getQuantity();
            if (quantity > 0 && quantity <= products.getQuantity() && (products.getOrderLimit() >=
                    quantity || products.getOrderLimit() == -1)) {
                userC.addPurchase(sellerID, storeID, productID, quantity,
                        productPrice);
                productStore.addSale(productID, quantity, productPrice, userC.getCustId(), products.getName());
                cartToRemove.add(cart);
            } else {
                purchase = false;
            }
        }
        for (Cart cart : cartToRemove) {
            userC.removeCart(cart);
        }
        if (purchase) {
            return "true";
        } else {
            return "false";
        }
    }


}



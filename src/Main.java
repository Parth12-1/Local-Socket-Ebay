import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.json.JSONObject;

public class Main {
    static int userID;
    static int userType;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 1000); //PORT: 1000
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();


        boolean worked = false;
        do {
            String[] choices = {"1. Log In", "2. Sign Up", "3. Exit"};
            String choiceString;
            choiceString = (String) JOptionPane.showInputDialog(null,
                    "Welcome to the Marketplace! Please login or signup to access the marketplace.",
                    "Login/Signup", JOptionPane.QUESTION_MESSAGE,
                    null, choices, choices[0]);
            if (choiceString == null) {
                return;
            }
            int choice = Integer.parseInt(choiceString.substring(0, 1));
            if (choice == 1) {
                String email = JOptionPane.showInputDialog(null, "Enter your Email:",
                        "Credential Login", JOptionPane.QUESTION_MESSAGE);
                String password = JOptionPane.showInputDialog(null, "Enter your Password:",
                        "Credential Login", JOptionPane.QUESTION_MESSAGE);
                if (email == null || password == null) {
                    return;
                }
                oos.writeObject((new JSONObject().put("userKey", "Login").put("email", email).put("password",
                        password)).toString());
                oos.flush();
                String presignedIn = (String) ois.readObject();

                if (presignedIn == null) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid email or password. Please try again.",
                            "Invalid Credentials",
                            JOptionPane.ERROR_MESSAGE); // displays error message
                } else {
                    JSONObject signedIn = new JSONObject(presignedIn);
                    JOptionPane.showMessageDialog(null,
                            "You have successfully logged in.",
                            "Login Successful",
                            JOptionPane.INFORMATION_MESSAGE); // displays error message
                    worked = true;
                    userID = signedIn.getInt("userID");
                    userType = signedIn.getInt("userType");
                    System.out.println("ID = " + userID);
                    System.out.println("Type = " + userType);
                }
            } else if (choice == 2) {
                String email = JOptionPane.showInputDialog(null, "Enter your Email:",
                        "Credential Login", JOptionPane.QUESTION_MESSAGE);
                String password = JOptionPane.showInputDialog(null, "Enter your Password:",
                        "Credential Login", JOptionPane.QUESTION_MESSAGE);
                boolean valid = true;
                if (email == null || password == null) {
                    return;
                } //TODO this is still allowing the user to create account
                //int userType = 0;
                oos.writeObject((new JSONObject().put("userKey", "Login").put("email", email).put("password",
                        password)).toString());
                oos.flush();
                String preCheck = (String) ois.readObject();
                if ((preCheck == null)) {
                    do { //Make sure that we get the info we need properly
                        valid = true;
                        String[] userTypes = {"1. Seller", "2. Customer"};
                        String userTypeString;
                        userTypeString = (String) JOptionPane.showInputDialog(null,
                                "Do you want to create a seller or customer account?",
                                "Login/Signup", JOptionPane.QUESTION_MESSAGE,
                                null, userTypes, userTypes[0]);
                        if (userTypeString == null) {
                            return;
                        }
                        userType = Integer.parseInt(userTypeString.substring(0, 1));
                        try {
                            if (userType != 1 && userType != 2) {
                                JOptionPane.showMessageDialog(null,
                                        "Invalid input. Please try again.",
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE); // displays error message
                                valid = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            valid = false;
                            JOptionPane.showMessageDialog(null,
                                    "Invalid input. Please try again.",
                                    "Invalid Input",
                                    JOptionPane.ERROR_MESSAGE); // displays error message
                        }
                    } while (!valid);
                    oos.writeObject((new JSONObject().put("userKey", "Signup").put("email", email).put("password",
                            password).put("userType", userType)).toString());
                    oos.flush();
                    String presignedIn = (String) ois.readObject();

                    if (presignedIn == null) {
                        JOptionPane.showMessageDialog(null,
                                "Invalid email or password. Please try again.",
                                "Invalid Credentials",
                                JOptionPane.ERROR_MESSAGE); // displays error message
                    } else {
                        JSONObject signedIn = new JSONObject(presignedIn);
                        JOptionPane.showMessageDialog(null,
                                "You have successfully signed up.",
                                "Sign Up Successful",
                                JOptionPane.INFORMATION_MESSAGE); // displays error message
                        worked = true;
                        userID = signedIn.getInt("userID");
                        userType = signedIn.getInt("userType");
                        System.out.println("ID = " + userID);
                        System.out.println("Type = " + userType);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "You have successfully logged in.",
                            "Login Successful",
                            JOptionPane.INFORMATION_MESSAGE); // displays error message
                    worked = true;
                    JSONObject check = new JSONObject(preCheck);
                    userID = check.getInt("userID");
                    userType = check.getInt("userType");
                }
                //signedIn = true;
            } else if (choice == 3) {
                JOptionPane.showMessageDialog(null, "Thank you for using the Marketplace",
                        "Goodbye",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid Choice. Please try again.",
                        "Invalid Choice",
                        JOptionPane.ERROR_MESSAGE); // displays error message
            }
        } while (!worked);
        //Signed in by now

        if (userType == 1) { //Seller
            System.out.println("IN Seller");
            int option;
            String storeSelected;
            String delete;
            boolean exit = false;
            do {
                do {
                    String[] options = {"1. Edit my Stores", "2. Make a New Store", "3. Delete a Store",
                            "4. View Sales", "5. Import CSV for Stores", "6. Export CSV for Stores",
                            "7. Export CSV for Sales", "8. Log Out"};
                    String optionString;
                    optionString = (String) JOptionPane.showInputDialog(null, "What would you like to do?",
                            "Seller Account", JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    if (optionString == null) {
                        exit = true;
                        return;
                    }
                    option = Integer.parseInt(optionString.substring(0, 1));
                    if (option < 1 || option > 8) {
                        JOptionPane.showMessageDialog(null,
                                "Invalid input. Please try again.",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE); // displays error message
                    }
                } while (option < 1 || option > 8);


                if (option == 1) { // Edit Stores
                    oos.writeObject((new JSONObject().put("userKey", "getStores").put("userID", userID)).toString());
                    oos.flush();
                    ArrayList<String> userStores = (ArrayList<String>) ois.readObject();
                    if (userStores != null && userStores.size() != 0) {
                        String[] names = new String[userStores.size()];
                        for (int i = 0; i < userStores.size(); i++) {
                            names[i] = (userStores.get(i));
                        }
                        storeSelected = (String) JOptionPane.showInputDialog(null,
                                "Please select a store", "Select Store", JOptionPane.QUESTION_MESSAGE,
                                null, names, names[0]);
                        if (storeSelected == null) {
                            exit = true;
                            return;
                        }
                        String selected = null;
                        for (String temp : userStores) {
                            if (temp.equalsIgnoreCase(storeSelected)) {
                                selected = temp;
                                break;
                            }
                        }
                        if (selected != null) {
                            int option2;
                            do {
                                String[] optionChoices = {"1. Edit Products",
                                        "2. Add a Product",
                                        "3. Delete a Product",
                                        "4. Go Back"};
                                /*ArrayList<Products> productArrayList = store.getStoreProducts();*/
                                String option2String = (String) JOptionPane.showInputDialog(null, "What is your choice?",
                                        "Store: " + selected, JOptionPane.QUESTION_MESSAGE,
                                        null, optionChoices, optionChoices[0]);
                                if (option2String == null) {
                                    exit = true;
                                    return;
                                }
                                option2 = Integer.parseInt(option2String.substring(0, 1));


                                if (option2 < 1 || option2 > 4) {
                                    JOptionPane.showMessageDialog(null,
                                            "Invalid input. Please try again.",
                                            "Invalid Input",
                                            JOptionPane.ERROR_MESSAGE); // displays error message
                                }
                            } while (option2 < 1 || option2 > 4);
                            if (option2 == 1) {
                                oos.writeObject((new JSONObject().put("userKey", "getProducts").put("storeName",
                                        selected).put("userID", userID)).toString());
                                oos.flush();
                                ArrayList<String> pNames = (ArrayList<String>) ois.readObject();
                                if (pNames != null && pNames.size() != 0) {
                                    String[] productsTemp1 = new String[pNames.size()];
                                    for (int i = 0; i < pNames.size(); i++) {
                                        productsTemp1[i] = pNames.get(i);
                                    }

                                    String productSelected = (String) JOptionPane.showInputDialog(null,
                                            "Please select a product",
                                            "Select Product", JOptionPane.QUESTION_MESSAGE,
                                            null, productsTemp1, productsTemp1[0]);
                                    if (productSelected == null) {
                                        exit = true;
                                        return;
                                    }
                                    String selectedProduct = null;
                                    for (String temp : pNames) {
                                        if (temp.equalsIgnoreCase(productSelected)) {
                                            selectedProduct = temp;
                                            break;
                                        }
                                    }
                                    if (selectedProduct != null) {
                                        int option3;
                                        do {
                                            String[] options = {"1. Edit Product", "2. Go Back"};
                                            /*System.out.println("1. Edit Product"); //TEST
                                            System.out.println("2. Delete Product"); //TODO: Need to see what to do if in cart
                                            System.out.println("3. Go Back");*/
                                            String option3String = (String) JOptionPane.showInputDialog(
                                                    null,
                                                    "What would you like to do with this product?",
                                                    "Product Action", JOptionPane.QUESTION_MESSAGE,
                                                    null, options, options[0]);
                                            if (option3String == null) {
                                                exit = true;
                                                return;
                                            }
                                            option3 = Integer.parseInt(option3String.substring(0, 1));
                                            /*scanner.nextLine();*/
                                            if (option3 < 1 || option3 > 3) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Invalid input. Please try again.",
                                                        "Invalid Input",
                                                        JOptionPane.ERROR_MESSAGE); // displays error message
                                            }
                                        } while (option3 < 1 || option3 > 3);
                                        if (option3 == 1) {
                                            oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey",
                                                            "1").put("1Key", "3").put("editStoreKey", "1").put("editProductKey",
                                                            "info").put("userID", userID).put("storeName", selected)
                                                    .put("productName", selectedProduct)).toString());
                                            oos.flush();
                                            String prejsonObjectInfo = (String) ois.readObject();
                                            JSONObject productInfo = new JSONObject(prejsonObjectInfo);
                                            String name = productInfo.getString("name");
                                            String description = productInfo.getString("description");
                                            double price = productInfo.getDouble("price");
                                            int quantity = productInfo.getInt("quantity");
                                            int maxQ = productInfo.getInt("maxQuantity");
                                            String newName = name;
                                            String newDescription = description;
                                            double newPrice = price;
                                            int newQuantity = quantity;
                                            int newMaxQ = maxQ;
                                            do {
                                                String[] options = {"1. Edit Name", "2. Edit Description",
                                                        "3. Edit Price", "4. Edit Quantity", "5. Edit Max Quantity"};
                                                String option3String = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "What would you like to edit with this product?",
                                                        "Product Action", JOptionPane.QUESTION_MESSAGE,
                                                        null, options, options[0]);
                                                if (option3String == null) {
                                                    exit = true;
                                                    return;
                                                }
                                                option3 = Integer.parseInt(option3String.substring(0, 1));
                                                /*scanner.nextLine();*/
                                                if (option3 < 1 || option3 > 5) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Invalid input. Please try again.",
                                                            "Invalid Input",
                                                            JOptionPane.ERROR_MESSAGE); // displays error message
                                                }
                                            } while (option3 < 1 || option3 > 5);
                                            if (option3 == 1) {
                                                newName = JOptionPane.showInputDialog(null,
                                                        "Enter the new name of the product." +
                                                                " Current name: " + name,
                                                        "New Name of the Product", JOptionPane.QUESTION_MESSAGE);
                                                if (newName == null) {
                                                    exit = true;
                                                    return;
                                                }
                                            }
                                            /*System.out.println("Enter the new name of the product." +
                                                    " Current name: " + product.getName());
                                            String newName = scanner.nextLine();*/
                                            else if (option3 == 2) {
                                                newDescription = JOptionPane.showInputDialog(null,
                                                        "Enter the new description of the product." +
                                                                " Current description: " + description,
                                                        "New Description of the Product", JOptionPane.QUESTION_MESSAGE);
                                                if (newDescription == null) {
                                                    exit = true;
                                                    return;
                                                }
                                            }
                                            /*System.out.println("Enter the new description of the product." +
                                                    " Current description: " + product.getDescription());
                                            String newDescription = scanner.nextLine();*/
                                            else if (option3 == 4) {
                                                String newQuantityString = JOptionPane.showInputDialog(null,
                                                        "Enter the new quantity of the product." +
                                                                " Current quantity: " + quantity,
                                                        "New Quantity of the Product", JOptionPane.QUESTION_MESSAGE);
                                                if (newQuantityString == null) {
                                                    exit = true;
                                                    return;
                                                }
                                                newQuantity = Integer.parseInt(newQuantityString);
                                            } else if (option3 == 3) {
                                                String newPriceString = JOptionPane.showInputDialog(null,
                                                        "Enter the new price of the product." +
                                                                " Current price: $" + price,
                                                        "New Price of the Product", JOptionPane.QUESTION_MESSAGE);
                                                if (newPriceString == null) {
                                                    exit = true;
                                                    return;
                                                }
                                                newPrice = Double.parseDouble(newPriceString);
                                            } else if (option3 == 5) {
                                                String newMaxQString = JOptionPane.showInputDialog(null,
                                                        "Enter the new max quantity of the " +
                                                                "product (insert -1 for no max). Current " +
                                                                "Max quantity:" +
                                                                " " + maxQ,
                                                        "New Quantity Limit of the Product", JOptionPane.QUESTION_MESSAGE);
                                                if (newMaxQString == null) {
                                                    exit = true;
                                                    return;
                                                }
                                                newMaxQ = Integer.parseInt(newMaxQString);
                                            }
                                            oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey",
                                                            "1").put("1Key", "3").put("editStoreKey", "1").put("editProductKey",
                                                            "edit").put("userID", userID).put("storeName", selected).put("oldProductName", name)
                                                    .put("productName", selectedProduct).put("newName", newName)
                                                    .put("productDescription", newDescription).put("productPrice", newPrice)
                                                    .put("productQuantity", newQuantity).put("maxQuantity", newMaxQ)).toString());
                                            oos.flush();
                                            System.out.println("Product edited successfully!");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null,
                                                "Product not found.",
                                                "Invalid Product",
                                                JOptionPane.ERROR_MESSAGE); // displays error message
                                    }
                                }
                            } else if (option2 == 2) {
                                String name = JOptionPane.showInputDialog(null,
                                        "Enter the name of the product",
                                        "Name of the New Product", JOptionPane.QUESTION_MESSAGE);

                                if (name == null) {
                                    exit = true;
                                    return;
                                }

                                String description = JOptionPane.showInputDialog(null,
                                        "Enter the description of the product",
                                        "Description of the New Product", JOptionPane.QUESTION_MESSAGE);

                                if (description == null) {
                                    exit = true;
                                    return;
                                }

                                /*System.out.println("Enter the description of the product");
                                String description = scanner.nextLine();*/

                                String quantityString = JOptionPane.showInputDialog(null,
                                        "Enter the quantity of the product",
                                        "Quantity of the New Product", JOptionPane.QUESTION_MESSAGE);

                                if (quantityString == null) {
                                    exit = true;
                                    return;
                                }
                                int quantity = Integer.parseInt(quantityString);

                                /*System.out.println("Enter the quantity of the product");
                                int quantity = scanner.nextInt();
                                scanner.nextLine();*/

                                String priceString = JOptionPane.showInputDialog(null,
                                        "Enter the price of the product",
                                        "Price of the New Product", JOptionPane.QUESTION_MESSAGE);
                                if (priceString == null) {
                                    exit = true;
                                    return;
                                }
                                double price = Double.parseDouble(priceString);

                                /*System.out.println("Enter the price of the product");
                                double price = scanner.nextDouble();
                                scanner.nextLine();*/

                                String quantity2String = JOptionPane.showInputDialog(null,
                                        "Enter the max quantity of the product (insert -1 for no max)",
                                        "Quantity Limit of the New Product", JOptionPane.QUESTION_MESSAGE);
                                if (quantity2String == null) {
                                    exit = true;
                                    return;
                                }
                                int quantity2 = Integer.parseInt(quantity2String);

                                /*System.out.println("Enter the max quantity of the product (insert -1 for no max)");
                                int quantity2 = scanner.nextInt();
                                scanner.nextLine();*/
                                oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey",
                                                "1").put("1Key", "3").put("editStoreKey", "2").put("userID", userID)
                                        .put("storeName", selected)
                                        .put("productName", name)
                                        .put("productDescription", description).put("productPrice", price)
                                        .put("productQuantity", quantity).put("maxQuantity", quantity2)).toString());
                                oos.flush();
                                JOptionPane.showMessageDialog(null, "Product added Successfully!",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else if (option2 == 3) {
                                // remove product
                                oos.writeObject((new JSONObject().put("userKey", "getProducts").put("storeName",
                                        selected).put("userID", userID)).toString());
                                oos.flush();
                                ArrayList<String> pNames = (ArrayList<String>) ois.readObject();
                                if (pNames != null && pNames.size() != 0) {
                                    String[] products = new String[pNames.size()];
                                    for (int i = 0; i < pNames.size(); i++) {
                                        products[i] = pNames.get(i);
                                    }
                                    String selectedProduct = (String) JOptionPane.showInputDialog(null,
                                            "Select a product to remove",
                                            "Remove Product", JOptionPane.QUESTION_MESSAGE, null,
                                            products, products[0]);
                                    if (selectedProduct == null) {
                                        exit = true;
                                        return;
                                    }
                                    if (selectedProduct != null) {
                                        oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey",
                                                        "1").put("1Key", "3").put("editStoreKey", "3").put("userID", userID)
                                                .put("storeName", selected).put("productName", selectedProduct)).toString());
                                        oos.flush();
                                    }
                                }
                            }


                        } else {
                            JOptionPane.showMessageDialog(null, "Store doesn't Exist. please try again!",
                                    "Store not found", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "There are no stores in your account.",
                                "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (option == 2) {
                    String newStore = JOptionPane.showInputDialog(null, "What is the name of the new store you would " +
                                    "like to create?",
                            "New Store", JOptionPane.QUESTION_MESSAGE);
                    if (newStore == null) {
                        exit = true;
                        return;
                    }
                    oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey", "2").put("userID",
                            userID).put("storeName",
                            newStore)).toString());
                    oos.flush();
                } else if (option == 3) {
                    oos.writeObject((new JSONObject().put("userKey", "getStores").put("userID", userID)).toString());
                    oos.flush();
                    ArrayList<String> userStores = (ArrayList<String>) ois.readObject();
                    if (userStores != null && userStores.size() != 0) {
                        String[] names = new String[userStores.size()];
                        for (int i = 0; i < userStores.size(); i++) {
                            names[i] = (userStores.get(i));
                        }
                        storeSelected = (String) JOptionPane.showInputDialog(null,
                                "Please select a store", "Select Store", JOptionPane.QUESTION_MESSAGE,
                                null, names, names[0]);
                        if (storeSelected == null) {
                            exit = true;
                            return;
                        }
                        oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey", "3")
                                .put("3Key", "2").put("userID", userID).put("storeName", storeSelected)).toString());
                        oos.flush();
                        boolean remove = (Boolean) ois.readObject();
                        if (remove) {
                            JOptionPane.showMessageDialog(null, "Store successfully deleted.",
                                    "Delete Successful",
                                    JOptionPane.INFORMATION_MESSAGE); // displays error message
                        } else {
                            JOptionPane.showMessageDialog(null, "Store could not be deleted.",
                                    "Delete Unsuccessful",
                                    JOptionPane.ERROR_MESSAGE); // displays error message
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You have no stores to delete.",
                                "No Stores",
                                JOptionPane.ERROR_MESSAGE); // displays error message
                    }
                } else if (option == 4) {
                    //View Sales
                    oos.writeObject((new JSONObject().put("userKey", "getStores").put("userID",
                            userID)).toString());
                    oos.flush();
                    ArrayList<String> stores = (ArrayList<String>) ois.readObject();
                    if (stores != null && stores.size() != 0) {
                        String[] storeNames = new String[stores.size()];
                        for (String temp : stores) {
                            storeNames[stores.indexOf(temp)] = temp;
                        }
                        String selected = (String) JOptionPane.showInputDialog(null, "Select a store to view sales",
                                "View Sales", JOptionPane.QUESTION_MESSAGE, null,
                                storeNames, storeNames[0]);
                        if (selected == null) {
                            exit = true;
                            return;
                        }
                        if (selected != null) {
                            oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey", "4").put("userID",
                                    userID).put("storeName", selected)).toString());
                            oos.flush();
                            ArrayList<String> sales = (ArrayList<String>) ois.readObject();
                            if (sales.size() > 1) {
                                String[] salesArray = new String[sales.size()];
                                for (String temp : sales) {
                                    salesArray[sales.indexOf(temp)] = temp;
                                }
                                JOptionPane.showMessageDialog(null, salesArray, "Sales", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "There are no sales for this store.",
                                        "No Sales",
                                        JOptionPane.INFORMATION_MESSAGE); // displays error message
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Store not found!",
                                    "Store not Found", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You have no stores to view sales for.",
                                "No Stores",
                                JOptionPane.ERROR_MESSAGE); // displays error message
                    }
                } else if (option == 5) {
                    //Import CSV for stores
                    boolean success = true;
                    do {
                        success = true;
                        String storeFileName = JOptionPane.showInputDialog(null,
                                "To import a file, Follow this file format:\n" +
                                        "Store name, Product name,\n" +
                                        " Product description, Product quantity,\n" +
                                        " Product price, Product max quantity\n" +
                                        "Put the same store name for\n" +
                                        " products that you would like associated to that \n" +
                                        "store.\n" +
                                        "What is the name of the file\n" +
                                        " you want to import from (No \n" +
                                        "filename extension (.csv))?",
                                "File Name", JOptionPane.QUESTION_MESSAGE);
                        if (storeFileName == null) {
                            exit = true;
                            return;
                        }
                        ArrayList<JSONObject> storeProducts = new ArrayList<>();
                        try {
                            CSVReader br = new CSVReader(new FileReader("userFiles/" + storeFileName +
                                    ".csv"));
                            String[] line;
                            while ((line = br.readNext()) != null) {
                                if (line.length == 6) {
                                    line[0] = line[0].replace("\uFEFF", "");

                                    storeProducts.add(new JSONObject().put("storeName", line[0]).put("productName",
                                            line[1]).put("productDescription", line[2]).put("productQuantity",
                                            line[3]).put("productPrice", line[4]).put("productMaxQuantity",
                                            line[5]));

                                } else {
                                    JOptionPane.showMessageDialog(null, "Store formatted incorrectly!",
                                            "Format Error", JOptionPane.ERROR_MESSAGE);
                                    success = false;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "File not found, please try again",
                                    "File not Found", JOptionPane.WARNING_MESSAGE);
                            success = false;
                        }
                        if (success) {
                            if (storeProducts.size() > 0) {
                                oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey", "5").put("userID",
                                        userID).put("objectSize", storeProducts.size())).toString());
                                oos.flush();
                                for (JSONObject temp : storeProducts) {
                                    oos.writeObject(temp.toString());
                                    oos.flush();
                                }

                                boolean importSuccess = (Boolean) ois.readObject();
                                if (importSuccess) {
                                    JOptionPane.showMessageDialog(null, "File successfully imported!",
                                            "Import Successful", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "File could not be imported!",
                                            "Import Unsuccessful", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } while (!success);
                } else if (option == 6) {
                    //Export CSV for stores
                    String storeFileName = JOptionPane.showInputDialog(null,
                            "What is the name of the file you would like to export into " +
                                    "(No  + filename extension (.csv))?",
                            "File Name", JOptionPane.QUESTION_MESSAGE);
                    if (storeFileName == null) {
                        exit = true;
                        return;
                    }
                    File file = new File("userFiles/" + storeFileName + ".csv");
                    file.createNewFile();
                    oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey", "6").put("userID",
                            userID)).toString());
                    oos.flush();
                    ArrayList<String> storeExport = (ArrayList<String>) ois.readObject();
                    try {
                        CSVWriter writer = new CSVWriter(
                                new FileWriter("userFiles/" + storeFileName + ".csv"));
                        for (String s : storeExport) {
                            String[] line = s.split(",");
                            writer.writeNext(line);
                        }
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Store not found!",
                                "Store not Found", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (option == 7) {
                    String storeFileName = JOptionPane.showInputDialog(null,
                            "What is the name of the file you would like to export into " +
                                    "(No  + filename extension (.csv))?",
                            "File Name", JOptionPane.QUESTION_MESSAGE);
                    if (storeFileName == null) {
                        exit = true;
                        return;
                    }
                    File file = new File("userFiles/" + storeFileName + ".csv");
                    file.createNewFile();
                    //View Sales
                    oos.writeObject((new JSONObject().put("userKey", "getStores").put("userID",
                            userID)).toString());
                    oos.flush();
                    ArrayList<String> stores = (ArrayList<String>) ois.readObject();
                    String[] storeNames = new String[stores.size()];
                    for (String temp : stores) {
                        storeNames[stores.indexOf(temp)] = temp;
                    }
                    String selected = (String) JOptionPane.showInputDialog(null, "Select a store to export sales",
                            "export Sales", JOptionPane.QUESTION_MESSAGE, null,
                            storeNames, storeNames[0]);
                    if (selected == null) {
                        exit = true;
                        return;
                    }
                    if (selected != null) {
                        oos.writeObject((new JSONObject().put("userKey", "Seller").put("actionKey", "7").put("userID",
                                userID).put("storeName", selected)).toString());
                        oos.flush();
                        ArrayList<String> writeLines = (ArrayList<String>) ois.readObject();
                        if (writeLines.size() > 1) {
                            try {
                                CSVWriter writer = new CSVWriter(
                                        new FileWriter("userFiles/" + storeFileName + ".csv"));
                                for (String s : writeLines) {
                                    String[] line = s.split(",");
                                    writer.writeNext(line);
                                }
                                writer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, "File not found, please try again",
                                        "File not Found", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "There are no sales for this store.",
                                    "No Sales",
                                    JOptionPane.INFORMATION_MESSAGE); // displays error message
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Store not found!",
                                "Store not Found", JOptionPane.WARNING_MESSAGE);
                    }


                } else if (option == 8) {
                    JOptionPane.showMessageDialog(null, "Thank you and Have a good day",
                            "Farewell", JOptionPane.INFORMATION_MESSAGE);
                    exit = true;
                    return;
                }
            } while (!exit);
        } else if (userType == 2) {
            int response;
            boolean leave = false;
            do {
                do {
                    String[] options = {"1. View product listings", "2. Search...", "3. View your purchase history",
                            "4. View your shopping cart", "5. Export Purchase History CSV", "6. Log Out"};
                    String optionsString;
                    optionsString = (String) JOptionPane.showInputDialog(null,
                            "What would you like to do?", "Customer Account", JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    if (optionsString == null) {
                        leave = true;
                        return;
                    }
                    response = Integer.parseInt(optionsString.substring(0, 1));
                    if (response < 1 || response > 6) {
                        JOptionPane.showMessageDialog(null, "Invalid Input!",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } while (response < 1 || response > 6);
                if (response == 1) { // View product listing
                    // view all products
                    // view all stores
                    // view all products in a store
                    oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                            "getStoresCust")).toString());
                    oos.flush();
                    ArrayList<String> storeNames = (ArrayList<String>) ois.readObject();
                    String[] storeNamesArray = new String[storeNames.size()];
                    System.out.println(storeNames.size());
                    for (String temp : storeNames) {
                        storeNamesArray[storeNames.indexOf(temp)] = temp;
                        System.out.println(temp);
                    }
                    String selected = (String) JOptionPane.showInputDialog(null, "Select a store to view products",
                            "View Products", JOptionPane.QUESTION_MESSAGE, null,
                            storeNamesArray, storeNamesArray[0]);
                    if (selected == null) {
                        leave = true;
                        return;
                    }
                    if (selected != null) {
                        oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                "getProductsStoreCust").put("storeName", selected)).toString());
                        oos.flush();
                        ArrayList<String> productNames = (ArrayList<String>) ois.readObject();
                        String[] productNamesArray = new String[productNames.size()];
                        for (String temp : productNames) {
                            productNamesArray[productNames.indexOf(temp)] = temp;
                        }
                        String selectedProduct = (String) JOptionPane.showInputDialog(null, "Select a product to view",
                                "View Product", JOptionPane.QUESTION_MESSAGE, null,
                                productNamesArray, productNamesArray[0]);
                        if (selectedProduct == null) {
                            leave = true;
                            return;
                        }
                        if (selectedProduct != null) {
                            oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                    "specificProductCust").put("storeName", selected).put("productName",
                                    selectedProduct)).toString());
                            oos.flush();
                            String productInfoTemp = (String) ois.readObject();
                            JSONObject productInfo = new JSONObject(productInfoTemp);
                            int productID = productInfo.getInt("productID");
                            String description = productInfo.getString("description");
                            String price = productInfo.getString("price");
                            int quantity = productInfo.getInt("quantity");
                            String product = "Product Name: " + selectedProduct + "\n" +
                                    "Product Descriptions: " + description + "\n" +
                                    "Price: " + price + "\n" +
                                    "Quantity: " + quantity;
                            JOptionPane.showMessageDialog(null, product);
                            int addToCart = 0;
                            do {
                                String[] addToCartOptions = {"1. Add to cart?", "2. Buy Now!", "3. Go back"};
                                String addToCartString;
                                addToCartString = (String) JOptionPane.showInputDialog(null,
                                        "What would you like to do?", "Customer Account",
                                        JOptionPane.QUESTION_MESSAGE, null, addToCartOptions, addToCartOptions[0]);
                                if (addToCartString == null) {
                                    leave = true;
                                    return;
                                }
                                addToCart = Integer.parseInt(addToCartString.substring(0, 1));
                            } while (addToCart < 1 || addToCart > 3);
                            if (addToCart == 1) { // add to Cart
                                int quantityToCart;
                                String quantityToCartString = JOptionPane.showInputDialog(null,
                                        "How many would you like to buy?");
                                if (quantityToCartString == null) {
                                    leave = true;
                                    return;
                                }
                                quantityToCart = Integer.parseInt(quantityToCartString);
                                if (quantityToCart > quantity) {
                                    JOptionPane.showMessageDialog(null, "Not enough in stock!",
                                            "Not enough in stock", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                            "addToCartCust").put("storeName", selected).put("productID",
                                            productID).put("quantity", quantityToCart).put("customerID", userID)).toString());
                                    oos.flush();
                                    Boolean addToCartResponse = (Boolean) ois.readObject();
                                    if (addToCartResponse) {
                                        JOptionPane.showMessageDialog(null, "Added to cart!",
                                                "Added to cart", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Failed to add to cart!",
                                                "Failed to add to cart", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            } else if (addToCart == 2) { // Purchase Now
                                int quantityToCart;
                                String quantityToCartString = JOptionPane.showInputDialog(null,
                                        "How many would you like to buy?");
                                if (quantityToCartString == null) {
                                    leave = true;
                                    return;
                                }
                                quantityToCart = Integer.parseInt(quantityToCartString);
                                if (quantityToCart > quantity) {
                                    JOptionPane.showMessageDialog(null, "Not enough in stock!",
                                            "Not enough in stock", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                            "buyCust").put("storeName", selected).put("productID",
                                            productID).put("quantity", quantityToCart).put("customerID", userID)).toString());
                                    oos.flush();
                                    String purchaseNowResponse = (String) ois.readObject();
                                    if (purchaseNowResponse.equals("true")) {
                                        JOptionPane.showMessageDialog(null, "Purchased!",
                                                "Purchased", JOptionPane.INFORMATION_MESSAGE);
                                    } else if (purchaseNowResponse.equals("falseMQ")) {
                                        int maxQuantity = (Integer) ois.readObject();
                                        JOptionPane.showMessageDialog(null, "Failed to purchase! Above max quantity " +
                                                        "of " + maxQuantity + "!",
                                                "Failed to purchase", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Failed to purchase!",
                                                "Failed to purchase", JOptionPane.ERROR_MESSAGE);
                                    }
                                }

                            } else if (addToCart == 3) { // Go back

                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Product not found!",
                                    "Product not Found", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Store not found!",
                                "Store not Found", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (response == 2) { //view search
                    int response1;
                    do {
                        String[] searchArray = {"1. Stores", "2. Products"};
                        String searchString;
                        searchString = (String) JOptionPane.showInputDialog(null,
                                "What do you want to search?", "Customer Account",
                                JOptionPane.QUESTION_MESSAGE, null, searchArray, searchArray[0]);
                        if (searchString == null) {
                            leave = true;
                            return;
                        }
                        response1 = Integer.parseInt(searchString.substring(0, 1));
                    } while (response1 < 1 || response1 > 2);
                    if (response1 == 1) {
                        // view all products
                        // view all stores
                        // view all products in a store
                        oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                "getStoresCust")).toString());
                        oos.flush();
                        ArrayList<String> storeNames = (ArrayList<String>) ois.readObject();
                        String[] storeNamesArray = new String[storeNames.size()];
                        for (String temp : storeNames) {
                            storeNamesArray[storeNames.indexOf(temp)] = temp;
                        }
                        String selected = (String) JOptionPane.showInputDialog(null, "Select a store to view products",
                                "View Products", JOptionPane.QUESTION_MESSAGE, null,
                                storeNamesArray, storeNamesArray[0]);
                        if (selected == null) {
                            leave = true;
                            return;
                        }
                        if (selected != null) {
                            oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                    "getProductsStoreCust").put("storeName", selected)).toString());
                            oos.flush();
                            ArrayList<String> productNames = (ArrayList<String>) ois.readObject();
                            String[] productNamesArray = new String[productNames.size()];
                            for (String temp : productNames) {
                                productNamesArray[productNames.indexOf(temp)] = temp;
                            }
                            String selectedProduct = (String) JOptionPane.showInputDialog(null, "Select a product to view",
                                    "View Product", JOptionPane.QUESTION_MESSAGE, null,
                                    productNamesArray, productNamesArray[0]);
                            if (selectedProduct == null) {
                                leave = true;
                                return;
                            }
                            if (selectedProduct != null) {
                                oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                        "specificProductCust").put("storeName", selected).put("productName",
                                        selectedProduct)).toString());
                                oos.flush();
                                String productInfoTemp = (String) ois.readObject();
                                JSONObject productInfo = new JSONObject(productInfoTemp);
                                int productID = productInfo.getInt("productID");
                                String description = productInfo.getString("description");
                                String price = productInfo.getString("price");
                                int quantity = productInfo.getInt("quantity");
                                String product = "Product Name: " + selectedProduct + "\n" +
                                        "Product Descriptions: " + description + "\n" +
                                        "Price: " + price + "\n" +
                                        "Quantity: " + quantity;
                                JOptionPane.showMessageDialog(null, product);
                                int addToCart = 0;
                                do {
                                    String[] addToCartOptions = {"1. Add to cart?", "2. Buy Now!", "3. Go back"};
                                    String addToCartString;
                                    addToCartString = (String) JOptionPane.showInputDialog(null,
                                            "What would you like to do?", "Customer Account",
                                            JOptionPane.QUESTION_MESSAGE, null, addToCartOptions, addToCartOptions[0]);
                                    if (addToCartString == null) {
                                        leave = true;
                                        return;
                                    }
                                    addToCart = Integer.parseInt(addToCartString.substring(0, 1));
                                } while (addToCart < 1 || addToCart > 3);
                                if (addToCart == 1) { // add to Cart
                                    int quantityToCart;
                                    String quantityToCartString = JOptionPane.showInputDialog(null,
                                            "How many would you like to buy?");
                                    if (quantityToCartString == null) {
                                        leave = true;
                                        return;
                                    }
                                    quantityToCart = Integer.parseInt(quantityToCartString);
                                    if (quantityToCart > quantity) {
                                        JOptionPane.showMessageDialog(null, "Not enough in stock!",
                                                "Not enough in stock", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                                "addToCartCust").put("storeName", selected).put("productID",
                                                productID).put("quantity", quantityToCart).put("customerID", userID)).toString());
                                        oos.flush();
                                        Boolean addToCartResponse = (Boolean) ois.readObject();
                                        if (addToCartResponse) {
                                            JOptionPane.showMessageDialog(null, "Added to cart!",
                                                    "Added to cart", JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Failed to add to cart!",
                                                    "Failed to add to cart", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                } else if (addToCart == 2) { // Purchase Now
                                    int quantityToCart;
                                    String quantityToCartString = JOptionPane.showInputDialog(null,
                                            "How many would you like to buy?");
                                    if (quantityToCartString == null) {
                                        leave = true;
                                        return;
                                    }
                                    quantityToCart = Integer.parseInt(quantityToCartString);
                                    if (quantityToCart > quantity) {
                                        JOptionPane.showMessageDialog(null, "Not enough in stock!",
                                                "Not enough in stock", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                                "buyCust").put("storeName", selected).put("productID",
                                                productID).put("quantity", quantityToCart).put("customerID", userID)).toString());
                                        oos.flush();
                                        String purchaseNowResponse = (String) ois.readObject();
                                        if (purchaseNowResponse.equals("true")) {
                                            JOptionPane.showMessageDialog(null, "Purchased!",
                                                    "Purchased", JOptionPane.INFORMATION_MESSAGE);
                                        } else if (purchaseNowResponse.equals("falseMQ")) {
                                            int maxQuantity = (Integer) ois.readObject();
                                            JOptionPane.showMessageDialog(null, "Failed to purchase! Above max quantity " +
                                                            "of " + maxQuantity + "!",
                                                    "Failed to purchase", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Failed to purchase!",
                                                    "Failed to purchase", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }

                                } else if (addToCart == 3) { // Go back

                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Product not found!",
                                        "Product not Found", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Store not found!",
                                    "Store not Found", JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (response1 == 2) {
                        boolean leave1 = false;
                        String productSearch = JOptionPane.showInputDialog(null,
                                "Enter a product name to search", "Customer Account", JOptionPane.QUESTION_MESSAGE);
                        if (productSearch == null) {
                            leave = true;
                            return;
                        }
                        oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                "searchProduct").put("productName", productSearch)).toString());
                        oos.flush();
                        leave1 = (Boolean) ois.readObject();
                        if (leave1) {
                            ArrayList<String> psNames = (ArrayList<String>) ois.readObject();
                            String[] psNamesArray = new String[psNames.size()];
                            for (int i = 0; i < psNames.size(); i++) {
                                psNamesArray[i] = psNames.get(i);
                            }
                            String selectedProduct = (String) JOptionPane.showInputDialog(null,
                                    "Select a product", "Search Product", JOptionPane.QUESTION_MESSAGE, null,
                                    psNamesArray, psNamesArray[0]);
                            if (selectedProduct == null) {
                                leave = true;
                                return;
                            }
                            if (selectedProduct != null) {
                                oos.writeObject((new JSONObject().put("userKey", "Customer").put(
                                        "actionKey", "getStorefromProduct").put("productName", selectedProduct)).toString());
                                oos.flush();
                                String selected = (String) ois.readObject();
                                oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                        "specificProductCust").put("storeName", selected).put("productName",
                                        selectedProduct)).toString());
                                oos.flush();
                                String productInfoTemp = (String) ois.readObject();
                                JSONObject productInfo = new JSONObject(productInfoTemp);
                                int productID = productInfo.getInt("productID");
                                String description = productInfo.getString("description");
                                String price = productInfo.getString("price");
                                int quantity = productInfo.getInt("quantity");
                                String product = "Product Name: " + selectedProduct + "\n" +
                                        "Product Descriptions: " + description + "\n" +
                                        "Price: " + price + "\n" +
                                        "Quantity: " + quantity;
                                JOptionPane.showMessageDialog(null, product);
                                int addToCart = 0;
                                do {
                                    String[] addToCartOptions = {"1. Add to cart?", "2. Buy Now!", "3. Go back"};
                                    String addToCartString;
                                    addToCartString = (String) JOptionPane.showInputDialog(null,
                                            "What would you like to do?", "Customer Account",
                                            JOptionPane.QUESTION_MESSAGE, null, addToCartOptions, addToCartOptions[0]);
                                    if (addToCartString == null) {
                                        leave = true;
                                        return;
                                    }
                                    addToCart = Integer.parseInt(addToCartString.substring(0, 1));
                                } while (addToCart < 1 || addToCart > 3);
                                if (addToCart == 1) { // add to Cart
                                    int quantityToCart;
                                    String quantityToCartString = JOptionPane.showInputDialog(null,
                                            "How many would you like to buy?");
                                    if (quantityToCartString == null) {
                                        leave = true;
                                        return;
                                    }
                                    quantityToCart = Integer.parseInt(quantityToCartString);
                                    System.out.println("Test 1"); //TODO:Remove
                                    if (quantityToCart > quantity) {
                                        JOptionPane.showMessageDialog(null, "Not enough in stock!",
                                                "Not enough in stock", JOptionPane.ERROR_MESSAGE);
                                        System.out.println("Test too many"); //TODO:Remove
                                    } else {
                                        oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                                "addToCartCust").put("storeName", selected).put("productID",
                                                productID).put("quantity", quantityToCart).put("customerID", userID)).toString());
                                        oos.flush();
                                        Boolean addToCartResponse = (Boolean) ois.readObject();
                                        if (addToCartResponse) {
                                            JOptionPane.showMessageDialog(null, "Added to cart!",
                                                    "Added to cart", JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Failed to add to cart!",
                                                    "Failed to add to cart", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                } else if (addToCart == 2) { // Purchase Now
                                    int quantityToCart;
                                    String quantityToCartString = JOptionPane.showInputDialog(null,
                                            "How many would you like to buy?");
                                    if (quantityToCartString == null) {
                                        leave = true;
                                        return;
                                    }
                                    quantityToCart = Integer.parseInt(quantityToCartString);
                                    if (quantityToCart > quantity) {
                                        JOptionPane.showMessageDialog(null, "Not enough in stock!",
                                                "Not enough in stock", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        oos.writeObject((new JSONObject().put("userKey", "Customer").put("actionKey",
                                                "buyCust").put("storeName", selected).put("productID",
                                                productID).put("quantity", quantityToCart).put("customerID", userID)).toString());
                                        oos.flush();
                                        String purchaseNowResponse = (String) ois.readObject();
                                        if (purchaseNowResponse.equals("true")) {
                                            JOptionPane.showMessageDialog(null, "Purchased!",
                                                    "Purchased", JOptionPane.INFORMATION_MESSAGE);
                                        } else if (purchaseNowResponse.equals("falseMQ")) {
                                            int maxQuantity = (Integer) ois.readObject();
                                            JOptionPane.showMessageDialog(null,
                                                    "Failed to purchase! Above max quantity " + "of "
                                                            + maxQuantity + "!",
                                                    "Failed to purchase", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Failed to purchase!",
                                                    "Failed to purchase", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }

                                } else if (addToCart == 3) { // Go back

                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No results",
                                    "Customer Account", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                } else if (response == 3) { //view purchase history
                    System.out.println("Before calling purchase history");
                    oos.writeObject(new JSONObject().put("userKey", "Customer").put("actionKey",
                            "viewPurchaseHistory").put("customerID", userID).toString());
                    oos.flush();
                    System.out.println("After calling purchase history");
                    ArrayList<String[]> purchaseHistory = (ArrayList<String[]>) ois.readObject();
                    System.out.println("After reading purchase history");
                    if (purchaseHistory.size() == 0) {
                        JOptionPane.showMessageDialog(null, "No purchases",
                                "Customer Account", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        String[] purchaseHistoryArray = new String[purchaseHistory.size()];
                        for (int i = 0; i < purchaseHistory.size(); i++) {
                            String[] purchase = purchaseHistory.get(i);
                            String productName = purchase[0];
                            int quantity = Integer.parseInt(purchase[1]);
                            String price = purchase[2];
                            String total = purchase[3];
                            String ret = String.format("Product Name: " + productName + "\n" + "Quantity bought: "
                                    + quantity + "\n" + "Price: $" + price + "\n" + "Total: $" + total);
                            purchaseHistoryArray[i] = ret;
                        }
                        for (int i = 0; i < purchaseHistoryArray.length; i++) {
                            JOptionPane.showMessageDialog(null, purchaseHistoryArray[i],
                                    "Customer Account", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                } else if (response == 4) { //view shopping cart
                    System.out.println("Test 1"); //TODO:Remove
                    oos.writeObject(new JSONObject().put("userKey", "Customer").put("actionKey",
                            "testCart").put("customerID", userID).toString());
                    oos.flush();
                    ArrayList<String[]> customerCart = (ArrayList<String[]>) ois.readObject();
                    if (customerCart.size() > 0) {
                        String[] customerCartArray = new String[customerCart.size()];
                        for (int i = 0; i < customerCart.size(); i++) {
                            String[] inCart = customerCart.get(i);
                            String productName = inCart[0];
                            System.out.println(productName + "is in the cart");
                            String quantity = inCart[1];
                            String price = inCart[2];
                            String ret = String.format("Product Name: " + productName + "\n" + "Quantity: " + quantity +
                                    "\n" + "Price: $" + price);
                            customerCartArray[i] = ret;
                        }
                        for (int i = 0; i < customerCartArray.length; i++) {
                            JOptionPane.showMessageDialog(null, customerCartArray[i],
                                    "Customer Account", JOptionPane.PLAIN_MESSAGE);
                        }
                        String[] cartOptions = {"1. Checkout", "2. Remove Item", "3. Go back"};
                        int cartInt = 0;
                        do {
                            String cartString = ((String) JOptionPane.showInputDialog(null,
                                    "What would you like to do?", "Customer Account",
                                    JOptionPane.QUESTION_MESSAGE, null, cartOptions, cartOptions[0]));
                            if (cartString == null) {
                                leave = true;
                                return;
                            }
                            cartInt = Integer.parseInt(cartString.substring(0, 1));
                        } while (cartInt < 1 || cartInt > 3);
                        if (cartInt == 1) { // checkout the cart
                            oos.writeObject(new JSONObject().put("userKey", "Customer").put("actionKey",
                                    "buyCart").put("customerID", userID).toString());
                            oos.flush();
                            String success = (String) ois.readObject();
                            if (success.equals("true")) {
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to Purchase all!",
                                        "Failed to purchase ALL items, look at max order quantities and quanitity of " +
                                                "products", JOptionPane.ERROR_MESSAGE);
                            }
                            /*oos.writeObject(new JSONObject().put("userKey", "Customer").put("actionKey",
                                    "getCheckoutCart").put("customerID", userID).toString());
                            oos.flush();
                            ArrayList<String[]> products = (ArrayList<String[]>) ois.readObject();
                            for(int i = 0; i < products.size(); i++) {
                                String storeReturn = products.get(i)[0];
                                int productIDReturn = Integer.parseInt(products.get(i)[1]);
                                int quantityReturn = Integer.parseInt(products.get(i)[2]);
                                String productName = products.get(i)[3];
                                JSONObject jsonRet = new JSONObject().put("userKey", "Customer").put("actionKey",
                                        "buyCust").put("storeName", storeReturn).put("productID",
                                        productIDReturn).put("quantity", quantityReturn).put("customerID",
                                        userID);
                                oos.writeObject(jsonRet.toString());
                                JSONObject removeItem = new JSONObject().put("userKey", "Customer").put("actionKey",
                                        "removeItem").put("customerID", userID).put("productName", productName).put(
                                                "quantityToRemove", 0);
                                oos.writeObject(removeItem.toString());
                                oos.flush();
                            }*/
                        } else if (cartInt == 2) { //Remove Item/Change item quantity
                            oos.writeObject(new JSONObject().put("userKey", "Customer").put("actionKey",
                                    "testCart").put("customerID", userID).toString());
                            oos.flush();
                            ArrayList<String[]> cartNames = (ArrayList<String[]>) ois.readObject();
                            String[] cartNamesArray = new String[cartNames.size()];
                            for (int i = 0; i < cartNames.size(); i++) {
                                cartNamesArray[i] = cartNames.get(i)[0];
                            }
                            String selected = (String) JOptionPane.showInputDialog(null,
                                    "Which item would you like to remove?", "Customer Account",
                                    JOptionPane.QUESTION_MESSAGE, null, cartNamesArray, cartNamesArray[0]);
                            if (selected == null) {
                                leave = true;
                                return;
                            }
                            int currentQuantity = 0;
                            if (selected != null) {
                                for (String[] cartName : cartNames) {
                                    if (cartName[0].equals(selected)) {
                                        currentQuantity = Integer.parseInt(cartName[1]);
                                    }
                                }
                            }
                            String quantityToRemoveString = JOptionPane.showInputDialog(null,
                                    "How many would you like to have now? (Enter 0 to complete remove item) " +
                                            "Current quantity: " + currentQuantity);
                            if (quantityToRemoveString == null) {
                                leave = true;
                                return;
                            }
                            int quantityToRemove = Integer.parseInt(quantityToRemoveString);
                            JSONObject removeItem = new JSONObject().put("userKey", "Customer").put("actionKey",
                                    "removeItem").put("customerID", userID).put("productName", selected).put("quantityToRemove",
                                    quantityToRemove);
                            oos.writeObject(removeItem.toString());
                            oos.flush();
                            Boolean removeItemResponse = (Boolean) ois.readObject();
                            if (removeItemResponse) {
                                JOptionPane.showMessageDialog(null, "Removed/Changed!",
                                        "Customer Cart", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to Change!",
                                        "Failed to remove", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No items in cart",
                                "Customer Account", JOptionPane.PLAIN_MESSAGE);
                    }
                } else if (response == 5) { // export purchase history csv
                    String fileName = JOptionPane.showInputDialog(null,
                            "What is the name of the file you would like to export into " +
                                    "(No  + filename extension (.csv))?",
                            "File Name", JOptionPane.QUESTION_MESSAGE);
                    if (fileName == null) {
                        leave = true;
                        return;
                    }
                    File file = new File("userFiles/" + fileName + ".csv");
                    file.createNewFile();
                    oos.writeObject(new JSONObject().put("userKey", "Customer").put("actionKey",
                            "viewPurchaseHistory").put("customerID", userID).toString());
                    oos.flush();
                    ArrayList<String[]> purchaseHistory = (ArrayList<String[]>) ois.readObject();
                    if (purchaseHistory.size() == 0) {
                        JOptionPane.showMessageDialog(null, "No purchases",
                                "Customer Account", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        ArrayList<String> purchaseHistString = new ArrayList<>();
                        purchaseHistString.add("Product Name, Quantity bought, Price, Total");
                        for (int i = 1; i < purchaseHistory.size() + 1; i++) {
                            String[] purchase = purchaseHistory.get(i - 1);
                            String productName = purchase[0];
                            int quantity = Integer.parseInt(purchase[1]);
                            String price = purchase[2];
                            String total = purchase[3];
                            String ret = productName + ", " + quantity + ", " + price + ", " + total;
                            purchaseHistString.add(ret);
                        }
                        try {
                            CSVWriter writer = new CSVWriter(new FileWriter("userFiles/" + fileName + ".csv"));
                            for (String s : purchaseHistString) {
                                writer.writeNext(s.split(","));
                            }
                            writer.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            FileWriter csvWriter = new FileWriter(fileName);
                            for (String x : purchaseHistString) {
                                csvWriter.append(x);
                                csvWriter.append("\n");
                            }
                            csvWriter.flush();
                            csvWriter.close();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Failed to export",
                                    "Customer Account", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (response == 6) { // log out
                    JOptionPane.showMessageDialog(null, "Thank you and Have a good day",
                            "Farewell", JOptionPane.INFORMATION_MESSAGE);
                    leave = true;
                    return;
                }
            } while (!leave);
        }

        oos.flush();
        oos.close();
        ois.close();
        socket.close();


    }
}


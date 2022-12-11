# Test Cases - Project 5 

## Log In and Sign Up Test Cases

Test 1: User Sign Up
Steps: 
  1. User launches application 
  2. User selects "Sign Up" option 
  3. User enters email via the keyboard 
  4. User enters password via the keyboard 
  5. User selects account type. (Seller/Customer)

Expected Results: Application creates user account and loads their homepage menu automatically. 

Test Status Passed 

Test 2: Existing User Log In 
Steps: 
  1. User launches application 
  2. User selects "Log In" option 
  3. User enters email via the keyboard
  4. User enters password via the keyboard 

Expected Results: Application verifies user's email and password and loads their homepage automatically. 

Test Status: Passed 

## Seller Account Test Cases 

Test 3: Seller creating a store
Steps:
1. User log into seller account
2. User selects option 2 (Make a New Store)
3. User enters their desired store name
4. User selects option 8 (Log out)

Expected Results: Seller should have a new store added into their account when selecting option 1 (Edit my Stores) 

Test Status: Passed 

Test 4: Seller adding a product
Steps: 
1. User log into seller account 
2. user selection option 1 (Edit my Store)
3. User selects the store created in test 3
4. user selects option 2 (Add a Product)
5. user enters the name, description, price, quantity, and max quantity of a product they would like to add

Expected Results: JOptionPanel should display "Product Added Successfully"

Test Status: Pass

Test 5: Editing a product
Steps:
1. User selects option 1 (Edit my Stores)
2. User selects the store created in test 3
3. user selects option 1 (Edit Products)
4. user selects the product created in test 4
5. user selects option 1 (Edit Product)
6. user selects the option they would like to change (Ex: change name would be selection option 1 and inputting the 
follow name they would like to change to(laptop --> Apple Laptop))

Expected Results: The program should return back to the main menu, indicating that the change has been made successfully

Test Status: Pass

Test 6: Deleting a product
Steps: 
1. User selects option 1 (Edit my Stores)
2. User selects the store created in test 3
3. User selects option 3 (Delete a Product)
4. User selects the product added and edited in test 4 and 5. 

Expected Results: The program should return back to the main menu, indicating that the product has been deleted.

Test Status: Pass

Test 7: Seller deleting a store
Steps:
1. User log into the seller account
2. User selects option 3 (Delete a store)
3. User selects the store created in test 3
4. User selects option 8 (Log out)

Expected Results: JOptionPanel should show "Store deleted Successfully"

Test Status: Passed 

Test 8: Import CSV for Stores
Steps: 
1. User selects option 5 (Import CSV for Stores)
2. User types the file name of the CSV (Example: userImport)

Expected Results: JOptionPanel should show "File Successfully imported!"

Test Status: Pass

Test 9: Export CSV for stores
Steps: 
1. User selects option 6 (Export CSV for Stores)
2. User types the filename that the user would like to save (Example: userOutput)

Expected Results: The program should return back to the main menu, indicating that the CV has been exported into userFiles.

Test Status: Pass

## Customer Account Test Cases

Test 10: Adding products using View Product Listing
Steps:
1. User selects option 1 (View Product listings)
2. User selects a store (Example: Taco Bell)
3. User selects a product (Example: Taco)
4. user sees product details
5. user selects option 1 (Add to cart?)
6. user inputs how many to buy (Example: 6)

Expected Results: JOptionPanel should show "Added to cart!"

Test Status: Pass

Test 11: Adding Products using Search... (By Stores)
Steps:
1. User selects option 2 (Search...)
2. User selects option 1 (Stores)
3. User selects a store (Example: NBA)
4. User selects a product (Example : Bulls)
5. User sees product details
6. User selects Option 1 (Add to cart?)
7. User inputs how many to buy (Example: 20)

Expected Results: JOptionPanel should show "Added to cart!"

Test Status: Pass

Test 12: Removing Items in Shopping Cart
Steps:
1. user selects option 4 (View your shopping cart)
2. User should be able to see each JOptionPanel of all the products they have in cart
3. User selects option 2 (Remove Item)
4. user selects which item to remove (Example: Taco)
5. user inputs how many items they would like to change (Example: 3)

Expected Results: JOptionPanel should show "Removed/Changed"

Test Status: Pass

Test 13: Checkout Items in shopping Cart
Steps:
1. user selects option 4 (view your shopping cart)
2. User should be able to see each JOptionPanel of all the products they have in cart
3. User selects option 1 (Checkout)

Expected Results: The program should return back to the main menu, indicating that the checkout is completed.

Test Status: Pass

Test 14: Buying products using Buy Now
Steps:
1. user selects option 2 (Search...)
2. user selects option 2 (Products)
3. user inputs a product name (Example: fries)
4. user selects product related to keyword (Example: fries)
5. user sees product details
6. user selects option 2 (Buy Now!)
7. user inputs how many to buy? (Example: 20)

Expected Results: JOptionPanel should show "Purchased!"

Test Status: Pass

Test 15: Viewing Purchase History
Steps:
1. user selects option 3 (View your Purchase history)

Expected Results: user should see the JOptionPanels of products they ordered in chronological order

Test Status: Pass

Test 16: Exporting Purchase History
Steps:
1. user selects option 5 (Export Purchase History CSV)
2. user inputs the filename they would like to export the file as (Example: purchaseHistory)

Expected Results: The program should return back to the main menu, indicating that the file is exported into the userFiles folder.

Test Status: Pass

## Seller sales test cases
Test 17: View Sales
Steps: 
1. user selects option 4 (View Sales)
2. user selects a particular store (Example: Taco Bell)

Expected Results: JOptionPanels should show the list of transactions of that store

Test Status: Pass

Test 18: Export CSV for Sales
Steps: 
1. user selects option 7 (Export CSV for Sales)
2. user inputs the filename they would like to export the file as (Example: TacoBellSales)
3. user selects which store to export sales (Example: Taco Bell)

Expected Results: The program should return back to the main menu, indicating that the export is successful.

Test Status: Pass

Test 19: Concurrency (Multi-user)
Steps:
1. Run test 18 and test 4 at the same time

Expected Results: The program should not run into any errors or should not end during this test. 

Test Status: Pass

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores.java
 * This class description is mentioned in the README.md.
 *
 * @author Parth Shah, Pranav Konda, Aarush Sachdeva
 * @version December 10th, 2022
 */
public class Stores implements Serializable {

    int storeID;
    String name;
    int sellerID;
    ArrayList<Products> storeProducts = new ArrayList<Products>();
    Sales sales = new Sales(storeID, sellerID, 0);

    public Stores(int storeID, String name, int sellerID) {
        this.storeID = storeID;
        this.name = name;
        this.sellerID = sellerID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }


    public int getStoreID() {
        return storeID;
    }

    public String getName() {
        return name;
    }

    public Sales getSales() {
        return sales;
    }

    public int getSellerID() {
        return sellerID;
    }

    public ArrayList<Products> getStoreProducts() {
        return storeProducts;
    }

    public void setStoreProducts(ArrayList<Products> storeProducts) {
        this.storeProducts = storeProducts;
    }

    public void addStoreProduct(int productID, String name, String description, int quantity, double price, int orderLimit) {
        storeProducts.add(new Products(productID, name, description, quantity, price, orderLimit));
    }

    public void removeStoreProduct(Products product) {
        storeProducts.remove(product);
    }

    public void addSale(int productID, int quantity, double price, int customerId, String productName) {
        sales.addProductSales(productID, quantity, price, customerId, productName);
        Products changeQ = selectProductID(productID);
        changeQ.setQuantity(changeQ.getQuantity() - quantity);
    }


    public boolean isEmpty() {
        if (storeProducts == null || storeProducts.size() == 0) {
            return true;
        }
        return false;
    }

    public Products selectProduct(String productSelected) {
        Products selected = null;
        for (Products p : storeProducts) {
            if (p.getName().equalsIgnoreCase(productSelected)) {
                selected = p;
                break;
            }
        }
        return selected;
    }

    public Products selectProductID(int productIDselected) {
        Products selected = null;
        for (Products p : storeProducts) {
            if (p.getProductID() == productIDselected) {
                selected = p;
                break;
            }
        }
        return selected;
    }


}

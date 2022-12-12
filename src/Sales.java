import java.io.Serializable;
import java.util.ArrayList;

/**
 * Sales.java
 * This class description is mentioned in the README.md.
 *
 * @author Parth Shah, Pranav Konda, Aarush Sachdeva
 * @version December 10th, 2022
 */
public class Sales implements Serializable {

    ArrayList<ProductSales> productSales = new ArrayList<ProductSales>();
    int storeID;
    int sellerID;
    double totalSales;

    public Sales(int storeID, int sellerID, int totalSales) {
        this.storeID = storeID;
        this.sellerID = sellerID;
        this.totalSales = totalSales;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public ArrayList<ProductSales> getProductSales() {
        return productSales;
    }

    public void setProductSales(ArrayList<ProductSales> productSales) {
        this.productSales = productSales;
    }

    public int getStoreID() {
        return storeID;
    }

    public void addProductSales(int productID, int quantity, double price, int customerId, String productName) {
        productSales.add(new ProductSales(productID, quantity, price, customerId, productName));
        totalSales = totalSales + (quantity * price);
    }


    public int getSellerID() {
        return sellerID;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }
}


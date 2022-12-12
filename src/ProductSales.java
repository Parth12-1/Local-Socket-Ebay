import java.io.Serializable;

/**
 * ProductSales.java
 * This class description is mentioned in the README.md.
 *
 * @author Parth Shah, Pranav Konda, Aarush Sachdeva
 * @version December 10th, 2022
 */
public class ProductSales implements Serializable {
    int productID;
    int quantity;
    double price;
    int customerId;
    String productName;

    public ProductSales(int productID, int quantity, double price, int customerId, String productName) {
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.customerId = customerId;
        this.productName = productName;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public int getProductID() {
        return productID;
    }


    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}

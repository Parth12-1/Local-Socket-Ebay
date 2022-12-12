import java.io.Serializable;

/**
 * Products.java
 * This class description is mentioned in the README.md.
 *
 * @author Parth Shah, Pranav Konda, Aarush Sachdeva
 * @version December 10th, 2022
 */
public class Products implements Serializable {
    int productID;
    String name;
    String description;
    double price;
    int quantity;
    int orderLimit;

    public Products(int productID, String name, String description, int quantity, double price, int orderLimit) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.orderLimit = orderLimit;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOrderLimit() {
        return orderLimit;
    }

    public void editProduct(String name1, String description1, int quantity1, double price1, int orderLimit1) {
        this.name = name1;
        this.description = description1;
        this.price = price1;
        this.quantity = quantity1;
        this.orderLimit = orderLimit1;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}

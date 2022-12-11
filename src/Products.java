import java.io.Serializable;

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

    public void editProduct(String name, String description, int quantity, double price, int orderLimit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.orderLimit = orderLimit;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}

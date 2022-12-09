import java.io.Serializable;

public class Cart implements Serializable {
    int userID;
    int quantity;
    int productID;

    public Cart(int userID, int quantity, int productID) {
        this.userID = userID;
        this.quantity = quantity;
        this.productID = productID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getUserID() {
        return userID;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

import java.io.Serializable;

/**
 * Cart.java
 * This class description is mentioned in the README.md.
 *
 * @author Parth Shah, Pranav Konda, Aarush Sachdeva
 * @version December 10th, 2022
 */
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

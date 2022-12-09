import java.io.Serializable;

public class PurchaseHist implements Serializable {
    int userId;
    int sellerId;
    int storeId;
    int productId;
    int quantity;
    double price;

    public PurchaseHist(int userId, int sellerId, int storeId, int productId, int quantity, double price) {
        this.userId = userId;
        this.sellerId = sellerId;
        this.storeId = storeId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}

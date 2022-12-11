import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    int custId;
    ArrayList<Cart> carts = new ArrayList<>();
    ArrayList<PurchaseHist> purchaseHists = new ArrayList<>();

    public Customer(int custId) {
        this.custId = custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public void setCarts(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    public void setPurchaseHists(ArrayList<PurchaseHist> purchaseHists) {
        this.purchaseHists = purchaseHists;
    }

    public int getCustId() {
        return custId;
    }

    public void addToCart(int quantity, int productID) {
        boolean found = false;
        for (Cart cart : carts) {
            if (cart.getProductID() == productID) {
                cart.setQuantity(cart.getQuantity() + quantity);
                found = true;
                System.out.println("DUPLICATE");
            }
        }
        if (!found) {
            carts.add(new Cart(custId, quantity, productID));
        }
    }

    public ArrayList<PurchaseHist> getPurchaseHists() {
        return purchaseHists;
    }

    public ArrayList<Cart> getCarts() {
        return carts;
    }

    public void addPurchase(int sellerId, int storeId, int productId, int quantity, double price) {
        purchaseHists.add(new PurchaseHist(custId, sellerId, storeId, productId, quantity, price));
    }

    public void removeCart(Cart cart) {
        carts.remove(cart);
    }
}

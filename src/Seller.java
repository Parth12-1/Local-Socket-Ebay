import java.io.Serializable;
import java.util.ArrayList;

public class Seller implements Serializable {

    int sellerId;
    ArrayList<Stores> stores = new ArrayList<Stores>();


    public Seller(int sellerId) {
        this.sellerId = sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public ArrayList<Stores> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Stores> stores) {
        this.stores = stores;
    }

    public void addStore(int storeId, String storeName) {
        stores.add(new Stores(storeId, storeName, sellerId));
    }

    public void removeStore(Stores store) { //TODO: Remove the sales too
        stores.remove(store);
    }

    public boolean isEmpty() {
        if (stores == null || stores.size() == 0) {
            return true;
        }
        return false;
    }

    public Stores selectStore(String storeSelected) {
        Stores selected = null;
        for (Stores s : stores) {
            if (s.getName().equals(storeSelected)) {
                selected = s;
                break;
            }
        }
        return selected;
    }

}

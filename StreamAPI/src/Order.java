public class Order {
    private final int id;
    private final String sku;
    private final int amount;
    private final int totalPrice;

    public Order(int id, String sku, int amount, int totalPrice) {
        this.id = id;
        this.sku = sku;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public int getAmount() {
        return amount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}

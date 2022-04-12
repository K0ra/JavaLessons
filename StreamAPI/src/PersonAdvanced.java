import java.util.ArrayList;
import java.util.List;

public class PersonAdvanced {
    private final int id;
    private final String name;
    private final int age;
    private final List<Order> orders;

    public PersonAdvanced(int id, String name, int age, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.orders = new ArrayList<>(orders);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
